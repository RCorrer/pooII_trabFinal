package pizzaria.dao;

import pizzaria.model.Cliente;
import pizzaria.util.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoSql implements ClienteDao {
    @Override
    public void adicionar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, sobrenome, telefone) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getTelefone());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return clientes;
    }

    @Override
    public Cliente buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return null;
    }

    @Override
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, telefone = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setLong(4, cliente.getId());
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Cliente> buscar(String filtro) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR sobrenome LIKE ? OR telefone LIKE ?";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String termo = "%" + filtro + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            stmt.setString(3, termo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return clientes;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setSobrenome(rs.getString("sobrenome"));
        cliente.setTelefone(rs.getString("telefone"));
        return cliente;
    }
}