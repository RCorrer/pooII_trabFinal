package pizzaria.controller;

import pizzaria.dao.ClienteDao;
import pizzaria.model.Cliente;
import pizzaria.util.DaoFactory;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private static int idsCliente;
    private final ClienteDao clienteDao;

    public ClienteController() {
        this.clienteDao = DaoFactory.getClienteDao();
    }

    public void adicionarCliente(Cliente cliente) throws SQLException {
        validarCliente(cliente);
        clienteDao.adicionar(cliente);
    }

    public List<Cliente> listarClientes() throws SQLException {
        return clienteDao.listar();
    }

    public Cliente buscarClientePorId(Long id) throws SQLException {
        return clienteDao.buscarPorId(id);
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        validarCliente(cliente);
        clienteDao.atualizar(cliente);
    }

    public void removerCliente(Long id) throws SQLException {
        clienteDao.remover(id);
    }

    public List<Cliente> buscarClientes(String filtro) throws SQLException {
        return clienteDao.buscar(filtro);
    }
    
    public static int IncrementaIdCliente(){
        idsCliente++;
        return idsCliente;
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
        }
        if (cliente.getSobrenome() == null || cliente.getSobrenome().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome do cliente não pode ser vazio");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente não pode ser vazio");
        }
    }
}