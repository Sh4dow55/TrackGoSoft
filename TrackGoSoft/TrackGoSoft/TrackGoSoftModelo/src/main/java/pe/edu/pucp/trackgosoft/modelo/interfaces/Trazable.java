package pe.edu.pucp.trackgosoft.modelo.interfaces;

/**
 * Contrato para entidades que registran su historial de estados.
 * Al llamar registrarEstado(), el objeto guarda el estado actual en su historial interno.
 */
public interface Trazable {
    void registrarEstado();
}
