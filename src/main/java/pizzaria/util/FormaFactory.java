package pizzaria.util;

import pizzaria.enums.FormaPizza;
import pizzaria.model.formas.*;

public class FormaFactory {
    public static Forma criarForma(FormaPizza tipo, double tamanho) {
        switch (tipo) {
            case CIRCULAR:
                return new Circulo(tamanho);
            case QUADRADA:
                return new Quadrado(tamanho);
            case TRIANGULAR:
                return new TrianguloEquilatero(tamanho);
            default:
                throw new IllegalArgumentException("Tipo de forma inválido");
        }
    }

    public static Forma criarFormaPorArea(FormaPizza tipo, double area) {
        switch (tipo) {
            case CIRCULAR:
                return new Circulo(new Circulo(0).calcularTamanhoAPartirDaArea(area));
            case QUADRADA:
                return new Quadrado(new Quadrado(0).calcularTamanhoAPartirDaArea(area));
            case TRIANGULAR:
                return new TrianguloEquilatero(new TrianguloEquilatero(0).calcularTamanhoAPartirDaArea(area));
            default:
                throw new IllegalArgumentException("Tipo de forma inválido");
        }
    }
}