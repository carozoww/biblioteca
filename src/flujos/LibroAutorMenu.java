package flujos;

import dao.LibroAutorDAO;
import models.Autor;
import models.Libro;
import models.LibroAutor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibroAutorMenu {
    private final LibroAutorDAO libroautorDAO;
    private final AutorMenu autormenu;
    private final LibroMenu libromenu;

    public LibroAutorMenu() {
        this.libroautorDAO = new LibroAutorDAO();
        this.autormenu = new AutorMenu();
        this.libromenu = new LibroMenu();
    }

    public void iniciar(Scanner sc) throws SQLException {
        int op;
        do {
            System.out.println("\n=== Gestión de autores de libros ===");
            System.out.println("1. Asignar autor a libro");
            System.out.println("2. Listar autores asignados a libro");
            System.out.println("3. Editar autor asignado a libro");
            System.out.println("4. Eliminar autor asignado a libro");
            System.out.println("5. Volver");
            System.out.print("Opción: ");

            op = leerOpcion(sc);

            switch (op) {
                case 1 -> asignarAutorALibro(sc);
                case 2 -> mostrarAutoresDeLibros();
                case 3 -> modificarAutorDeLibro(sc);
                case 4 -> eliminarAutorDeLibro(sc);
                case 5 -> { return; }
                default -> System.out.println("Opción no válida.");
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

    public void asignarAutorALibro(Scanner sc) throws SQLException {
        List<Libro> libros = libromenu.obtenerLibros();
        if(libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }
        for(Libro libro : libros) libro.mostrarInformacion();

        System.out.print("Ingrese el id del libro al cual desea asignar un autor: ");
        int id_libro = sc.nextInt();

        autormenu.mostrarAutor();
        System.out.print("Ingrese el id del autor a asignar: ");
        int id_autor = sc.nextInt();

        libroautorDAO.asignarAutorALibro(id_autor, id_libro);
    }

    public void mostrarAutoresDeLibros() throws SQLException {
        List<LibroAutor> autores = libroautorDAO.listarAutoresDeLibros();
        Map<Integer,String> mapa = new HashMap<>();
        if(autores.isEmpty()){
            System.out.println("No hay autores asignados a libros");
        } else {
            for(LibroAutor la : autores){
                mapa.put(la.getLibro().getIdLibro(), la.getLibro().getTitulo());
            }
            for(Map.Entry<Integer, String> entry : mapa.entrySet()){
                System.out.println("Libro: " + entry.getValue());
                for(LibroAutor la : autores){
                    if(entry.getKey() == la.getLibro().getIdLibro()){
                        la.getAutor().mostrarInfo();
                    }
                }
            }
        }
    }

    public void mostrarAutoresDeUnLibro(int id_libro) throws SQLException {
        List<Autor> autores = libroautorDAO.listarAutoresDeUnLibro(id_libro);
        if(autores.isEmpty()){
            System.out.println("No hay autores asignados a ese libro");
        } else {
            for(Autor autor: autores){
                autor.mostrarInfo();
            }
        }
    }

    public void modificarAutorDeLibro(Scanner sc) throws SQLException {

        List<Libro> libros = libromenu.obtenerLibros();
        if(libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }
        for(Libro libro : libros) libro.mostrarInformacion();

        System.out.print("Ingrese el id del libro cuyo autor desea modificar: ");
        int id_libro = sc.nextInt();

        mostrarAutoresDeUnLibro(id_libro);

        System.out.print("Ingrese el id del autor a reemplazar: ");
        int id_autor_old = sc.nextInt();

        autormenu.mostrarAutor();
        System.out.print("Ingrese el id del nuevo autor: ");
        int id_autor_new = sc.nextInt();

        libroautorDAO.modificarAutorDeLibro(id_libro, id_autor_old, id_autor_new);
    }

    public void eliminarAutorDeLibro(Scanner sc) throws SQLException {
        List<Libro> libros = libromenu.obtenerLibros();
        if(libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }
        for(Libro libro : libros) libro.mostrarInformacion();

        System.out.print("Ingrese el id del libro: ");
        int id_libro = sc.nextInt();

        mostrarAutoresDeUnLibro(id_libro);

        System.out.print("Ingrese el id del autor a eliminar: ");
        int id_autor = sc.nextInt();

        libroautorDAO.eliminarAutorDeLibro(id_libro, id_autor);
    }
}
