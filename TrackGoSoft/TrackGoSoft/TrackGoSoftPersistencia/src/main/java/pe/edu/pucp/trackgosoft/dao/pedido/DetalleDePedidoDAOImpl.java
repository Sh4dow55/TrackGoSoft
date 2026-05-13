package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.pedido.DetalleDePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetalleDePedidoDAOImpl extends DefaultBaseDAO<DetalleDePedido> implements DetalleDePedidoDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, DetalleDePedido modelo) throws SQLException {
        String sql = "INSERT INTO detalleDePedido(descripcion, cantidad, idPedido) VALUES(?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getDescripcion());
        cmd.setInt(2, modelo.getCantidad());
        cmd.setInt(3, modelo.getIdPedido());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, DetalleDePedido modelo) throws SQLException {
        String sql = "UPDATE detalleDePedido SET descripcion=?, cantidad=?, idPedido=? WHERE idDetalle=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getDescripcion());
        cmd.setInt(2, modelo.getCantidad());
        cmd.setInt(3, modelo.getIdPedido());
        cmd.setInt(4, modelo.getIdDetalle());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM detalleDePedido WHERE idDetalle=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM detalleDePedido WHERE idDetalle=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM detalleDePedido";
        return conn.prepareStatement(sql);
    }

    @Override
    protected DetalleDePedido mapearModelo(ResultSet rs) throws SQLException {
        DetalleDePedido detalle = new DetalleDePedido();
        detalle.setIdDetalle(rs.getInt("idDetalle"));
        detalle.setIdPedido(rs.getInt("idPedido"));
        detalle.setDescripcion(rs.getString("descripcion"));
        detalle.setCantidad(rs.getInt("cantidad"));
        return detalle;
    }

    @Override
    public List<DetalleDePedido> listarPorPedido(int idPedido) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM detalleDePedido WHERE idPedido=?";
            try (PreparedStatement cmd = conn.prepareStatement(sql)) {
                cmd.setInt(1, idPedido);
                try (ResultSet rs = cmd.executeQuery()) {
                    List<DetalleDePedido> detalles = new ArrayList<>();
                    while (rs.next()) {
                        detalles.add(mapearModelo(rs));
                    }
                    return detalles;
                }
            }
        });
    }
}
