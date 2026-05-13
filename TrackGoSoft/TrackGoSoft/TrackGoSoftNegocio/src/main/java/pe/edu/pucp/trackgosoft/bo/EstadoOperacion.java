package pe.edu.pucp.trackgosoft.bo;

/**
 * Enum que representa la operación CRUD a realizar en la capa de negocio.
 * Se usa en el método guardar(modelo, EstadoOperacion) de los BOs.
 */
public enum EstadoOperacion {
    Nuevo,
    Modificado,
    Eliminado
}
