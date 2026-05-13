package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.Persistible;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

public interface PedidoDAO extends Persistible<Pedido, Integer> {
    Pedido buscarPorCodigo(String codigoPedido);
}
