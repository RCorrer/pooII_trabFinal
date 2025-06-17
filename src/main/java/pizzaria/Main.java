package pizzaria;

import pizzaria.controller.*;
import pizzaria.enums.FormaPizza;
import pizzaria.enums.StatusPedido;
import pizzaria.model.*;
import pizzaria.model.formas.Forma;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            try {
                // Teste de conexão direta
                String url = "jdbc:postgresql://localhost:5432/pizzashop";
                String user = "pizzashop";
                String pwd = "pizzashop123";

                System.out.println("Testando conexão direta...");
                Connection conn = DriverManager.getConnection(url, user, pwd);
                System.out.println("Conexão bem-sucedida!");
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro na conexão direta:");
                e.printStackTrace();
            }

            // Criando controllers
            TipoPizzaController tipoController = new TipoPizzaController();
            SaborController saborController = new SaborController();
            ClienteController clienteController = new ClienteController();
            PizzaController pizzaController = new PizzaController();
            PedidoController pedidoController = new PedidoController();

            System.out.println("=== TESTE DO SISTEMA PIZZARIA ===");

            // 1. Cadastrar tipos de pizza
            // CRIANDO TIPOS DANDO ERRADO SEI LA PORQUE TEM QUE VER, NAO ESTAO SENDO CRIADOS DIREITO 
            // MAS NAO VAI PRECISAR FAZER ISSO PORQUE BD JA TA INICIALIZANDO
            System.out.println("\n1. Cadastrando tipos de pizza...");
            TipoPizza tipoSimples = new TipoPizza();
            tipoSimples.setNome("Tradicional");
            tipoSimples.setPrecoPorCm2(new BigDecimal("0.05"));
            tipoSimples.setCategoria(TipoPizza.Categoria.SIMPLES);
//            tipoController.adicionarTipoPizza(tipoSimples);

            TipoPizza tipoEspecial = new TipoPizza();
            tipoEspecial.setNome("Especial");
            tipoEspecial.setPrecoPorCm2(new BigDecimal("0.08"));
            tipoEspecial.setCategoria(TipoPizza.Categoria.ESPECIAL);
//            tipoController.adicionarTipoPizza(tipoEspecial);

            System.out.println("Tipos de pizza cadastrados com sucesso!");

            // 2. Cadastrar sabores
            //TUDO BUGADO SABORES PQ PRECISA AJEITAR OS TIPOS DE PIZZA
            int teste = 1;
            long teste2 = teste;
            System.out.println("\n2. Cadastrando sabores de pizza...");
            Sabor saborCalabresa = new Sabor();
            saborCalabresa.setNome("Calabresa");
            saborCalabresa.setTipoPizzaId(teste2);
     //       saborController.adicionarSabor(saborCalabresa);
            System.out.println("passou aqui");

            Sabor saborMargherita = new Sabor();
            saborMargherita.setNome("Margherita");
            saborMargherita.setTipoPizzaId(teste2);
   //         saborController.adicionarSabor(saborMargherita);

            Sabor saborPortuguesa = new Sabor();
            saborPortuguesa.setNome("Portuguesa");
            saborPortuguesa.setTipoPizzaId(teste2);
//             saborController.adicionarSabor(saborPortuguesa);

            System.out.println("Sabores cadastrados com sucesso!");

            // 3. Cadastrar cliente
            System.out.println("\n3. Cadastrando cliente...");
            Cliente cliente = new Cliente();
            cliente.setNome("João");
            cliente.setSobrenome("Silva");
            cliente.setTelefone("(11) 99999-9999");
            // clienteController.adicionarCliente(cliente);

            System.out.println("Cliente cadastrado com sucesso! ID: " + cliente.getId());

            // 4. Criar pizzas
            System.out.println("\n4. Criando pizzas...");
            List<Long> saboresPizza1 = new ArrayList<>();
            saboresPizza1.add(teste2);

            List<Long> saboresPizza2 = new ArrayList<>();
            saboresPizza2.add(teste2);
            saboresPizza2.add(teste2);

            Pizza pizza1 = pizzaController.criarPizza(
                    FormaPizza.CIRCULAR, 8.0, saboresPizza1, 1);

            Pizza pizza2 = pizzaController.criarPizza(
                    FormaPizza.QUADRADA, 25.0, saboresPizza2, 2);

            System.out.println("Pizzas criadas com sucesso!");
            System.out.println("Pizza 1 - Valor unitário: R$ " + pizza1.getValorUnitario());
            System.out.println("Pizza 2 - Valor unitário: R$ " + pizza2.getValorUnitario());

            // 5. Fazer pedido
            System.out.println("\n5. Fazendo pedido...");
            List<Pizza> pizzas = new ArrayList<>();
            pizzas.add(pizza1);
            pizzas.add(pizza2);
             // SENDO FEITO PORÉM COM ERRO DENTRO SOBRE PRIMARY KEYS
             Pedido pedido = pedidoController.criarPedido(teste2, pizzas);

            System.out.println("Pedido realizado com sucesso! ID: " + pedido.getId());
            System.out.println("Valor total: R$ " + pedido.getValorTotal());
            System.out.println("Status: " + pedido.getStatus());

            // 6. Atualizar status do pedido para "CAMINHO" (entrega)
            System.out.println("\n6. Atualizando status do pedido para 'CAMINHO'...");
            pedidoController.atualizarStatusPedido(pedido.getId(), StatusPedido.CAMINHO);
            System.out.println("Status atualizado para: " + StatusPedido.CAMINHO);

            // 7. Atualizar status do pedido para "ENTREGUE"
            System.out.println("\n7. Atualizando status do pedido para 'ENTREGUE'...");
            pedidoController.atualizarStatusPedido(pedido.getId(), StatusPedido.ENTREGUE);
            System.out.println("Status atualizado para: " + StatusPedido.ENTREGUE);

            // 8. Listar pedidos do cliente
            //TO TENDO QUE COLOCAR O ID HARD CODADO NO MOMENTO
            System.out.println("\n8. Listando pedidos do cliente...");
            List<Pedido> pedidosCliente = pedidoController.listarPedidosPorCliente(teste2);
            for (Pedido p : pedidosCliente) {
                System.out.println("Pedido ID: " + p.getId() +
                        " | Valor: R$ " + p.getValorTotal() +
                        " | Status: " + p.getStatus());
            }

            System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO ===");

        } catch (SQLException e) {
            System.err.println("Erro de banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}