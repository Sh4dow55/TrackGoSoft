package pe.edu.pucp.trackgosoft.modelo.interfaces;

/**
 * Contrato para entidades que exponen su estado actual como valor numérico.
 * El valor retornado corresponde al ordinal del EstadoPedido.
 */
public interface Consultable {
    int consultarEstadoActual();
}
