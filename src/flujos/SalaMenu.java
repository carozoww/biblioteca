package flujos;

import dao.LectorDAO;
import dao.SalaDAO;
import dao.ReservaDAO;
import models.Lector;
import models.Sala;
import models.Reserva;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class SalaMenu {
    private final SalaDAO dao;
    private final ReservaDAO reservaDAO;
    private final leer instancia;
    private final SalaDAO saladao;
    private final LectorDAO lectorDAO;

    public SalaMenu() {
        this.dao = new SalaDAO();
        this.reservaDAO = new ReservaDAO();
        this.saladao = new SalaDAO();
        this.lectorDAO = new LectorDAO();
        this.instancia = new leer();
    }

    public void iniciar(Scanner sc) throws SQLException{
        while (true) {
            System.out.println("\n=== Gestión de Salas ===");
            System.out.println("1. Registrar Sala");
            System.out.println("2. Listar salas");
            System.out.println("3. Editar sala");
            System.out.println("4. Borrar sala");
            System.out.println("5. Gestionar reservas");
            System.out.println("6. Volver");
            System.out.print("Opción: ");

            int op = leerEntero(sc);
            switch (op) {
                case 1 -> crearSala(sc);
                case 2 -> listarSalas();
                case 3 -> editarSala(sc);
                case 4 -> borrarSala(sc);
                case 5 -> gestionarReservas(sc);
                case 6 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void crearSala(Scanner sc) throws SQLException{

        System.out.println("Número de sala: ");
        int numeroSala = leerEntero(sc);

        System.out.println("Ubicacion: ");
        String ubicacion = instancia.leerPalabra(sc);

        System.out.println("Máximo de Personas: ");
        int maxPersonas = leerEntero(sc);

        dao.crearSala(numeroSala, ubicacion, maxPersonas);

    }

    private void listarSalas() throws SQLException {
        List<Sala> salas = dao.listarSalas();

        if (salas.isEmpty()){
            System.out.println("No hay salas registradas.");
        }else {
            System.out.printf("%-5s %-20s %-20s %-20s%n",
                    "ID", "Numero de sala", "Ubicacion", "Max. Personas");
            for (Sala sala: salas) {
                sala.mostrarInformacion();
            }
        }
    }

    private void editarSala(Scanner sc) throws SQLException{
        System.out.println("ID a editar: ");
        int idEditar = leerEntero(sc);

        Sala sala = saladao.buscarSalaPorId(idEditar);

        if(sala == null){
            System.out.println("No existe sala con ese id");
            return;
        }

        System.out.println("Nuevo numero de sala: ");
        int numeroSala = leerEntero(sc);

        System.out.println("Nueva ubicacion: ");
        String nuevaUbicacion = instancia.leerPalabra(sc);

        System.out.println("Nuevo Max Personas: ");
        int nuevaMaxPersonas = leerEntero(sc);

        dao.editarSala(idEditar, numeroSala, nuevaUbicacion, nuevaMaxPersonas);
    }

    private void borrarSala(Scanner sc) throws SQLException{
        listarSalas();
        System.out.println("ID a borrar: ");
        int idBorrar = leerEntero(sc);

        Sala sala = saladao.buscarSalaPorId(idBorrar);

        if(sala == null){
            System.out.println("No existe sala con ese id");
            return;
        }

        dao.borrarSala(idBorrar);
    }

    private int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Ingrese un número válido: ");
            sc.next();
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }

    private void gestionarReservas(Scanner sc) throws SQLException {
        while (true) {
            System.out.println("\n=== Reservas por Sala ===");
            System.out.println("1. Agregar reserva a sala");
            System.out.println("2. Eliminar reserva de sala");
            System.out.println("3. Listar reservas de un sala");
            System.out.println("4. Volver");
            System.out.print("Opción: ");

            int op = leerEntero(sc);
            switch (op) {
                case 1 -> agregarReservaASala(sc);
                case 2 -> eliminarReservaDeSala(sc);
                case 3 -> listarReservasDeSala(sc);
                case 4 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarReservaASala(Scanner sc) throws SQLException {
        this.listarSalas();
        System.out.println("Sala ID: ");
        int salaId = leerEntero(sc);

        Sala sala = saladao.buscarSalaPorId(salaId);
        if(sala == null){
            System.out.println("No existe sala con ese id");
            return;
        }

        System.out.println("Usuario ID: ");
        int userId = leerEntero(sc);

        Lector lectorActual = lectorDAO.buscarPorId(userId);
        if (lectorActual == null) {
            System.out.println("No se encontró el lector con ID " + userId);
            return;
        }

        System.out.println("Fecha de inicio (YYYY-MM-DD HH:mm:ss): ");
        String fecha = sc.nextLine().trim();
        Timestamp fechaInicio = null;
        if (!fecha.isBlank()) {
            fechaInicio = Timestamp.valueOf(fecha);
        }

        System.out.println("Duracion de la reserva (minutos): ");
        int duracion = leerEntero(sc);

        reservaDAO.agregarReserva(salaId, userId, fechaInicio, duracion);
    }

    private void eliminarReservaDeSala(Scanner sc) throws SQLException {
        System.out.print("Reserva ID: ");
        int reservaId = leerEntero(sc);

        Reserva reserva = reservaDAO.buscarReservaPorId(reservaId);
        if (reserva == null) {
            System.out.println("Reserva no encontrada");
            return;
        }

        reservaDAO.eliminarReserva(reservaId);
    }

    private void listarReservasDeSala(Scanner sc) throws SQLException {
        System.out.print("Sala ID: ");
        int salaId = leerEntero(sc);

        List<Reserva> reservas = reservaDAO.listarReservasDeSala(salaId);
        if (reservas.isEmpty()) {
            System.out.println("La sala no tiene reservas cargadas.");
        } else {
            reservas.forEach(Reserva::mostrarInformacion);
        }
    }
}