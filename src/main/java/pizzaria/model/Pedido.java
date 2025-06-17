package pizzaria.model;

import pizzaria.enums.StatusPedido;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public class Pedido {
    private Long id;
    private Long clienteId;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private List<Pizza> pizzas;

    public Pedido() {}

    public Pedido(Long id, Long clienteId, LocalDateTime dataPedido,
                  StatusPedido status, BigDecimal valorTotal, List<Pizza> pizzas) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.status = status;
        this.valorTotal = valorTotal;
        this.pizzas = pizzas;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public List<Pizza> getPizzas() { return pizzas; }
    public void setPizzas(List<Pizza> pizzas) { this.pizzas = pizzas; }
}