package pe.edu.pucp.trackgosoft.dao.empresa;

import pe.edu.pucp.trackgosoft.dao.DefaultBaseDAO;
import pe.edu.pucp.trackgosoft.modelo.empresa.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpresaDAOImpl extends DefaultBaseDAO<Empresa> implements EmpresaDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Empresa modelo) throws SQLException {
        String sql = "INSERT INTO empresa(nombre, RUC, direccion, sector, fechaFundacion) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getNombre());
        cmd.setString(2, modelo.getRuc());
        cmd.setString(3, modelo.getDireccion());
        cmd.setString(4, modelo.getSector());
        if (modelo.getFechaFundacion() != null) {
            cmd.setDate(5, new java.sql.Date(modelo.getFechaFundacion().getTime()));
        } else {
            cmd.setNull(5, java.sql.Types.DATE);
        }
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Empresa modelo) throws SQLException {
        String sql = "UPDATE empresa SET nombre=?, RUC=?, direccion=?, sector=?, fechaFundacion=? WHERE idEmpresa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getNombre());
        cmd.setString(2, modelo.getRuc());
        cmd.setString(3, modelo.getDireccion());
        cmd.setString(4, modelo.getSector());
        if (modelo.getFechaFundacion() != null) {
            cmd.setDate(5, new java.sql.Date(modelo.getFechaFundacion().getTime()));
        } else {
            cmd.setNull(5, java.sql.Types.DATE);
        }
        cmd.setInt(6, modelo.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM empresa WHERE idEmpresa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM empresa WHERE idEmpresa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM empresa";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Empresa mapearModelo(ResultSet rs) throws SQLException {
        Empresa empresa = new Empresa(
                rs.getString("nombre"),
                rs.getString("RUC"),
                rs.getString("direccion"),
                rs.getString("sector"),
                rs.getDate("fechaFundacion")
        );
        empresa.setId(rs.getInt("idEmpresa"));
        return empresa;
    }

    @Override
    public Empresa buscarPorRuc(String ruc) {
        return ejecutarComando(conn -> {
            String sql = "SELECT * FROM empresa WHERE RUC=?";
            try (PreparedStatement cmd = conn.prepareStatement(sql)) {
                cmd.setString(1, ruc);
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
