package pe.edu.pucp.trackgosoft.bo;

import java.util.List;

/**
 * Interfaz genérica para los Business Objects del sistema.
 * @param <M> tipo del modelo
 * @param <ID> tipo del identificador
 */
public interface Gestionable<M, ID> {
    List<M> listar();
    M obtener(ID id);
    void eliminar(ID id);
    void guardar(M modelo, EstadoOperacion estado);
}
