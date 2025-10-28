package dao;

import basedatos.conexion;
import models.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void agregarReserva(int salaId, int userId, LocalDateTime fecha_in, LocalDateTime fecha_fin){
        String sql = "INSERT INTO reserva (id_sala, id_lector, fecha_in, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        String estado = "RESERVADA";
        Timestamp fechaInicio = Timestamp.valueOf(fecha_in);
        Timestamp fechaFin = Timestamp.valueOf(fecha_fin);
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, salaId);
            ps.setInt(2, userId);
            ps.setTimestamp(3, fechaInicio);
            ps.setTimestamp(4, fechaFin);
            ps.setString(5, estado);
            ps.executeUpdate();
            System.out.println("Reserva agregada al usuario " + userId + ".");
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar reserva: " + e.getMessage(), e);
        }
    }

    public void finalizarReserva(int reservaId) {
        String sql = "UPDATE reserva SET fecha_fin = ?, estado = ? WHERE id_reserva = ? AND estado = 'RESERVADA'";
        Timestamp fechaInicio = new Timestamp(System.currentTimeMillis());
        String estado = "DISPONIBLE";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, fechaInicio);
            ps.setString(2, estado);
            ps.setInt(3, reservaId);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Reserva finalizada.");
            else System.out.println("Reserva no encontrada");
        } catch (SQLException e) {
            throw new RuntimeException("Error al finalizar la reserva: " + e.getMessage(), e);
        }
    }

    public void cancelarReservaPorLector(int lectorId) {
        String sql = "UPDATE reserva SET estado = ? WHERE id_lector = ? AND estado = 'RESERVADA'";
        String estado = "CANCELADA";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, lectorId);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Reserva cancelada.");
            else System.out.println("Reserva no encontrada");
        } catch (SQLException e) {
            throw new RuntimeException("Error al finalizar la reserva: " + e.getMessage(), e);
        }
    }

    public void finalizarReservaPorLector(int lectorId) {
        String sql = "UPDATE reserva SET fecha_fin = ?, estado = ? WHERE id_lector = ? AND estado = 'RESERVADA'";
        Timestamp fechaInicio = new Timestamp(System.currentTimeMillis());
        String estado = "DISPONIBLE";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, fechaInicio);
            ps.setString(2, estado);
            ps.setInt(3, lectorId);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Reserva finalizada.");
            else System.out.println("Reserva no encontrada");
        } catch (SQLException e) {
            throw new RuntimeException("Error al finalizar la reserva: " + e.getMessage(), e);
        }
    }

    public List<Reserva> listarReservasSinFinalizar() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE r.estado = 'RESERVADA'" +
                "ORDER BY r.fecha_in ASC";
        try (Statement st = conexion.getInstancia().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getTimestamp("fecha_in").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                        rs.getInt("id_sala"),
                        rs.getInt("id_lector"),
                        rs.getString("estado"),
                        rs.getString("lector"),
                        rs.getInt("sala")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar reservas: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Reserva> listarReservasSinFinalizarPorFecha(LocalDate fecha) {
        List<Reserva> lista = new ArrayList<>();

        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.plusDays(1).atStartOfDay();

        String sql = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE r.estado = 'RESERVADA' AND r.fecha_in >= ? AND r.fecha_in < ? " +
                "ORDER BY r.fecha_in ASC";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Reserva(
                            rs.getInt("id_reserva"),
                            rs.getTimestamp("fecha_in").toLocalDateTime(),
                            rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                            rs.getInt("id_sala"),
                            rs.getInt("id_lector"),
                            rs.getString("estado"),
                            rs.getString("lector"),
                            rs.getInt("sala")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar reservas: " + e.getMessage(), e);
        }
        return lista;
    }



    public Reserva listarReservaActivaDelLector(int idLector) {
        String sql = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE r.estado = 'RESERVADA' AND r.id_lector = ? " +
                "ORDER BY r.fecha_in ASC";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, idLector);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reserva(
                            rs.getInt("id_reserva"),
                            rs.getTimestamp("fecha_in").toLocalDateTime(),
                            rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                            rs.getInt("id_sala"),
                            rs.getInt("id_lector"),
                            rs.getString("estado"),
                            rs.getString("lector"),
                            rs.getInt("sala")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reserva: " + e.getMessage(), e);
        }
        return null;
    }



    public void eliminarReserva(int reservaId) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, reservaId);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Reserva eliminada");
            else System.out.println("Reserva no encontrada para ese usuario.");
        }
    }

    public List<Reserva> listarReservasDeSala(int salaId) throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE r.id_sala = ? " +
                "ORDER BY r.fecha_in ASC";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, salaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Reserva(
                            rs.getInt("id_reserva"),
                            rs.getTimestamp("fecha_in").toLocalDateTime(),
                            rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                            rs.getInt("id_sala"),
                            rs.getInt("id_lector"),
                            rs.getString("estado"),
                            rs.getString("lector"),
                            rs.getInt("sala")
                    ));
                }
            }
        }
        return lista;
    }

    public List<Reserva> listarReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "ORDER BY r.fecha_in ASC";
        try (Statement st = conexion.getInstancia().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getTimestamp("fecha_in").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                        rs.getInt("id_sala"),
                        rs.getInt("id_lector"),
                        rs.getString("estado"),
                        rs.getString("lector"),
                        rs.getInt("sala")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar reservas: " + e.getMessage(), e);
        }
        return lista;
    }

    public Reserva buscarReservaPorId(int id) {
        String consulta = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE r.id_sala = ? ";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getTimestamp("fecha_in").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                        rs.getInt("id_sala"),
                        rs.getInt("id_lector"),
                        rs.getString("estado"),
                        rs.getString("lector"),
                        rs.getInt("sala")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reserva: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Reserva> reservasNoTerminadasPorSalaYFecha(int numeroSala, String fecha) {
        List<Reserva> lista = new ArrayList<>();
        String consulta = "SELECT r.id_reserva, r.fecha_in, r.fecha_fin, r.id_sala, r.id_lector, r.estado, " +
                "l.nombre AS lector, s.numero_sala AS sala " +
                "FROM reserva r " +
                "LEFT JOIN lector l ON r.id_lector = l.ID " +
                "LEFT JOIN sala s ON r.id_sala = s.id_sala " +
                "WHERE s.numero_sala= ? AND r.fecha_in >= ? AND r.fecha_in < ? AND r.estado = 'RESERVADA'";

        LocalDate date = LocalDate.parse(fecha);
        LocalDateTime fechaInicio = date.atStartOfDay();
        LocalDateTime fechaFin = date.plusDays(1).atStartOfDay();
        Timestamp fechInicio = Timestamp.valueOf(fechaInicio);
        Timestamp fechFin = Timestamp.valueOf(fechaFin);

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, numeroSala);
            ps.setTimestamp(2, fechInicio);
            ps.setTimestamp(3, fechFin);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getTimestamp("fecha_in").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null,
                        rs.getInt("id_sala"),
                        rs.getInt("id_lector"),
                        rs.getString("estado"),
                        rs.getString("lector"),
                        rs.getInt("sala")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reserva: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean reservaActivaPorLector(int idLector) {
        String consulta = "SELECT COUNT(*) FROM reserva WHERE id_lector = ? AND estado = 'RESERVADA'";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, idLector);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar préstamo activo: " + e.getMessage(), e);
        }
    }
}
