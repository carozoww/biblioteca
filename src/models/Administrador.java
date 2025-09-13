package models;

import java.time.LocalDate;
import java.util.Date;

public class Administrador {
    private int  id;
    private String nombre;
    private String correo;
    private Date fechaNacimiento;

    public Administrador(int id, String nombre, String correo, Date fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }


    public void setId(int id) {
    this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public void mostrarInfo(){
        System.out.println("id: " + this.id);
        System.out.println("nombre: " + this.nombre);
        System.out.println("correo: " + this.correo);
        System.out.println("fechaNacimiento: " + this.fechaNacimiento);

    }

}
