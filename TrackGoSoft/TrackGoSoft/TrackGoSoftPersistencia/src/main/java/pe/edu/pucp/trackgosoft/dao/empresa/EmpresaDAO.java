package pe.edu.pucp.trackgosoft.dao.empresa;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.empresa.Empresa;

public interface EmpresaDAO extends Persistible<Empresa, Integer> {
    Empresa buscarPorRuc(String ruc);
}
