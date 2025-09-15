package models;

public class Penalizacion {
    private int id_pena;
    private int duracion;
    private String motivo;
    private int id_lector;
    private int id_admin;

    public Penalizacion(int id_pena, int duracion, String motivo, int id_lector, int id_admin) {
        this.id_pena = id_pena;
        this.duracion = duracion;
        this.motivo = motivo;
        this.id_lector = id_lector;
        this.id_admin = id_admin;
    }

    public int getId_pena() {
        return id_pena;
    }
    public void setId_pena(int id_pena) {
        this.id_pena = id_pena;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public int getId_lector() {
        return id_lector;
    }
    public void setId_lector(int id_lector) {
        this.id_lector = id_lector;    }
    public int getId_admin() {
        return id_admin;
    }
    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public void mostrarInformacion(){
        System.out.println("Id de la penalizacion: " + this.id_pena);
        System.out.println("Duracion en dias: " + this.duracion);
        System.out.println("Motivo: " + this.motivo);
        System.out.println("Id del lector: " + this.id_lector);
        System.out.println("Id del admin: " + this.id_admin);
        System.out.println("\n");
    }
}
