package flujos;

import java.sql.SQLException;
import dao.autorDAO;
import java.util.Scanner;

public class menu {
    private final Scanner scanner = new Scanner(System.in);
    private final autorDAO autordao;

    public menu() {
        this.autordao = new autorDAO();
    }

    public void mostrarMenu(){
        try{


            int opcion= 0;

            do{
                System.out.println("\n === Gestion de biblioteca ===");
                System.out.println("1. Crear Autor");
                System.out.println("9. Salir");


                System.out.println("Opcion");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch(opcion){
                    case 1: crearAutor();
                        break;
                    case 9:
                        break;
                }
            }while(opcion != 9);


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void crearAutor() throws SQLException {
        System.out.println("Ingrese el nombre del autor");
        String nombre = scanner.next();

        System.out.println("Ingrese el apellido del autor");
        String ape = scanner.next();

        autorDAO.crearAutor(nombre, ape);
    }

}
