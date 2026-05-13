package pe.edu.pucp.trackgosoft.bo.transporte;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.transporte.TransporteDAO;
import pe.edu.pucp.trackgosoft.dao.transporte.TransporteDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.transporte.Transporte;

import java.util.List;

public class TransporteBOImpl extends BaseBO implements TransporteBO {
    private final TransporteDAO transporteDAO;

    public TransporteBOImpl() {
        this.transporteDAO = new TransporteDAOImpl();
    }

    @Override
    public List<Transporte> listar() {
        return transporteDAO.leerTodos();
    }

    @Override
    public Transporte obtener(String placa) {
        validarTextoObligatorio(placa, "Placa");
        return transporteDAO.leer(placa);
    }

    @Override
    public Transporte obtenerPorPlaca(String placa) {
        return obtener(placa);
    }

    @Override
    public void guardar(Transporte modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Transporte");
        validarTextoObligatorio(modelo.getPlaca(), "Placa");

        switch (estado) {
            case Nuevo:
                transporteDAO.crear(modelo);
                break;
            case Modificado:
                transporteDAO.actualizar(modelo);
                break;
            case Eliminado:
                eliminar(modelo.getPlaca());
                break;
        }
    }

    @Override
    public void eliminar(String placa) {
        validarTextoObligatorio(placa, "Placa");
        transporteDAO.eliminar(placa);
    }
}
