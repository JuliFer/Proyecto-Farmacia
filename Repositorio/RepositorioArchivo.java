
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Dominio.IGenerica;

//Clase genérica para persistencia en archivo
//Usa serialización para guardar y leer listas de objetos
public class RepositorioArchivo<T> implements IGenerica<T> {

    private File archivo;

    //Constructor - Recibe el nombre del archivo
    public RepositorioArchivo(String nombreArchivo) {
        this.archivo = new File(nombreArchivo);
    }

    //Metodo Guardar
    @Override
    public void guardar(T obj) {
        
        List<T> lista = consultar();  //Leo la lista actual del archivo
        lista.add(obj);        //Agrego el nuevo objeto

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);         //Guardo la lista completa en el archivo

        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    //Metodo Consultar
    @Override
    public List<T> consultar() {
        if (!archivo.exists()) {
            return new ArrayList<>(); // Si no existe el archivo, devuelvo lista vacía
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject(); // Leo lo que hay en el archivo
            if (obj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<T> lista = (List<T>) obj;  // Convierto a lista del tipo T
                return lista;
            }
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
