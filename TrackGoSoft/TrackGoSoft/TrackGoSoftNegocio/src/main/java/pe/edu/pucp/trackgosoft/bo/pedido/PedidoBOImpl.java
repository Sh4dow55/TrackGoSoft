package pe.edu.pucp.trackgosoft.bo.pedido;

import pe.edu.pucp.trackgosoft.bo.BaseBO;
import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.dao.TransactionsManager;
import pe.edu.pucp.trackgosoft.dao.pedido.DetalleDePedidoDAO;
import pe.edu.pucp.trackgosoft.dao.pedido.DetalleDePedidoDAOImpl;
import pe.edu.pucp.trackgosoft.dao.pedido.HistorialDePedidoDAO;
import pe.edu.pucp.trackgosoft.dao.pedido.HistorialDePedidoDAOImpl;
import pe.edu.pucp.trackgosoft.dao.pedido.PedidoDAO;
import pe.edu.pucp.trackgosoft.dao.pedido.PedidoDAOImpl;
import pe.edu.pucp.trackgosoft.modelo.pedido.DetalleDePedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.HistorialDePedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

import java.util.List;

public class PedidoBOImpl extends BaseBO implements PedidoBO {

    private final PedidoDAO pedidoDAO;
    private final DetalleDePedidoDAO detalleDAO;
    private final HistorialDePedidoDAO historialDAO;

    public PedidoBOImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
        this.detalleDAO = new DetalleDePedidoDAOImpl();
        this.historialDAO = new HistorialDePedidoDAOImpl();
    }

    @Override
    public List<Pedido> listar() {
        return pedidoDAO.leerTodos();
    }

    @Override
    public Pedido obtener(Integer id) {
        validarIdPositivo(id, "ID Pedido");
        Pedido pedido = pedidoDAO.leer(id);
        if (pedido != null) {
            cargarDependencias(pedido);
        }
        return pedido;
    }

    @Override
    public Pedido buscarPorCodigo(String codigoPedido) {
        validarTextoObligatorio(codigoPedido, "Código de Pedido");
        Pedido pedido = pedidoDAO.buscarPorCodigo(codigoPedido);
        if (pedido != null) {
            cargarDependencias(pedido);
        }
        return pedido;
    }

    private void cargarDependencias(Pedido pedido) {
        int id = pedido.getIdPedido();
        pedido.setDetalleDePedido((java.util.ArrayList<DetalleDePedido>) detalleDAO.listarPorPedido(id));
        pedido.setHistorialDePedido((java.util.ArrayList<HistorialDePedido>) historialDAO.listarPorPedido(id));
    }

    @Override
    public void guardar(Pedido modelo, EstadoOperacion estado) {
        validarEstadoOperacion(estado);
        validarNoNulo(modelo, "Pedido");
        validarTextoObligatorio(modelo.getDestinatario(), "Destinatario");

        switch (estado) {
            case Nuevo:
                try {
                    TransactionsManager.iniciarTransaccion();
                    int idGenerado = pedidoDAO.crear(modelo);
                    modelo.setIdPedido(idGenerado);

                    String codigo = "PD-" + String.format("%05d", idGenerado);
                    modelo.setCodigoPedido(codigo);
                    pedidoDAO.actualizar(modelo);

                    if (modelo.getDetalleDePedido() != null) {
                        for (DetalleDePedido det : modelo.getDetalleDePedido()) {
                            det.setIdPedido(idGenerado);
                            detalleDAO.crear(det);
                        }
                    }

                    if (modelo.getHistorialDePedido() != null) {
                        for (HistorialDePedido hist : modelo.getHistorialDePedido()) {
                            hist.setIdPedido(idGenerado);
                            historialDAO.crear(hist);
                        }
                    }

                    TransactionsManager.commitTransaccion();
                } catch (Exception ex) {
                    TransactionsManager.rollbackTransaccion();
                    throw new RuntimeException("Error al guardar el Pedido.", ex);
                }
                break;
            case Modificado:
                pedidoDAO.actualizar(modelo);
                if (modelo.getHistorialDePedido() != null) {
                    for (HistorialDePedido hist : modelo.getHistorialDePedido()) {
                        if (hist.getIdHistorial() == 0) {
                            hist.setIdPedido(modelo.getIdPedido());
                            historialDAO.crear(hist);
                        }
                    }
                }
                break;
            case Eliminado:
                eliminar(modelo.getIdPedido());
                break;
        }
    }

    @Override
    public void eliminar(Integer id) {
        validarIdPositivo(id, "ID Pedido");
        pedidoDAO.eliminar(id);
    }
}
