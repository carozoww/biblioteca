package dao;

import basedatos.conexion;
import models.Autor;
import models.Libro;
import models.LibroAutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroAutorDAO {

    public void asignarAutorALibro(int id_autor,int id_libro){
        String query = "INSERT INTO libro_autor(id_libro,id_autor) VALUES(?,?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, id_libro);
            ps.setInt(2, id_autor);
            ps.executeUpdate();

            System.out.println("Autor de libro asignado correctamente!!!! ");

        }catch(SQLException e){
            new RuntimeException(e);
        }
    }

    public List<LibroAutor> listarAutoresDeLibros(){
        List<LibroAutor>  libroAutores = new ArrayList<>();
        String query = "SELECT l.id_libro,titulo,fecha_publicacion,isbn,id_editorial,a.id_autor,a.nombre,a.apellido,sinopsis,numPaginas  \n" +
                "FROM libro l JOIN libro_autor la ON l.id_libro = la.id_libro\n" +
                "JOIN autor a ON la.id_autor = a.id_autor";
        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                libroAutores.add(
                        new LibroAutor(
                                new Libro(
                                        rs.getInt("id_libro"),
                                        rs.getString("titulo"),
                                        rs.getString("isbn"),
                                        rs.getDate("fecha_publicacion"),
                                        rs.getInt("id_editorial"),
                                        rs.getString("nombre"),
                                        rs.getString("sinopsis"),
                                        rs.getInt("numPaginas")
                                ),
                                new Autor(
                                        rs.getInt("id_autor"),
                                        rs.getString("nombre"),
                                        rs.getString("apellido")
                                )
                        ));
            }

        }catch(SQLException e){
            new RuntimeException(e);
        }
        return libroAutores;
    }

    public List<Autor> listarAutoresDeUnLibro(int id_libro){
        List<Autor>  Autores = new ArrayList<>();
        String query = "SELECT a.id_autor,a.nombre,a.apellido from autor a JOIN libro_autor la ON a.id_autor = la.id_autor WHERE la.id_libro = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, id_libro);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Autores.add(
                        new Autor(
                                rs.getInt("id_autor"),
                                rs.getString("nombre"),
                                rs.getString("apellido")
                        ));
            }


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Autores;
    }

    public void modificarAutorDeLibro(int id_libro,int id_autor_old,int id_autor_new){
        String queryMod = "UPDATE libro_autor SET id_autor = ? WHERE id_libro = ? and id_autor = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryMod);
            ps.setInt(1,id_autor_new);
            ps.setInt(2,id_libro);
            ps.setInt(3,id_autor_old);

            ps.executeUpdate();

            System.out.println("Autor de libro modificado correctamente");


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void eliminarAutorDeLibro(int id_libro,int id_autor){
        String queryDel = "DELETE FROM libro_autor WHERE id_libro = ? and id_autor = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(queryDel);
            ps.setInt(1,id_libro);
            ps.setInt(2,id_autor);
            ps.executeUpdate();

            System.out.println("Autor de libro eliminado correctamente");

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Libro> listarLibroPorAutor(int id_autor){
        List<Libro>  librosAutor = new ArrayList<>();
        String query = "SELECT l.id_libro,titulo,isbn,fecha_publicacion,id_editorial,ed_asignada,sinopsis,numPaginas\n" +
                "FROM libro l JOIN libro_autor la ON l.id_libro = la.id_libro JOIN autor a ON la.id_autor = a.id_autor\n" +
                "WHERE a.id_autor = ?";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1,id_autor);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                librosAutor.add(
                        new Libro(
                                rs.getInt("id_libro"),
                                rs.getString("titulo"),
                                rs.getString("isbn"),
                                rs.getDate("fecha_publicacion"),
                                rs.getInt("id_editorial"),
                                rs.getString("ed_asignada"),
                                rs.getString("sinopsis"),
                                rs.getInt("numPaginas")
                        ));
            }


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return librosAutor;
    }


}
