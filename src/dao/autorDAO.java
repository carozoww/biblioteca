package dao;

import basedatos.conexion;
import models.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class autorDAO {



    public static void crearAutor(String nombre, String apellido) throws SQLException {
        String query = "INSERT INTO autor(nombre,apellido) VALUES (?,?)";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setString(1,nombre);
            ps.setString(2,apellido);
            ps.executeUpdate();

            System.out.println("Autor creado con exito");



        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static List<Autor> mostrarAutores() throws SQLException{
        String query1 = "SELECT * FROM autor";
        List<Autor> listaAutores = new ArrayList<>();
        try{
            Statement st =  conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(query1);

            while(rs.next()){
                listaAutores.add(
                        new Autor(
                                rs.getInt("id_autor"),
                                rs.getString("nombre"),
                                rs.getString("apellido")));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return  listaAutores;
    }

    public static void modificarAutor(int idautor,String nuevoNombre, String nuevoApellido){
        String queryEd = "UPDATE autor SET nombre = ?, apellido = ? WHERE id_autor = ?";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryEd);

            ps.setString(1,nuevoNombre);
            ps.setString(2,nuevoApellido);
            ps.setInt(3,idautor);

            ps.executeUpdate();

            System.out.println("Autor modificado con exito");

        }catch(SQLException e ){
            throw new RuntimeException(e);
        }

    }

    public static void eliminarAutor(int idautor){
        String query = "DELETE FROM autor WHERE id_autor = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, idautor);
            ps.executeUpdate();

            System.out.println("Autor eliminado con exito");
        }catch(SQLException e){
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";

            if (e instanceof SQLIntegrityConstraintViolationException || msg.contains("foreign key") || msg.contains("constraint fails")){
                throw new RuntimeException("No se puede eliminar el autor porque esta asignado a uno o varios libros");
            } else {
                throw new RuntimeException("Error al eliminar el autor: " + e.getMessage());
            }
        }
    }
    public Autor buscarAutorPorId(int id) {
        String consulta = "SELECT * FROM autor WHERE id_autor = ?";
        try (PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Autor(
                        rs.getInt("id_autor"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar autor: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Autor> ObtenerAutoresResenias(int id_lector){
        List<Autor> autores = new ArrayList<>();
        String consulta = "SELECT a.id_autor,a.nombre,a.apellido FROM review r LEFT JOIN libro l ON r.id_libro = l.id_libro\n" +
                "LEFT JOIN libro_autor la ON l.id_libro = la.id_libro LEFT JOIN autor a ON la.id_autor = la.id_autor\n" +
                "WHERE r.id_lector = ? and r.valoracion >= 3 GROUP BY a.id_autor,a.nombre,a.apellido;";
        try{
            PreparedStatement ps =  conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, id_lector);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                autores.add(
                        new Autor(
                                rs.getInt("id_autor"),
                                rs.getString("nombre"),
                                rs.getString("apellido")));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return autores;
    }

}
