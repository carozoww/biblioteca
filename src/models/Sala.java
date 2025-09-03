package models;

public class Sala {
    private int id_sala;
    private String ubicacion;
    private int max_personas;
    private String estado;

    public Sala(int id_sala, String ubicacion, int max_personas, String estado) {
        this.id_sala = id_sala;
        this.ubicacion = ubicacion;
        this.max_personas = max_personas;
        this.estado = estado;
    }

    public int getId_sala() {
        return id_sala;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public int getMax_personas() {
        return max_personas;
    }

    public String getEstado() {
        return estado;
    }

    public void mostrarInformacion(){
        System.out.println("###############");
        System.out.println("ID: " + this.id_sala);
        System.out.println("Ubicacion: " + this.ubicacion);
        System.out.println("Personas Max.: " + this.max_personas);
        System.out.println("Estado: " + this.estado);
        System.out.println("###############");
        return;
    }

}
