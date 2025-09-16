package flujos;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import dao.*;
import models.*;

import java.util.*;

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
    private final LectorMenu lectormenu;
    private final LectorDAO lectorDAO;
    private final PrestamoDAO prestamoDAO;
    private final PrestamoMenu prestamomenu;
    private final ComentarioDAO comentarioDAO;
    private final LibroGeneroDAO librogeneroDAO;
    private final LibroAutorDAO libroautorDAO;
    private final PenalizacionMenu penalizacionmenu;

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
        this.lectormenu = new LectorMenu();
        this.lectorDAO = new LectorDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.prestamomenu = new PrestamoMenu(scanner, prestamoDAO, lectorDAO);
        this.librogeneroDAO = new LibroGeneroDAO();
        this.libroautorDAO = new LibroAutorDAO();
        this.penalizacionmenu = new PenalizacionMenu();
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
                System.out.println("10. Gestionar Lectores");
                System.out.println("11. Gestión de préstamos");
                System.out.println("12. Gestionar penalizaciones");
                System.out.println("15. Salir");


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
                        lectormenu.iniciar(scanner);
                        break;
                    case 11:
                        prestamomenu.mostrarMenuPrestamo();
                        break;
                    case 12:
                        //confimarDevolucion();
                        break;
                    case 13:
                        penalizacionmenu.mostarMenuPena(scanner);
                        break;

                }
            } while (opcion != 14);



        }catch(Exception e){
            System.out.println(e.getMessage());
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

  /*  public void confimarDevolucion() {
        try {
            System.out.println("Ingrese el id del libro: ");
            int idLibro = scanner.nextInt();

            prestamoDAO.confirmarDevolucion(idLibro);
        } catch (SQLException e) {
            System.out.println("Error en la devolución del libro: " + e.getMessage());
        }
    }
*/


    private void eliminarComentario() {
        System.out.println("Ingrese el id del lector a eliminar: ");
        int id_comentario = scanner.nextInt();
        scanner.nextLine();

        comentarioDAO.eliminarComentario(id_comentario);
    }
}


