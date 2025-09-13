package dao;

import models.Administrador;
import basedatos.conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public void crearAdministradorDAO(String nombre, Date fechaNacimiento , String correo ){

        String consulta = "INSERT INTO administrador(nombre,fechaNac,correo) VALUE(?,?,?) ";

        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);


            ps.setString(1, nombre);
            ps.setDate(2, fechaNacimiento);
            ps.setString(3, correo);
            ps.executeUpdate();

            System.out.println("Administrador insertado exitosamente");


        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public void editarAdministrador(int id,String nombre,Date fechaNacimiento ,String correo ){

            String consulta = "UPDATE administrador SET nombre = ?, fechaNac = ?, correo = ? WHERE id = ?" ;
            try {
                PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

                ps.setString(1, nombre);
                ps.setDate(2, fechaNacimiento);
                ps.setString(3, correo);
                ps.setInt(4, id);
                ps.executeUpdate();

                System.out.println("Administrador modificando exitosamente");

            }catch (Exception e){
                throw  new RuntimeException(e);
            }

    }

    public List<Administrador> listarAdministradores(){
        String consulta = "SELECT * FROM administrador";
        List<Administrador> listaAdministradores = new ArrayList<>();

        try {
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                listaAdministradores.add(new Administrador(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getDate("fechaNac"))
                );
            }

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return listaAdministradores;
    }

    public void eliminarAdministrador(int id){
            String consulta = "DELETE FROM administrador WHERE id = ?";

            try {
                PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);

                ps.setInt(1, id);
                ps.executeUpdate();

                System.out.println("Administrador eliminado exitosamente");
            }catch (Exception e){
                throw  new RuntimeException(e);
            }

    }

}
