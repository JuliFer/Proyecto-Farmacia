package Dominio;

import Excepcion.MedicamentoNoDisponible;

// Hereda de Medicamento
public class Controlado extends Medicamento {

    private int nivelControl;   // ej: 1, 2, 3 según grado de control
    private String autoridad;   // organismo que autoriza la venta

    public Controlado(String nombre, double precio, int stock, boolean requiereReceta,
                      EEstadoMedicamento estado, int nivelControl, String autoridad) {
        super(nombre, precio, stock, requiereReceta, estado);
        this.nivelControl = nivelControl;
        this.autoridad = autoridad;
    }

    @Override
    public String getLaboratorio() {
        return autoridad;
    }

    @Override
    public void dispensar() throws MedicamentoNoDisponible {
        // Los medicamentos controlados siempre requieren receta
        if (!requiereReceta) {
            System.out.println("Advertencia: medicamento controlado dispensado sin receta registrada.");
        }
        super.dispensar();
    }

    @Override
    public void reponer(int cantidad) {
        super.reponer(cantidad);
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("  Tipo: Controlado | Nivel de Control: " + nivelControl
                + " | Autoridad: " + autoridad);
    }

    // Getters y Setters
    public int getNivelControl() {
        return nivelControl;
    }

    public void setNivelControl(int nivelControl) {
        this.nivelControl = nivelControl;
    }

    public String getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(String autoridad) {
        this.autoridad = autoridad;
    }

    @Override
    public String toString() {
        return "[Controlado] " + super.toString() + " | Nivel: " + nivelControl
                + " | Autoridad: " + autoridad;
    }
}
