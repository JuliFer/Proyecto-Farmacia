package Dominio;

import java.io.Serializable;

//Implementamos dos interfaces 
//Comparable - Se comparan con otras personas con Collection.sort
//Serializable - Interfaz vacia
public abstract class Persona  implements Comparable <Persona>, Serializable{

    protected String nombre;
    protected String apellido;
    protected int dni;



    //Constructor
    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    //Utilizamos nuestra interfaz Comparable
    //Ordena por apellido
    @Override
    public int compareTo(Persona o) {
        return this.apellido.compareTo(o.apellido);
    }


    //Define que dos personas son la misma si tienen el mismo DNI
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


    //Metodo abstracto
    //Todas las subclases de personas tienen que mostrar este metodo.
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
