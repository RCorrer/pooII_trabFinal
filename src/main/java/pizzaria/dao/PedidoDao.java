package pizzaria.dao;

import pizzaria.model.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface PedidoDao {
    void adicionar(Pedido pedido) throws SQLException;
    List<Pedido> listar() throws SQLException;
    Pedido buscarPorId(Long id) throws SQLException;
    void atualizar(Pedido pedido) throws SQLException;
    void remover(Long id) throws SQLException;
    List<Pedido> buscarPorCliente(Long clienteId) throws SQLException;
    List<Pedido> buscarPorStatus(String status) throws SQLException;
}