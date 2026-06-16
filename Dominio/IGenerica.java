package Dominio;

import java.util.List;

// Interfaz genérica para repositorios
// <T> indica que la interfaz trabaja con cualquier tipo de objeto

public interface IGenerica<T> {
    void guardar(T obj);          //Guarda un objeto del tipo T (Medicamento, Cliente, etc)
    List<T> consultar();          //Devuelve una lista de objetos del tipo T
}
