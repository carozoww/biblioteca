package dao;

import basedatos.conexion;
import models.Libro;
import models.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Lista paginada de reviews
    public List<Review> listarReviews(int offset, int limit) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.id_review, r.id_libro, r.id_lector, r.valoracion, r.resenia, r.fecha, " +
                "l.nombre AS nombreLector, b.titulo AS nombreLibro, " +
                "(SELECT COUNT(*) FROM review_like rl WHERE rl.id_review = r.id_review) AS likes " +
                "FROM review r " +
                "JOIN lector l ON r.id_lector = l.ID " +
                "JOIN libro b ON r.id_libro = b.id_libro " +
                "ORDER BY r.fecha DESC " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("id_review"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getInt("valoracion"),
                        rs.getString("resenia"),
                        rs.getTimestamp("fecha"),
                        rs.getString("nombreLector"),
                        rs.getString("nombreLibro")
                );
                review.setLikes(rs.getInt("likes"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }


    // Para tener una review por ID
    public Review obtenerReview(int idReview) {
        String query = "SELECT r.id_review, r.id_libro, r.id_lector, r.valoracion, r.resenia, r.fecha, " +
                "l.nombre AS nombreLector, b.titulo AS nombreLibro, " +
                "(SELECT COUNT(*) FROM review_like rl WHERE rl.id_review = r.id_review) AS likes " +
                "FROM review r " +
                "JOIN lector l ON r.id_lector = l.ID " +
                "JOIN libro b ON r.id_libro = b.id_libro " +
                "WHERE r.id_review = ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idReview);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Review review = new Review(
                        rs.getInt("id_review"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getInt("valoracion"),
                        rs.getString("resenia"),
                        rs.getTimestamp("fecha"),
                        rs.getString("nombreLector"),
                        rs.getString("nombreLibro")
                );
                review.setLikes(rs.getInt("likes"));
                return review;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    // Crear review
    public boolean crearReview(int idLector, int idLibro, int valoracion, String resenia) {
        if (valoracion < 1 || valoracion > 5) return false;
        String query = "INSERT INTO review (id_libro, id_lector, valoracion, resenia) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, idLibro);
            ps.setInt(2, idLector);
            ps.setInt(3, valoracion);
            ps.setString(4, resenia);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Review obtenerReviewPorLibroYLector(int idLector, int idLibro) {
        String query = "SELECT r.id_review, r.id_libro, r.id_lector, r.valoracion, r.resenia, r.fecha, " +
                "l.nombre AS nombreLector, b.titulo AS nombreLibro " +
                "FROM review r " +
                "JOIN lector l ON r.id_lector = l.ID " +
                "JOIN libro b ON r.id_libro = b.id_libro " +
                "WHERE r.id_lector = ? AND r.id_libro = ?";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, idLector);
            ps.setInt(2, idLibro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Review(
                        rs.getInt("id_review"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getInt("valoracion"),
                        rs.getString("resenia"),
                        rs.getTimestamp("fecha"),
                        rs.getString("nombreLector"),
                        rs.getString("nombreLibro")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean actualizarReview(int idReview, int valoracion, String resenia) {
        if (valoracion < 1 || valoracion > 5) return false;
        String query = "UPDATE review SET valoracion=?, resenia=? WHERE id_review=?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, valoracion);
            ps.setString(2, resenia);
            ps.setInt(3, idReview);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int darLike(int idReview, int idLector) {
        try {
            // Verifica si este lector ya dio like a esta review
            String checkQuery = "SELECT COUNT(*) AS existe FROM review_like WHERE id_review = ? AND id_lector = ?";
            PreparedStatement psCheck = conexion.getInstancia().getConnection().prepareStatement(checkQuery);
            psCheck.setInt(1, idReview);
            psCheck.setInt(2, idLector);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt("existe") > 0) {

                String deleteQuery = "DELETE FROM review_like WHERE id_review = ? AND id_lector = ?";
                PreparedStatement psDel = conexion.getInstancia().getConnection().prepareStatement(deleteQuery);
                psDel.setInt(1, idReview);
                psDel.setInt(2, idLector);
                psDel.executeUpdate();
            } else {

                String insertQuery = "INSERT INTO review_like (id_review, id_lector) VALUES (?, ?)";
                PreparedStatement psIns = conexion.getInstancia().getConnection().prepareStatement(insertQuery);
                psIns.setInt(1, idReview);
                psIns.setInt(2, idLector);
                psIns.executeUpdate();
            }

            // Contador de likes
            String countQuery = "SELECT COUNT(*) AS total FROM review_like WHERE id_review = ?";
            PreparedStatement psCount = conexion.getInstancia().getConnection().prepareStatement(countQuery);
            psCount.setInt(1, idReview);
            ResultSet rsCount = psCount.executeQuery();
            if (rsCount.next()) {
                return rsCount.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public Review existeReviewDeLibro(int id_libro){
        Review review = null;
        String query = "SELECT r.id_review, r.id_libro, r.id_lector, r.valoracion, r.resenia, r.fecha, \n" +
                "l.nombre AS nombreLector, b.titulo AS nombreLibro, \n" +
                "(SELECT COUNT(*) FROM review_like rl WHERE rl.id_review = r.id_review) AS likes \n" +
                "FROM review r JOIN lector l ON r.id_lector = l.ID \n" +
                "JOIN libro b ON r.id_libro = b.id_libro \n" +
                "WHERE r.id_libro = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, id_libro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                review = new Review(
                                rs.getInt("id_review"),
                                rs.getInt("id_libro"),
                                rs.getInt("id_lector"),
                                rs.getInt("valoracion"),
                                rs.getString("resenia"),
                                rs.getTimestamp("fecha"),
                                rs.getString("nombreLector"),
                                rs.getString("nombreLibro")

                );
                review.setLikes(rs.getInt("likes"));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return review;
    }

    public int obtenerNumReseniasPositivas(int id_lector){
        int num = 0;
        String query = "SELECT COUNT(*) FROM review where id_lector = ? and valoracion >= 3";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, id_lector);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                num = rs.getInt(1);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return num;
    }





}

