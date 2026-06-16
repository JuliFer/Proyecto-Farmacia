package Dominio;

import java.io.Serializable;

import Excepcion.MedicamentoNoDisponible;

//Clase abstracta
//Implementamos los métodos dispensar y reponer por IDispensable

public abstract class Medicamento implements IDispensable, Serializable {

    protected String nombre;
    protected double precio;
    protected int stock;
    protected boolean requiereReceta;
    protected EEstadoMedicamento estado;

    public Medicamento(String nombre, double precio, int stock, boolean requiereReceta, EEstadoMedicamento estado) {
        this.nombre = nombre;
        this.precio = (precio >= 0) ? precio : 0; // validación simple
        this.stock = Math.max(stock, 0);          // evita stock negativo
        this.requiereReceta = requiereReceta;
        // Si el stock es mayor a 0, el estado es DISPONIBLE, sino SIN_STOCK
        this.estado = this.stock > 0 ? EEstadoMedicamento.DISPONIBLE : EEstadoMedicamento.SIN_STOCK;
    }

    public Medicamento() {
    }

    // Es abstracta porque cada tipo de medicamento se implementa de manera diferente
    public abstract String getLaboratorio();

    public void mostrarInfo() {
        System.out.println("Medicamento: " + nombre + " - Precio: "
                + String.format("%.2f", precio)
                + " - Stock: " + stock + " - Estado: " + estado);
    }

    // Sobreescribimos nuestra interfaz IDispensable
    @Override
    public void dispensar() throws MedicamentoNoDisponible {
        if (estado == EEstadoMedicamento.SIN_STOCK) {
            throw new MedicamentoNoDisponible(
                    "El medicamento " + nombre + " no tiene stock disponible.");
        }
        if (estado == EEstadoMedicamento.VENCIDO) {
            throw new MedicamentoNoDisponible(
                    "El medicamento " + nombre + " está vencido y no puede dispensarse.");
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
        // Si el medicamento está vencido, agregar stock no lo hace disponible
        if (this.estado != EEstadoMedicamento.VENCIDO) {
            this.estado = EEstadoMedicamento.DISPONIBLE;
        }
        System.out.println("Stock de " + nombre + " repuesto. Nuevo stock: " + this.stock);
    }

    // Cómo se ve el objeto cuando lo imprimimos
    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", precio)
                + " | Stock: " + stock + " | " + estado;
    }


    //Setter del atributo stock
     public void setStock(int stock) {
        this.stock = Math.max(stock, 0); // Negativo a 0
        // Si el stock quedó en 0 y el medicamento NO está vencido,
        // entonces el estado pasa a SIN_STOCK.
        if (this.stock == 0 && this.estado != EEstadoMedicamento.VENCIDO) {
            this.estado = EEstadoMedicamento.SIN_STOCK;
        // Si el stock es mayor a 0 y el medicamento NO está vencido,
         // entonces el estado pasa a DISPONIBLE.
        } else if (this.stock > 0 && this.estado != EEstadoMedicamento.VENCIDO) {
            this.estado = EEstadoMedicamento.DISPONIBLE;
        }
    }

    
    // Metodo opcional
    //Devuelve true si el medicamento esta disponible
    //Devuelve false si esta sin stock o vencido
    public boolean estaDisponible() {
        return this.estado == EEstadoMedicamento.DISPONIBLE;
    }

    // Getters y setters
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
        this.precio = (precio >= 0) ? precio : 0;
    }

    public int getStock() {
        return stock;
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
