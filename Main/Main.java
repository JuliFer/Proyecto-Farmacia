package farmacia.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import RepositorioArchivo;
import farmacia.dominio.Farmacia;
import farmacia.dominio.Farmaceutico;
import farmacia.dominio.Cliente;
import farmacia.dominio.Medicamento;
import farmacia.dominio.Generico;
import farmacia.dominio.DeMarca;
import farmacia.dominio.Controlado;
import farmacia.dominio.EstadoMedicamento;
import farmacia.excepcion.MedicamentoNoDisponibleException;
import farmacia.repositorio.IRepositorio;
import farmacia.ui.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.iniciar();
    }
}

git checkout main;
