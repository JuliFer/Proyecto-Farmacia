package farmacia.ui;

import java.util.List;
import java.util.Scanner;

import farmacia.dominio.Cliente;
import farmacia.dominio.Farmacia;
import farmacia.dominio.Farmaceutico;
import farmacia.dominio.Medicamento;
import farmacia.dominio.Generico;
import farmacia.dominio.DeMarca;
import farmacia.dominio.Controlado;
import farmacia.dominio.EstadoMedicamento;
import farmacia.excepcion.MedicamentoNoDisponibleException;
import farmacia.repositorio.IRepositorio;
import farmacia.repositorio.RepositorioArchivo;

public class Menu {
    private Scanner scanner;
    private Farmacia farmacia;

    public void iniciar() {
        scanner = new Scanner(System.in);
        farmacia = new Farmacia();

        // Persistencia: cargar datos si existen
        IRepositorio<Cliente> repositorioClientes = new RepositorioArchivo<>("clientes.dat");
        farmacia.setPersonas((List) repositorioClientes.consultar());

        IRepositorio<Medicamento> repositorioMedicamentos = new RepositorioArchivo<>("medicamentos.dat");
        farmacia.setMedicamentos((List) repositorioMedicamentos.consultar());

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
                case 1 -> agregarMedicamento();
                case 2 -> registrarCliente();
                case 3 -> venderMedicamento();
                case 4 -> reponerStock();
                case 5 -> mostrarDisponibles();
                case 6 -> alertaStock();
                case 7 -> mostrarTotalGastado();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);

        // Guardar datos al salir
        repositorioMedicamentos.guardar(farmacia.getMedicamentos());
        repositorioClientes.guardar(farmacia.getPersonas());
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
            case 1 -> {
                System.out.print("Principio activo: ");
                String principio = scanner.nextLine();
                m = new Generico(1, nombre, precio, stock, receta, principio);
            }
            case 2 -> {
                System.out.print("Laboratorio: ");
                String lab = scanner.nextLine();
                System.out.print("¿Patente vigente? (true/false): ");
                boolean patente = scanner.nextBoolean();
                m = new DeMarca(2, nombre, precio, stock, receta, lab, patente);
            }
            case 3 -> {
                System.out.print("Nivel de control: ");
                int nivel = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Autoridad: ");
                String autoridad = scanner.nextLine();
                m = new Controlado(3, nombre, precio, stock, receta, nivel, autoridad);
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

        Cliente c = new Cliente(dni, nombre, apellido, obraSocial);
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
        String nombre = scanner.nextLine();
        Medicamento m = farmacia.buscarMedicamento(nombre);
        if (m == null) {
            System.out.println("Medicamento no encontrado.");
            return;
        }

        try {
            m.dispensar();
            c.comprarMedicamento(m);
            System.out.println("Venta realizada con éxito.");
        } catch (MedicamentoNoDisponibleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void reponerStock() {
        System.out.print("Nombre del medicamento: ");
        String nombre = scanner.nextLine();
        Medicamento m = farmacia.buscarMedicamento(nombre);
        if (m == null) {
            System.out.println("Medicamento no encontrado.");
            return;
        }
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
}
