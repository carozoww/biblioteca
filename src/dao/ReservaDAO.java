package dao;

import basedatos.conexion;
import models.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void agregarReserva (int salaId, int userId, Date fechaInicio, int duracion) throws SQLException {
        String sql = "INSERT INTO reserva (id_sala, id_lector, fecha_in, duracion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, salaId);
            ps.setInt(2, userId);
            if (fechaInicio != null) ps.setDate(3, fechaInicio);
            else ps.setNull(3, Types.DATE);
            ps.setInt(4, duracion);
            ps.executeUpdate();
            System.out.println("Reserva agregada al usuario " + userId + ".");
        }
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

    public List<Reserva> listarReservasDeSala (int salaId) throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE id_sala = ? ORDER BY fecha_in ASC";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, salaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Reserva(
                            rs.getInt("id_reserva"),
                            rs.getDate("fecha_in"),
                            rs.getInt("duracion"),
                            rs.getInt("id_sala"),
                            rs.getInt("id_lector")
                    ));
                }
            }
        }
        return lista;
    }
}
