package pizzaria.controller;

import pizzaria.dao.*;
import pizzaria.model.*;
import pizzaria.enums.FormaPizza;
import pizzaria.model.formas.Forma;
import pizzaria.util.DaoFactory;
import pizzaria.util.FormaFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaController {
    private final PizzaDao pizzaDao;
    private final SaborDao saborDao;
    private final TipoPizzaDao tipoPizzaDao;

    public PizzaController() {
        this.pizzaDao = DaoFactory.getPizzaDao();
        this.saborDao = DaoFactory.getSaborDao();
        this.tipoPizzaDao = DaoFactory.getTipoPizzaDao();
    }

    public Pizza criarPizza(FormaPizza forma, double tamanho, List<Long> saboresIds, int quantidade) throws SQLException {
        Forma formaGeometrica = FormaFactory.criarForma(forma, tamanho);

        Pizza pizza = new Pizza();
        pizza.setForma(formaGeometrica);
        pizza.setQuantidade(quantidade);
        pizza.setSaboresIds(saboresIds);

        // Calcula o valor
        List<TipoPizza> tiposSabores = buscarTiposDosSabores(saboresIds);
        pizza.calcularValor(tiposSabores);

        return pizza;
    }

    public Pizza criarPizzaPorArea(FormaPizza forma, double area, List<Long> saboresIds, int quantidade) throws SQLException {
        Forma formaGeometrica = FormaFactory.criarFormaPorArea(forma, area);

        Pizza pizza = new Pizza();
        pizza.setForma(formaGeometrica);
        pizza.setQuantidade(quantidade);
        pizza.setSaboresIds(saboresIds);

        // Calcula o valor
        List<TipoPizza> tiposSabores = buscarTiposDosSabores(saboresIds);
        pizza.calcularValor(tiposSabores);

        return pizza;
    }

    private List<TipoPizza> buscarTiposDosSabores(List<Long> saboresIds) throws SQLException {
        List<TipoPizza> tipos = new ArrayList<>();
        if (saboresIds == null) return tipos;

        for (Long saborId : saboresIds) {
            Sabor sabor = saborDao.buscarPorId(saborId);
            if (sabor != null) {
                TipoPizza tipo = tipoPizzaDao.buscarPorId(sabor.getTipoPizzaId());
                if (tipo != null) {
                    tipos.add(tipo);
                }
            }
        }
        return tipos;
    }
}