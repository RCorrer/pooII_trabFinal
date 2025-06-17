package pizzaria.dao;

import pizzaria.model.TipoPizza;
import java.sql.SQLException;
import java.util.List;

public interface TipoPizzaDao {
    void adicionar(TipoPizza tipo) throws SQLException;
    List<TipoPizza> listar() throws SQLException;
    TipoPizza buscarPorId(Long id) throws SQLException;
    void atualizar(TipoPizza tipo) throws SQLException;
    void remover(Long id) throws SQLException;
}