package pizzaria.model;

public class Sabor {
    private Long id;
    private String nome;
    private Long tipoPizzaId;

    public Sabor() {}

    public Sabor(Long id, String nome, Long tipoPizzaId) {
        this.id = id;
        this.nome = nome;
        this.tipoPizzaId = tipoPizzaId;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Long getTipoPizzaId() { return tipoPizzaId; }
    public void setTipoPizzaId(Long tipoPizzaId) { this.tipoPizzaId = tipoPizzaId; }
}