package pe.edu.pucp.trackgosoft.bo.empresa;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.empresa.EmpresaDAO;
import pe.edu.pucp.trackgosoft.dao.empresa.EmpresaDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.empresa.Empresa;

import java.util.List;

public class EmpresaBOImpl extends BaseBO implements EmpresaBO {
    private final EmpresaDAO empresaDAO;

    public EmpresaBOImpl() {
        this.empresaDAO = new EmpresaDAOImpl();
    }

    @Override
    public List<Empresa> listar() {
        return empresaDAO.leerTodos();
    }

    @Override
    public Empresa obtener(Integer id) {
        validarIdPositivo(id, "ID Empresa");
        return empresaDAO.leer(id);
    }

    @Override
    public void guardar(Empresa modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Empresa");
        validarTextoObligatorio(modelo.getRuc(), "RUC");

        switch (estado) {
            case Nuevo:
                int idEmpresa = empresaDAO.crear(modelo);
                modelo.setId(idEmpresa);
                break;
            case Modificado:
                validarIdPositivo(modelo.getId(), "ID Empresa");
                empresaDAO.actualizar(modelo);
                break;
            case Eliminado:
                eliminar(modelo.getId());
                break;
        }
    }

    @Override
    public void eliminar(Integer id) {
        validarIdPositivo(id, "ID Empresa");
        empresaDAO.eliminar(id);
    }

    @Override
    public Empresa buscarPorRuc(String ruc) {
        validarTextoObligatorio(ruc, "RUC");
        return empresaDAO.buscarPorRuc(ruc);
    }
}
