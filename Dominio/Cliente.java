package Dominio;

import Excepcion.MedicamentoNoDisponible;

import java.util.ArrayList;

// Hereda de Persona
public class Cliente extends Persona {

    private ArrayList<Medicamento> historialCompras;
    private boolean tieneSocialBoolean;

    public Cliente(String nombre, String apellido, int dni) {
        super(nombre, apellido, dni);
        this.historialCompras = new ArrayList<>();
        this.tieneSocialBoolean = false;
    }

    /**
     * Intenta comprar (dispensar) un medicamento y lo agrega al historial si tiene éxito.
     */
    public void comprarMedicamento(Medicamento m) throws MedicamentoNoDisponible {
        m.dispensar();
        historialCompras.add(m);
    }

    /**
     * Muestra el historial de compras del cliente.
     */
    public void mostrarHistorial() {
        if (historialCompras.isEmpty()) {
            System.out.println(nombre + " " + apellido + " no tiene compras registradas.");
            return;
        }
        System.out.println("=== Historial de compras de " + nombre + " " + apellido + " ===");
        historialCompras.forEach(System.out::println);   // referencia a método (::)
    }

    /**
     * Calcula el total gastado por el cliente usando Stream.mapToDouble().sum()
     */
    public double getTotalGastado() {
        return historialCompras.stream()
                .mapToDouble(Medicamento::getPrecio)
                .sum();
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Cliente: " + nombre + " " + apellido
                + " | DNI: " + dni
                + " | Total gastado: $" + String.format("%.2f", getTotalGastado()));
    }

    // Getters y Setters
    public ArrayList<Medicamento> getHistorialCompras() {
        return historialCompras;
    }

    public boolean isTieneSocialBoolean() {
        return tieneSocialBoolean;
    }

    public void setTieneSocialBoolean(boolean tieneSocialBoolean) {
        this.tieneSocialBoolean = tieneSocialBoolean;
    }

    @Override
    public String toString() {
        return "Cliente | " + super.toString() + " | DNI: " + dni;
    }
}
