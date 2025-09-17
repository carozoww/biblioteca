package models;

import java.sql.Date;

public class Libro {
    private int idLibro;
    private String titulo;
    private String isbn;
    private Date fechaPublicacion;
    private int idEditorial;

    public Libro(int idLibro, String titulo, String isbn, Date fechaPublicacion,  int idEditorial) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idEditorial = idEditorial;
    }
    public int getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }
    public void mostrarInformacion(){
        System.out.println("Id del libro: " + idLibro);
        System.out.println("Titulo: " + titulo);
        System.out.println("ISBN: " + isbn);
        System.out.println("Fecha de publicacion: " + fechaPublicacion);
        System.out.println("Id de la editorial: " + idEditorial);
        System.out.println("\n");

    }
}
