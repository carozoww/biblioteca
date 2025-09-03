package models;

public class Administrador {
    int  cedula;
    String nombre;
    String correo;
    String direccion;

    public Administrador(int cedula, String nombre, String correo, String direccion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }


    public void setCedula(int cedula) {
    this.cedula = cedula;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void mostrarInfo(){
        System.out.println("cedula: " + this.cedula);
        System.out.println("nombre: " + this.nombre);
        System.out.println("correo: " + this.correo);
        System.out.println("direccion: " + this.direccion);

    }

}
