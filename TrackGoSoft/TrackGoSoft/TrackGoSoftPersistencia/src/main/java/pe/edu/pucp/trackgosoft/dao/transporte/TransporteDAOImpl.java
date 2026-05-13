package pe.edu.pucp.trackgosoft.dao.transporte;

import pe.edu.pucp.trackgosoft.dao.BaseDAO;
import pe.edu.pucp.trackgosoft.modelo.transporte.Transporte;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransporteDAOImpl extends BaseDAO<Transporte, String> implements TransporteDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Transporte modelo) throws SQLException {
        String sql = "INSERT INTO transporte(placa, tipo, marca, modelo) VALUES(?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getPlaca());
        cmd.setString(2, modelo.getTipo());
        cmd.setString(3, modelo.getMarca());
        cmd.setString(4, modelo.getModelo());
        return cmd;
    }

    @Override
    protected String ejecutarComandoCrear(Connection conn, Transporte modelo) {
        try (PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
            if (cmd.executeUpdate() == 0) {
                return null;
            }
            // Como la placa se provee, devolvemos la misma placa como ID
            return modelo.getPlaca();
        } catch (SQLException e) {
            System.err.println("Error SQL en TransporteDAO: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, Transporte modelo) throws SQLException {
        String sql = "UPDATE transporte SET tipo=?, marca=?, modelo=? WHERE placa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getTipo());
        cmd.setString(2, modelo.getMarca());
        cmd.setString(3, modelo.getModelo());
        cmd.setString(4, modelo.getPlaca());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, String id) throws SQLException {
        String sql = "DELETE FROM transporte WHERE placa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, String id) throws SQLException {
        String sql = "SELECT * FROM transporte WHERE placa=?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM transporte";
        return conn.prepareStatement(sql);
    }

    @Override
    protected Transporte mapearModelo(ResultSet rs) throws SQLException {
        Transporte transporte = new Transporte(
                rs.getString("placa"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getString("modelo")
        );
        return transporte;
    }

    @Override
    protected String extraerIdDesdeCallable(CallableStatement cmd) throws SQLException {
        return null; // No usado
    }

    @Override
    protected String extraerIdDesdeGeneratedKeys(ResultSet rs) throws SQLException {
        return null; // No usado
    }
}
