package pizzaria.model.formas;

import pizzaria.enums.FormaPizza;

public class Quadrado extends Forma {
    private static final double LADO_MINIMO = 10;
    private static final double LADO_MAXIMO = 40;

    public Quadrado(double lado) {
        super(lado);
        validarTamanho();
    }

    @Override
    public FormaPizza getTipo() {
        return FormaPizza.QUADRADA;
    }

    @Override
    public double calcularArea() {
        return Math.pow(tamanho, 2);
    }

    @Override
    public double calcularTamanhoAPartirDaArea(double area) {
        return Math.sqrt(area);
    }

    @Override
    public void validarTamanho() {
        if (tamanho < LADO_MINIMO || tamanho > LADO_MAXIMO) {
            throw new IllegalArgumentException(
                    String.format("Lado deve estar entre %.2f e %.2f cm", LADO_MINIMO, LADO_MAXIMO));
        }
    }
}