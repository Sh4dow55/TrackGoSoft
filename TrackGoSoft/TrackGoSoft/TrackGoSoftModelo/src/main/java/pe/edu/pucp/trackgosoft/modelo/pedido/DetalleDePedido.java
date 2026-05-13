package pe.edu.pucp.trackgosoft.modelo.pedido;

/**
 * Ítem de un pedido: describe qué se está enviando y en qué cantidad.
 * Tiene idDetalle (auto-generado por DB) e idPedido para la FK.
 */
public class DetalleDePedido {
    private int idDetalle;
    private int idPedido;
    private String descripcion;
    private int cantidad;

    public DetalleDePedido() {}

    public DetalleDePedido(String descripcion, int cantidad) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public DetalleDePedido(int idPedido, String descripcion, int cantidad) {
        this.idPedido = idPedido;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return descripcion + " x" + cantidad;
    }
}
