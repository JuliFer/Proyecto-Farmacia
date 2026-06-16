package Dominio;

import Excepcion.MedicamentoNoDisponible;

// Hereda de Medicamento
public class Generico extends Medicamento {

    private String laboratorio;

    public Generico(String nombre, double precio, int stock, boolean requiereReceta,
                    EEstadoMedicamento estado, String laboratorio) {
        super(nombre, precio, stock, requiereReceta, estado);
        this.laboratorio = laboratorio;
    }

    @Override
    public String getLaboratorio() {
        return laboratorio;
    }

    @Override
    public void dispensar() throws MedicamentoNoDisponible {
        super.dispensar();
    }

    @Override
    public void reponer(int cantidad) {
        super.reponer(cantidad);
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("  Tipo: Generico | Laboratorio: " + laboratorio);
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public String toString() {
        return "[Generico] " + super.toString() + " | Lab: " + laboratorio;
    }
}
