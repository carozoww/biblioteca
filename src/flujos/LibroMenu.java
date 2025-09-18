package flujos;

import dao.EditorialDAO;
import dao.LibroDAO;
import models.Editorial;
import models.Libro;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibroMenu {

    private final LibroDAO librodao;
    private final EditorialDAO editorialdao;

    public LibroMenu() {
        this.librodao = new LibroDAO();
        this.editorialdao = new EditorialDAO();
    }

    public void mostrarMenuLibro(Scanner scanner) {
        try {
            int opcion = 0;
            do {
                System.out.println("\n === Gestion de Libros ===");
                System.out.println("1. Crear libro");
                System.out.println("2. Modificar libro");
                System.out.println("3. Eliminar libro");
                System.out.println("4. Listar libros");
                System.out.println("5. Volver al menu principal");

                System.out.println("Ingrese la opcion: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        crearLibro(scanner);
                        break;
                    case 2:
                        editarLibro(scanner);
                        break;
                    case 3:
                        eliminarLibro(scanner);
                        break;
                    case 4:
                        listarLibros();
                        break;
                    case 5:
                        {return;}
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }while (opcion != 6);
        }catch (SQLException e){
            System.out.println("Error en la base de datos" + e.getMessage());
        }
    }


    public void crearLibro(Scanner scanner) throws SQLException {
        System.out.println("Ingrese el titulo del libro: ");
        String titulo = scanner.nextLine();

        System.out.println("Ingrese el isbn del libro: ");
        String isbn = scanner.nextLine();

        List<Libro> libro = librodao.existeISBN(isbn);
        if(!libro.isEmpty()){
            System.out.println("Ya existe un libro con ese isbn");
            return;
        }

        System.out.println("Ingrese la fecha de publicacion del libro(formato: YYYY-MM-DD): ");
        Date fechaPublicacion = Date.valueOf(scanner.next());

        List<Editorial> editoriales = editorialdao.listarEditorial();
        if(editoriales.isEmpty()){
            System.out.println("No hay editoriales para mostrar");
        } else {
            for(Editorial editorial : editoriales){
                editorial.mostrarInformacion();
            }
        }

        System.out.println("Ingrese el id de la editorial del libro: ");
        int idEditorial = scanner.nextInt();

        Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);

        if(editorial == null){
            System.out.println("La editorial no existe");
            return;
        }

        librodao.crearLibro(titulo, isbn, fechaPublicacion, idEditorial);
    }

    public void eliminarLibro(Scanner scanner) {
        listarLibros();
        System.out.println("\n Ingrese el id del libro a eliminar: ");
        int idLibro = scanner.nextInt();

        Libro libro = librodao.buscarPorId(idLibro);

        if(libro == null){
            System.out.println("Error: no existe un libro con ese id");
            return;
        }

        librodao.eliminarLibro(idLibro);
    }

    public void editarLibro(Scanner scanner) {
        listarLibros();
        System.out.println("\n Ingrese el id del libro a modificar: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine();

        Libro libro = librodao.buscarPorId(idLibro);

        if(libro == null){
            System.out.println("Error: no existe un libro con ese id");
            return;
        }

        System.out.println("Ingrese el nuevo nombre del libro: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el nuevo isbn del libro: ");
        String isbn = scanner.nextLine();

        List<Libro> libro1 = librodao.existeISBNporId(idLibro,isbn);
        if(!libro1.isEmpty()){
            System.out.println("Ya existe un libro con ese isbn");
            return;
        }


        System.out.println("Ingrese la fecha de publicacion del libro(formato: YYYY-MM-DD): ");
        Date fechaPublicacion = Date.valueOf(scanner.next());

        List<Editorial> editoriales = editorialdao.listarEditorial();
        if(editoriales.isEmpty()){
            System.out.println("No hay editoriales para mostrar");
            return;
        } else {
            for(Editorial editorial : editoriales){
                editorial.mostrarInformacion();
            }
        }

        System.out.println("Ingrese el id de la editorial del libro: ");
        int idEditorial = scanner.nextInt();

        Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);

        if(editorial == null){
            System.out.println("La editorial no existe");
            return;
        }

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

}


