/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pizzaria.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import pizzaria.model.Pedido;


/**
 *
 * @author joaow
 */
public class TableModelPedidosDoCliente extends AbstractTableModel{

    private String[] colunas=new String[]{"ID","Valor Total", "Status"};

    private List<Pedido> lista=new ArrayList();

    
    public TableModelPedidosDoCliente(List<Pedido> lista){
        this.lista=lista;
    }

    public TableModelPedidosDoCliente(){
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
        Pedido pedido = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return pedido.getId();//if column 0 (code)
            case 1: return pedido.getValorTotal();//if column 1 (name)
            case 2: return pedido.getStatus().toString();//if column 2 (birthday)
            default : return null;
        }
    }

    public boolean removePedido(Pedido pedido) {
        int linha = this.lista.indexOf(pedido);
        boolean result = this.lista.remove(pedido);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaPedido(Pedido pedido) {
        this.lista.add(pedido);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
    }

    public void setListaPedidos(List<Pedido> pedidos) {
        this.lista = pedidos;
        this.fireTableDataChanged();
        //this.fireTableRowsInserted(0,contatos.size()-1);//update JTable
    }
    
    public void setListaPedido(HashMap<String,Pedido> pedidos) {
        
        this.lista = new ArrayList<Pedido>();
        for(Pedido p:pedidos.values()){
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

    public Pedido getPedido(int linha){
        return lista.get(linha);
    }

    public void removePedidos(List<Pedido> listaParaExcluir) {
        for(Pedido p:listaParaExcluir)
            removePedido(p);
    }
    
    public void atualizarPedido(int linha){
        this.fireTableRowsUpdated(linha, linha);
    }    
}
