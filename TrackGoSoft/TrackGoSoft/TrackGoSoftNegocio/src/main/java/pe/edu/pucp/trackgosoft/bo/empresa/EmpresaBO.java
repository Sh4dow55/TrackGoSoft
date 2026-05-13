package pe.edu.pucp.trackgosoft.bo.empresa;

import pe.edu.pucp.trackgosoft.bo.Gestionable;
import pe.edu.pucp.trackgosoft.modelo.empresa.Empresa;

public interface EmpresaBO extends Gestionable<Empresa, Integer> {
    Empresa buscarPorRuc(String ruc);
}
