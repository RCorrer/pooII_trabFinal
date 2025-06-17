package pizzaria.model.formas;

import pizzaria.enums.FormaPizza;

public class TrianguloEquilatero extends Forma {
    private static final double LADO_MINIMO = 20;
    private static final double LADO_MAXIMO = 60;

    public TrianguloEquilatero(double lado) {
        super(lado);
        validarTamanho();
    }

    @Override
    public FormaPizza getTipo() {
        return FormaPizza.TRIANGULAR;
    }

    @Override
    public double calcularArea() {
        return (Math.sqrt(3) / 4) * Math.pow(tamanho, 2);
    }

    @Override
    public double calcularTamanhoAPartirDaArea(double area) {
        return Math.sqrt((4 * area) / Math.sqrt(3));
    }

    @Override
    public void validarTamanho() {
        if (tamanho < LADO_MINIMO || tamanho > LADO_MAXIMO) {
            throw new IllegalArgumentException(
                    String.format("Lado deve estar entre %.2f e %.2f cm", LADO_MINIMO, LADO_MAXIMO));
        }
    }
}