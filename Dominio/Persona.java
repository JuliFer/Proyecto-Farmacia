package Dominio;

import java.io.Serializable;

public abstract class Persona  implements Comparable <Persona>, Serializable{

    protected String nombre;
    protected String apellido;
    protected int dni;


    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    @Override
    public int compareTo(Persona o) {
        return this.apellido.compareTo(o.apellido);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        if (dni != other.dni)
            return false;
        return true;
    }

    public int hashCode(){
        return dni;
    }

    public abstract void mostrarInfo();

    @Override
    public String toString() {
        return "Nombre " + nombre + ", Apellido " + apellido ;
    }

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    

    

    

    

}
