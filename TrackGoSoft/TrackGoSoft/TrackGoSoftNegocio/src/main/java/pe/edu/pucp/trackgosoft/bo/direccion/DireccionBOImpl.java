package pe.edu.pucp.trackgosoft.bo.direccion;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.direccion.DireccionDAO;
import pe.edu.pucp.trackgosoft.dao.direccion.DireccionDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.direccion.Direccion;

import java.util.List;

public class DireccionBOImpl extends BaseBO implements DireccionBO {
    private final DireccionDAO direccionDAO;

    public DireccionBOImpl() {
        this.direccionDAO = new DireccionDAOImpl();
    }

    @Override
    public List<Direccion> listar() {
        return direccionDAO.leerTodos();
    }

    @Override
    public Direccion obtener(Integer id) {
        validarIdPositivo(id, "ID Direccion");
        return direccionDAO.leer(id);
    }

    @Override
    public void guardar(Direccion modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Direccion");
        validarTextoObligatorio(modelo.getDepartamento(), "Departamento");
        validarTextoObligatorio(modelo.getProvincia(), "Provincia");
        validarTextoObligatorio(modelo.getDistrito(), "Distrito");
        validarTextoObligatorio(modelo.getCalleNumero(), "Calle y Número");

        switch (estado) {
            case Nuevo:
                int idDireccion = direccionDAO.crear(modelo);
                modelo.setIdDireccion(idDireccion);
                break;
            case Modificado:
                validarIdPositivo(modelo.getIdDireccion(), "ID Direccion");
                direccionDAO.actualizar(modelo);
                break;
            case Eliminado:
                eliminar(modelo.getIdDireccion());
                break;
        }
    }

    @Override
    public void eliminar(Integer id) {
        validarIdPositivo(id, "ID Direccion");
        direccionDAO.eliminar(id);
    }
}
