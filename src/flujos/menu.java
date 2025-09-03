package flujos;

import java.sql.SQLException;

import dao.autorDAO;
import dao.generoDAO;

import java.util.List;
import java.util.Scanner;

public class menu {
    private final Scanner scanner = new Scanner(System.in);
    private final autorDAO autordao;
    private final generoDAO generodao;


    public menu() {
        this.autordao = new autorDAO();
        this.generodao = new generoDAO();
    }

    public void mostrarMenu(){
        try{


            int opcion= 0;

            do{
                System.out.println("\n === Gestion de biblioteca ===");
                System.out.println("1. Crear autor");
                System.out.println("2. Modificar autor");
                System.out.println("3. Eliminar autor");
                System.out.println("4. Crear genero");
                System.out.println("5. Modificar genero");
                System.out.println("6. Eliminar genero");
                System.out.println("9. Salir");



                System.out.println("Opcion");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch(opcion){
                    case 1: crearAutor();
                        break;
                    case 2: modificarAutor();
                        break;
                    case 3: eliminarAutor();
                        break;
                    case 4: crearGenero();
                        break;
                    case 5: modificarGenero();
                        break;
                    case 6: eliminarGenero();
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

    private void mostrarAutor() throws SQLException {
        List<String> autores = autorDAO.mostrarAutores();
        if(autores.isEmpty()){
            System.out.println("No existe el usuario");
        }else{
            autores.forEach(System.out::println);
        }

    }

    private void modificarAutor() throws SQLException {
        mostrarAutor();
        System.out.println("Ingrese el id del autor a modificar");
        int id = scanner.nextInt();

        System.out.println("Ingrese el nuevo nombre del autor");
        String nombre = scanner.next();

        System.out.println("Ingrese el nuevo apellido del autor");
        String ape = scanner.next();

        autorDAO.modificarAutor(id,nombre,ape);


    }

    private void eliminarAutor() throws SQLException {
        mostrarAutor();
        System.out.println("Ingrese el id del autor a eliminar");
        int id = scanner.nextInt();
        autorDAO.eliminarAutor(id);

    }

    private void crearGenero() throws SQLException {
        System.out.println("Ingrese el nombre del nuevo genero");
        String nombre = scanner.next();

        generoDAO.crearGenero(nombre);

    }

    private void mostrarGenero() throws SQLException{
        List<String> generos = generoDAO.mostrarGeneros();

        if(generos.isEmpty()){
            System.out.println("No hay generos registrados");
        }else{
            generos.forEach(System.out::println);
        }
    }

    private void modificarGenero() throws SQLException{
        mostrarGenero();

        System.out.println("Ingrese el id del genero a modificar");
        int id = scanner.nextInt();

        System.out.println("Ingrese el nuevo nombre del genero");
        String nomvre = scanner.next();

        generoDAO.modificarGenero(id,nomvre);

    }

    private void eliminarGenero() throws SQLException{
        mostrarGenero();

        System.out.println("Ingrese el id del genero a eliminar");
        int id = scanner.nextInt();

        generoDAO.eliminarGenero(id);
    }



}
