package pizzaria.model;

import pizzaria.model.formas.Forma;

import java.math.BigDecimal;
import java.util.List;

public class Pizza {
    private Long id;
    private Long pedidoId;
    private Forma forma;
    private BigDecimal valorUnitario;
    private int quantidade;
    private List<Long> saboresIds;

    public Pizza() {}

    public Pizza(Long id, Long pedidoId, Forma forma,
                 BigDecimal valorUnitario, int quantidade,
                 List<Long> saboresIds) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.forma = forma;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.saboresIds = saboresIds;
    }

    public void calcularValor(List<TipoPizza> tiposSabores) {
        if (tiposSabores == null || tiposSabores.isEmpty()) return;

        BigDecimal somaPrecos = BigDecimal.ZERO;
        for (TipoPizza tipo : tiposSabores) {
            somaPrecos = somaPrecos.add(tipo.getPrecoPorCm2());
        }

        BigDecimal mediaPrecos = somaPrecos.divide(
                new BigDecimal(tiposSabores.size()),
                BigDecimal.ROUND_HALF_UP);

        this.valorUnitario = forma.calcularValor(mediaPrecos);
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Forma getForma() { return forma; }
    public void setForma(Forma forma) { this.forma = forma; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public List<Long> getSaboresIds() { return saboresIds; }
    public void setSaboresIds(List<Long> saboresIds) {
        if (saboresIds != null && saboresIds.size() > 2) {
            throw new IllegalArgumentException("No máximo 2 sabores por pizza");
        }
        this.saboresIds = saboresIds;
    }
}