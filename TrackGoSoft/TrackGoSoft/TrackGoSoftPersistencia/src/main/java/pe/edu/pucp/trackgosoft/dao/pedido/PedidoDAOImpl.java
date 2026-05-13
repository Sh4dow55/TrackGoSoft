package pe.edu.pucp.trackgosoft.dao.pedido;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.pedido.EstadoPedido;
import pe.edu.pucp.trackgosoft.modelo.pedido.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoDAOImpl extends DefaultBaseDAO<Pedido> implements PedidoDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Pedido modelo) throws SQLException {
        String sql = "INSERT INTO pedido(codigoPedido, destinatario, fechaCreacion, fechaActualizacion, tarifaEnvio, estado, idDireccion, placa, idEmpleado, idAdministrador, idEmpresa) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getCodigoPedido());
        cmd.setString(2, modelo.getDestinatario());
        cmd.setDate(3, new java.sql.Date(modelo.getFechaCreacion().getTime()));
        cmd.setDate(4, new java.sql.Date(modelo.getFechaActualizacion().getTime()));
        cmd.setDouble(5, modelo.getTarifaEnvio());
        cmd.setString(6, modelo.getEstado().name());
        
        cmd.setInt(7, modelo.getDireccion() != null ? modelo.getDireccion().getIdDireccion() : 0);
        cmd.setString(8, modelo.getDetalleTransporte() != null ? modelo.getDetalleTransporte().getPlaca() : null);
        setEnteroNullable(cmd, 9, modelo.getIdUsuario() > 0 ? modelo.getIdUsuario() : null);
        setEnteroNullable(cmd, 10, null); 
        setEnteroNullable(cmd, 11, modelo.getEmpresaDeOrigen() != null ? modelo.getEmpresaDeOrigen().getId() : null);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Pedido modelo) throws SQLException {
        String sql = "UPDATE pedido SET codigoPedido=?, destinatario=?, fechaActualizacion=?, tarifaEnvio=?, estado=?, idDireccion=?, placa=?, idEmpleado=?, idEmpresa=? WHERE idPedido=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getCodigoPedido());
        cmd.setString(2, modelo.getDestinatario());
        cmd.setDate(3, new java.sql.Date(modelo.getFechaActualizacion().getTime()));
        cmd.setDouble(4, modelo.getTarifaEnvio());
        cmd.setString(5, modelo.getEstado().name());
        cmd.setInt(6, modelo.getDireccion() != null ? modelo.getDireccion().getIdDireccion() : 0);
        cmd.setString(7, modelo.getDetalleTransporte() != null ? modelo.getDetalleTransporte().getPlaca() : null);
        setEnteroNullable(cmd, 8, modelo.getIdUsuario() > 0 ? modelo.getIdUsuario() : null);
        setEnteroNullable(cmd, 9, modelo.getEmpresaDeOrigen() != null ? modelo.getEmpresaDeOrigen().getId() : null);
        cmd.setInt(10, modelo.getIdPedido());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM pedido WHERE idPedido=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE idPedido=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM pedido";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Pedido mapearModelo(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("idPedido"));
        pedido.setCodigoPedido(rs.getString("codigoPedido"));
        pedido.setDestinatario(rs.getString("destinatario"));
        
        java.sql.Date sqlFechaCreacion = rs.getDate("fechaCreacion");
        if (sqlFechaCreacion != null) {
            pedido.setFechaCreacion(new java.util.Date(sqlFechaCreacion.getTime()));
        }

        java.sql.Date sqlFechaActualizacion = rs.getDate("fechaActualizacion");
        if (sqlFechaActualizacion != null) {
            pedido.setFechaActualizacion(new java.util.Date(sqlFechaActualizacion.getTime()));
        }
        
        pedido.setTarifaEnvio(rs.getDouble("tarifaEnvio"));
        pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
        pedido.setIdUsuario(rs.getInt("idEmpleado"));
        
        int idDir = rs.getInt("idDireccion");
        if (!rs.wasNull()) {
            pe.edu.pucp.trackgosoft.modelo.direccion.Direccion dir = new pe.edu.pucp.trackgosoft.modelo.direccion.Direccion("", "", "", "", "", "");
            dir.setIdDireccion(idDir);
            pedido.setDireccion(dir);
        }

        int idEmp = rs.getInt("idEmpresa");
        if (!rs.wasNull()) {
            pe.edu.pucp.trackgosoft.modelo.empresa.Empresa emp = new pe.edu.pucp.trackgosoft.modelo.empresa.Empresa("", "", "", "", new java.util.Date());
            emp.setId(idEmp);
            pedido.setEmpresaDeOrigen(emp);
        }

        String placa = rs.getString("placa");
        if (placa != null) {
            pe.edu.pucp.trackgosoft.modelo.transporte.Transporte trans = new pe.edu.pucp.trackgosoft.modelo.transporte.Transporte();
            trans.setPlaca(placa);
            pedido.asignarInformacionTransporte(pedido.getIdUsuario(), trans);
        }

        return pedido;
    }

    @Override
    public Pedido buscarPorCodigo(String codigoPedido) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM pedido WHERE codigoPedido = ?";
            try (PreparedStatement cmd = conn.prepareStatement(sql)) {
                cmd.setString(1, codigoPedido);
                try (ResultSet rs = cmd.executeQuery()) {
                    if (rs.next()) {
                        return mapearModelo(rs);
                    }
                }
            }
            return null;
        });
    }
}
