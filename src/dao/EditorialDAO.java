package dao;

import basedatos.conexion;
import models.Editorial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAO {

    public void crearEditorial(String nombre) throws SQLException {
        String consulta = "INSERT INTO editorial (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
    }

    public void editarEditorial(int idEditorial, String nombre) throws SQLException {
        String consulta = "UPDATE editorial SET nombre = ? WHERE id_editorial = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setString(1, nombre);
            ps.setInt(2, idEditorial);
            ps.executeUpdate();
        }
    }

    public void eliminarEditorial(int idEditorial) throws SQLException {
        String consultaUpd = "UPDATE libro SET id_editorial = null WHERE id_editorial = ?";
        String consulta = "DELETE FROM editorial WHERE id_editorial = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consultaUpd);
             PreparedStatement ps1 = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idEditorial);
            ps.executeUpdate();

            ps1.setInt(1, idEditorial);
            ps1.executeUpdate();
        }
    }

    public List<Editorial> listarEditorial() throws SQLException {
        List<Editorial> editoriales = new ArrayList<>();
        String consulta = "SELECT * FROM editorial";
        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(consulta)) {
            while (rs.next()) {
                editoriales.add(new Editorial(rs.getInt("id_editorial"), rs.getString("nombre")));
            }
        }
        return editoriales;
    }

    public Editorial buscarEditorialPorId(int idEditorial) throws SQLException {
        String consulta = "SELECT * FROM editorial WHERE id_editorial = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idEditorial);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Editorial(rs.getInt("id_editorial"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    public void desasignarEditorial(int idEditorial) throws SQLException {
        String query = "UPDATE libro SET ed_asignada = FALSE WHERE id_editorial = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query)) {
            ps.setInt(1, idEditorial);
            ps.executeUpdate();
        }
    }
}
