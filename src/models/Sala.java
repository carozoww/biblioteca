package models;

public class Sala {
    private int idSala;
    private int numeroSala;
    private String ubicacion;
    private int maxPersonas;
    private String imagen;
    private boolean habilitada;

    public Sala(int idSala, int numeroSala, String ubicacion, int maxPersonas, String imagen, boolean habilitada) {
        this.idSala = idSala;
        this.numeroSala = numeroSala;
        this.ubicacion = ubicacion;
        this.maxPersonas = maxPersonas;
        this.imagen = imagen;
        this.habilitada = habilitada;
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

    public boolean getHabilitada() { return habilitada; }

    public void mostrarInformacion(){
        System.out.printf("%-5d %-20s %-20s %-20s%n",
                getIdSala(),
                getNumeroSala(),
                getUbicacion(),
                getMaxPersonas());
    }

}
