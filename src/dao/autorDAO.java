package dao;

import basedatos.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            System.out.println("Usuario creado con exito");



        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static List<String> mostrarAutores() throws SQLException{
        String query1 = "SELECT * FROM autor";
        List<String> listaAutores = new ArrayList<>();
        try{
            Statement st =  conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(query1);

            while(rs.next()){
                listaAutores.add(rs.getInt("id_autor") + " - " + rs.getString("nombre")+ " - " + rs.getString("apellido"));
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

            System.out.println("Usuario creado con exito");

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

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


}
