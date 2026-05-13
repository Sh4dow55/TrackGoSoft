package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.pedido.EstadoPedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.HistorialDePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HistorialDePedidoDAOImpl extends DefaultBaseDAO<HistorialDePedido> implements HistorialDePedidoDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, HistorialDePedido modelo) throws SQLException {
        String sql = "INSERT INTO historialDePedido(instante, idEmpleado, idAdministrador, estado, placa, duracionSegundos, observacionIncidencia, idPedido) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setTimestamp(1, modelo.getInstante());
        
        setEnteroNullable(cmd, 2, modelo.getIdEmpleado() > 0 ? modelo.getIdEmpleado() : null);
        setEnteroNullable(cmd, 3, modelo.getIdAdministrador() > 0 ? modelo.getIdAdministrador() : null);
        
        cmd.setString(4, modelo.getEstado().name());
        cmd.setString(5, modelo.getPlaca());
        cmd.setLong(6, modelo.getDuracionSegundos());
        cmd.setString(7, modelo.getObservacionIncidencia());
        cmd.setInt(8, modelo.getIdPedido());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, HistorialDePedido modelo) throws SQLException {
        String sql = "UPDATE historialDePedido SET instante=?, idEmpleado=?, idAdministrador=?, estado=?, placa=?, duracionSegundos=?, observacionIncidencia=?, idPedido=? WHERE idHistorial=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setTimestamp(1, modelo.getInstante());
        
        setEnteroNullable(cmd, 2, modelo.getIdEmpleado() > 0 ? modelo.getIdEmpleado() : null);
        setEnteroNullable(cmd, 3, modelo.getIdAdministrador() > 0 ? modelo.getIdAdministrador() : null);
        
        cmd.setString(4, modelo.getEstado().name());
        cmd.setString(5, modelo.getPlaca());
        cmd.setLong(6, modelo.getDuracionSegundos());
        cmd.setString(7, modelo.getObservacionIncidencia());
        cmd.setInt(8, modelo.getIdPedido());
        cmd.setInt(9, modelo.getIdHistorial());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM historialDePedido WHERE idHistorial=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM historialDePedido WHERE idHistorial=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM historialDePedido";
        return conn.prepareStatement(sql);
    }

    @Override
    protected HistorialDePedido mapearModelo(ResultSet rs) throws SQLException {
        HistorialDePedido historial = new HistorialDePedido();
        historial.setIdHistorial(rs.getInt("idHistorial"));
        historial.setIdPedido(rs.getInt("idPedido"));
        historial.setInstante(rs.getTimestamp("instante"));
        historial.setIdEmpleado(rs.getInt("idEmpleado"));
        historial.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
        historial.setPlaca(rs.getString("placa"));
        historial.setDuracionSegundos(rs.getLong("duracionSegundos"));
        historial.setObservacionIncidencia(rs.getString("observacionIncidencia"));
        
        Integer idAdmin = leerEnteroNullable(rs, "idAdministrador");
        if(idAdmin != null) {
            historial.setIdAdministrador(idAdmin);
        }
        return historial;
    }

    @Override
    public List<HistorialDePedido> listarPorPedido(int idPedido) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM historialDePedido WHERE idPedido=? ORDER BY instante ASC";
            try (PreparedStatement cmd = conn.prepareStatement(sql)) {
                cmd.setInt(1, idPedido);
                try (ResultSet rs = cmd.executeQuery()) {
                    List<HistorialDePedido> historiales = new ArrayList<>();
                    while (rs.next()) {
                        historiales.add(mapearModelo(rs));
                    }
                    return historiales;
                }
            }
        });
    }
}
