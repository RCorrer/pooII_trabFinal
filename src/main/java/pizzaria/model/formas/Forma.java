package pizzaria.model.formas;

import pizzaria.enums.FormaPizza;

import java.math.BigDecimal;

public abstract class Forma {
    protected double tamanho;

    public Forma(double tamanho) {
        this.tamanho = tamanho;
    }

    public abstract double calcularArea();
    public abstract double calcularTamanhoAPartirDaArea(double area);
    public abstract void validarTamanho();
    public abstract FormaPizza getTipo();

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
        validarTamanho();
    }

    public BigDecimal calcularValor(BigDecimal precoPorCm2) {
        return precoPorCm2.multiply(new BigDecimal(calcularArea()));
    }
}