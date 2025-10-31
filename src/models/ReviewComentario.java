package models;
import java.sql.Timestamp;

public class ReviewComentario {
                        private int idComentario;
                        private int idReview;
                        private int idLector;
                        private String contenido;
                        private Timestamp fecha;
                        private String nombreLector;
                        private String imagenLector;

    public ReviewComentario() {}

    public ReviewComentario(int idComentario, int idReview, int idLector, String contenido, Timestamp fecha, String nombreLector, String imagenLector) {
        this.idComentario = idComentario;
        this.idReview = idReview;
        this.idLector = idLector;
        this.contenido = contenido;
        this.fecha = fecha;
        this.nombreLector = nombreLector;
        this.imagenLector = imagenLector;
    }

    // Getters y Setters
    public int getIdComentario() { return idComentario; }
    public void setIdComentario(int idComentario) { this.idComentario = idComentario; }
    public int getIdReview() { return idReview; }
    public void setIdReview(int idReview) { this.idReview = idReview; }
    public int getIdLector() { return idLector; }
    public void setIdLector(int idLector) { this.idLector = idLector; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getNombreLector() { return nombreLector; }
    public void setNombreLector(String nombreLector) { this.nombreLector = nombreLector; }
    public String getImagenLector() { return imagenLector; }
    public void setImagenLector(String imagenLector) { this.imagenLector = imagenLector; }

}
