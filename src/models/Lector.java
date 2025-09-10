package models;

public class Lector {
    private int idLector;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;

    public Lector() {}

    public Lector(int idLector, String nombre, String direccion, String telefono, String correo) {
        this.idLector = idLector;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y setters
    public int getIdLector() { 
        return idLector; 
    }
    public void setIdLector(int idLector) { 
        this.idLector = idLector; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getDireccion() { 
        return direccion; 
    }
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }

    public String getTelefono() { 
        return telefono; 
    }
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getCorreo() { 
        return correo; 
    }
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    @Override
    public String toString() {
        return "Lector [id=" + idLector +
               ", nombre=" + nombre +
               ", direccion=" + direccion +
               ", telefono=" + telefono +
               ", correo=" + correo + "]";
    }
}
