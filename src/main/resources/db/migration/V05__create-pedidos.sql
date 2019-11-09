CREATE TABLE pedidos
(
id INT AUTO_INCREMENT,
data_pedido TIMESTAMP NOT NULL DEFAULT now(),
usuario_id INT NOT NULL,
preco_total DECIMAL(7,2) NOT NULL,
ativo TINYINT(1) NULL DEFAULT 1,

CONSTRAINT pk_pedido PRIMARY KEY(id),
CONSTRAINT fk_usuario_pedidos FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
