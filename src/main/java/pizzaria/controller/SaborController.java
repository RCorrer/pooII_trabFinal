package pizzaria.controller;

import pizzaria.dao.SaborDao;
import pizzaria.model.Sabor;
import pizzaria.util.DaoFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SaborController {
    private final SaborDao saborDao;
    private static int incrementSaborId;

    public SaborController() {
        this.saborDao = DaoFactory.getSaborDao();
    }

    public void adicionarSabor(Sabor sabor) throws SQLException {
        validarSabor(sabor);
        saborDao.adicionar(sabor);
    }

    public List<Sabor> listarSabores() throws SQLException {
        return saborDao.listar();
    }
    
    

    public Sabor buscarSaborPorId(Long id) throws SQLException {
        return saborDao.buscarPorId(id);
    }

    public void atualizarSabor(Sabor sabor) throws SQLException {
        validarSabor(sabor);
        saborDao.atualizar(sabor);
    }

    public void removerSabor(Long id) throws SQLException {
        saborDao.remover(id);
    }

    public List<Sabor> buscarSaboresPorTipo(Long tipoPizzaId) throws SQLException {
        return saborDao.buscarPorTipoPizza(tipoPizzaId);
    }
    // para imprimir os sabores em string
    public String imprimeSabores(){
        StringBuilder todosSabores = new StringBuilder();
        
        try {
        todosSabores.append("=========================\n");
        todosSabores.append("SABORES SIMPLES:\n");
        todosSabores.append("=========================\n");
        
        for (Sabor s : saborDao.listar()){
            if (Objects.equals(s.getTipoPizzaId(), Long.valueOf(1))){
                todosSabores.append(s.getNome()).append("\n");
            }
         }
        
        todosSabores.append("=========================\n");
        todosSabores.append("SABORES ESPECIAIS:\n");        
        todosSabores.append("=========================\n");
        
        for (Sabor s : saborDao.listar()){
            if (Objects.equals(s.getTipoPizzaId(), Long.valueOf(2))){
                todosSabores.append(s.getNome()).append("\n");
            }
         }
        
        todosSabores.append("=========================\n");
        todosSabores.append("SABORES PREMIUM:\n");
        todosSabores.append("=========================\n");
         for (Sabor s : saborDao.listar()){
            if (Objects.equals(s.getTipoPizzaId(), Long.valueOf(3))){
                todosSabores.append(s.getNome()).append("\n");
            }
         }
        
        return todosSabores.toString();
        } catch (SQLException ex) {
            System.out.println("ERRO NO IMPRIME SABORES:\n" +ex);
            return null;
        }
    }

    private void validarSabor(Sabor sabor) {
        if (sabor.getNome() == null || sabor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do sabor não pode ser vazio");
        }
        if (sabor.getTipoPizzaId() == null) {
            throw new IllegalArgumentException("Tipo da pizza deve ser informado");
        }
    }
    
    public static int incrementarId(){
        incrementSaborId++;
        return incrementSaborId;
    }
    
    
}