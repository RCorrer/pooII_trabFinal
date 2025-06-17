package pizzaria.dao;

import pizzaria.model.Sabor;
import java.sql.SQLException;
import java.util.List;

public interface SaborDao {
    void adicionar(Sabor sabor) throws SQLException;
    List<Sabor> listar() throws SQLException;
    Sabor buscarPorId(Long id) throws SQLException;
    void atualizar(Sabor sabor) throws SQLException;
    void remover(Long id) throws SQLException;
    List<Sabor> buscarPorTipoPizza(Long tipoPizzaId) throws SQLException;
}