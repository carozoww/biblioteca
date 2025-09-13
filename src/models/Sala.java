package models;

public class Sala {
    private int idSala;
    private int numeroSala;
    private String ubicacion;
    private int maxPersonas;

    public Sala(int idSala, int numeroSala, String ubicacion, int maxPersonas) {
        this.idSala = idSala;
        this.numeroSala = numeroSala;
        this.ubicacion = ubicacion;
        this.maxPersonas = maxPersonas;
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

    public void mostrarInformacion(){
        System.out.println("###############");
        System.out.println("ID: " + this.idSala);
        System.out.println("Numero de Sala: " + this.numeroSala);
        System.out.println("Ubicacion: " + this.ubicacion);
        System.out.println("Personas Max.: " + this.maxPersonas);
    }

}
