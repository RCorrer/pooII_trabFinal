package pizzaria.controller;

import pizzaria.dao.*;
import pizzaria.enums.StatusPedido;
import pizzaria.model.*;
import pizzaria.util.DaoFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoController {
    private final PedidoDao pedidoDao;
    private final PizzaDao pizzaDao;
    private final ClienteDao clienteDao;

    public PedidoController() {
        this.pedidoDao = DaoFactory.getPedidoDao();
        this.pizzaDao = DaoFactory.getPizzaDao();
        this.clienteDao = DaoFactory.getClienteDao();
    }

    public Pedido criarPedido(Long clienteId, List<Pizza> pizzas) throws SQLException {
        validarClienteExistente(clienteId);

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setPizzas(pizzas);

        // Calcula valor total
        BigDecimal valorTotal = calcularValorTotal(pizzas);
        pedido.setValorTotal(valorTotal);

        pedidoDao.adicionar(pedido);

        // Adiciona pizzas ao pedido
        for (Pizza pizza : pizzas) {
            pizza.setPedidoId(pedido.getId());
            pizzaDao.adicionar(pizza);
        }

        return pedido;
    }

    public void atualizarStatusPedido(Long pedidoId, StatusPedido novoStatus) throws SQLException {
        Pedido pedido = pedidoDao.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        pedido.setStatus(novoStatus);
        pedidoDao.atualizar(pedido);
    }

    public List<Pedido> listarPedidosPorCliente(Long clienteId) throws SQLException {
         return pedidoDao.buscarPorCliente(clienteId);
    }

    public List<Pedido> listarPedidosPorStatus(StatusPedido status) throws SQLException {
        return pedidoDao.buscarPorStatus(status.name());
    }

    private BigDecimal calcularValorTotal(List<Pizza> pizzas) {
        BigDecimal total = BigDecimal.ZERO;
        for (Pizza pizza : pizzas) {
            total = total.add(pizza.getValorUnitario().multiply(new BigDecimal(pizza.getQuantidade())));
        }
        return total;
    }

    private void validarClienteExistente(Long clienteId) throws SQLException {
        if (clienteDao.buscarPorId(clienteId) == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
    }
}