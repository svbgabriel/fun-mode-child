CREATE TABLE produtos
(
    id            BIGSERIAL,
    sku           INT,
    nome          TEXT          NOT NULL,
    descricao     TEXT          NOT NULL,
    preco         DECIMAL(6, 2) NOT NULL,
    promovido     BOOLEAN                DEFAULT false,
    categoria_id  INT,
    refe          VARCHAR(255),
    refebig       VARCHAR(255),
    dt_cadastrado TIMESTAMP     NOT NULL DEFAULT now(),
    quantidade    INT                    DEFAULT 0,

    CONSTRAINT pk_produto PRIMARY KEY (id),
    CONSTRAINT fk_catogria_id FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);
