package dao;

import basedatos.conexion;
import models.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class generoDAO {

    public void crearGenero(String nombre) throws SQLException {
        String queryAlta = "INSERT INTO genero(nombre) VALUES(?)";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryAlta)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("Genero creado");
        }
    }

    public List<Genero> mostrarGeneros() throws SQLException {
        List<Genero> generos = new ArrayList<>();
        String query = "SELECT * FROM genero";
        try (Statement st = conexion.getInstancia().getConnection().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                generos.add(new Genero(rs.getInt("id_genero"), rs.getString("nombre")));
            }
        }
        return generos;
    }

    public void modificarGenero(int id_genero, String nombre_genero) throws SQLException {
        String queryEd = "UPDATE genero SET nombre = ? WHERE id_genero = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryEd)) {
            ps.setString(1, nombre_genero);
            ps.setInt(2, id_genero);
            ps.executeUpdate();
            System.out.println("Genero modificado correctamente");
        }
    }

    public void eliminarGenero(int id_genero) throws SQLException {
        String queryDel = "DELETE FROM genero WHERE id_genero = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryDel)) {
            ps.setInt(1, id_genero);
            ps.executeUpdate();
            System.out.println("Genero eliminado correctamente");
        }
    }
}

