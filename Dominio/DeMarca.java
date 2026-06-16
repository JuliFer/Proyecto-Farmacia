package Dominio;

import Excepcion.MedicamentoNoDisponible;

// Hereda de Medicamento
public class DeMarca extends Medicamento {

    private String marca;
    private boolean patenteVigente;

    public DeMarca(String nombre, double precio, int stock, boolean requiereReceta,
                   EEstadoMedicamento estado, String marca, boolean patenteVigente) {
        super(nombre, precio, stock, requiereReceta, estado);
        this.marca = marca;
        this.patenteVigente = patenteVigente;
    }

    @Override
    public String getLaboratorio() {
        return marca;
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
        System.out.println("  Tipo: De Marca | Marca: " + marca
                + " | Patente Vigente: " + (patenteVigente ? "Si" : "No"));
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isPatenteVigente() {
        return patenteVigente;
    }

    public void setPatenteVigente(boolean patenteVigente) {
        this.patenteVigente = patenteVigente;
    }

    @Override
    public String toString() {
        return "[De Marca] " + super.toString() + " | Marca: " + marca
                + " | Patente: " + (patenteVigente ? "Vigente" : "Vencida");
    }
}
