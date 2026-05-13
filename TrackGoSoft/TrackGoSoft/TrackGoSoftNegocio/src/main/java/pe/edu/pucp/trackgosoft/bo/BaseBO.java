package pe.edu.pucp.trackgosoft.bo;

import java.util.Objects;

/**
 * Clase base abstracta para todos los Business Objects del sistema.
 * Contiene validaciones reutilizables que todos los BOs pueden usar.
 *
 * Inspirado en BaseBO de la plantilla del profesor (SoftProg).
 */
public abstract class BaseBO {

    protected void validarIdPositivo(int id, String nombreCampo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser mayor a 0");
        }
    }

    protected void validarEstadoOperacion(EstadoOperacion estado) {
        Objects.requireNonNull(estado, "El estado de operación es obligatorio");
    }

    protected void validarTextoObligatorio(String valor, String nombreCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El " + nombreCampo + " es obligatorio");
        }
    }

    protected void validarNumeroPositivo(double valor, String nombreCampo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser mayor a 0");
        }
    }

    protected void validarNoNulo(Object objeto, String nombreCampo) {
        Objects.requireNonNull(objeto, "El " + nombreCampo + " no puede ser nulo");
    }
}
