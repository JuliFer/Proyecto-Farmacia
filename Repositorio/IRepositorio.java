package Repositorio;

import java.util.List;

public interface IRepositorio<T> {
    void guardar(T obj);          // Guarda un objeto del tipo T
    void guardarTodos(List<T> lista); // Guarda toda la lista sobreescribiendo el archivo
    List<T> consultar();          // Devuelve una lista de objetos del tipo T
}

