package pe.edu.pucp.trackgosoft.bo.usuario;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.usuario.EmpleadoDAO;
import pe.edu.pucp.trackgosoft.dao.usuario.EmpleadoDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.usuario.Empleado;

import java.util.List;


public class EmpleadoBOImpl extends BaseBO implements EmpleadoBO {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoBOImpl() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    @Override
    public List<Empleado> listar() {
        return empleadoDAO.leerTodos();
    }

    @Override
    public Empleado obtener(Integer id) {
        validarIdPositivo(id, "ID Empleado");
        return empleadoDAO.leer(id);
    }

    @Override
    public void guardar(Empleado modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Empleado");
        validarTextoObligatorio(modelo.getDni(), "DNI");
        if (modelo.getDni().length() != 8) {
            throw new IllegalArgumentException("El DNI debe tener 8 caracteres");
        }

        switch (estado) {
            case Nuevo:
                int idEmpleado = empleadoDAO.crear(modelo);
                modelo.setIdUsuario(idEmpleado);
                break;
            case Modificado:
                validarIdPositivo(modelo.getIdUsuario(), "ID Empleado");
                empleadoDAO.actualizar(modelo);
                break;
            case Eliminado:
                eliminar(modelo.getIdUsuario());
                break;
        }
    }

    @Override
    public void eliminar(Integer id) {
        validarIdPositivo(id, "ID Empleado");
        empleadoDAO.eliminar(id);
    }
}
