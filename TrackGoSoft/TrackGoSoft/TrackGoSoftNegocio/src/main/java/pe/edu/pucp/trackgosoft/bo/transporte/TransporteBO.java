package pe.edu.pucp.trackgosoft.bo.transporte;

import pe.edu.pucp.trackgosoft.bo.Gestionable;
import pe.edu.pucp.trackgosoft.modelo.transporte.Transporte;

public interface TransporteBO extends Gestionable<Transporte, String> {
    Transporte obtenerPorPlaca(String placa);
}
