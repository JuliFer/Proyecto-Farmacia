package Dominio;

import Excepcion.MedicamentoNoDisponible;

//INTERFAZ QUE IMPLEMENTA LOS MEDICAMENTOS
//Define los compartiemtnos de vender y reponer

public interface IDispensable {


    
    void dispensar() throws MedicamentoNoDisponible;
    void reponer(int cantidad);

}
