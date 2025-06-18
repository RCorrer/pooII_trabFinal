-- Criação do usuário e banco se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'pizzashop') THEN
        CREATE USER pizzashop WITH PASSWORD 'pizzashop123';
    END IF;
END
$$;

ALTER USER pizzashop WITH SUPERUSER;
CREATE DATABASE pizzashop OWNER pizzashop;
GRANT ALL PRIVILEGES ON DATABASE pizzashop TO pizzashop;

-- Conecta ao banco
\c pizzashop

-- Criação das tabelas com relacionamentos

-- Tabela de tipos de pizza (fixos: SIMPLES, ESPECIAL, PREMIUM)
CREATE TABLE IF NOT EXISTS tipo_pizza (
    id BIGSERIAL PRIMARY KEY,
    categoria VARCHAR(10) NOT NULL UNIQUE
        CHECK (categoria IN ('SIMPLES', 'ESPECIAL', 'PREMIUM')),
    preco_por_cm2 DECIMAL(10,2) NOT NULL
);

-- Inserção dos tipos básicos (executado apenas na primeira inicialização)
INSERT INTO tipo_pizza (categoria, preco_por_cm2) VALUES
('SIMPLES', 0.10),
('ESPECIAL', 0.15),
('PREMIUM', 0.20)
ON CONFLICT (categoria) DO NOTHING;

-- Tabela de sabores (relacionada com tipo_pizza)
CREATE TABLE IF NOT EXISTS sabores (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo_pizza_id BIGINT NOT NULL REFERENCES tipo_pizza(id),
    CONSTRAINT unique_sabor_nome UNIQUE (nome)
);

-- Tabela de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL UNIQUE
);

-- Tabela de pedidos (relacionada com clientes)
CREATE TABLE IF NOT EXISTS pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id),
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL
        CHECK (status IN ('ABERTO', 'CAMINHO', 'ENTREGUE')),
    valor_total DECIMAL(10,2) NOT NULL
);

-- Tabela de pizzas (relacionada com pedidos)
CREATE TABLE IF NOT EXISTS pizzas (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    forma VARCHAR(20) NOT NULL
        CHECK (forma IN ('CIRCULO', 'QUADRADO', 'TRIANGULOEQUILATERO')),
    tamanho DECIMAL(10,2) NOT NULL,
    area DECIMAL(10,2) NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL DEFAULT 1
);

-- Tabela de relacionamento pizza-sabor (N-N)
CREATE TABLE IF NOT EXISTS pizza_sabores (
    pizza_id BIGINT NOT NULL REFERENCES pizzas(id) ON DELETE CASCADE,
    sabor_id BIGINT NOT NULL REFERENCES sabores(id),
);

-- Índices para melhor performance
CREATE INDEX IF NOT EXISTS idx_pedidos_cliente ON pedidos(cliente_id);
CREATE INDEX IF NOT EXISTS idx_pizzas_pedido ON pizzas(pedido_id);
CREATE INDEX IF NOT EXISTS idx_sabores_tipo ON sabores(tipo_pizza_id);

-- Permissões adicionais
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO pizzashop;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO pizzashop;

ALTER TABLE pedidos DROP CONSTRAINT pedidos_cliente_id_fkey;


ALTER TABLE pedidos 
ADD CONSTRAINT pedidos_cliente_id_fkey 
FOREIGN KEY (cliente_id) 
REFERENCES clientes(id) 
ON DELETE CASCADE;