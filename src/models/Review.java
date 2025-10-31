package models;

import java.sql.Timestamp;

public class Review {
    private int idReview;
    private int idLibro;
    private int idLector;
    private int valoracion; // 1-5
    private String resenia; // texto opcional
    private Timestamp fecha;
    private String nombreLector; // para mostrar en lista
    private String nombreLibro;  // para mostrar en lista
    private int likes;

    public Review() {}

    public Review(int idReview, int idLibro, int idLector, int valoracion, String resenia,
                  Timestamp fecha, String nombreLector, String nombreLibro) {
        this.idReview = idReview;
        this.idLibro = idLibro;
        this.idLector = idLector;
        this.valoracion = valoracion;
        this.resenia = resenia;
        this.fecha = fecha;
        this.nombreLector = nombreLector;
        this.nombreLibro = nombreLibro;
    }

    // Getters y Setters
    public int getIdReview() { return idReview; }
    public void setIdReview(int idReview) { this.idReview = idReview; }
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public int getIdLector() { return idLector; }
    public void setIdLector(int idLector) { this.idLector = idLector; }
    public int getValoracion() { return valoracion; }
    public void setValoracion(int valoracion) { this.valoracion = valoracion; }
    public String getResenia() { return resenia; }
    public void setResenia(String resenia) { this.resenia = resenia; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getNombreLector() { return nombreLector; }
    public void setNombreLector(String nombreLector) { this.nombreLector = nombreLector; }
    public String getNombreLibro() { return nombreLibro; }
    public void setNombreLibro(String nombreLibro) { this.nombreLibro = nombreLibro; }
    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
