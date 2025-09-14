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
    private final LibroMenu libromenu;
    private final EditorialMenu editorialmenu;
    private final LibroDAO librodao;
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
        this.libromenu = new LibroMenu();
        this.editorialmenu = new EditorialMenu();
        this.librodao = new LibroDAO();
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
                System.out.println("5. Gestionar libros");
                System.out.println("6. Gestionar editoriales");
                System.out.println("7. Gestionar salas");
                System.out.println("8. Gestionar Administrador");
                System.out.println("9. Eliminar Comentario");
                System.out.println("10. Crear lector");
                System.out.println("11. Eliminar lector");
                System.out.println("12. Modificar lector");
                System.out.println("13. Listar lectores");
                System.out.println("14. Crear préstamo");
                System.out.println("15. Finalizar préstamo");
                System.out.println("16. Listar préstamos");
                System.out.println("17. Listar libros reservados");
                System.out.println("18. Confirmar devolución de un libro");
                System.out.println("19. Gestionar penalizaciones");
                System.out.println("20. Salir");


                System.out.println("Opcion: ");

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
                    case 5:
                        libromenu.mostrarMenuLibro(scanner);
                        break;
                    case 6:
                        editorialmenu.mostrarMenuEditorial(scanner);
                        break;
                    case 7:
                        salamenu.iniciar(scanner);
                        break;
                    case 8:
                        adminmenu.iniciar(scanner);
                        break;
                    case 9:
                        eliminarComentario();
                        break;
                    case 10:
                        crearLector();
                        break;
                    case 11:
                        eliminarLector();
                        break;
                    case 12:

                        editarLector();
                        break;
                    case 13:
                        listarLectores();
                        break;
                    case 14:
                        crearPrestamo();
                        break;
                    case 15:
                        eliminarPrestamo();
                        break;
                    case 16:
                        listarPrestamos();
                        break;
                    case 17:
                        listarLibrosReservados();
                        break;
                    case 18:
                        confimarDevolucion();
                        break;
                    case 19:
                        //penalizacionmenu.mostrarMenuPena(scanner);
                        break;

                }
            } while (opcion != 20);



        }catch(Exception e){
            System.out.println(e.getMessage());
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


