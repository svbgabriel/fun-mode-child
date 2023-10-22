CREATE TABLE pagamentos
(
    id             BIGSERIAL,
    pedido_id      INT         NOT NULL,
    numero_cartao  VARCHAR(16) NULL,
    nome_cartao    VARCHAR(18) NULL,
    validade_mes   INTEGER     NULL,
    validade_ano   INTEGER     NULL,
    codigo         INT         NULL,
    parcelas       INT         NULL,
    data_pagamento TIMESTAMP   NOT NULL DEFAULT now(),

    CONSTRAINT pk_pagamento PRIMARY KEY (id),
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id)
);