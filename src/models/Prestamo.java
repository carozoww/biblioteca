package models;

import java.time.LocalDate;

public class Prestamo {
    private int idPrestamo;
    private int idLector;
    private int idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private String estado;

    public Prestamo() {}

    public Prestamo(int idPrestamo, int idLector, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado) {
        this.idPrestamo = idPrestamo;
        this.idLector = idLector;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdPrestamo() { 
        return idPrestamo; 
    }
    public void setIdPrestamo(int idPrestamo) { 
        this.idPrestamo = idPrestamo; 
    }

    public int getIdLector() { 
        return idLector; 
    }
    public void setIdLector(int idLector) { 
        this.idLector = idLector; 
    }

    public int getIdLibro() { 
        return idLibro; 
    }
    public void setIdLibro(int idLibro) { 
        this.idLibro = idLibro; 
    }

    public LocalDate getFechaPrestamo() { 
        return fechaPrestamo; 
    }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { 
        this.fechaPrestamo = fechaPrestamo; 
    }

    public LocalDate getFechaDevolucion() { 
        return fechaDevolucion; 
    }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { 
        this.fechaDevolucion = fechaDevolucion; 
    }

    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) {
         this.estado = estado; 
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + idPrestamo +
               ", lector=" + idLector +
               ", libro=" + idLibro +
               ", fechaPrestamo=" + fechaPrestamo +
               ", fechaDevolucion=" + fechaDevolucion +
               ", estado=" + estado + "]";
    }
}

