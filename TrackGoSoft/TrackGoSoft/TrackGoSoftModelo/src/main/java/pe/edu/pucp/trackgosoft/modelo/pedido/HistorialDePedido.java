package pe.edu.pucp.trackgosoft.modelo.pedido;

import java.sql.Timestamp;
import java.time.Duration;

/**
 * Registro de un evento de tracking del pedido.
 * Cada cambio de estado del pedido genera una entrada en el historial.
 *
 * Tiene idHistorial (auto-generado por DB) e idPedido para la FK.
 */
public class HistorialDePedido {
    private int idHistorial;
    private int idPedido;
    private Timestamp instante;
    private int idEmpleado;
    private int idAdministrador;
    private EstadoPedido estado;
    private String placa;
    private long duracionSegundos;
    private String observacionIncidencia;

    public HistorialDePedido() {}

    public HistorialDePedido(int idPedido, Timestamp instante, int idEmpleado,
                             EstadoPedido estado, String placa,
                             long duracionSegundos, String observacionIncidencia) {
        this.idPedido = idPedido;
        this.instante = instante;
        this.idEmpleado = idEmpleado;
        this.idAdministrador = 0;
        this.estado = estado;
        this.placa = placa;
        this.duracionSegundos = duracionSegundos;
        this.observacionIncidencia = observacionIncidencia;
    }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public Timestamp getInstante() { return instante; }
    public void setInstante(Timestamp instante) { this.instante = instante; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(int idAdministrador) { this.idAdministrador = idAdministrador; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public long getDuracionSegundos() { return duracionSegundos; }
    public void setDuracionSegundos(long duracionSegundos) { this.duracionSegundos = duracionSegundos; }

    public String getObservacionIncidencia() { return observacionIncidencia; }
    public void setObservacionIncidencia(String observacionIncidencia) {
        this.observacionIncidencia = observacionIncidencia;
    }

    @Override
    public String toString() {
        return "[" + instante + "] Estado: " + estado + " | Observación: " + observacionIncidencia;
    }
}
