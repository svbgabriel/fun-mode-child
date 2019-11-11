CREATE TABLE produtos
(
	id INT AUTO_INCREMENT,
	sku INT,
	nome LONGTEXT NOT NULL,
	descricao LONGTEXT NOT NULL,
	preco DECIMAL(6,2) NOT NULL,
	promovido TINYINT(1) DEFAULT 0,
	categoria_id INT,
	refe VARCHAR(255),
 	refebig VARCHAR(255),
	dt_cadastrado TIMESTAMP NOT NULL DEFAULT now(),
	quantidade INT DEFAULT 0,

	CONSTRAINT pk_produto PRIMARY KEY (id),
	CONSTRAINT fk_catogria_id FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);
