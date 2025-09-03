package models;

import java.util.Date;

public class Reserva {
    private int id_Reserva;
    private Date fecha_in;
    private int duracion;
    private int id_sala;
    private int id_usuario;

    public Reserva(int id_Reserva, Date fecha_in, int duracion, int id_sala, int id_usuario) {
        this.id_Reserva = id_Reserva;
        this.fecha_in = fecha_in;
        this.duracion = duracion;
        this.id_sala = id_sala;
        this.id_usuario = id_usuario;
    }

    public int getId_Reserva() {
        return id_Reserva;
    }

    public Date getFecha_in() {
        return fecha_in;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getId_sala() {
        return id_sala;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void mostrarInformacion(){
        System.out.println("###############");
        System.out.println("ID: " + this.id_Reserva);
        System.out.println("Fecha Inicio: " + this.fecha_in);
        System.out.println("Duracion: " + this.duracion);
        System.out.println("ID Sala: " + this.id_sala);
        System.out.println("ID Lector: " + this.id_usuario);
    }
}
