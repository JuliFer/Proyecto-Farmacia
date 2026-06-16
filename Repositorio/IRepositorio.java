import java.util.List;

public interface IRepositorio<T> {
    void guardar(T obj);          // Guarda un objeto del tipo T
    List<T> consultar();          // Devuelve una lista de objetos del tipo T
}

