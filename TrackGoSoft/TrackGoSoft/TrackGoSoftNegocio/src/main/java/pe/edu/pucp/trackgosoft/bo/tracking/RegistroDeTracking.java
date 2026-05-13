package pe.edu.pucp.trackgosoft.bo.tracking;

import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

/**
 * Gestor especializado en hacer seguimiento en memoria a un pedido específico.
 * Cumple con el UML aplicando delegación y sin modificar la clase Pedido del Modelo.
 */

public class RegistroDeTracking {
    
    private Pedido pedidoObservado;

    public RegistroDeTracking() {
        this.pedidoObservado = null;
    }

    public void observar(Pedido p) { 
        this.pedidoObservado = p; 
    }

    public void desfijar() { 
        this.pedidoObservado = null; 
    }

    public int consultarEstadoActual() {
        if (pedidoObservado == null) {
            return -1;
        }
        return pedidoObservado.consultarEstadoActual();
    }
    
    public Pedido getPedidoObservado() {
        return this.pedidoObservado;
    }
}
