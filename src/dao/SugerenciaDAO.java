package dao;

import basedatos.conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SugerenciaDAO {

    public void crearSugerencia(String contenido, int id_lector){
        String query = "INSERT INTO sugerencia(contenido,id_lector) VALUES(?,?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setString(1, contenido);
            ps.setInt(2, id_lector);
            ps.executeUpdate();

            System.out.println("Sugerencia creada con exito!!!");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
