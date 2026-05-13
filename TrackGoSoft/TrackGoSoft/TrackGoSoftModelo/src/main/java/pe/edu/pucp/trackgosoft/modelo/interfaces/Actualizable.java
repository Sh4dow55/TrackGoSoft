package pe.edu.pucp.trackgosoft.modelo.interfaces;

/**
 * Contrato para entidades cuyo estado puede ser actualizado.
 * Permite cambiar el estado logístico usando un código numérico.
 */
public interface Actualizable {
    void actualizarEstado(int estado);
}
