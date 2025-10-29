package dao;

import basedatos.conexion;
import models.ReviewComentario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewComentarioDAO {

    public List<ReviewComentario> listarComentarios(int idReview) {
        List<ReviewComentario> comentarios = new ArrayList<>();
        String query = "SELECT rc.id_comentario, rc.id_review, rc.id_lector, rc.contenido, rc.fecha, " +
                "l.nombre AS nombreLector, l.imagen_url AS imagenLector " +
                "FROM review_comentario rc " +
                "JOIN lector l ON rc.id_lector = l.ID " +
                "WHERE rc.id_review = ? " +
                "ORDER BY rc.fecha DESC";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idReview);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReviewComentario c = new ReviewComentario();
                c.setIdComentario(rs.getInt("id_comentario"));
                c.setIdReview(rs.getInt("id_review"));
                c.setIdLector(rs.getInt("id_lector"));
                c.setContenido(rs.getString("contenido"));
                Timestamp ts = rs.getTimestamp("fecha");
                c.setFecha(ts != null ? ts : new Timestamp(System.currentTimeMillis()));
                c.setNombreLector(rs.getString("nombreLector"));
                c.setImagenLector(rs.getString("imagenLector"));
                System.out.println("Comentario: " + c.getContenido() + " - Fecha: " + c.getFecha());
                comentarios.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }


    // Crear comentario
    public boolean crearComentario(int idReview, int idLector, String contenido) {
        String query = "INSERT INTO review_comentario (id_review, id_lector, contenido) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idReview);
            ps.setInt(2, idLector);
            ps.setString(3, contenido);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Contar comentarios para un review
    public int contarComentarios(int idReview) {
        String query = "SELECT COUNT(*) AS total FROM review_comentario WHERE id_review = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idReview);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
