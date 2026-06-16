package Dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Clase principal que gestiona el sistema de la farmacia
public class Farmacia {

    // ArrayList para el stock de medicamentos
    private ArrayList<Medicamento> medicamentos;

    // HashMap indexado por DNI para clientes y farmacéuticos
    private HashMap<Integer, Persona> personas;

    // HashSet para clientes que realizaron al menos una compra (evita duplicados)
    private HashSet<Persona> clientesConCompras;

    public Farmacia() {
        this.medicamentos = new ArrayList<>();
        this.personas = new HashMap<>();
        this.clientesConCompras = new HashSet<>();
    }

    // =========================================================
    //  GESTIÓN DE PERSONAS
    // =========================================================

    /**
     * Registra un cliente o farmacéutico indexado por DNI.
     * Si ya existe, informa al usuario.
     */
    public void registrarPersona(Persona p) {
        if (personas.containsKey(p.getDni())) {
            System.out.println("Ya existe una persona registrada con DNI " + p.getDni());
        } else {
            personas.put(p.getDni(), p);
            System.out.println("Persona registrada: " + p.getNombre() + " " + p.getApellido());
        }
    }

    /**
     * Busca un cliente por DNI y lo devuelve si existe y es Cliente.
     */
    public Cliente buscarCliente(int dni) {
        Persona p = personas.get(dni);
        if (p instanceof Cliente) {
            return (Cliente) p;
        }
        System.out.println("No se encontró un cliente con DNI " + dni);
        return null;
    }

    // =========================================================
    //  GESTIÓN DE MEDICAMENTOS
    // =========================================================

    /**
     * Agrega un medicamento al stock de la farmacia.
     */
    public void agregarMedicamento(Medicamento m) {
        medicamentos.add(m);
        System.out.println("Medicamento agregado: " + m.getNombre());
    }

    /**
     * Busca un medicamento por nombre usando Stream.filter() + findFirst().
     */
    public Optional<Medicamento> buscarMedicamento(String nombre) {
        return medicamentos.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    /**
     * Filtra medicamentos disponibles y retorna una nueva lista (Streams + Collectors).
     */
    public List<Medicamento> mostrarDisponibles() {
        List<Medicamento> disponibles = medicamentos.stream()
                .filter(m -> m.getEstado() == EEstadoMedicamento.DISPONIBLE)
                .collect(Collectors.toList());

        if (disponibles.isEmpty()) {
            System.out.println("No hay medicamentos disponibles.");
        } else {
            System.out.println("=== Medicamentos disponibles ===");
            disponibles.forEach(Medicamento::mostrarInfo);  // referencia a método (::)
        }
        return disponibles;
    }

    /**
     * Genera alerta de stock bajo filtrando medicamentos con stock menor al mínimo indicado.
     */
    public void alertaStockBajo(int minimo) {
        List<Medicamento> bajoStock = medicamentos.stream()
                .filter(m -> m.getStock() < minimo)
                .collect(Collectors.toList());

        if (bajoStock.isEmpty()) {
            System.out.println("Todos los medicamentos tienen stock suficiente (>= " + minimo + ").");
        } else {
            System.out.println("=== ALERTA: Medicamentos con stock bajo (< " + minimo + ") ===");
            bajoStock.forEach(Medicamento::mostrarInfo);
        }
    }

    // =========================================================
    //  VENTA / DISPENSACIÓN
    // =========================================================

    /**
     * Vende un medicamento a un cliente: lo dispensa y lo agrega al historial.
     * Agrega al cliente al HashSet de clientesConCompras.
     */
    public void venderMedicamento(String nombreMedicamento, int dniCliente) {
        Cliente cliente = buscarCliente(dniCliente);
        if (cliente == null) return;

        Optional<Medicamento> opt = buscarMedicamento(nombreMedicamento);
        if (opt.isEmpty()) {
            System.out.println("Medicamento '" + nombreMedicamento + "' no encontrado.");
            return;
        }

        Medicamento med = opt.get();
        try {
            cliente.comprarMedicamento(med);
            clientesConCompras.add(cliente);
            System.out.println("Venta registrada para " + cliente.getNombre()
                    + ". Total gastado: $" + String.format("%.2f", cliente.getTotalGastado()));
        } catch (Exception e) {
            System.out.println("Error al dispensar: " + e.getMessage());
        } finally {
            System.out.println("-- Operación de venta finalizada --");
        }
    }

    // =========================================================
    //  REPOSICIÓN DE STOCK
    // =========================================================

    /**
     * Repone stock de un medicamento buscado por nombre.
     */
    public void reponerMedicamento(String nombreMedicamento, int cantidad) {
        Optional<Medicamento> opt = buscarMedicamento(nombreMedicamento);
        if (opt.isPresent()) {
            opt.get().reponer(cantidad);
        } else {
            System.out.println("Medicamento '" + nombreMedicamento + "' no encontrado.");
        }
    }

    // =========================================================
    //  ORDENAMIENTO (Collections)
    // =========================================================

    /**
     * Ordena los medicamentos por nombre usando Comparable (Collections.sort).
     * Se apoya en un Comparator externo sobre nombre.
     */
    public void ordenarMedicamentosPorNombre() {
        Collections.sort(medicamentos, Comparator.comparing(Medicamento::getNombre));
        System.out.println("Medicamentos ordenados por nombre.");
    }

    /**
     * Ordena los medicamentos por precio usando un Comparator externo.
     */
    public void ordenarMedicamentosPorPrecio() {
        medicamentos.sort(Comparator.comparingDouble(Medicamento::getPrecio));
        System.out.println("Medicamentos ordenados por precio.");
    }

    // =========================================================
    //  REPORTES
    // =========================================================

    /**
     * Muestra el total gastado por un cliente.
     */
    public void mostrarTotalGastado(int dniCliente) {
        Cliente cliente = buscarCliente(dniCliente);
        if (cliente != null) {
            System.out.println("Total gastado por " + cliente.getNombre() + " "
                    + cliente.getApellido() + ": $"
                    + String.format("%.2f", cliente.getTotalGastado()));
        }
    }

    // =========================================================
    //  GETTERS
    // =========================================================

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public HashMap<Integer, Persona> getPersonas() {
        return personas;
    }

    public HashSet<Persona> getClientesConCompras() {
        return clientesConCompras;
    }
}
