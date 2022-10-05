CREATE TABLE lancamento
(
    id bigserial NOT NULL PRIMARY KEY ,
    descricao character varying(100) NOT NULL,
    mes integer NOT NULL,
    ano integer NOT NULL,
    valor numeric(16,2),
    tipo character varying(20),
    status character varying(20),
    id_usuario bigint REFERENCES usuario (id),
    data_cadastro date default now()
);