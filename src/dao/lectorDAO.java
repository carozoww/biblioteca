package dao;

import models.Lector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class lectorDAO {
    private Connection conn;

    public lectorDAO(Connection conn) {
        this.conn = conn;
    }

    public void crearLector(Lector l) throws SQLException {
        String sql = "INSERT INTO lector (nombre, direccion, telefono, correo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getNombre());
            ps.setString(2, l.getDireccion());
            ps.setString(3, l.getTelefono());
            ps.setString(4, l.getCorreo());
            ps.executeUpdate();
        }
    }

    public void editarLector(Lector lector) throws SQLException {
        String sql = "UPDATE lector SET nombre = ?, direccion = ?, telefono = ?, correo = ? WHERE id_lector = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lector.getNombre());
            ps.setString(2, lector.getDireccion());
            ps.setString(3, lector.getTelefono());
            ps.setString(4, lector.getCorreo());
            ps.setInt(5, lector.getIdLector());
            ps.executeUpdate();
        }
    }

    public List<Lector> listarLectores() throws SQLException {
        List<Lector> lectores = new ArrayList<>();
        String sql = "SELECT * FROM lector";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Lector l = new Lector(
                    rs.getInt("id_lector"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
                lectores.add(l);
            }
        }
        return lectores;
    }

    public void eliminarLector(int idLector) throws SQLException {
        String sql = "DELETE FROM lector WHERE id_lector = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLector);
            ps.executeUpdate();
        }
    }
}
