package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.pedido.HistorialDePedido;
import java.util.List;

public interface HistorialDePedidoDAO extends Persistible<HistorialDePedido, Integer> {
    List<HistorialDePedido> listarPorPedido(int idPedido);
}
