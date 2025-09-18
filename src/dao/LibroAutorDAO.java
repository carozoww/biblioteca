package dao;

import basedatos.conexion;
import models.Autor;
import models.Libro;
import models.LibroAutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroAutorDAO {

    public void asignarAutorALibro(int id_autor, int id_libro) throws SQLException {
        String query = "INSERT INTO libro_autor(id_libro,id_autor) VALUES(?,?)";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, id_libro);
            ps.setInt(2, id_autor);
            ps.executeUpdate();
            System.out.println("Autor de libro asignado correctamente!");
        }
    }

    public List<LibroAutor> listarAutoresDeLibros() throws SQLException {
        List<LibroAutor> libroAutores = new ArrayList<>();
        String query = "SELECT l.id_libro, titulo, fecha_publicacion, isbn, id_editorial, " +
                "a.id_autor, a.nombre, a.apellido " +
                "FROM libro l " +
                "JOIN libro_autor la ON l.id_libro = la.id_libro " +
                "JOIN autor a ON la.id_autor = a.id_autor";
        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                libroAutores.add(new LibroAutor(
                        new Libro(
                                rs.getInt("id_libro"),
                                rs.getString("titulo"),
                                rs.getString("isbn"),
                                rs.getDate("fecha_publicacion"),
                                rs.getInt("id_editorial"),
                                rs.getString("nombre")
                        ),
                        new Autor(
                                rs.getInt("id_autor"),
                                rs.getString("nombre"),
                                rs.getString("apellido")
                        )
                ));
            }
        }
        return libroAutores;
    }

    public List<Autor> listarAutoresDeUnLibro(int id_libro) throws SQLException {
        List<Autor> autores = new ArrayList<>();
        String query = "SELECT a.id_autor, a.nombre, a.apellido " +
                "FROM autor a " +
                "JOIN libro_autor la ON a.id_autor = la.id_autor " +
                "WHERE la.id_libro = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, id_libro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    autores.add(new Autor(
                            rs.getInt("id_autor"),
                            rs.getString("nombre"),
                            rs.getString("apellido")
                    ));
                }
            }
        }
        return autores;
    }

    public void modificarAutorDeLibro(int id_libro, int id_autor_old, int id_autor_new) throws SQLException {
        String queryMod = "UPDATE libro_autor SET id_autor = ? WHERE id_libro = ? AND id_autor = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryMod)) {
            ps.setInt(1, id_autor_new);
            ps.setInt(2, id_libro);
            ps.setInt(3, id_autor_old);
            ps.executeUpdate();
            System.out.println("Autor de libro modificado correctamente");
        }
    }

    public void eliminarAutorDeLibro(int id_libro, int id_autor) throws SQLException {
        String queryDel = "DELETE FROM libro_autor WHERE id_libro = ? AND id_autor = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryDel)) {
            ps.setInt(1, id_libro);
            ps.setInt(2, id_autor);
            ps.executeUpdate();
            System.out.println("Autor de libro eliminado correctamente");
        }
    }
}
