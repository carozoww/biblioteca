package flujos;

import dao.PenalizacionDAO;
import dao.lectorDAO;
import models.Penalizacion;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PenalizacionMenu {

    private final PenalizacionDAO penalizaciondao;
    private final lectorDAO lectordao = new lectorDAO(null);

    public PenalizacionMenu() {
        this.penalizaciondao = new PenalizacionDAO();
    }

    public void mostarMenuPena(Scanner scanner) {
        try {
            int opcion = 0;
            do {
                System.out.println("\n === Gestion de Penalizaciones ===");
                System.out.println("1. Crear penalizacion");
                System.out.println("2. Eliminar penalizacion");
                System.out.println("3. Listar penalizaciones activas");
                System.out.println("4. Volver al menu principal");

                System.out.println("Ingrese la opcion: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        aplicarPenalizacion(scanner);
                        break;
                    case 2:
                        quitarPenalizacion(scanner);
                        break;
                    case 3:
                        listarPenalizaciones();
                        break;
                    case 4: {
                        return;
                    }
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 5);
        } catch (SQLException e) {
            System.out.println("Error en la base de datos" + e.getMessage());
        }
    }
    public void aplicarPenalizacion(Scanner scanner) throws SQLException {
        //lectorDAO.listarLectores();
        System.out.println("Ingrese el id del lector a penalizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        List<Penalizacion> penaActiva = penalizaciondao.buscarPenalizacionActivaPorUsuario(id);
        if (!penaActiva.isEmpty()) {
            System.out.println("Error: ya existe una penalizacion activa para este usuario");
            return;
        }

        System.out.println("Ingrese la duracion (en dias) de la penalizacion: ");
        int duracion = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el motivo de la penalizacion: ");
        String motivo = scanner.nextLine();

        System.out.println("Ingrese el id del administrador: ");
        int idAd = scanner.nextInt();

        penalizaciondao.crearPenalizacion(duracion, motivo, id, idAd);
    }

    public void quitarPenalizacion(Scanner scanner) throws SQLException {

        listarPenalizaciones();
        System.out.println("Ingrese el id del lector para quitarle la penalizacion: ");
        int id = scanner.nextInt();

        List<Penalizacion> penaActiva = penalizaciondao.buscarPenalizacionActivaPorUsuario(id);
        if(penaActiva.isEmpty()) {
            System.out.println("Error: no existe una penalizacion activa para este usuario");
            return;
        }

        penalizaciondao.eliminarPenalizacion(id);
    }

    public void listarPenalizaciones() {
        List<Penalizacion> pena = penalizaciondao.listarPenalizaciones();

        if (pena.isEmpty()) {
            System.out.println("No hay penalizaciones para mostrar");
        } else {
            for (Penalizacion penalizacion : pena) {
                penalizacion.mostrarInformacion();
            }
        }
    }
}

