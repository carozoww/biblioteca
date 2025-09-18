package dao;

import basedatos.conexion;
import models.Autor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class autorDAO {

    public void crearAutor(String nombre, String apellido) throws SQLException {
        String query = "INSERT INTO autor(nombre,apellido) VALUES (?,?)";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.executeUpdate();
            System.out.println("Autor creado con éxito");
        }
    }

    public List<Autor> mostrarAutores() throws SQLException {
        String query = "SELECT * FROM autor";
        List<Autor> listaAutores = new ArrayList<>();

        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                listaAutores.add(
                        new Autor(
                                rs.getInt("id_autor"),
                                rs.getString("nombre"),
                                rs.getString("apellido")
                        ));
            }
        }
        return listaAutores;
    }

    public void modificarAutor(int idAutor, String nuevoNombre, String nuevoApellido) throws SQLException {
        String query = "UPDATE autor SET nombre = ?, apellido = ? WHERE id_autor = ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevoApellido);
            ps.setInt(3, idAutor);
            ps.executeUpdate();
            System.out.println("Autor modificado con éxito");
        }
    }

    public void eliminarAutor(int idAutor) throws SQLException {
        String query = "DELETE FROM autor WHERE id_autor = ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idAutor);
            ps.executeUpdate();
            System.out.println("Autor eliminado con éxito");
        }
    }
}
