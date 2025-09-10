package dao;
//import database.DatabaseConnection;
import basedatos.conexion;
import models.Administrador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public void crearAdministradorDAO(int cedula,String nombre,String telefono ,String direccion ){

        String consulta = "INSERT INTO administrador(cedula,nombre,telefono,direccion) VALUE(?,?,?,?) ";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

            ps.setInt(1, cedula);
            ps.setString(2, nombre);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.executeUpdate();

            System.out.println("Administrador insertado exitosamente");


        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public void editarAdministrador(int cedula,String nombre,String telefono ,String direccion ){

            String consulta = "UPDATE usuarios SET nombre = ?, telefono = ?, direccion = ? WHERE cedula = ?" ;
            try {
                PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

                ps.setString(1, nombre);
                ps.setString(2, telefono);
                ps.setString(3, direccion);
                ps.setInt(4, cedula);
                ps.executeUpdate();

                System.out.println("Administrador modificando exitosamente");

            }catch (Exception e){
                throw  new RuntimeException(e);
            }

    }

    public List<Administrador> listarAdministradores(){
        String consulta = "SELECT * FROM administradores";
        List<Administrador> listaAdministradores = new ArrayList<>();

        try {
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                listaAdministradores.add(new Administrador(rs.getInt("cedula"),
                        " - "+rs.getString("nombre"),
                        " - "+rs.getString("telefono"),
                        " - "+rs.getString("direccion")));
            }

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return listaAdministradores;
    }

    public void eliminarAdministrador(int cedula){
            String consulta = "DELETE FROM administradores WHERE cedula = ?";

            try {
                PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

                ps.setInt(1, cedula);
                ps.executeUpdate();

                System.out.println("Administrador eliminado exitosamente");
            }catch (Exception e){
                throw  new RuntimeException(e);
            }

    }

}
