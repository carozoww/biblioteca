package models;

import java.time.LocalDateTime;

public class Prestamo {
    private int idPrestamo;
    private int idLibro;
    private int idLector;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private LocalDateTime fechaDevolucionEsperada;
    private String estado; // "DISPONIBLE", "RESERVADO", "NO_DISPONIBLE"

    public Prestamo(int idLibro, int idLector, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucionEsperada, String estado) {
        this.idLibro = idLibro;
        this.idLector = idLector;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
    }

    public Prestamo(int idPrestamo, int idLibro, int idLector, LocalDateTime fechaPrestamo, LocalDateTime fechaDevolucion, LocalDateTime fechaDevolucionEsperada, String estado) {
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idLector = idLector;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.estado = estado;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }
    public int getIdLibro() {
        return idLibro;
    }
    public int getIdLector() {
        return idLector;
    }
    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }
    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }
    public String getEstado() {
        return estado;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }
    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    public LocalDateTime getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    public void setFechaDevolucionEsperada(LocalDateTime fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
