CREATE TABLE usuarios
(
    id          BIGSERIAL,
    nome        VARCHAR(100),
    email       VARCHAR(100) NOT NULL UNIQUE,
    cpf         varchar(11)  NOT NULL,
    endereco    varchar(255) NOT NULL,
    numero      varchar(6)   NOT NULL,
    complemento varchar(255) DEFAULT NULL,
    bairro      varchar(255) NOT NULL,
    cep         varchar(8)   NOT NULL,
    cidade      varchar(255) NOT NULL,
    estado      varchar(2)   NOT NULL,
    telefone    varchar(25)  NOT NULL,
    senha       VARCHAR(100),
    active      BOOLEAN,

    CONSTRAINT pk_usuario PRIMARY KEY (id)
);
