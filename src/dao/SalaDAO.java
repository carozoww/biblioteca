package dao;

import basedatos.conexion;
import models.Sala;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    public void crearSala(int numeroSala, String ubicacion, int maxPersonas, String imagen) throws SQLException {
        String sql = "INSERT INTO sala (numero_sala, ubicacion, max_personas, imagen_url) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, numeroSala);
            ps.setString(2, ubicacion);
            ps.setInt(3, maxPersonas);
            ps.setString(4, imagen);
            ps.executeUpdate();
            System.out.println("Sala creada");
        }
    }

    public List<Sala> listarSalas(){
        List<Sala> lista = new ArrayList<>();

        String sql = "SELECT * FROM sala ORDER BY id_sala DESC";

        try (Statement st = conexion.getInstancia().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Sala(rs.getInt("id_sala"), rs.getInt("numero_sala"), rs.getString("ubicacion"), rs.getInt("max_personas"), rs.getString("imagen_url")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public List<Sala> listarSalas(String campo, String valor) throws SQLException {
        List<Sala> lista = new ArrayList<>();
        String sql;

        if (campo.equals("numeroSala")) {
            sql = "SELECT * FROM sala WHERE numero_sala = ? ORDER BY id_sala DESC";
        } else if (campo.equals("maxPersonas")) {
            sql = "SELECT * FROM sala WHERE max_personas = ? ORDER BY id_sala DESC";
        } else {
            sql = "SELECT * FROM sala WHERE ubicacion LIKE ? ORDER BY id_sala DESC";
        }
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            if (campo.equals("ubicacion")) {
                ps.setString(1, "%"+valor+"%");
            } else {
                ps.setInt(1, Integer.parseInt(valor));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Sala(rs.getInt("id_sala"), rs.getInt("numero_sala"), rs.getString("ubicacion"), rs.getInt("max_personas"), rs.getString("imagen_url")));
            }
        }
        return lista;
    }

    public void editarSala(int id, int numeroSala, String nuevaUbicacion, int nuevoMaxPersonas, String imagen) throws SQLException {
        String sql = "UPDATE sala SET numero_sala = ?, ubicacion = ?, max_personas = ?, imagen_url = ? WHERE id_sala = ?";

        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, numeroSala);
            ps.setString(2, nuevaUbicacion);
            ps.setInt(3, nuevoMaxPersonas);
            ps.setString(4, imagen);
            ps.setInt(5, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Sala actualizada.");
            else System.out.println("Sala no encontrada");
        }
    }

    public void borrarSala(int id) throws SQLException {
        String sql = "DELETE FROM sala WHERE id_sala = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("Sala eliminada.");
            else System.out.println("Sala no encontrada");
        }
    }

    public Sala buscarSalaPorId(int id) {
        String consulta = "SELECT * FROM sala WHERE id_sala = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Sala(rs.getInt("id_sala"), rs.getInt("numero_sala"), rs.getString("ubicacion"), rs.getInt("max_personas"), rs.getString("imagen_url"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sala: " + e.getMessage(), e);
        }
        return null;
    }

    public Sala buscarSalaPorNumeroSala(int numeroSala) {
        String consulta = "SELECT * FROM sala WHERE numero_sala = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, numeroSala);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Sala(rs.getInt("id_sala"), rs.getInt("numero_sala"), rs.getString("ubicacion"), rs.getInt("max_personas"), rs.getString("imagen_url"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sala: " + e.getMessage(), e);
        }
        return null;
    }
}