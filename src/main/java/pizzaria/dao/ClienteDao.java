package pizzaria.dao;

import pizzaria.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao {
    void adicionar(Cliente cliente) throws SQLException;
    List<Cliente> listar() throws SQLException;
    Cliente buscarPorId(Long id) throws SQLException;
    void atualizar(Cliente cliente) throws SQLException;
    void remover(Long id) throws SQLException;
    List<Cliente> buscar(String filtro) throws SQLException;
}