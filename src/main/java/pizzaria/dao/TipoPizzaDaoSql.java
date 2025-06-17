package pizzaria.dao;

import pizzaria.model.TipoPizza;
import pizzaria.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class TipoPizzaDaoSql implements TipoPizzaDao {
    @Override
    public void adicionar(TipoPizza tipo) throws SQLException {
        String sql = "INSERT INTO tipo_pizza (nome, preco_por_cm2, categoria) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipo.getNome());
            stmt.setBigDecimal(2, tipo.getPrecoPorCm2());
            stmt.setString(3, tipo.getCategoria().name()); // Alterado para usar o enum Categoria
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipo.setId(rs.getLong(1));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<TipoPizza> listar() throws SQLException {
        String sql = "SELECT * FROM tipo_pizza";
        List<TipoPizza> tipos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tipos.add(mapearTipoPizza(rs));
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return tipos;
    }

    @Override
    public TipoPizza buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM tipo_pizza WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTipoPizza(rs);
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return null;
    }

    @Override
    public void atualizar(TipoPizza tipo) throws SQLException {
        String sql = "UPDATE tipo_pizza SET nome = ?, preco_por_cm2 = ?, categoria = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo.getNome());
            stmt.setBigDecimal(2, tipo.getPrecoPorCm2());
            stmt.setString(3, tipo.getCategoria().name());
            stmt.setLong(4, tipo.getId());
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public void remover(Long id) throws SQLException {
        String sql = "DELETE FROM tipo_pizza WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    private TipoPizza mapearTipoPizza(ResultSet rs) throws SQLException {
        TipoPizza tipo = new TipoPizza();
        tipo.setId(rs.getLong("id"));
        tipo.setPrecoPorCm2(rs.getBigDecimal("preco_por_cm2"));
        tipo.setCategoria(TipoPizza.Categoria.valueOf(rs.getString("categoria"))); // Alterado para usar o enum interno
        return tipo;
    }
}