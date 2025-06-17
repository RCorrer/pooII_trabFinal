/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pizzaria.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import pizzaria.controller.SaborController;
import pizzaria.model.Pizza;
import pizzaria.model.Sabor;

/**
 *
 * @author joaow
 */
public class TableModelPizzas extends AbstractTableModel{
    private String[] colunas=new String[]{"Forma","Sabor 1","Sabor 2", "Dimensao","Area","Preço Total"};

    private List<Pizza> lista=new ArrayList();

    
   
    public TableModelPizzas(){
    }
    
    public TableModelPizzas(List<Pizza> pizzas){
        this.lista = pizzas;
    }


    @Override
    public int getRowCount() {
        return this.lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
        /*if(column==0)
            return true;
        return false;*/
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        //tratar caso nao tenha 2 sabor e escrever nenhum na tabelinha
        Pizza pizza = lista.get(rowIndex);
        String sabor2Nome = "";
        List<Long> listaIdsSabores = pizza.getSaboresIds();
        SaborController saborcontroller = new SaborController();
        Sabor sabor1;       
        try {
            sabor1 = saborcontroller.buscarSaborPorId(listaIdsSabores.get(0));
        
        
        String sabor1Nome = sabor1.getNome();
        if (lista.size() == 2){
            sabor2Nome = saborcontroller.buscarSaborPorId(listaIdsSabores.get(1)).getNome();
        } else {
            sabor2Nome = "Nenhum";
        }
        
        
        switch (columnIndex) {
            case 0: return pizza.getForma();//if column 0 (code)
            case 1: return sabor1Nome;//if column 1 (name)
            case 2: return sabor2Nome;//if column 2 (birthday)
            case 3: return pizza.getForma().getTamanho();
            case 4: return pizza.getForma().calcularArea();//if column 2 (birthday)
            case 5: return pizza.getValorUnitario();

            default : return null;
        }
        
        } catch (SQLException ex) {
            Logger.getLogger(TableModelPizzas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean removePizza(Pizza pizza) {
        int linha = this.lista.indexOf(pizza);
        boolean result = this.lista.remove(pizza);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaPizza(Pizza pizza) {
        this.lista.add(pizza);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
    }

    public void setListaPizzas(List<Pizza> pizzas) {
        this.lista = pizzas;
        this.fireTableDataChanged();
        this.fireTableRowsInserted(0,pizzas.size()-1);//update JTable
    }
    
    public void setListaPizzas(HashMap<String,Pizza> pizza) {
        
        this.lista = new ArrayList<Pizza>();
        for(Pizza p:pizza.values()){
            this.lista.add(p);
        }
        this.fireTableDataChanged();
        //this.fireTableRowsInserted(0,contatos.size()-1);//update JTable
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public Pizza getPizza(int linha){
        return lista.get(linha);
    }

    public void removePizzas(List<Pizza> listaParaExcluir) {
        for(Pizza p:listaParaExcluir)
            removePizza(p);
    }
    
    public void atualizarPizza(int linha){
        this.fireTableRowsUpdated(linha, linha);
    }    
}
