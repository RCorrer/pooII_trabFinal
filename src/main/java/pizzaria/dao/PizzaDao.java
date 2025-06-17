package pizzaria.dao;

import pizzaria.model.Pizza;
import java.sql.SQLException;
import java.util.List;

public interface PizzaDao {
    void adicionar(Pizza pizza) throws SQLException;
    List<Pizza> listar() throws SQLException;
    Pizza buscarPorId(Long id) throws SQLException;
    void atualizar(Pizza pizza) throws SQLException;
    void remover(Long id) throws SQLException;
    List<Pizza> buscarPorPedido(Long pedidoId) throws SQLException;
}