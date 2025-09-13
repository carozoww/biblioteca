package flujos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.*;
import models.*;

import java.util.*;

import basedatos.conexion;

public class menu {
    private final Scanner scanner = new Scanner(System.in);
    private final AutorMenu autormenu;
    private final GeneroMenu generomenu;
    private final LibroAutorMenu libroautormenu;
    private final LibroGeneroMenu librogeneromenu;
    private final LibroDAO librodao;
    private final EditorialDAO editorialdao;
    private final SalaMenu salamenu;
    private final AdminMenu adminmenu;
    private final AdministradorDAO administradorDAO;
    private final lectorDAO lectorDAO;
    private final prestamoDAO prestamoDAO;
    private final ComentarioDAO comentarioDAO;
    private Connection conn;
    private final LibroGeneroDAO librogeneroDAO;
    private final LibroAutorDAO libroautorDAO;

    public menu() {
        this.comentarioDAO = new ComentarioDAO();
        this.administradorDAO = new AdministradorDAO();
        this.autormenu = new AutorMenu();
        this.generomenu = new GeneroMenu();
        this.libroautormenu = new LibroAutorMenu();
        this.librogeneromenu = new LibroGeneroMenu();
        this.librodao = new LibroDAO();
        this.editorialdao = new EditorialDAO();
        this.salamenu = new SalaMenu();
        this.adminmenu = new AdminMenu();
        this.lectorDAO = new lectorDAO(null);
        this.prestamoDAO = new prestamoDAO(null);
        this.conn = conn;
        this.librogeneroDAO = new LibroGeneroDAO();
        this.libroautorDAO = new LibroAutorDAO();
    }

    public void mostrarMenu() {
        try {


            int opcion = 0;

            do {
                System.out.println("\n === Gestion de biblioteca ===");
                System.out.println("1. Gestionar autores");
                System.out.println("2. Gestionar generos");
                System.out.println("3. Gestionar autores de libros");
                System.out.println("4. Gestionar Generos de libros");
                System.out.println("7. Crear libro");
                System.out.println("8. Eliminar libro");
                System.out.println("9. Modificar libro");
                System.out.println("10. Listar libros");
                System.out.println("11. Crear editorial");
                System.out.println("12. Eliminar editorial");
                System.out.println("13. Modificar editorial");
                System.out.println("14. Listar editoriales");
                System.out.println("15. Gestionar salas");

                System.out.println("16. Gestionar Administrador");
                System.out.println("17. eliminar Comentario");
                System.out.println("18. Crear lector");
                System.out.println("19. Eliminar lector");
                System.out.println("20. Modificar lector");
                System.out.println("21. Listar lectores");
                System.out.println("22. Crear préstamo");
                System.out.println("23. Finalizar préstamo");
                System.out.println("24. Listar préstamos");
                System.out.println("25. Listar libros reservados");
                System.out.println("26. Confirmar devolución de un libro");


                System.out.println("Opcion");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        autormenu.iniciar(scanner);
                        break;
                    case 2:
                        generomenu.iniciar(scanner);
                        break;
                    case 3:
                        libroautormenu.iniciar(scanner);
                        break;
                    case 4:
                        librogeneromenu.iniciar(scanner);
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
                        salamenu.iniciar(scanner);
                        break;
                    case 16:
                        adminmenu.iniciar(scanner);
                        break;
                    case 17:
                        eliminarComentario();
                        break;
                    case 18:
                        crearLector();
                        break;
                    case 19:
                        eliminarLector();
                        break;
                    case 20:

                        editarLector();
                        break;
                    case 21:
                        listarLectores();
                        break;
                    case 22:
                        crearPrestamo();
                        break;
                    case 23:
                        eliminarPrestamo();
                        break;
                    case 24:
                        listarPrestamos();
                        break;
                    case 25:
                        listarLibrosReservados();
                        break;
                    case 26:
                        confimarDevolucion();
                        break;

                }
            } while (opcion != 35);



        }catch(Exception e){
            System.out.println(e.getMessage());
        }
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

    public void eliminarLibro() {
        listarLibros();
        System.out.println("\n Ingrese el id del libro a eliminar: ");
        int idLibro = scanner.nextInt();

        librodao.eliminarLibro(idLibro);
    }

