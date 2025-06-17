package pizzaria.dao;

import pizzaria.model.Sabor;
import pizzaria.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class SaborDaoSql implements SaborDao {
    @Override
    public void adicionar(Sabor sabor) throws SQLException {
        String sql = "INSERT INTO sabores (nome, tipo_pizza_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sabor.getNome());
            stmt.setLong(2, sabor.getTipoPizzaId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sabor.setId(rs.getLong(1));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Sabor> listar() throws SQLException {
        String sql = "SELECT * FROM sabores";
        List<Sabor> sabores = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sabores.add(mapearSabor(rs));
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return sabores;
    }

    @Override
    public Sabor buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM sabores WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearSabor(rs);
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return null;
    }

    @Override
    public void atualizar(Sabor sabor) throws SQLException {
        String sql = "UPDATE sabores SET nome = ?, tipo_pizza_id = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sabor.getNome());
            stmt.setLong(2, sabor.getTipoPizzaId());
            stmt.setLong(3, sabor.getId());
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM sabores WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Sabor> buscarPorTipoPizza(Long tipoPizzaId) throws SQLException {
        String sql = "SELECT * FROM sabores WHERE tipo_pizza_id = ?";
        List<Sabor> sabores = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, tipoPizzaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sabores.add(mapearSabor(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return sabores;
    }

    private Sabor mapearSabor(ResultSet rs) throws SQLException {
        Sabor sabor = new Sabor();
        sabor.setId(rs.getLong("id"));
        sabor.setNome(rs.getString("nome"));
        sabor.setTipoPizzaId(rs.getLong("tipo_pizza_id"));
        return sabor;
    }
}