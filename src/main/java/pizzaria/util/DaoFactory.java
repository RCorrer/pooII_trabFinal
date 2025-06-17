package pizzaria.util;

import pizzaria.dao.*;

public class DaoFactory {
    private DaoFactory() {}

    public static ClienteDao getClienteDao() {
        return new ClienteDaoSql();
    }

    public static PedidoDao getPedidoDao() {
        return new PedidoDaoSql();
    }

    public static PizzaDao getPizzaDao() {
        return new PizzaDaoSql();
    }

    public static SaborDao getSaborDao() {
        return new SaborDaoSql();
    }

    public static TipoPizzaDao getTipoPizzaDao() {
        return new TipoPizzaDaoSql();
    }
}