    public void editarLibro() {
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

    public void eliminarEditorial() {
        listarEditoriales();
        System.out.println("Ingrese el id de la editorial a eliminar: ");
        int idEditorial = scanner.nextInt();

        editorialdao.eliminarEditorial(idEditorial);
    }

    public void editarEditorial() {
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

    private void crearLector() {
        try {
            System.out.println("Ingrese el id del lector: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese el nombre del lector: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese la dirección del lector: ");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese el teléfono del lector: ");
            String telefono = scanner.nextLine();

            System.out.println("Ingrese el correo del lector: ");
            String correo = scanner.nextLine();

            Lector lector = new Lector(id, nombre, direccion, telefono, correo);
            lectorDAO.crearLector(lector);

            System.out.println("Lector creado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al crear lector: " + e.getMessage());
        }
    }

    private void editarLector() {
        try {
            listarLectores();
            System.out.println("Ingrese el id del lector a modificar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese la nueva dirección: ");
            String direccion = scanner.nextLine();

            System.out.println("Ingrese el nuevo teléfono: ");
            String telefono = scanner.nextLine();

            System.out.println("Ingrese el nuevo correo: ");
            String correo = scanner.nextLine();

            Lector lectorEditado = new Lector(id, nombre, direccion, telefono, correo);

            lectorDAO.editarLector(lectorEditado);

            System.out.println("Lector editado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al editar lector: " + e.getMessage());
        }
    }

    private void listarLectores() {
        try {
            List<Lector> lectores = lectorDAO.listarLectores();

            if (lectores.isEmpty()) {
                System.out.println("No hay lectores registrados.");
            } else {
                for (Lector lector : lectores) {
                    System.out.println("ID: " + lector.getIdLector()
                            + ", Nombre: " + lector.getNombre()
                            + ", Dirección: " + lector.getDireccion()
                            + ", Teléfono: " + lector.getTelefono()
                            + ", Correo: " + lector.getCorreo());
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar lectores: " + e.getMessage());
        }
    }


    private void eliminarLector() {
        try {
            listarLectores();
            System.out.println("Ingrese el id del lector a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            lectorDAO.eliminarLector(id);
            System.out.println("Lector eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar lector: " + e.getMessage());
        }
    }


    private void crearPrestamo() {
        try {
            System.out.println("Ingrese el id del préstamo: ");
            int idPrestamo = scanner.nextInt();

            System.out.println("Ingrese el id del lector: ");
            int idLector = scanner.nextInt();

            System.out.println("Ingrese el id del libro: ");
            int idLibro = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Ingrese la fecha de préstamo (YYYY-MM-DD): ");
            LocalDate fechaPrestamo = LocalDate.parse(scanner.nextLine());

            System.out.println("Ingrese la fecha de devolución (YYYY-MM-DD): ");
            LocalDate fechaDevolucion = LocalDate.parse(scanner.nextLine());

            System.out.println("Ingrese el estado del préstamo (ej: 'activo', 'devuelto'): ");
            String estado = scanner.nextLine();

            Prestamo prestamo = new Prestamo(idPrestamo, idLector, idLibro, fechaPrestamo, fechaDevolucion, estado);
            prestamoDAO.crearPrestamo(prestamo);

            System.out.println("Préstamo creado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear préstamo: " + e.getMessage());
        }
    }

    private void listarPrestamos() {
        try {
            List<Prestamo> prestamos = prestamoDAO.listarPrestamos();

            if (prestamos.isEmpty()) {
                System.out.println("No hay préstamos registrados.");
            } else {
                for (Prestamo prestamo : prestamos) {
                    System.out.println("ID Préstamo: " + prestamo.getIdPrestamo()
                            + ", ID Lector: " + prestamo.getIdLector()
                            + ", ID Libro: " + prestamo.getIdLibro()
                            + ", Fecha Préstamo: " + prestamo.getFechaPrestamo()
                            + ", Fecha Devolución: " + prestamo.getFechaDevolucion()
                            + ", Estado: " + prestamo.getEstado());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }
    }

    private void eliminarPrestamo() {
        try {
            listarPrestamos();
            System.out.println("Ingrese el id del préstamo a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            prestamoDAO.eliminarPrestamo(id);
            System.out.println("Préstamo eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar préstamo: " + e.getMessage());
        }
    }




    public void listarLibrosReservados() {
        List<Libro> libros = librodao.listarLibrosReservados();

        if (libros.isEmpty()) {
            System.out.println("No hay libros para mostrar");
        } else {
            for (Libro libro : libros) {
                libro.mostrarInformacion();
            }
        }
    }

    public void confimarDevolucion() {
        try {
            System.out.println("Ingrese el id del libro: ");
            int idLibro = scanner.nextInt();

            prestamoDAO.confirmarDevolucion(idLibro);
        } catch (SQLException e) {
            System.out.println("Error en la devolución del libro: " + e.getMessage());
        }
    }



    private void eliminarComentario() {
        System.out.println("Ingrese el id del lector a eliminar: ");
        int id_comentario = scanner.nextInt();
        scanner.nextLine();

        comentarioDAO.eliminarComentario(id_comentario);
    }
}


