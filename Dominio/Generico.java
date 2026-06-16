package Dominio;

import Excepcion.MedicamentoNoDisponible;

// Hereda de Medicamento
public class Generico extends Medicamento {

    private String principioActivo;
    private String laboratorio;

    public Generico(String nombre, double precio, int stock, boolean requiereReceta,
                    EEstadoMedicamento estado, String principioActivo, String laboratorio) {
        super(nombre, precio, stock, requiereReceta, estado);
        this.principioActivo = principioActivo;
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
        System.out.println("  Tipo: Generico | Principio Activo: " + principioActivo
                + " | Laboratorio: " + laboratorio);
    }

    // Getters y Setters
    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public String toString() {
        return "[Generico] " + super.toString() + " | Principio Activo: " + principioActivo
                + " | Lab: " + laboratorio;
    }
}
