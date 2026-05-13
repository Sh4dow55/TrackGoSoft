package pe.edu.pucp.trackgosoft.bo.pedido;

import pe.edu.pucp.trackgosoft.bo.Gestionable;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

public interface PedidoBO extends Gestionable<Pedido, Integer> {
    Pedido buscarPorCodigo(String codigoPedido);
}
