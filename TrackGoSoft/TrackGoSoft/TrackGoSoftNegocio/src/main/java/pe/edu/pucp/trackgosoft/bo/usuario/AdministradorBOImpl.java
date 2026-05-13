package pe.edu.pucp.trackgosoft.bo.usuario;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.usuario.AdministradorDAO;
import pe.edu.pucp.trackgosoft.dao.usuario.AdministradorDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.usuario.Administrador;

import java.util.List;


public class AdministradorBOImpl extends BaseBO implements AdministradorBO {
    private final AdministradorDAO adminDAO;

    public AdministradorBOImpl() {
        this.adminDAO = new AdministradorDAOImpl();
    }

    @Override
    public List<Administrador> listar() {
        return adminDAO.leerTodos();
    }

    @Override
    public Administrador obtener(Integer id) {
        validarIdPositivo(id, "ID Administrador");
        return adminDAO.leer(id);
    }

    @Override
    public void guardar(Administrador modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Administrador");
        validarTextoObligatorio(modelo.getDni(), "DNI");
        validarTextoObligatorio(modelo.getCorreo(), "Correo");

        switch (estado) {
            case Nuevo:
                int idAdmin = adminDAO.crear(modelo);
                modelo.setIdUsuario(idAdmin);
                break;
            case Modificado:
                validarIdPositivo(modelo.getIdUsuario(), "ID Administrador");
                adminDAO.actualizar(modelo);
                break;
            case Eliminado:
                eliminar(modelo.getIdUsuario());
                break;
        }
    }

    @Override
    public void eliminar(Integer id) {
        validarIdPositivo(id, "ID Administrador");
        adminDAO.eliminar(id);
    }
}
