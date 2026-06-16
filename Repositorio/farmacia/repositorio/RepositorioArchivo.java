package farmacia.repositorio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Clase genérica para persistencia
// <T> indica que la clase es GENÉRICA
// T representa el tipo de dato que se define al crear el objeto.
// Ejemplo:
// RepositorioArchivo<Cliente>
// RepositorioArchivo<Medicamento>
// De esta manera la misma clase puede trabajar con distintos tipos de objetos.

public class RepositorioArchivo<T> implements IRepositorio<T> {

    private File archivo;

    public RepositorioArchivo(String nombreArchivo) {
        this.archivo = new File(nombreArchivo);
    }

    @Override
    public void guardar(Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<T> consultar() {
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            return (List<T>) obj;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}