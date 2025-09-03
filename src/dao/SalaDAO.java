package dao;

import basedatos.conexion;
import models.Sala;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    public void crearSala (int id, String ubicacion, int maxPersonas) throws SQLException{
        String sql = "INSERT INTO sala (id_sala, ubicacion, max_personas, estado) VALUES (?, ?, ?, ?)";
        String estado = "DISPONIBLE";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.setString(2, ubicacion);
            ps.setInt(3, maxPersonas);
            ps.setString(4, estado);
            ps.executeUpdate();
            System.out.println("Sala creada");
        }
    }

    public List<Sala> listarSalas() throws SQLException {
        List<Sala> lista = new ArrayList<>();

        String sql = "SELECT * FROM sala ORDER BY id_sala DESC";

        try (Statement st = conexion.getInstancia().getConnection().createStatement()){
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Sala(rs.getInt("id_sala"), rs.getString("ubicacion"), rs.getInt("max_personas"), rs.getString("estado")));
            }

        }

        return lista;
    }

    public void editarSala (int id, String nuevaUbicacion, int nuevoMaxPersonas, String nuevoEstado) throws SQLException {
        String sql = "UPDATE sala SET ubicacion = ?, max_personas = ?, estado = ? WHERE id_sala = ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setString(1, nuevaUbicacion);
            ps.setInt(2, nuevoMaxPersonas);
            ps.setString(3, nuevoEstado);
            ps.setInt(4, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Sala actualizada.");
            else System.out.println("Sala no encontrada");
        }
    }

    public void borrarSala (int id) throws SQLException {
        String sql = "DELETE FROM sala WHERE id_sala = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Sala eliminada.");
            else System.out.println("Sala no encontrada");
        }
    }
}