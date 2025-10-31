package dao;

import basedatos.conexion;
import models.Prestamo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public void crearPrestamo(int idLector, int idLibro, LocalDateTime fechaPrestamo, String estado) {
        String consulta = "INSERT INTO prestamo (id_libro, id_lector, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idLibro);
            ps.setInt(2, idLector);
            ps.setTimestamp(3, Timestamp.valueOf(fechaPrestamo));
            ps.setString(4, estado);

            ps.executeUpdate();
            System.out.println("Préstamo creado correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear préstamo: " + e.getMessage(), e);
        }
    }

    public void eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamo WHERE id_prestamo = ?";
        try(PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Préstamo eliminado correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prestamo buscarPorId(int idPrestamo) {
        String consulta = "SELECT * FROM prestamo WHERE id_prestamo = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idPrestamo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_lector"),
                        rs.getInt("id_libro"),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion") != null ? rs.getTimestamp("fecha_devolucion").toLocalDateTime() : null,
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar préstamo: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean prestamoActivoPorLibro(int idLibro) {
        String consulta = "SELECT COUNT(*) FROM prestamo WHERE id_libro = ? AND estado IN ('PENDIENTE','RESERVADO')";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar préstamo activo: " + e.getMessage(), e);
        }
    }

    public boolean prestamoActivoPorLector(int idLector) {
        String consulta = "SELECT COUNT(*) FROM prestamo WHERE id_lector = ? AND estado IN ('PENDIENTE','RESERVADO', 'ACEPTADO')";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idLector);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { // si hay resultado (siempre habra una fila con COUNT)
                return rs.getInt(1) > 0; // devuelve true si hay 1 o más prestamos activos
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar préstamo activo: " + e.getMessage(), e);
        }
    }


    public List<Prestamo> listarPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String consulta = "SELECT * FROM prestamo";
        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion") != null ? rs.getTimestamp("fecha_devolucion").toLocalDateTime() : null,
                        rs.getString("estado")
                );
                prestamos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos: " + e.getMessage(), e);
        }
        return prestamos;
    }

    public void finalizarPrestamo(int idPrestamo, LocalDateTime fechaDevolucion) {
        String sql = "UPDATE prestamo SET fecha_devolucion = ?, estado = 'DISPONIBLE' WHERE id_prestamo = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(fechaDevolucion));
            ps.setInt(2, idPrestamo);
            ps.executeUpdate();
            System.out.println("Préstamo finalizado correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al finalizar préstamo: " + e.getMessage(), e);
        }
    }

    public List<Prestamo> listarPrestamosPendientes() {
        List<Prestamo> prestamos = new ArrayList<>();
        String consulta = "SELECT * FROM prestamo WHERE estado = 'PENDIENTE'";
        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(consulta)) {

            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion") != null ? rs.getTimestamp("fecha_devolucion").toLocalDateTime() : null,
                        rs.getString("estado")
                );
                prestamos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos: " + e.getMessage(), e);
        }
        return prestamos;
    }

    public boolean isLibroDisponible(int idLibro) {
        String consulta = "SELECT COUNT(*) AS total FROM prestamo WHERE id_libro = ? AND estado != 'DISPONIBLE'";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar disponibilidad: " + e.getMessage(), e);
        }
        return false;
    }

    public List<Prestamo> listarPrestamosPorLector(int idLector) {
        List<Prestamo> prestamos = new ArrayList<>();
        String consulta = "SELECT * FROM prestamo WHERE id_lector = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idLector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_libro"),
                        rs.getInt("id_lector"),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion") != null ? rs.getTimestamp("fecha_devolucion").toLocalDateTime() : null,
                        rs.getString("estado")
                );
                prestamos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar préstamos del usuario: " + e.getMessage(), e);
        }
        return prestamos;
    }

    public void confirmarPrestamo(int idPrestamo) {
        String sql = "UPDATE prestamo SET estado = 'CONFIRMADO' WHERE id_prestamo = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
            System.out.println("Préstamo confirmado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException("Error al confirmar préstamo: " + e.getMessage(), e);
        }
    }

}

