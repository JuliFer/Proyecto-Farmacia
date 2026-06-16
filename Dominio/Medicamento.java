package Dominio;

import Excepcion.MedicamentoNoDisponible;
import java.io.Serializable;

//Clase abstracta
public abstract class Medicamento implements IDispensable, Serializable{

    protected String nombre;
    protected double precio;
    protected int stock;
    protected boolean requiereReceta;
    protected EEstadoMedicamento estado;


    public Medicamento(String nombre, double precio, int stock, boolean requiereReceta, EEstadoMedicamento estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.requiereReceta = requiereReceta;
        this.estado = stock > 0 ? EEstadoMedicamento.DISPONIBLE : EEstadoMedicamento.SIN_STOCK;
    }
    
    public Medicamento() {
    }

    public abstract String getLaboratorio();

    public void mostrarInfo() {
        System.out.println("Medicamento: " + nombre + " - Precio: $"
                + String.format("%.2f", precio)
                + " - Stock: " + stock + " - Estado: " + estado);
    }

    @Override
    public void dispensar() throws MedicamentoNoDisponible {
        if (estado == EEstadoMedicamento.SIN_STOCK) {
            throw new MedicamentoNoDisponible(
                    "El medicamento " + nombre + " no tiene stock disponible.");
        }
        if (estado == EEstadoMedicamento.VENCIDO) {
            throw new MedicamentoNoDisponible(
                    "El medicamento " + nombre + " esta vencido y no puede dispensarse.");
        }
        stock--;
        if (stock == 0) {
            estado = EEstadoMedicamento.SIN_STOCK;
        }
        System.out.println("Medicamento dispensado: " + nombre + ". Stock restante: " + stock);
    }

    @Override
    public void reponer(int cantidad) {
        this.stock += cantidad;
        if (this.estado != EEstadoMedicamento.VENCIDO) {
            this.estado = EEstadoMedicamento.DISPONIBLE;
        }
        System.out.println("Stock de " + nombre + " repuesto. Nuevo stock: " + this.stock);
    }

    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", precio)
                + " | Stock: " + stock + " | " + estado;
    }


    //Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public EEstadoMedicamento getEstado() {
        return estado;
    }

    public void setEstado(EEstadoMedicamento estado) {
        this.estado = estado;
    }

   

    

}
