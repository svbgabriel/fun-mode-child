CREATE TABLE detalhe_pedidos
( 
	id INT AUTO_INCREMENT,
  pedido_id INT ,
  produto_id INT NOT NULL,
  preco_item  DECIMAL(7,2) NOT NULL,
  quantidade INT,

  CONSTRAINT pk_detalhe_pedidos PRIMARY KEY(id),
  CONSTRAINT fk_detalhe_produto FOREIGN KEY (produto_id) REFERENCES produtos(id),
  CONSTRAINT fk_detalhe_pedidos FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);
