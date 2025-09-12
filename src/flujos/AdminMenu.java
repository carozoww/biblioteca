package flujos;

import dao.AdministradorDAO;
import models.Administrador;

import java.sql.*;

import static java.util.Date.*;
import java.util.List;
import java.util.Scanner;


public class AdminMenu {

    private final AdministradorDAO admindao;

    public AdminMenu() {
        this.admindao = new AdministradorDAO();
    }

    public void iniciar(Scanner sc) throws SQLException {
        try{
            while (true) {
                System.out.println("\n=== Gestión de Administradores ===");
                System.out.println("1. Crear Administrador");
                System.out.println("2. Listar Administrador");
                System.out.println("3. Editar Administrador");
                System.out.println("4. Borrar Administrador");
                System.out.println("6. Volver");
                System.out.print("Opción: ");

                int op = leerOpcion(sc);

                switch (op) {
                    case 1: crearAdministrador(sc);
                    case 2: listarAdministradores();
                    case 3: editarAdministrador(sc);
                    case 4: eliminarAdministrador(sc);
                    case 6: {
                        return;
                    }
                }while (op != 6);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
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

    public void crearAdministrador(Scanner sc) throws SQLException {

        System.out.println("Ingrese nombre del  administrador: ");
        String nombre = sc.nextLine();

        System.out.println("Ingrese fecha Nacimiento del administrador: ");
        Date fechaNacimiento = Date.valueOf(sc.next());

        sc.nextLine();

        System.out.println("Ingrese direccion del administrador: ");
        String correo = sc.nextLine();

        admindao.crearAdministradorDAO(nombre, fechaNacimiento, correo);
    }


    public void eliminarAdministrador(Scanner sc) throws SQLException {
        listarAdministradores();
        System.out.println("Ingrese el id del administrador: ");
        int id = sc.nextInt();
        sc.nextLine();

        admindao.eliminarAdministrador(id);
    }

    public void editarAdministrador(Scanner sc) throws SQLException {

        listarAdministradores();
        System.out.println("Ingrese el id del administrador a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Ingrese nuevo nombre del administrador: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese nuevo fecha nacimiento del administrador: ");
        Date fechaNacimiento = Date.valueOf(sc.next());
        System.out.println("Ingrese nuevo correo del administrador: ");
        String correo = sc.nextLine();

        admindao.editarAdministrador(id, nombre, fechaNacimiento , correo);
    }

    public void listarAdministradores() throws SQLException {
        List<Administrador> administrador = admindao.listarAdministradores();
        if (administrador.isEmpty()) {
            System.out.println("No hay administradores para mostrar");
        } else {
            for (Administrador administrador1 : administrador) {
                administrador1.mostrarInfo();
            }
        }
    }
}