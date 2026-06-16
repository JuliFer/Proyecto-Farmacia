package Dominio;

// Hereda de Persona
public class Farmaceutico extends Persona {

    private int matricula;
    private String turno;   // "Mañana", "Tarde", "Noche"

    public Farmaceutico(String nombre, String apellido, int dni, int matricula, String turno) {
        super(nombre, apellido, dni);
        this.matricula = matricula;
        this.turno = turno;
    }

    /**
     * El farmacéutico puede reponer stock de un medicamento.
     */
    public void registrarMedicamento(Medicamento m) {
        System.out.println("Farmacéutico " + nombre + " " + apellido
                + " registró el medicamento: " + m.getNombre());
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Farmacéutico: " + nombre + " " + apellido
                + " | DNI: " + dni
                + " | Matrícula: " + matricula
                + " | Turno: " + turno);
    }

    // Getters y Setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Farmacéutico | " + super.toString() + " | Matrícula: " + matricula
                + " | Turno: " + turno;
    }
}
