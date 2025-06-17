package pizzaria.model.formas;

import pizzaria.enums.FormaPizza;

public class Circulo extends Forma {
    private static final double RAIO_MINIMO = 7;
    private static final double RAIO_MAXIMO = 23;

    public Circulo(double raio) {
        super(raio);
        validarTamanho();
    }

    @Override
    public FormaPizza getTipo() {
        return FormaPizza.CIRCULAR;
    }

    @Override
    public double calcularArea() {
        return Math.PI * Math.pow(tamanho, 2);
    }

    @Override
    public double calcularTamanhoAPartirDaArea(double area) {
        return Math.sqrt(area / Math.PI);
    }

    @Override
    public void validarTamanho() {
        if (tamanho < RAIO_MINIMO || tamanho > RAIO_MAXIMO) {
            throw new IllegalArgumentException(
                    String.format("Raio deve estar entre %.2f e %.2f cm", RAIO_MINIMO, RAIO_MAXIMO));
        }
    }
}