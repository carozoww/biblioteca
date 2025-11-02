package dao;

import basedatos.conexion;
import models.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    public void crearLibro(String titulo, String isbn, Date fechaPublicacion, int idEditorial,String sinopsis, int numpaginas) {
        String consulta = "INSERT INTO libro (titulo, isbn, fecha_publicacion, id_editorial,ed_asignada,sinopsis,numpaginas) VALUES (?, ?, ?, ?,TRUE,?,?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.setString(5, sinopsis);
            ps.setInt(6, numpaginas);

            ps.executeUpdate();

            System.out.println("Libro creado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void editarLibro(int idLibro, String titulo, String isbn, Date fechaPublicacion, int idEditorial,String sinopsis,int numpaginas) {
        String consulta = "UPDATE libro SET titulo = ?, isbn = ?, fecha_publicacion = ?, id_editorial = ?,sinopsis = ?,numPaginas = ? WHERE id_libro = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.setInt(5, idLibro);
            ps.setString(6,sinopsis);
            ps.setInt(7, numpaginas);

            ps.executeUpdate();

            System.out.println("Libro modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void EditarLibroActualizado(String titulo, String isbn, Date fechaPublicacion, int idEditorial,String sinopsis,int numpaginas,String imagen_url,int id_libro){
        String consulta = "UPDATE libro SET titulo = ?, isbn = ?, fecha_publicacion = ?, id_editorial = ?,sinopsis = ?,numPaginas = ?, imagen_url = ? WHERE id_libro = ?";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.setString(5,sinopsis);
            ps.setInt(6, numpaginas);
            ps.setString(7, imagen_url);
            ps.setInt(8, id_libro);

            ps.executeUpdate();

            System.out.println("Libro modificado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarLibro(int idLibro) {
        String consulta = "DELETE FROM libro WHERE id_libro = ?";

        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idLibro);
            ps.executeUpdate();

            System.out.println("Libro eliminado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT id_libro,titulo,isbn,fecha_publicacion, sinopsis, numPaginas,e.id_editorial,e.nombre " +
                "FROM libro l LEFT JOIN editorial e ON l.id_editorial = e.id_editorial";

        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                libros.add(new Libro(rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial"),
                        rs.getString("nombre"),
                        rs.getString("sinopsis"),
                        rs.getInt("numPaginas")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libros;
    }

    public List<Libro> listarLibrosReservados(){
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT titulo,isbn,fecha_publicacion,l.id_editorial,ed_asignada,e.nombre,sinopsis,numPaginas\n" +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial\n" +
                "JOIN prestamo p ON l.id_libro = p.id_libro WHERE estado = 'RESERVADO'";

        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                libros.add(new Libro(rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial"),
                        rs.getString("nombre"),
                        rs.getString("sinopsis"),
                        rs.getInt("numPaginas")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return libros;
    }

    public Libro buscarPorId(int idLibro) {
        String consulta = "SELECT l.id_libro,titulo,isbn,fecha_publicacion,l.id_editorial,ed_asignada,e.nombre,sinopsis,numPaginas\n" +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial\n" +
                "WHERE id_libro = ?";
        try {
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setInt(1, idLibro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial"),
                        rs.getString("nombre"),
                        rs.getString("sinopsis"),
                        rs.getInt("numPaginas")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar libro: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Libro> existeISBN(String isbn) {
        List<Libro> libros = new ArrayList<>();
        String consulta = "SELECT l.id_libro,titulo,isbn,fecha_publicacion,l.id_editorial,ed_asignada,e.nombre,sinopsis,numPaginas\n" +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial\n" +
                "WHERE isbn = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                libros.add(
                        new Libro(
                                rs.getInt("id_libro"),
                                rs.getString("titulo"),
                                rs.getString("isbn"),
                                rs.getDate("fecha_publicacion"),
                                rs.getInt("id_editorial"),
                                rs.getString("nombre"),
                                rs.getString("sinopsis"),
                                rs.getInt("numPaginas")
                        ));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return libros;
    }

    public List<Libro> existeISBNporId(int id_libro, String isbn) {
        List<Libro> libros = new ArrayList<>();
        String query = "SELECT l.id_libro,titulo,isbn,fecha_publicacion,l.id_editorial,ed_asignada,e.nombre,sinopsis,numPaginas\n" +
                "FROM libro l JOIN editorial e ON l.id_editorial = e.id_editorial\n" +
                "WHERE id_libro != ? and isbn = ?";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1, id_libro);
            ps.setString(2, isbn);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                libros.add(
                        new Libro(
                                rs.getInt("id_libro"),
                                rs.getString("titulo"),
                                rs.getString("isbn"),
                                rs.getDate("fecha_publicacion"),
                                rs.getInt("id_editorial"),
                                rs.getString("nombre"),
                                rs.getString("sinopsis"),
                                rs.getInt("numPaginas")
                        ));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return libros;
    }


    public List<Libro> listarLibrosCompleto(){
        List<Libro> libros = new ArrayList<>();
        String query ="SELECT l.id_libro,l.titulo,l.isbn,l.fecha_publicacion,l.sinopsis,l.numPaginas,e.id_editorial,imagen_url,e.nombre as editorial_nombre,\n" +
                "                GROUP_CONCAT(CONCAT(a.nombre, ' ', a.apellido) SEPARATOR ', ') as autores\n" +
                "                FROM libro l\n" +
                "                LEFT JOIN editorial e ON l.id_editorial = e.id_editorial \n" +
                "                LEFT JOIN libro_autor la ON l.id_libro = la.id_libro\n" +
                "                LEFT JOIN autor a ON la.id_autor = a.id_autor\n" +
                "                GROUP BY l.id_libro, l.titulo, l.isbn, l.fecha_publicacion, l.sinopsis, l.numPaginas, e.id_editorial, e.nombre;";
        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                libros.add(new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial"),
                        rs.getString("editorial_nombre"),
                        rs.getString("sinopsis"),
                        rs.getInt("numPaginas"),
                        rs.getString("autores"),
                        rs.getString("imagen_url")


                        ));
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return libros;
    }

    public void crearLibroConImagen(String titulo, String isbn, Date fechaPublicacion, int idEditorial,String sinopsis, int numpaginas,String imagen_url) {
        String consulta = "INSERT INTO libro (titulo, isbn, fecha_publicacion, id_editorial,ed_asignada,sinopsis,numpaginas,imagen_url) VALUES (?, ?, ?, ?,TRUE,?,?,?)";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(consulta);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setDate(3, fechaPublicacion);
            ps.setInt(4, idEditorial);
            ps.setString(5, sinopsis);
            ps.setInt(6, numpaginas);
            ps.setString(7, imagen_url);

            ps.executeUpdate();

            System.out.println("Libro creado correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Libro buscarLibroPorID(int id_libro){
        Libro lib = null;
        String query = "SELECT l.id_libro,l.titulo,l.isbn,l.fecha_publicacion,l.sinopsis,l.numPaginas,e.id_editorial,imagen_url,e.nombre as editorial_nombre,\n" +
                "                GROUP_CONCAT(CONCAT(a.nombre, ' ', a.apellido) SEPARATOR ', ') as autores\n" +
                "                FROM libro l\n" +
                "                LEFT JOIN editorial e ON l.id_editorial = e.id_editorial \n" +
                "                LEFT JOIN libro_autor la ON l.id_libro = la.id_libro\n" +
                "                LEFT JOIN autor a ON la.id_autor = a.id_autor WHERE l.id_libro = ?  \n" +
                "                GROUP BY l.id_libro, l.titulo, l.isbn, l.fecha_publicacion, l.sinopsis, l.numPaginas, e.id_editorial, e.nombre";
        try{
            PreparedStatement ps = conexion.getInstancia().getConnection().prepareStatement(query);
            ps.setInt(1,id_libro);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                lib = new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getDate("fecha_publicacion"),
                        rs.getInt("id_editorial"),
                        rs.getString("editorial_nombre"),
                        rs.getString("sinopsis"),
                        rs.getInt("numPaginas"),
                        rs.getString("autores"),
                        rs.getString("imagen_url")
                );
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return lib;
    }

    public int obtenerId(){
        int id_libro = 0;
        String query = "SELECT max(id_libro) from libro";
        try{
            Statement st = conexion.getInstancia().getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                id_libro = rs.getInt("id_libro");
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return id_libro;
    }


}
