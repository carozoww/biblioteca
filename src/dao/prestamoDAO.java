package dao;

import basedatos.conexion;
import models.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class prestamoDAO {

    private boolean existeLibro(int idLibro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM libro WHERE id_libro = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql);
        ps.setInt(1, idLibro);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    private boolean existeLector(int idLector) throws SQLException {
        String sql = "SELECT COUNT(*) FROM lector WHERE ID = ?";
        PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql);
        ps.setInt(1, idLector);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public void crearPrestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado, int idLibro, int idLector) {
        try {
            if (!existeLibro(idLibro)) throw new RuntimeException("El libro no existe");
            if (!existeLector(idLector)) throw new RuntimeException("El lector no existe");

            String consulta = "INSERT INTO prestamo (fecha_prestamo, fecha_devolucion, estado, id_libro, ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setDate(1, Date.valueOf(fechaPrestamo));
            ps.setDate(2, Date.valueOf(fechaDevolucion));
            ps.setString(3, estado);
            ps.setInt(4, idLibro);
            ps.setInt(5, idLector);
            ps.executeUpdate();
            System.out.println("Préstamo creado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarPrestamo(int idPrestamo, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado, int idLibro, int idLector) {
        try {
            if (!existeLibro(idLibro)) throw new RuntimeException("El libro no existe");
            if (!existeLector(idLector)) throw new RuntimeException("El lector no existe");

            String consulta = "UPDATE prestamo SET fecha_prestamo = ?, fecha_devolucion = ?, estado = ?, id_libro = ?, ID = ? WHERE id_prestamo = ?";
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setDate(1, Date.valueOf(fechaPrestamo));
            ps.setDate(2, Date.valueOf(fechaDevolucion));
            ps.setString(3, estado);
            ps.setInt(4, idLibro);
            ps.setInt(5, idLector);
            ps.setInt(6, idPrestamo);
            ps.executeUpdate();
            System.out.println("Préstamo modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarPrestamo(int idPrestamo) {
        String consulta = "DELETE FROM prestamo WHERE id_prestamo = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
            System.out.println("Préstamo eliminado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prestamo> listarPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();
        String consulta = "SELECT * FROM prestamo";
        try {
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                prestamos.add(new Prestamo(
                        rs.getInt("id_prestamo"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion").toLocalDate(),
                        rs.getString("estado"),
                        rs.getInt("id_libro"),
                        rs.getInt("ID")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prestamos;
    }
}

