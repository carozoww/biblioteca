package flujos;

import dao.EditorialDAO;
import models.Editorial;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class EditorialMenu {

    private final EditorialDAO editorialdao;

    public EditorialMenu() {
        this.editorialdao = new EditorialDAO();
    }

    public void mostrarMenuEditorial(Scanner scanner) {
        int opcion = 0;
        do {
            try {
                System.out.println("\n=== Gestión de Editoriales ===");
                System.out.println("1. Crear editorial");
                System.out.println("2. Modificar editorial");
                System.out.println("3. Eliminar editorial");
                System.out.println("4. Listar editoriales");
                System.out.println("5. Desactivar editorial");
                System.out.println("6. Volver al menú principal");

                System.out.print("Ingrese la opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        crearEditorial(scanner);
                        break;
                    case 2:
                        editarEditorial(scanner);
                        break;
                    case 3:
                        eliminarEditorial(scanner);
                        break;
                    case 4:
                        listarEditoriales();
                        break;
                    case 5:
                        desactivarEditorial(scanner);
                        break;
                    case 6:
                        System.out.println("Volviendo al menú principal...");
                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número válido.");
                scanner.nextLine();
                opcion = 0;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                opcion = 0;
            }
        } while (opcion != 6);
    }

    public void crearEditorial(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre de la editorial: ");
            String nombre = scanner.nextLine();
            editorialdao.crearEditorial(nombre);
            System.out.println("Editorial creada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la editorial: " + e.getMessage());
        }
    }

    public void editarEditorial(Scanner scanner) {
        try {
            listarEditoriales();
            System.out.print("Ingrese el id de la editorial a modificar: ");
            int idEditorial = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese el nuevo nombre de la editorial: ");
            String nombre = scanner.nextLine();

            editorialdao.editarEditorial(idEditorial, nombre);
            System.out.println("Editorial modificada correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("Error al modificar la editorial: " + e.getMessage());
        }
    }

    public void eliminarEditorial(Scanner scanner) {
        try {
            listarEditoriales();
            System.out.print("Ingrese el id de la editorial a eliminar: ");
            int idEditorial = scanner.nextInt();
            scanner.nextLine();

            editorialdao.eliminarEditorial(idEditorial);
            System.out.println("Editorial eliminada correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("Error al eliminar la editorial: " + e.getMessage());
        }
    }

    public void listarEditoriales() {
        try {
            List<Editorial> editoriales = editorialdao.listarEditorial();
            if (editoriales.isEmpty()) {
                System.out.println("No hay editoriales para mostrar.");
            } else {
                for (Editorial editorial : editoriales) {
                    editorial.mostrarInformacion();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar editoriales: " + e.getMessage());
        }
    }

    public void desactivarEditorial(Scanner scanner) {
        try {
            listarEditoriales();
            System.out.print("Ingrese el id de la editorial a desactivar: ");
            int idEditorial = scanner.nextInt();
            scanner.nextLine();

            editorialdao.desasignarEditorial(idEditorial);
            System.out.println("Editorial desasignada correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("Error al desasignar la editorial: " + e.getMessage());
        }
    }
}
