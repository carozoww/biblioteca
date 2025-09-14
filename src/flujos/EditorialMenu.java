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
                System.out.println("6. Salir");

                System.out.println("Ingrese la opcion: ");

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
                        {return;}
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 6);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

        editorialdao.eliminarEditorial(idEditorial);
    }

    public void editarEditorial(Scanner scanner){
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
