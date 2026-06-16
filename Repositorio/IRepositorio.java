package farmacia.repositorio;

import java.util.List;

public interface IRepositorio<T> {
    void guardar(Object obj);
    List<T> consultar();
}
