package models;

public class Sala {
    private int idSala;
    private int numeroSala;
    private String ubicacion;
    private int maxPersonas;
    private String imagen;

    public Sala(int idSala, int numeroSala, String ubicacion, int maxPersonas, String imagen) {
        this.idSala = idSala;
        this.numeroSala = numeroSala;
        this.ubicacion = ubicacion;
        this.maxPersonas = maxPersonas;
        this.imagen = imagen;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getNumeroSala() {return numeroSala;}

    public String getUbicacion() {
        return ubicacion;
    }

    public int getMaxPersonas() {
        return maxPersonas;
    }

    public String getImagen() { return imagen; }

    public void mostrarInformacion(){
        System.out.printf("%-5d %-20s %-20s %-20s%n",
                getIdSala(),
                getNumeroSala(),
                getUbicacion(),
                getMaxPersonas());
    }

}
