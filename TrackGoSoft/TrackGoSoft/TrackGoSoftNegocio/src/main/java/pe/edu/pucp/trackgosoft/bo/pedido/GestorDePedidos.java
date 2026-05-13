package pe.edu.pucp.trackgosoft.bo.pedido;

import pe.edu.pucp.trackgosoft.bo.EstadoOperacion;
import pe.edu.pucp.trackgosoft.modelo.pedido.EstadoPedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Gestor Central de Pedidos (Singleton).
 * Implementa Caché en Memoria (pedidos) y delega a la BD mediante PedidoBOImpl.
 */
public class GestorDePedidos {

    private static GestorDePedidos instance;
    
    // Caché en memoria
    private ArrayList<Pedido> pedidos;
    
    // Delegado de BD
    private final PedidoBO pedidoBO;
    
    // Bandera para saber si ya cargamos TODOS los pedidos
    private boolean cacheCompletamenteCargado;

    private GestorDePedidos() {
        this.pedidos = new ArrayList<>();
        this.pedidoBO = new PedidoBOImpl();
        this.cacheCompletamenteCargado = false;
    }

    public static synchronized GestorDePedidos getInstance() {
        if (instance == null) {
            instance = new GestorDePedidos();
        }
        return instance;
    }

    public void agregarPedido(Pedido pedido) {
        // Guardar en BD (TransactionsManager se encarga del Pedido, Detalles e Historial)
        pedidoBO.guardar(pedido, EstadoOperacion.Nuevo);
        
        // Agregar a la memoria caché local (el BO ya le puso el ID generado y el Código)
        pedidos.add(pedido);
    }

    public void modificarPedido(Pedido pedido) {
        // Guardar en BD
        pedidoBO.guardar(pedido, EstadoOperacion.Modificado);
        
        // Actualizar caché
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getIdPedido() == pedido.getIdPedido()) {
                pedidos.set(i, pedido);
                break;
            }
        }
    }

    public void eliminarPedido(int idPedido) {
        // Eliminar en BD
        pedidoBO.eliminar(idPedido);
        
        // Eliminar del caché
        pedidos.removeIf(p -> p.getIdPedido() == idPedido);
    }

    public Pedido obtenerPedido(int idPedido) {
        // 1. Búsqueda en Caché primero (Respuesta < 2s)
        for (Pedido p : pedidos) {
            if (p.getIdPedido() == idPedido) {
                return p;
            }
        }

        // 2. Si no está en caché, delegamos al BO para ir a la Base de Datos
        Pedido pedidoBD = pedidoBO.obtener(idPedido);
        if (pedidoBD != null) {
            pedidos.add(pedidoBD); 
        }
        
        return pedidoBD;
    }

    public Pedido buscarPorCodigo(String codigoPedido) {
        // 1. Búsqueda en Caché primero
        for (Pedido p : pedidos) {
            if (p.getCodigoPedido() != null && p.getCodigoPedido().equals(codigoPedido)) {
                return p;
            }
        }

        // 2. Si no está en caché, delegamos al BO
        Pedido pedidoBD = pedidoBO.buscarPorCodigo(codigoPedido);
        if (pedidoBD != null) {
            pedidos.add(pedidoBD);
        }
        
        return pedidoBD;
    }

    public List<Pedido> listarPedidos() {
        if (!cacheCompletamenteCargado) {
            pedidos.clear();
            List<Pedido> dbList = pedidoBO.listar();
            if (dbList != null) {
                for (Pedido p : dbList) {
                    Pedido pedidoCompleto = pedidoBO.obtener(p.getIdPedido());
                    pedidos.add(pedidoCompleto);
                }
            }
            cacheCompletamenteCargado = true;
        }
        return pedidos;
    }

    public List<Pedido> listarPedidosPorFecha(Date inicio, Date fin) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : listarPedidos()) {
            Date creacion = p.getFechaCreacion();
            if (creacion != null && !creacion.before(inicio) && !creacion.after(fin)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Pedido> listarPedidosPorEstado(EstadoPedido estado) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : listarPedidos()) {
            if (p.getEstado() == estado) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Pedido> listarPedidosPorTransportista(int idUsuario) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : listarPedidos()) {
            if (p.getIdUsuario() == idUsuario) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<Pedido> ordenarPedidosPorFecha(Date inicio, Date fin) {
        return listarPedidosPorFecha(inicio, fin);
    }

    public void actualizarEstado(int idPedido, EstadoPedido nuevoEstado) {
        Pedido p = obtenerPedido(idPedido);
        if (p != null) {
            p.setEstado(nuevoEstado);
            p.registrarEstado(); 
            modificarPedido(p);
        }
    }

    public void asignarPedido(int idPedido, int idTransportista) {
        Pedido p = obtenerPedido(idPedido);
        if (p != null) {
            p.asignarInformacionTransporte(idTransportista, p.getDetalleTransporte());
            modificarPedido(p);
        }
    }

    public void reasignarPedido(int idPedido, int idNuevoTransportista) {
        asignarPedido(idPedido, idNuevoTransportista);
    }
}
