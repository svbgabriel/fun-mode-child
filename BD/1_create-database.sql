DROP database IF exists funchildmode;

CREATE database funchildmode;

USE funchildmode;

CREATE TABLE conta
(
  usuario_id INTEGER AUTO_INCREMENT,
  usuario VARCHAR(18) NOT NULL UNIQUE,
  nome VARCHAR(100),
  email VARCHAR(100),
  cpf varchar(11) NOT NULL,
  logradouro VARCHAR(20) NOT NULL,
  endereco varchar(255) NOT NULL,
  numero varchar(6) NOT NULL,
  complemento varchar(255) DEFAULT NULL,
  bairro varchar(255) NOT NULL,
  cep varchar(8) NOT NULL,
  cidade varchar(255) NOT NULL,
  estado varchar(2) NOT NULL,
  telefone varchar(25) NOT NULL,
  senha VARCHAR(12),
  active TINYINT(1),

CONSTRAINT pk_conta PRIMARY KEY (usuario_id)
);

CREATE TABLE categoria
(
categoria_id INTEGER,
nome varchar(255) NOT NULL,

CONSTRAINT pk_categoria PRIMARY KEY (categoria_id)
);

CREATE TABLE produtos
(
  sku INTEGER,
  nome LONGTEXT NOT NULL,
  descricao LONGTEXT NOT NULL,
  preco DECIMAL(6,2) NOT NULL,
  promovido TINYINT(1) DEFAULT 0,
  categoria_id INTEGER,
  refe VARCHAR(255),
  refebig VARCHAR(255),
  dt_cadastrado TIMESTAMP NOT NULL DEFAULT now(),

CONSTRAINT pk_produto PRIMARY KEY (sku),
CONSTRAINT fk_catogria_categoriaid FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id)
);

CREATE TABLE estoque
(
sku INTEGER NOT NULL,
quantidadeEstoque INTEGER NOT NULL,

CONSTRAINT fk_estoque_produto FOREIGN KEY (sku) REFERENCES produtos(sku)

);

CREATE TABLE pedidos
(
pedido_id INTEGER AUTO_INCREMENT,
data_pedido TIMESTAMP NOT NULL DEFAULT now(),
usuario VARCHAR(18) NOT NULL,
preco_total DECIMAL(7,2) NOT NULL,

CONSTRAINT pk_pedido PRIMARY KEY(pedido_id),
CONSTRAINT fk_usuario_pedidos FOREIGN KEY (usuario) REFERENCES conta(usuario)
);


CREATE TABLE detalhe_pedidos
( id_DetalhePedido INTEGER AUTO_INCREMENT,
  pedido_id INTEGER ,
  sku INTEGER NOT NULL,
  preco_item  DECIMAL(7,2) NOT NULL,
  quantidade INTEGER,

  CONSTRAINT pk_detalhe_pedidos PRIMARY KEY(id_DetalhePedido),
  CONSTRAINT fk_detalhe_produto FOREIGN KEY (sku) REFERENCES produtos(sku),
  CONSTRAINT fk_detalhe_pedidos FOREIGN KEY (pedido_id) REFERENCES pedidos(pedido_id)
)
