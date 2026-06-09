package Excepcion;

//Extiende de exception porque es checked
public class MedicamentoNoDisponible extends Exception {

    public MedicamentoNoDisponible(String mensaje){
        super(mensaje);
    }

}
