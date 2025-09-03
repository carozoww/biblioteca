package models;

import java.time.LocalDate;

public class Prestamo {
    private int id_prestamo;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;
    private String estado;
    private int id_libro;
    private int id_lector;

    public Prestamo() {}

    public Prestamo(int id_prestamo, LocalDate fecha_prestamo, LocalDate fecha_devolucion, String estado, int id_libro, int id_lector) {
        this.id_prestamo = id_prestamo;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.estado = estado;
        this.id_libro = id_libro;
        this.id_lector = id_lector;
    }

    public int getId_prestamo() { 
    return id_prestamo; 
    }
    public void setId_prestamo(int id_prestamo) { 
    this.id_prestamo = id_prestamo; 
    }

    public LocalDate getFecha_prestamo() { 
    return fecha_prestamo; 
    }
    public void setFecha_prestamo(LocalDate fecha_prestamo) { 
    this.fecha_prestamo = fecha_prestamo; 
    }

    public LocalDate getFecha_devolucion() { 
    return fecha_devolucion; 
    }
    public void setFecha_devolucion(LocalDate fecha_devolucion) { 
    this.fecha_devolucion = fecha_devolucion; 
    }

    public String getEstado() { 
    return estado; 
    }
    public void setEstado(String estado) { 
    this.estado = estado; 
    }

    public int getId_libro() { 
    return id_libro; 
    }
    public void setId_libro(int id_libro) { 
    this.id_libro = id_libro; 
    }

    public int getId_lector() { 
    return id_lector; 
    }
    public void setId_lector(int id_lector) { 
    this.id_lector = id_lector; 
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id_prestamo=" + id_prestamo +
                ", fecha_prestamo=" + fecha_prestamo +
                ", fecha_devolucion=" + fecha_devolucion +
                ", estado='" + estado + '\'' +
                ", id_libro=" + id_libro +
                ", id_lector=" + id_lector +
                '}';
    }
}

