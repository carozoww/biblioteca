package models;

public class Genero {
    private int id_genero;
    private String nombre;

    public Genero(int id_genero,String nombre){
        this.id_genero = id_genero;
        this.nombre = nombre;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void mostrarInfo(){
        System.out.println("Id genero: "+this.getId_genero());
        System.out.println("Nombre: "+this.getNombre());
    }
}
