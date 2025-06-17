package pizzaria.dao;

import pizzaria.model.Pedido;
import pizzaria.util.ConnectionFactory;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class PedidoDaoSql implements PedidoDao {
    @Override
    public void adicionar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (cliente_id, data_pedido, status, valor_total) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, pedido.getClienteId());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getDataPedido()));
            stmt.setString(3, pedido.getStatus().name());
            stmt.setBigDecimal(4, pedido.getValorTotal());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getLong(1));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Pedido> listar() throws SQLException {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pedidos.add(mapearPedido(rs));
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return pedidos;
    }

    @Override
    public Pedido buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return null;
    }

    @Override
    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedidos SET cliente_id = ?, data_pedido = ?, status = ?, valor_total = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pedido.getClienteId());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getDataPedido()));
            stmt.setString(3, pedido.getStatus().name());
            stmt.setBigDecimal(4, pedido.getValorTotal());
            stmt.setLong(5, pedido.getId());
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Pedido> buscarPorCliente(Long clienteId) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE cliente_id = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return pedidos;
    }

    @Override
    public List<Pedido> buscarPorStatus(String status) throws SQLException {
        String sql = "SELECT * FROM pedidos WHERE status = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearPedido(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return pedidos;
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getLong("id"));
        pedido.setClienteId(rs.getLong("cliente_id"));
        pedido.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime());
        pedido.setStatus(pizzaria.enums.StatusPedido.valueOf(rs.getString("status")));
        pedido.setValorTotal(rs.getBigDecimal("valor_total"));
        return pedido;
    }
}