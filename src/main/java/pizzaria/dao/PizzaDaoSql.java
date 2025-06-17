package pizzaria.dao;

import pizzaria.model.Pizza;
import pizzaria.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class PizzaDaoSql implements PizzaDao {
    @Override
    public void adicionar(Pizza pizza) throws SQLException {
        String sql = "INSERT INTO pizzas (pedido_id, forma, tamanho,area, valor_unitario, quantidade) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, pizza.getPedidoId());
            stmt.setString(2, pizza.getForma().getClass().getSimpleName().toUpperCase()); // Alterado aqui
            stmt.setDouble(3, pizza.getForma().getTamanho());
            stmt.setDouble(4, pizza.getForma().calcularArea());
            stmt.setBigDecimal(5, pizza.getValorUnitario());
            stmt.setInt(6, pizza.getQuantidade());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pizza.setId(rs.getLong(1));
                }
            }

            adicionarSaboresPizza(pizza);
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    private void adicionarSaboresPizza(Pizza pizza) throws SQLException {
        String sql = "INSERT INTO pizza_sabores (pizza_id, sabor_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Long saborId : pizza.getSaboresIds()) {
                stmt.setLong(1, pizza.getId());
                stmt.setLong(2, saborId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Pizza> listar() throws SQLException {
        String sql = "SELECT p.* FROM pizzas p";
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pizza pizza = mapearPizza(rs);
                pizza.setSaboresIds(buscarSaboresDaPizza(pizza.getId()));
                pizzas.add(pizza);
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return pizzas;
    }

    @Override
    public Pizza buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM pizzas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pizza pizza = mapearPizza(rs);
                    pizza.setSaboresIds(buscarSaboresDaPizza(id));
                    return pizza;
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return null;
    }

    @Override
    public void atualizar(Pizza pizza) throws SQLException {
        String sql = "UPDATE pizzas SET pedido_id = ?, forma = ?, tamanho = ?, valor_unitario = ?, quantidade = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pizza.getPedidoId());
            stmt.setString(2, pizza.getForma().getTipo().name());
            stmt.setDouble(3, pizza.getForma().getTamanho());
            stmt.setBigDecimal(4, pizza.getValorUnitario());
            stmt.setInt(5, pizza.getQuantidade());
            stmt.setLong(6, pizza.getId());
            stmt.executeUpdate();

            atualizarSaboresPizza(pizza);
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    private void atualizarSaboresPizza(Pizza pizza) throws SQLException {
        // Primeiro remove os sabores antigos
        String deleteSql = "DELETE FROM pizza_sabores WHERE pizza_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setLong(1, pizza.getId());
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }

        // Depois adiciona os novos
        adicionarSaboresPizza(pizza);
    }

    @Override
    public void remover(Long id) throws SQLException {
        // Primeiro remove os sabores associados
        String deleteSaboresSql = "DELETE FROM pizza_sabores WHERE pizza_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSaboresSql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }

        // Depois remove a pizza
        String sql = "DELETE FROM pizzas WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
    }

    @Override
    public List<Pizza> buscarPorPedido(Long pedidoId) throws SQLException {
        String sql = "SELECT * FROM pizzas WHERE pedido_id = ?";
        List<Pizza> pizzas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pizza pizza = mapearPizza(rs);
                    pizza.setSaboresIds(buscarSaboresDaPizza(pizza.getId()));
                    pizzas.add(pizza);
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return pizzas;
    }

    private List<Long> buscarSaboresDaPizza(Long pizzaId) throws SQLException {
        String sql = "SELECT sabor_id FROM pizza_sabores WHERE pizza_id = ?";
        List<Long> saboresIds = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pizzaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    saboresIds.add(rs.getLong("sabor_id"));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Erro ao acessar propriedades do banco", e);
        }
        return saboresIds;
    }

    private Pizza mapearPizza(ResultSet rs) throws SQLException {
        Pizza pizza = new Pizza();
        pizza.setId(rs.getLong("id"));
        pizza.setPedidoId(rs.getLong("pedido_id"));

        String formaStr = rs.getString("forma");
        double tamanho = rs.getDouble("tamanho");
        pizza.setForma(pizzaria.util.FormaFactory.criarForma(
                pizzaria.enums.FormaPizza.valueOf(formaStr),
                tamanho));

        pizza.setValorUnitario(rs.getBigDecimal("valor_unitario"));
        pizza.setQuantidade(rs.getInt("quantidade"));
        return pizza;
    }
}