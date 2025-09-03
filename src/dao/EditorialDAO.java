package dao;

import basedatos.conexion;
import models.Editorial;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAO {
    public void crearEditorial(String nombre) {
        String consulta = "INSERT INTO editorial (nombre) VALUES (?)";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("Editorial creada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarEditorial(int idEditorial, String nombre) {
        String consulta = "UPDATE editorial SET  nombre = ? WHERE id_editorial = ?";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setInt(2, idEditorial);
            ps.executeUpdate();


            System.out.println("Editorial modificada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarEditorial(int idEditorial) {
        String consulta = "DELETE FROM editorial WHERE id_editorial = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idEditorial);
            ps.executeUpdate();

            System.out.println("Editorial eliminada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Editorial> listarEditorial() {
        List<Editorial> editoriales = new ArrayList<>();
        String consulta = "SELECT  * FROM editorial";

        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                editoriales.add(new Editorial(rs.getInt("id_editorial"),
                        rs.getString("nombre")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return editoriales;
    }
}
