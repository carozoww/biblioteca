package flujos;

import dao.EditorialDAO;
import models.Editorial;

import java.util.List;
import java.util.Scanner;

public class EditorialMenu {
    private final EditorialDAO editorialdao;

    public EditorialMenu() {
        this.editorialdao = new EditorialDAO();
    }
    public void mostrarMenuEditorial(Scanner scanner) {
        try {
            int opcion = 0;
            do {
                System.out.println("\n === Gestion de Editoriales ===");
                System.out.println("1. Crear editorial");
                System.out.println("2. Modificar editorial");
                System.out.println("3. Eliminar editorial");
                System.out.println("4. Listar editoriales");
                System.out.println("5. Volver al menu principal");

                System.out.println("Ingrese la opcion: ");

                opcion = leerOpcion(scanner);

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
                        System.out.println("Volviendo al menú principal");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }while (opcion != 5);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

    public void crearEditorial(Scanner scanner) {
        System.out.println("Ingrese el nombre de la editorial: ");
        String nombre = scanner.nextLine();

        editorialdao.crearEditorial(nombre);
    }

    public void eliminarEditorial(Scanner scanner){
        listarEditoriales();
        System.out.println("Ingrese el id de la editorial a eliminar: ");
        int idEditorial = scanner.nextInt();

        Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);

        if(editorial == null){
            System.out.println("La editorial no existe");
            return;
        }

        editorialdao.eliminarEditorial(idEditorial);
    }

    public void editarEditorial(Scanner scanner){
        listarEditoriales();
        System.out.println("Ingrese el id de la editorial a modificar: ");
        int idEditorial = scanner.nextInt();
        scanner.nextLine();

        Editorial editorial = editorialdao.buscarEditorialPorId(idEditorial);

        if(editorial == null){
            System.out.println("La editorial no existe");
            return;
        }

        System.out.println("Ingrese el nuevo nombre de la editorial: ");
        String nombre = scanner.nextLine();

        editorialdao.editarEditorial(idEditorial, nombre);
    }

    public void listarEditoriales() {
        List<Editorial> editoriales = editorialdao.listarEditorial();

        if (editoriales.isEmpty()) {
            System.out.println("No hay editoriales para mostrar");
        } else {
            System.out.printf("%-5s %-20s%n", "ID", "Nombre");
            for (Editorial editorial : editoriales) {
                editorial.mostrarInformacion();
            }
        }
    }
}
