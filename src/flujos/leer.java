package flujos;

import java.util.Scanner;

public class leer {


    public String leerPalabra(Scanner sc){
        sc.nextLine();
        String palabra =  sc.nextLine();
        while(palabra.isEmpty() || palabra == " "){
            if(palabra.isEmpty()){
                System.out.println("El campo no puede quedar vacío ");
            }
            palabra =  sc.nextLine();
        }
        return palabra;
    }
}
