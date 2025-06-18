package pizzaria.controller;

import pizzaria.dao.TipoPizzaDao;
import pizzaria.model.TipoPizza;
import pizzaria.util.DaoFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TipoPizzaController {
    private final TipoPizzaDao tipoPizzaDao;

    public TipoPizzaController() {
        this.tipoPizzaDao = DaoFactory.getTipoPizzaDao();
    }

    public void adicionarTipoPizza(TipoPizza tipo) throws SQLException {
        validarTipoPizza(tipo);
        tipoPizzaDao.adicionar(tipo);
    }

    public List<TipoPizza> listarTiposPizza() throws SQLException {
        return tipoPizzaDao.listar();
    }

    public TipoPizza buscarTipoPizzaPorId(Long id) throws SQLException {
        return tipoPizzaDao.buscarPorId(id);
    }

    public void atualizarTipoPizza(TipoPizza tipo) throws SQLException {
        validarTipoPizza(tipo);
        tipoPizzaDao.atualizar(tipo);
    }

    public void removerTipoPizza(Long id) throws SQLException {
        tipoPizzaDao.remover(id);
    }

    private void validarTipoPizza(TipoPizza tipo) {
        if (tipo.getPrecoPorCm2() == null || tipo.getPrecoPorCm2().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço por cm² deve ser maior que zero");
        }
        if (tipo.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria deve ser informada");
        }
    }
}