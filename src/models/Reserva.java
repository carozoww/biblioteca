package models;

import java.util.Date;

public class Reserva {
    private int id_Reserva;
    private Date fecha_in;
    private int duracion;
    private int id_sala;
    private int id_usuario;
    private String estado;
    private String lector;
    private int sala;

    public Reserva(int id_Reserva, Date fecha_in, int duracion, int id_sala, int id_usuario, String estado,  String lector, int sala) {
        this.id_Reserva = id_Reserva;
        this.fecha_in = fecha_in;
        this.duracion = duracion;
        this.id_sala = id_sala;
        this.id_usuario = id_usuario;
        this.estado = estado;
        this.lector = lector;
        this.sala = sala;
    }

    public int getId_Reserva() {return id_Reserva;}

    public Date getFecha_in() {return fecha_in;}

    public int getDuracion() {return duracion;}

    public int getId_sala() {return id_sala;}

    public int getId_usuario() {return id_usuario;}

    public String getEstado() {return estado;}

    public String getLector() {return lector;}
    public int getSala() {return sala;}

    public void mostrarInformacion(){
        System.out.printf("%-5d %-20s %-20s %-20s %-5d %-20s %-5d %-20s %-10s%n",
                getId_Reserva(),
                getFecha_in(),
                getDuracion(),
                getId_sala(),
                getSala(),
                getId_usuario(),
                getLector(),
                getEstado());

    }
}
