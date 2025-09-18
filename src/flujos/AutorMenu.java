package flujos;

import dao.autorDAO;
import models.Autor;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AutorMenu {

    private final autorDAO autordao;

    public AutorMenu() {
        this.autordao = new autorDAO();
    }

    public void iniciar(Scanner sc) {
        int op;
        do {
            try {
                System.out.println("\n=== Gestión de Autores ===");
                System.out.println("1. Crear autor");
                System.out.println("2. Listar autores");
                System.out.println("3. Editar autor");
                System.out.println("4. Eliminar autor");
                System.out.println("5. Volver");
                System.out.print("Opción: ");

                op = leerOpcion(sc);

                switch (op) {
                    case 1: crearAutor(sc); break;
                    case 2: mostrarAutor(); break;
                    case 3: modificarAutor(sc); break;
                    case 4: eliminarAutor(sc); break;
                    case 5: return;
                    default: System.out.println("Opción no válida");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (true);
    }

    private int leerOpcion(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
    }

    public void crearAutor(Scanner sc) throws SQLException {
        try {
            System.out.println("Ingrese el nombre del autor:");
            String nombre = sc.nextLine();

            System.out.println("Ingrese el apellido del autor:");
            String ape = sc.nextLine();

            autordao.crearAutor(nombre, ape);
            System.out.println("Autor creado correctamente.");
        } catch (SQLException e) {
            throw e;
        }
    }

    public void mostrarAutor() throws SQLException {
        try {
            List<Autor> autores = autordao.mostrarAutores();
            if (autores.isEmpty()) {
                System.out.println("No existen autores registrados en el sistema");
            } else {
                for (Autor autor : autores) {
                    autor.mostrarInfo();
                    System.out.println("-------------------------------");
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    public void modificarAutor(Scanner sc) throws SQLException {
        mostrarAutor();
        try {
            System.out.println("Ingrese el id del autor a modificar:");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.println("Ingrese el nuevo nombre del autor:");
            String nombre = sc.nextLine();

            System.out.println("Ingrese el nuevo apellido del autor:");
            String ape = sc.nextLine();

            autordao.modificarAutor(id, nombre, ape);
            System.out.println("Autor modificado correctamente.");
        } catch (SQLException e) {
            throw e;
        }
    }

    public void eliminarAutor(Scanner sc) throws SQLException {
        mostrarAutor();
        try {
            System.out.println("Ingrese el id del autor a eliminar:");
            int id = sc.nextInt();
            sc.nextLine();

            autordao.eliminarAutor(id);
            System.out.println("Autor eliminado correctamente.");
        } catch (SQLException e) {
            throw e;
        }
    }
}

