package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.pedido.DetalleDePedido;
import java.util.List;

public interface DetalleDePedidoDAO extends Persistible<DetalleDePedido, Integer> {
    List<DetalleDePedido> listarPorPedido(int idPedido);
}
