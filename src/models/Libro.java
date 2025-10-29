package models;

import java.sql.Date;

public class Libro {
    private int idLibro;
    private String titulo;
    private String isbn;
    private Date fechaPublicacion;
    private int idEditorial;
    private String editorial;
    private Boolean estado_asignacion;
    private String sinopsis;
    private int numpaginas;
    private String nombreAutor;
    private String imagen_url;

    public Libro(int idLibro, String titulo, String isbn, Date fechaPublicacion,  int idEditorial,String editorial,String sinopsis,int numpaginas) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idEditorial = idEditorial;
        this.editorial = editorial;
        this.estado_asignacion = true;
        this.sinopsis = sinopsis;
        this.numpaginas = numpaginas;

    }
    public Libro(int idLibro, String titulo, String isbn, Date fechaPublicacion, int idEditorial, String editorial) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idEditorial = idEditorial;
        this.editorial = editorial;
        this.estado_asignacion = true;
    }

    public Libro(int idLibro,String titulo, String isbn, Date fechaPublicacion,  int idEditorial,String editorial,String sinopsis,int numpaginas,String nombreAutor,String imagen_url) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.idEditorial = idEditorial;
        this.editorial = editorial;
        this.estado_asignacion = true;
        this.sinopsis = sinopsis;
        this.numpaginas = numpaginas;
        this.nombreAutor = nombreAutor;
        this.imagen_url = imagen_url;
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

    public String getEditorial() {return editorial;}
    public void setEditorial(String editorial) {this.editorial = editorial;}

    public Boolean getEstado_asignacion() {
        return estado_asignacion;
    }
    public void setEstado_asignacion(Boolean estado_asignacion) {
        this.estado_asignacion = estado_asignacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdEditorial() {
        return idEditorial;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getNumpaginas() {
        return numpaginas;
    }

    public void setNumpaginas(int numpaginas) {
        this.numpaginas = numpaginas;
    }

    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }
    public void mostrarInformacion(){
            System.out.printf("%-5d %-50s %-20s %-30s %-30s %-15s %n",
                    getIdLibro(),
                    getTitulo(),
                    getIsbn(),
                    getFechaPublicacion(),
                    getEditorial() != null ? getEditorial() : "Sin editorial",
                    getEstado_asignacion());
    }
}
