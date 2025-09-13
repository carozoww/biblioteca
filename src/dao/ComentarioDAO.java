package dao;

import basedatos.conexion;
import models.Libro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ComentarioDAO {

    public void eliminarComentario(int id_comentario){

        String consulta = "DELETE FROM comentario WHERE id_comentario = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

            ps.setInt(1, id_comentario);
            ps.executeUpdate();

            System.out.println("Comentario eliminado exitosamente");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
