package flujos;

import dao.EditorialDAO;
import dao.LibroDAO;
import models.Editorial;
import models.Libro;

import java.sql.Date;
import java.sql.SQLException;
import java.util.InputMismatchException;
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
        int opcion = 0;
        do {
            try {
                System.out.println("\n=== Gestión de Libros ===");
                System.out.println("1. Crear libro");
                System.out.println("2. Modificar libro");
                System.out.println("3. Eliminar libro");
                System.out.println("4. Listar libros");
                System.out.println("5. Volver al menú principal");
                System.out.print("Ingrese la opción: ");

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
                        return;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número válido.");
                scanner.nextLine();
                opcion = 0;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                opcion = 0;
            }
        } while (opcion != 5);
    }

    private void crearLibro(Scanner scanner) {
        try {
            System.out.print("Ingrese el título del libro: ");
            String titulo = scanner.nextLine();

            System.out.print("Ingrese el ISBN del libro: ");
            String isbn = scanner.nextLine();

            if (!librodao.existeISBN(isbn).isEmpty()) {
                System.out.println("Ya existe un libro con ese ISBN.");
                return;
            }

            System.out.print("Ingrese la fecha de publicación (YYYY-MM-DD): ");
            Date fechaPublicacion = Date.valueOf(scanner.nextLine());

            List<Editorial> editoriales = editorialdao.listarEditorial();
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales para mostrar.");
                return;
            }
            for (Editorial e : editoriales) e.mostrarInformacion();

            System.out.print("Ingrese el id de la editorial del libro: ");
            int idEditorial = scanner.nextInt();
            scanner.nextLine();

            Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);
            if (editorial == null) {
                System.out.println("La editorial no existe.");
                return;
            }

            librodao.crearLibro(titulo, isbn, fechaPublicacion, idEditorial);
            System.out.println("Libro creado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear el libro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Formato de fecha no válido.");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    private void eliminarLibro(Scanner scanner) {
        try {
            listarLibros();
            System.out.print("Ingrese el id del libro a eliminar: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine();

            Libro libro = librodao.buscarPorId(idLibro);
            if (libro == null) {
                System.out.println("No existe un libro con ese id.");
                return;
            }

            librodao.eliminarLibro(idLibro);
            System.out.println("Libro eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    private void editarLibro(Scanner scanner) {
        try {
            listarLibros();
            System.out.print("Ingrese el id del libro a modificar: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine();

            Libro libro = librodao.buscarPorId(idLibro);
            if (libro == null) {
                System.out.println("No existe un libro con ese id.");
                return;
            }

            System.out.print("Ingrese el nuevo título del libro: ");
            String titulo = scanner.nextLine();

            System.out.print("Ingrese el nuevo ISBN del libro: ");
            String isbn = scanner.nextLine();

            if (!librodao.existeISBNporId(idLibro, isbn).isEmpty()) {
                System.out.println("Ya existe un libro con ese ISBN.");
                return;
            }

            System.out.print("Ingrese la fecha de publicación (YYYY-MM-DD): ");
            Date fechaPublicacion = Date.valueOf(scanner.nextLine());

            List<Editorial> editoriales = editorialdao.listarEditorial();
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales para mostrar.");
                return;
            }
            for (Editorial e : editoriales) e.mostrarInformacion();

            System.out.print("Ingrese el id de la editorial del libro: ");
            int idEditorial = scanner.nextInt();
            scanner.nextLine();

            Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);
            if (editorial == null) {
                System.out.println("La editorial no existe.");
                return;
            }

            librodao.editarLibro(idLibro, titulo, isbn, fechaPublicacion, idEditorial);
            System.out.println("Libro actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al editar el libro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Formato de fecha no válido.");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        }
    }

    private void listarLibros() {
        try {
            List<Libro> libros = librodao.listarLibros();
            if (libros.isEmpty()) {
                System.out.println("No hay libros para mostrar.");
                return;
            }
            for (Libro libro : libros) {
                libro.mostrarInformacion();
            }
        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
    }

    public List<Libro> obtenerLibros() throws SQLException {
        return librodao.listarLibros();
    }
}
