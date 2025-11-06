package dao;

import basedatos.conexion;
import models.Penalizacion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PenalizacionDAO {
    public void crearPenalizacion(int duracion, String motivo, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, int id_lector, int id_admin) {
        String consulta = "INSERT INTO penalizacion (duracion, motivo, fecha_inicio, fecha_fin, id_lector, id_admin) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, duracion);
            ps.setString(2, motivo);
            ps.setTimestamp(3, Timestamp.valueOf(fecha_inicio));
            ps.setTimestamp(4, Timestamp.valueOf(fecha_fin));
            ps.setInt(5, id_lector);
            ps.setInt(6, id_admin);
            ps.executeUpdate();

            System.out.println("Penalizacion creada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void eliminarPenalizacion(int id) {
        String consulta = "DELETE FROM penalizacion WHERE id_lector = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Penalizacion eliminada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Penalizacion> listarPenalizaciones() {
        List<Penalizacion> lista = new ArrayList<>();
        String consulta = "SELECT p.id_pena, p.duracion, p.motivo, p.fecha_inicio, p.fecha_fin, p.activa, p.id_lector, p.id_admin, l.nombre AS lector, a.nombre AS admin " +
                "FROM penalizacion p " +
                "LEFT JOIN lector l ON p.id_lector = l.ID " +
                "LEFT JOIN administrador a ON p.id_admin = a.ID";
        try {
             Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                lista.add(new Penalizacion(rs.getInt("id_pena"),
                rs.getInt("duracion"),
                rs.getString("motivo"),
                        rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin").toLocalDateTime(),
                        rs.getBoolean("activa"),
                rs.getInt("id_lector"),
                rs.getInt("id_admin"),
                rs.getString("lector"),
                rs.getString("admin")));

            }
        } catch (SQLException e) {
            System.out.println("Error listando penalizaciones: " + e.getMessage());
        }
        return lista;
    }

    public List<Penalizacion> buscarPenalizacionActivaPorUsuario(int id) {
        List<Penalizacion> lista = new ArrayList<>();
        String consulta = "SELECT p.id_pena, p.duracion, p.motivo, p.fecha_inicio, p.fecha_fin, p.activa, p.id_lector, p.id_admin, " +
                "l.nombre AS lector, a.nombre AS admin " +
                "FROM penalizacion p " +
                "LEFT JOIN lector l ON p.id_lector = l.ID " +
                "LEFT JOIN administrador a ON p.id_admin = a.ID " +
                "WHERE p.id_lector = ?";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Penalizacion(rs.getInt("id_pena"),
                        rs.getInt("duracion"),
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin").toLocalDateTime(),
                        rs.getBoolean("activa"),
                        rs.getInt("id_lector"),
                        rs.getInt("id_admin"),
                        rs.getString("lector"),
                        rs.getString("admin")));

            }
        } catch (SQLException e) {
            System.out.println("Error listando penalización: " + e.getMessage());
        }
        return lista;
    }

    public Penalizacion obtenerPenalizacionActivaPorUsuario(int id) {
        String consulta = "SELECT p.id_pena, p.duracion, p.motivo, p.fecha_inicio, p.fecha_fin, p.activa, p.id_lector, p.id_admin, " +
                "l.nombre AS lector, a.nombre AS admin " +
                "FROM penalizacion p " +
                "LEFT JOIN lector l ON p.id_lector = l.ID " +
                "LEFT JOIN administrador a ON p.id_admin = a.ID " +
                "WHERE p.id_lector = ? AND p.activa = TRUE AND p.fecha_fin > NOW() " +
                "ORDER BY p.fecha_fin DESC LIMIT 1";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Penalizacion(
                        rs.getInt("id_pena"),
                        rs.getInt("duracion"),
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin").toLocalDateTime(),
                        rs.getBoolean("activa"),
                        rs.getInt("id_lector"),
                        rs.getInt("id_admin"),
                        rs.getString("lector"),
                        rs.getString("admin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error listando penalización: " + e.getMessage());
        }

        return null;
    }


    public boolean tienePenalizacionActiva(int idLector) {
        String sql = "SELECT * FROM penalizacion WHERE id_lector = ? AND activa = TRUE AND NOW() < fecha_fin";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {

            ps.setInt(1, idLector);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si tiene penalización activa

        } catch (SQLException e) {

            return false;
        }
    }

    public void desactivarPenalizacion(int idPena){
        String sql = "UPDATE penalizacion SET activa = FALSE WHERE id_pena = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setInt(1, idPena);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
