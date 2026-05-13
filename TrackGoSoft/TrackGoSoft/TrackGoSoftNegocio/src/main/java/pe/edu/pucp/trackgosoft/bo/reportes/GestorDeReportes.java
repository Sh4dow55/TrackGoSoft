package pe.edu.pucp.trackgosoft.bo.reportes;

import pe.edu.pucp.trackgosoft.bo.pedido.GestorDePedidos;
import pe.edu.pucp.trackgosoft.modelo.pedido.DetalleDePedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.EstadoPedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.HistorialDePedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

import java.util.Date;
import java.util.List;

/**
 * Gestor especializado en emitir reportes formateados de Pedidos.
 * Consigue los datos directamente del caché de GestorDePedidos.
 */

public class GestorDeReportes {

    private GestorDeReportes() {
        // Constructor privado para evitar instanciación de clase utilitaria
    }

    public static String generarReportePedidosPorFecha(Date inicio, Date fin) {
        List<Pedido> filtrados = GestorDePedidos.getInstance().listarPedidosPorFecha(inicio, fin);
        return formatearListaDePedidos("Reporte de Pedidos por Fecha", filtrados);
    }

    public static String generarReportePedidosPorEstado(String estado) {
        try {
            EstadoPedido estadoEnum = EstadoPedido.valueOf(estado);
            List<Pedido> filtrados = GestorDePedidos.getInstance().listarPedidosPorEstado(estadoEnum);
            return formatearListaDePedidos("Reporte de Pedidos por Estado: " + estado, filtrados);
        } catch (IllegalArgumentException e) {
            return "Estado de pedido inválido para el reporte.";
        }
    }

    public static String generarReportePedidosPorTransportista(int idUsuario) {
        List<Pedido> filtrados = GestorDePedidos.getInstance().listarPedidosPorTransportista(idUsuario);
        return formatearListaDePedidos("Reporte de Pedidos del Transportista ID: " + idUsuario, filtrados);
    }

    private static String formatearListaDePedidos(String titulo, List<Pedido> pedidos) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n=======================================================\n");
        reporte.append("                 ").append(titulo).append("\n");
        reporte.append("=======================================================\n");

        if (pedidos == null || pedidos.isEmpty()) {
            reporte.append("No se encontraron pedidos con los criterios especificados.\n");
            reporte.append("=======================================================\n");
            return reporte.toString();
        }

        reporte.append("Total de Pedidos Encontrados: ").append(pedidos.size()).append("\n\n");

        for (Pedido pedido : pedidos) {
            reporte.append(">>> CÓDIGO DE TRACKING: ").append(pedido.getCodigoPedido()).append("\n");
            reporte.append("    ID INTERNO: ").append(pedido.getIdPedido()).append("\n");
            reporte.append("    DESTINATARIO: ").append(pedido.getDestinatario()).append("\n");
            reporte.append("    ESTADO ACTUAL: ").append(pedido.getEstado()).append("\n");
            
            reporte.append("    --- DETALLE DE PRODUCTOS ---\n");
            if (pedido.getDetalleDePedido() != null && !pedido.getDetalleDePedido().isEmpty()) {
                for (DetalleDePedido detalle : pedido.getDetalleDePedido()) {
                    reporte.append("    - [").append(detalle.getCantidad()).append("x] ")
                           .append(detalle.getDescripcion()).append("\n");
                }
            } else {
                reporte.append("    Sin productos registrados.\n");
            }
            
            reporte.append("    --- HISTORIAL DE TRACKING ---\n");
            if (pedido.getHistorialDePedido() != null && !pedido.getHistorialDePedido().isEmpty()) {
                for (HistorialDePedido hist : pedido.getHistorialDePedido()) {
                    reporte.append("    - [").append(hist.getInstante()).append("] ")
                           .append(hist.getEstado()).append(" | ")
                           .append(hist.getObservacionIncidencia()).append("\n");
                }
            } else {
                reporte.append("    Sin historial registrado.\n");
            }
            
            reporte.append("-------------------------------------------------------\n");
        }
        
        reporte.append("=======================================================\n");
        return reporte.toString();
    }
}
