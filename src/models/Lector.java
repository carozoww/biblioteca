package models;

import java.time.LocalDate;

public class Lector {
    private int id_lector;
    private String nombre;
    private String correo;
    private LocalDate fecha_nacimiento;
    private String telefono;
    private String contrasena;
    private String direccion;

    public Lector() {}

    public Lector(int id_lector, String nombre, String correo, LocalDate fecha_nacimiento, String telefono, String contrasena, String direccion) {
        this.id_lector = id_lector;
        this.nombre = nombre;
        this.correo = correo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.direccion = direccion;
    }

    public int getId_lector() { 
    return id_lector; 
    }
    public void setId_lector(int id_lector) { 
    this.id_lector = id_lector; 
    }

    public String getNombre() { 
    return nombre; 
    }
    public void setNombre(String nombre) { 
    this.nombre = nombre; 
    }

    public String getCorreo() { 
    return correo; 
    }
    public void setCorreo(String correo) { 
    this.correo = correo; 
    }

    public LocalDate getFecha_nacimiento() { 
    return fecha_nacimiento; 
    }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { 
    this.fecha_nacimiento = fecha_nacimiento; 
    }

    public String getTelefono() { 
    return telefono; 
    }
    public void setTelefono(String telefono) { 
    this.telefono = telefono; 
    }

    public String getContrasena() { 
    return contrasena; 
    }
    public void setContrasena(String contrasena) { 
    this.contrasena = contrasena; 
    }

    public String getDireccion() { 
    return direccion; 
    }
    public void setDireccion(String direccion) { 
    this.direccion = direccion; 
    }

    public String toString() {
        return "Lector{" +
                "id_lector=" + id_lector +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}

