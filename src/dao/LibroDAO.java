package dao;

import basedatos.conexion;
import models.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    public void crearLibro(String titulo, String isbn, Date fechaPublicacion, int idEditorial) {
        String consulta = "INSERT INTO libro (titulo, isbn, fecha_publicacion, id_editorial) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.executeUpdate();

            System.out.println("Libro creado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void editarLibro(int idLibro, String titulo, String isbn, Date fechaPublicacion, int idEditorial) {
        String consulta = "UPDATE libro SET titulo = ?, isbn = ?, fecha_publicacion = ?, id_editorial = ? WHERE id_libro = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.setInt(5, idLibro);

            ps.executeUpdate();

            System.out.println("Libro modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarLibro(int idLibro) {
        String consulta = "DELETE FROM libro WHERE id_libro = ?";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idLibro);
            ps.executeUpdate();

            System.out.println("Libro eliminado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT * FROM libro";

        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                libros.add(new Libro(rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libros;
    }

    public List<Libro> listarLibrosReservados(){
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT * FROM libro, prestamo WHERE estado = 'RESERVADO'";

        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                libros.add(new Libro(rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libros;
    }

    public Libro buscarPorId(int idLibro) {
        String consulta = "SELECT * FROM libro WHERE id_libro = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar libro: " + e.getMessage(), e);
        }
        return null;
    }



}
