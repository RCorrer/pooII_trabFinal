package pizzaria.model;

import java.math.BigDecimal;

public class TipoPizza {
    public enum Categoria {
        SIMPLES, ESPECIAL, PREMIUM
    }

    private Long id;
    private String nome;
    private BigDecimal precoPorCm2;
    private Categoria categoria;

    public TipoPizza() {}

    public TipoPizza(Long id, String nome, BigDecimal precoPorCm2, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoPorCm2 = precoPorCm2;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoPorCm2() { return precoPorCm2; }
    public void setPrecoPorCm2(BigDecimal precoPorCm2) { this.precoPorCm2 = precoPorCm2; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}