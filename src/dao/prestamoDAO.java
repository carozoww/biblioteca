package dao;

import models.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class prestamoDAO {
    private Connection conn;

    public prestamoDAO(Connection conn) {
        this.conn = conn;
    }

    public void crearPrestamo(Prestamo p) throws SQLException {
        String sql = "INSERT INTO prestamo (id_lector, id_libro, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdLector());
            ps.setInt(2, p.getIdLibro());
            ps.setDate(3, Date.valueOf(p.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(p.getFechaDevolucion()));
            ps.setString(5, p.getEstado());
            ps.executeUpdate();
            System.out.println("✅ Préstamo creado con éxito");
        }
    }

    public List<Prestamo> listarPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamo";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo p = new Prestamo(
                    rs.getInt("id_prestamo"),
                    rs.getInt("id_lector"),
                    rs.getInt("id_libro"),
                    rs.getDate("fecha_prestamo").toLocalDate(),
                    rs.getDate("fecha_devolucion").toLocalDate(),
                    rs.getString("estado")
                );
                prestamos.add(p);
            }
        }
        return prestamos;
    }

    public void eliminarPrestamo(int idPrestamo) throws SQLException {
        String sql = "DELETE FROM prestamo WHERE id_prestamo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
            System.out.println("✅ Préstamo eliminado con éxito");
        }
    }
}
