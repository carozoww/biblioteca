package dao;

import basedatos.conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
