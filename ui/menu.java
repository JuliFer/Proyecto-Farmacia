package ui;

import java.util.List;
import java.util.Scanner;

import Dominio.*;
import Excepcion.*;
import Repositorio.*;

public class menu {
    private Scanner scanner;
    private Farmacia farmacia;

    public void iniciar() {
        scanner = new Scanner(System.in);
        farmacia = new Farmacia();

        // Persistencia: cargar datos si existen
        IRepositorio<Cliente> repositorioClientes = new RepositorioArchivo<>("clientes.dat");
        List<Cliente> clientesGuardados = repositorioClientes.consultar();
        if (clientesGuardados != null) {
            for (Cliente c : clientesGuardados) {
                farmacia.registrarPersona(c);
            }
        }

        IRepositorio<Medicamento> repositorioMedicamentos = new RepositorioArchivo<>("medicamentos.dat");
        List<Medicamento> medsGuardados = repositorioMedicamentos.consultar();
        if (medsGuardados != null) {
            for (Medicamento m : medsGuardados) {
                farmacia.agregarMedicamento(m);
            }
        }

        int opcion = -1;

        do {
            System.out.println("\n===== FARMACIA =====");
            System.out.println("1. Agregar medicamento");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Vender medicamento");
            System.out.println("4. Reponer stock");
            System.out.println("5. Mostrar disponibles");
            System.out.println("6. Alerta stock bajo");
            System.out.println("7. Total gastado por cliente");
            System.out.println("8. Eliminar medicamento");
            System.out.println("9. Eliminar cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                System.out.println("Debe ingresar una opción válida");
                scanner.next();
                continue;
            }
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarMedicamento();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    venderMedicamento();
                    break;
                case 4:
                    reponerStock();
                    break;
                case 5:
                    mostrarDisponibles();
                    break;
                case 6:
                    alertaStock();
                    break;
                case 7:
                    mostrarTotalGastado();
                    break;
                case 8:
                    eliminarMedicamento();
                    break;
                case 9:
                    eliminarCliente();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }

        } while (opcion != 0);

        // Guardar datos al salir sobrescribiendo los archivos con el estado actual
        repositorioMedicamentos.guardarTodos(farmacia.getMedicamentos());
        List<Cliente> clientes = new java.util.ArrayList<>();
        for (Persona p : farmacia.getPersonas().values()) {
            if (p instanceof Cliente) {
                clientes.add((Cliente) p);
            }
        }
        repositorioClientes.guardarTodos(clientes);
        scanner.close();
    }

    private void agregarMedicamento() {
        System.out.println("1. Genérico");
        System.out.println("2. De Marca");
        System.out.println("3. Controlado");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Stock inicial: ");
        int stock = scanner.nextInt();
        System.out.print("¿Requiere receta? (true/false): ");
        boolean receta = scanner.nextBoolean();
        scanner.nextLine();

        Medicamento m = null;
        switch (tipo) {
            case 1: {
                System.out.print("Laboratorio: ");
                String laboratorio = scanner.nextLine();
                m = new Generico(nombre, precio, stock, receta, EEstadoMedicamento.DISPONIBLE, laboratorio);
                break;
            }
            case 2: {
                System.out.print("Laboratorio: ");
                String lab = scanner.nextLine();
                System.out.print("¿Patente vigente? (true/false): ");
                boolean patente = scanner.nextBoolean();
                m = new DeMarca(nombre, precio, stock, receta, EEstadoMedicamento.DISPONIBLE, lab, patente);
                break;
            }
            case 3: {
                System.out.print("Nivel de control: ");
                int nivel = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Autoridad: ");
                String autoridad = scanner.nextLine();
                m = new Controlado(nombre, precio, stock, receta, EEstadoMedicamento.DISPONIBLE, nivel, autoridad);
                break;
            }
        }
        if (m != null) {
            farmacia.agregarMedicamento(m);
            System.out.println("Medicamento agregado correctamente.");
        }
    }

    private void registrarCliente() {
        System.out.print("DNI: ");
        int dni = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("¿Tiene obra social? (true/false): ");
        boolean obraSocial = scanner.nextBoolean();

        Cliente c = new Cliente(nombre, apellido, dni);
        c.setTieneSocialBoolean(obraSocial);
        farmacia.registrarPersona(c);
        System.out.println("Cliente registrado correctamente.");
    }

    private void venderMedicamento() {
        System.out.print("DNI del cliente: ");
        int dni = scanner.nextInt();
        scanner.nextLine();
        Cliente c = farmacia.buscarCliente(dni);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Nombre del medicamento: ");
        String nombreMed = scanner.nextLine();
        java.util.Optional<Medicamento> optM = farmacia.buscarMedicamento(nombreMed);
        if (optM.isEmpty()) {
            System.out.println("Medicamento no encontrado.");
            return;
        }
        Medicamento m = optM.get();

        try {
            m.dispensar();
            c.comprarMedicamento(m);
            System.out.println("Venta realizada con éxito.");
        } catch (MedicamentoNoDisponible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void reponerStock() {
        System.out.print("Nombre del medicamento: ");
        String nombre = scanner.nextLine();
        java.util.Optional<Medicamento> opt = farmacia.buscarMedicamento(nombre);
        if (opt.isEmpty()) {
            System.out.println("Medicamento no encontrado.");
            return;
        }
        Medicamento m = opt.get();
        System.out.print("Cantidad a reponer: ");
        int cantidad = scanner.nextInt();
        m.reponer(cantidad);
        System.out.println("Stock actualizado.");
    }

    private void mostrarDisponibles() {
        System.out.println("\n--- MEDICAMENTOS DISPONIBLES ---");
        farmacia.mostrarDisponibles();
    }

    private void alertaStock() {
        System.out.print("Ingrese mínimo de stock: ");
        int minimo = scanner.nextInt();
        farmacia.alertaStockBajo(minimo);
    }

    private void mostrarTotalGastado() {
        System.out.print("DNI del cliente: ");
        int dni = scanner.nextInt();
        Cliente c = farmacia.buscarCliente(dni);
        if (c != null) {
            System.out.println("Total gastado: $" + c.getTotalGastado());
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void eliminarMedicamento() {
        System.out.print("Nombre del medicamento a eliminar: ");
        String nombre = scanner.nextLine();
        if (farmacia.eliminarMedicamento(nombre)) {
            System.out.println("Se eliminó el medicamento correctamente.");
        } else {
            System.out.println("No se pudo eliminar el medicamento.");
        }
    }

    private void eliminarCliente() {
        System.out.print("DNI del cliente a eliminar: ");
        int dni = scanner.nextInt();
        scanner.nextLine();
        if (farmacia.eliminarCliente(dni)) {
            System.out.println("Se eliminó el cliente correctamente.");
        } else {
            System.out.println("No se pudo eliminar el cliente.");
        }
    }
}
