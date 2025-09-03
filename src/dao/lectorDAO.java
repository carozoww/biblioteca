package dao;

import basedatos.conexion;
import models.Lector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class lectorDAO {

    public void crearLector(String nombre, String correo, LocalDate fechaNac, String telefono, String contrasena, String direccion) {
        String consulta = "INSERT INTO lector (nombre, correo, fechaNac, telefono, contrasenia, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            if (fechaNac != null) ps.setDate(3, Date.valueOf(fechaNac));
            else ps.setNull(3, Types.DATE);
            ps.setString(4, telefono);
            ps.setString(5, contrasena);
            ps.setString(6, direccion);
            ps.executeUpdate();
            System.out.println("Lector creado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarLector(int id, String nombre, String correo, LocalDate fechaNac, String telefono, String contrasena, String direccion) {
        String consulta = "UPDATE lector SET nombre = ?, correo = ?, fechaNac = ?, telefono = ?, contrasenia = ?, direccion = ? WHERE ID = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            if (fechaNac != null) ps.setDate(3, Date.valueOf(fechaNac));
            else ps.setNull(3, Types.DATE);
            ps.setString(4, telefono);
            ps.setString(5, contrasena);
            ps.setString(6, direccion);
            ps.setInt(7, id);
            ps.executeUpdate();
            System.out.println("Lector modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarLector(int id) {
        String consulta = "DELETE FROM lector WHERE ID = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Lector eliminado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Lector> listarLectores() {
        List<Lector> lectores = new ArrayList<>();
        String consulta = "SELECT * FROM lector";
        try {
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                lectores.add(new Lector(
                        rs.getInt("ID"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getDate("fechaNac").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getString("contrasenia"),
                        rs.getString("direccion")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lectores;
    }
}

