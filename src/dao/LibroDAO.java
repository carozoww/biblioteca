package dao;

import basedatos.conexion;
import models.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public void crearLibro(String titulo, String isbn, Date fechaPublicacion, int idEditorial) throws SQLException {
        String consulta = "INSERT INTO libro (titulo, isbn, fecha_publicacion, id_editorial, ed_asignada) VALUES (?, ?, ?, ?, TRUE)";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setString(1, titulo);
        ps.setString(2, isbn);
        ps.setDate(3, fechaPublicacion);
        ps.setInt(4, idEditorial);
        ps.executeUpdate();
    }

    public void editarLibro(int idLibro, String titulo, String isbn, Date fechaPublicacion, int idEditorial) throws SQLException {
        String consulta = "UPDATE libro SET titulo = ?, isbn = ?, fecha_publicacion = ?, id_editorial = ? WHERE id_libro = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setString(1, titulo);
        ps.setString(2, isbn);
        ps.setDate(3, fechaPublicacion);
        ps.setInt(4, idEditorial);
        ps.setInt(5, idLibro);
        ps.executeUpdate();
    }

    public void eliminarLibro(int idLibro) throws SQLException {
        String consulta = "DELETE FROM libro WHERE id_libro = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setInt(1, idLibro);
        ps.executeUpdate();
    }

    public List<Libro> listarLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT l.id_libro, titulo, isbn, fecha_publicacion, l.id_editorial, ed_asignada, e.nombre " +
                "FROM libro l LEFT JOIN editorial e ON l.id_editorial = e.id_editorial";
        Statement st = conexion.getInstancia().getConnection().createStatement();
        ResultSet rs = st.executeQuery(consulta);

        while (rs.next()) {
            libros.add(new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getDate("fecha_publicacion"),
                    rs.getInt("id_editorial"),
                    rs.getString("nombre")
            ));
        }
        return libros;
    }

    public List<Libro> listarLibrosReservados() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT l.id_libro, titulo, isbn, fecha_publicacion, l.id_editorial, ed_asignada, e.nombre " +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial " +
                "JOIN prestamo p ON l.id_libro = p.id_libro WHERE estado = 'RESERVADO'";
        Statement st = conexion.getInstancia().getConnection().createStatement();
        ResultSet rs = st.executeQuery(consulta);

        while (rs.next()) {
            libros.add(new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getDate("fecha_publicacion"),
                    rs.getInt("id_editorial"),
                    rs.getString("nombre")
            ));
        }
        return libros;
    }

    public Libro buscarPorId(int idLibro) throws SQLException {
        String consulta = "SELECT l.id_libro, titulo, isbn, fecha_publicacion, l.id_editorial, ed_asignada, e.nombre " +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial WHERE id_libro = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setInt(1, idLibro);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getDate("fecha_publicacion"),
                    rs.getInt("id_editorial"),
                    rs.getString("nombre")
            );
        }
        return null;
    }

    public List<Libro> existeISBN(String isbn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT l.id_libro, titulo, isbn, fecha_publicacion, l.id_editorial, ed_asignada, e.nombre " +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial WHERE isbn = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setString(1, isbn);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            libros.add(new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getDate("fecha_publicacion"),
                    rs.getInt("id_editorial"),
                    rs.getString("nombre")
            ));
        }
        return libros;
    }

    public List<Libro> existeISBNporId(int id_libro, String isbn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT l.id_libro, titulo, isbn, fecha_publicacion, l.id_editorial, ed_asignada, e.nombre " +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial WHERE id_libro != ? AND isbn = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
        ps.setInt(1, id_libro);
        ps.setString(2, isbn);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            libros.add(new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn"),
                    rs.getDate("fecha_publicacion"),
                    rs.getInt("id_editorial"),
                    rs.getString("nombre")
            ));
        }
        return libros;
    }
}

