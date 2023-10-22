CREATE TABLE categoria
(
    id   BIGSERIAL,
    nome varchar(255) NOT NULL,

    CONSTRAINT pk_categoria PRIMARY KEY (id)
);
