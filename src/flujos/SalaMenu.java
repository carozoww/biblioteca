package flujos;

import dao.SalaDAO;
import dao.ReservaDAO;
import models.Sala;
import models.Reserva;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class SalaMenu {
    private final Scanner sc ;
    private final SalaDAO dao;
    private final ReservaDAO reservaDAO;

    public SalaMenu() {
        this.sc = new Scanner (System.in);
        this.dao = new SalaDAO();
        this.reservaDAO = new ReservaDAO();
    }

    public void iniciar() {
            mostrarMenu();
            int opcion = leerOpcion();
            ejecutarOpcion(opcion);
    }

    private void mostrarMenu() {
        System.out.println("\n=== Gestión de Salas ===");
        System.out.println("1. Crear Sala");
        System.out.println("2. Listar salas");
        System.out.println("3. Editar sala");
        System.out.println("4. Borrar sala");
        System.out.println("5. Gestionar reservas");
        System.out.println("6. Salir");
        System.out.print("Opción: ");
    }

    private int leerOpcion() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int opcion = sc.nextInt();
        sc.nextLine(); // limpiar buffer
        return opcion;
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearSala();
                case 2 -> listarSalas();
                case 3 -> editarSala();
                case 4 -> borrarSala();
                case 5 -> gestionarReservas();
                case 6 -> salir();
                default -> System.out.println("Opción inválida.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void crearSala() throws SQLException{

        System.out.println("ID: ");
        int id = leerEntero();
        
        System.out.println("Ubicacion: ");
        String ubicacion = sc.nextLine();

        System.out.println("MAX Personas: ");
        int maxPersonas = leerEntero();

        dao.crearSala(id, ubicacion, maxPersonas);

    }

    private void listarSalas() throws SQLException
    {
        List<Sala> salas = dao.listarSalas();

        if (salas.isEmpty()){
            System.out.println("No hay salas registradas.");
        }else {
            for (Sala sala: salas) {
                sala.mostrarInformacion();
            }
        }
    }

    private void mostrarEstadoSala() {
        System.out.println("Nuevo estado: ");
        System.out.println("1. DISPONIBLE");
        System.out.println("2. RESERVADO");
        System.out.print("Opción: ");
    }

    private void editarSala() throws SQLException{
        System.out.println("ID a editar: ");
        int idEditar = leerEntero();

        System.out.println("Nueva ubicacion: ");
        String nuevaUbicacion = sc.nextLine();

        System.out.println("Nuevo Max Personas: ");
        int nuevaMaxPersonas = leerEntero();

        mostrarEstadoSala();
        int opcion = leerOpcion();
        switch (opcion) {
            case 1 -> dao.editarSala(idEditar, nuevaUbicacion, nuevaMaxPersonas, "DISPONIBLE");
            case 2 -> dao.editarSala(idEditar, nuevaUbicacion, nuevaMaxPersonas, "RESERVADO");
            default -> System.out.println("Opción inválida.");
        }


    }

    private void borrarSala() throws SQLException{
        System.out.println("ID a borrar: ");
        int idBorrar = leerEntero();
        dao.borrarSala(idBorrar);
    }

    private void salir() {
        System.out.println("Saliendo...");
        sc.close();
        System.exit(0);
    }
    
    private void gestionarReservas() throws SQLException {
        while (true) {
            System.out.println("\n=== Reservas por Sala ===");
            System.out.println("1. Agregar reserva a sala");
            System.out.println("2. Eliminar reserva de sala");
            System.out.println("3. Listar reservas de un sala");
            System.out.println("4. Volver");
            System.out.print("Opción: ");

            int op = leerOpcion();
            switch (op) {
                case 1 -> agregarReservaASala();
                case 2 -> eliminarReservaDeSala();
                case 3 -> listarReservasDeSala();
                case 4 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarReservaASala() throws SQLException {
        this.listarSalas();
        System.out.println("Sala ID: ");
        int salaId = leerEntero();

        System.out.println("Usuario ID: ");
        int userId = leerEntero();

        System.out.println("Fecha de inicio (YYYY-MM-DD): ");
        String fecha = sc.nextLine().trim();
        Date fechaInicio = null;
        if (!fecha.isBlank()) {
            fechaInicio = Date.valueOf(LocalDate.parse(fecha));
        }

        System.out.println("Duracion de la reserva (minutos): ");
        int duracion = leerEntero();

        reservaDAO.agregarReserva(salaId, userId, fechaInicio, duracion);
    }

    private void eliminarReservaDeSala() throws SQLException {
        System.out.print("Reserva ID: ");
        int reservaId = leerEntero();

        reservaDAO.eliminarReserva(reservaId);
    }

    private void listarReservasDeSala() throws SQLException {
        System.out.print("Sala ID: ");
        int salaId = leerEntero();

        List<Reserva> reservas = reservaDAO.listarReservasDeSala(salaId);
        if (reservas.isEmpty()) {
            System.out.println("La sala no tiene reservas cargadas.");
        } else {
            reservas.forEach(Reserva::mostrarInformacion);
        }
    }


    private int leerEntero() {
        while (!sc.hasNextInt()) {
            System.out.println("Ingrese un número válido: ");
            sc.next();
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }

}