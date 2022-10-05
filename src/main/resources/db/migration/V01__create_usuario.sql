CREATE TABLE usuario
(
    id bigserial NOT NULL PRIMARY KEY,
    nome character varying(150),
    email character varying(100),
    senha character varying(20),
    data_cadastro date default now()
);