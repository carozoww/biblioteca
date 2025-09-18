package flujos;

import dao.generoDAO;
import models.Genero;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class GeneroMenu {
    private final generoDAO generodao;

    public GeneroMenu() {
        this.generodao = new generoDAO();
    }

    public void iniciar(Scanner sc) {
        int op;
        do {
            System.out.println("\n=== Gestión de Géneros ===");
            System.out.println("1. Crear género");
            System.out.println("2. Listar géneros");
            System.out.println("3. Editar género");
            System.out.println("4. Eliminar género");
            System.out.println("5. Volver");
            System.out.print("Opción: ");

            op = leerOpcion(sc);

            try {
                switch (op) {
                    case 1:
                        crearGenero(sc);
                        break;
                    case 2:
                        mostrarGenero();
                        break;
                    case 3:
                        modificarGenero(sc);
                        break;
                    case 4:
                        eliminarGenero(sc);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }

        } while (op != 5);
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

    public void crearGenero(Scanner sc) throws SQLException {
        System.out.println("Ingrese el nombre del nuevo género");
        String nombre = sc.next();
        generodao.crearGenero(nombre);
    }

    public void mostrarGenero() throws SQLException {
        List<Genero> generos = generodao.mostrarGeneros();
        if (generos.isEmpty()) {
            System.out.println("No hay géneros registrados");
        } else {
            for (Genero genero : generos) {
                genero.mostrarInfo();
            }
        }
    }

    public void modificarGenero(Scanner sc) throws SQLException {
        mostrarGenero();
        System.out.println("Ingrese el id del género a modificar");
        int id = sc.nextInt();
        System.out.println("Ingrese el nuevo nombre del género");
        String nombre = sc.next();
        generodao.modificarGenero(id, nombre);
    }

    public void eliminarGenero(Scanner sc) throws SQLException {
        mostrarGenero();
        System.out.println("Ingrese el id del género a eliminar");
        int id = sc.nextInt();
        generodao.eliminarGenero(id);
    }
}
