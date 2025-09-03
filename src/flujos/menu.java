package flujos;

import java.sql.Date;
import java.sql.SQLException;

import dao.EditorialDAO;
import dao.LibroDAO;
import dao.autorDAO;
import dao.generoDAO;
import models.Libro;
import models.Editorial;

import java.util.List;
import java.util.Scanner;

public class menu {
    private final Scanner scanner = new Scanner(System.in);
    private final autorDAO autordao;
    private final generoDAO generodao;
    private final LibroDAO librodao;
    private final EditorialDAO editorialdao;

    public menu() {
        this.autordao = new autorDAO();
        this.generodao = new generoDAO();
        this.librodao = new LibroDAO();
        this.editorialdao = new EditorialDAO();
    }

    public void mostrarMenu(){
        try{


            int opcion= 0;

            do{
                System.out.println("\n === Gestion de biblioteca ===");
                System.out.println("1. Crear autor");
                System.out.println("2. Modificar autor");
                System.out.println("3. Eliminar autor");
                System.out.println("4. Crear genero");
                System.out.println("5. Modificar genero");
                System.out.println("6. Eliminar genero");
                System.out.println("7. Crear libro");
                System.out.println("8. Eliminar libro");
                System.out.println("9. Modificar libro");
                System.out.println("10. Listar libros");
                System.out.println("11. Crear editorial");
                System.out.println("12. Eliminar editorial");
                System.out.println("13. Modificar editorial");
                System.out.println("14. Listar editoriales");
                System.out.println("15. Salir");

                System.out.println("Opcion");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch(opcion){
                    case 1: crearAutor();
                        break;
                    case 2: modificarAutor();
                        break;
                    case 3: eliminarAutor();
                        break;
                    case 4: crearGenero();
                        break;
                    case 5: modificarGenero();
                        break;
                    case 6: eliminarGenero();
                        break;
                    case 7:
                        crearLibro();
                        break;
                    case 8:
                        eliminarLibro();
                        break;
                    case 9:
                        editarLibro();
                        break;
                    case 10:
                        listarLibros();
                        break;
                    case 11:
                        crearEditorial();
                        break;
                    case 12:
                        eliminarEditorial();
                        break;
                    case 13:
                        editarEditorial();
                        break;
                    case 14:
                        listarEditoriales();
                        break;
                    case 15:
                        break;
                }
            }while(opcion != 15);


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void crearAutor() throws SQLException {
        System.out.println("Ingrese el nombre del autor");
        String nombre = scanner.next();

        System.out.println("Ingrese el apellido del autor");
        String ape = scanner.next();

        autorDAO.crearAutor(nombre, ape);
    }

    private void mostrarAutor() throws SQLException {
        List<String> autores = autorDAO.mostrarAutores();
        if(autores.isEmpty()){
            System.out.println("No existe el usuario");
        }else{
            autores.forEach(System.out::println);
        }

    }

    private void modificarAutor() throws SQLException {
        mostrarAutor();
        System.out.println("Ingrese el id del autor a modificar");
        int id = scanner.nextInt();

        System.out.println("Ingrese el nuevo nombre del autor");
        String nombre = scanner.next();

        System.out.println("Ingrese el nuevo apellido del autor");
        String ape = scanner.next();

        autorDAO.modificarAutor(id,nombre,ape);


    }

    private void eliminarAutor() throws SQLException {
        mostrarAutor();
        System.out.println("Ingrese el id del autor a eliminar");
        int id = scanner.nextInt();
        autorDAO.eliminarAutor(id);

    }

    private void crearGenero() throws SQLException {
        System.out.println("Ingrese el nombre del nuevo genero");
        String nombre = scanner.next();

        generoDAO.crearGenero(nombre);

    }

    private void mostrarGenero() throws SQLException{
        List<String> generos = generoDAO.mostrarGeneros();

        if(generos.isEmpty()){
            System.out.println("No hay generos registrados");
        }else{
            generos.forEach(System.out::println);
        }
    }

    private void modificarGenero() throws SQLException{
        mostrarGenero();

        System.out.println("Ingrese el id del genero a modificar");
        int id = scanner.nextInt();

        System.out.println("Ingrese el nuevo nombre del genero");
        String nombre = scanner.next();

        generoDAO.modificarGenero(id,nombre);

    }

    private void eliminarGenero() throws SQLException{
        mostrarGenero();

        System.out.println("Ingrese el id del genero a eliminar");
        int id = scanner.nextInt();

        generoDAO.eliminarGenero(id);
    }
    public void crearLibro() throws SQLException {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese el isbn del libro: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese la fecha de publicacion del libro: ");
        Date fechaPublicacion = Date.valueOf(scanner.next());

        System.out.println("Ingrese el id de la editorial del libro: ");
        int idEditorial = scanner.nextInt();

        librodao.crearLibro(titulo, isbn, fechaPublicacion, idEditorial);
    }

    public void eliminarLibro(){
        listarLibros();
        System.out.println("\n Ingrese el id del libro a eliminar: ");
        int idLibro = scanner.nextInt();

        librodao.eliminarLibro(idLibro);
    }

    public void editarLibro(){
        listarLibros();
        System.out.println("\n Ingrese el id del libro a modificar: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el nuevo nombre del libro: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el nuevo isbn del libro: ");
        int isbn = scanner.nextInt();

        System.out.println("Ingrese la fecha de publicacion del libro: ");
        Date fechaPublicacion = Date.valueOf(scanner.next());

        System.out.println("Ingrese el id de la editorial del libro: ");
        int idEditorial = scanner.nextInt();

        librodao.editarLibro(idLibro, nombre, isbn, fechaPublicacion, idEditorial);
    }
    public void listarLibros() {
        List<Libro> libros = librodao.listarLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros para mostrar");
        } else {
            for (Libro libro : libros) {
                libro.mostrarInformacion();
            }
        }
    }
    public void crearEditorial() {
        System.out.println("Ingrese el nombre de la editorial: ");
        String nombre = scanner.nextLine();

        editorialdao.crearEditorial(nombre);
    }

    public void eliminarEditorial(){
        listarEditoriales();
        System.out.println("Ingrese el id de la editorial a eliminar: ");
        int idEditorial = scanner.nextInt();

        editorialdao.eliminarEditorial(idEditorial);
    }

    public void editarEditorial(){
        listarEditoriales();
        System.out.println("Ingrese el id de la editorial a modificar: ");
        int idEditorial = scanner.nextInt();

        System.out.println("Ingrese el nuevo nombre de la editorial: ");
        String nombre = scanner.nextLine();

        editorialdao.editarEditorial(idEditorial, nombre);
    }

    public void listarEditoriales() {
        List<Editorial> editoriales = editorialdao.listarEditorial();

        if (editoriales.isEmpty()) {
            System.out.println("No hay editoriales para mostrar");
        } else {
            for (Editorial editorial : editoriales) {
                editorial.mostrarInformacion();
            }
        }
    }


}
