CREATE DATABASE DEMO;

SET DATABASE DEMO;

CREATE TABLE PRODUTO(
CODIGO NUMBER(6),
DESCRICAO VARCHAR(50) NOT NULL,
VALOR NUMBER(6,2),
CONSTRAINT PK_PRODUTO PRIMARY KEY (CODIGO)
);

CREATE TABLE ITEM_PEDIDO(
NUMERO_PEDIDO NUMBER(6),
CODIGO_PRODUTO NUMBER(6),
QUANTIDADE NUMBER(6),
CONSTRAINT PK_ITEM_PEDIDO PRIMARY KEY (NUMERO_PEDIDO, CODIGO_PRODUTO)
);

CREATE TABLE PEDIDO(
NUMERO_PEDIDO NUMBER(6) PRIMARY KEY,
DATA DATE,
VALOR_TOTAL NUMBER(10,2),
CODIGO_CLIENTE NUMBER(4)
);


CREATE TABLE CLIENTE(
CODIGO NUMBER(4) PRIMARY KEY,
NOME VARCHAR(100),
SEXO CHAR(1),
DATA_CADASTRO DATE,
TELEFONE VARCHAR(15)
);

CREATE INDEX IDX_NOME ON CLIENTE(NOME);

CREATE INDEX IDX_DESCRICAO ON PRODUTO(DESCRICAO);

INSERT INTO PRODUTO VALUES(0001, "Teclado usb", 31.90);
INSERT INTO PRODUTO(CODIGO, DESCRICAO, VALOR) VALUES(2, "Mouse usb", 41.90);
INSERT INTO PRODUTO VALUES(4, "Notebook core i5 8GB HD 1T", 1889);
INSERT INTO PRODUTO VALUES(5, "Teclado usb", 35);

INSERT INTO CLIENTE VALUES(100, "Maria Santos", "F", 01/12/2012, "47-3344-0000" );
INSERT INTO CLIENTE VALUES(101, "Bruna da Silva", "F", 01/06/2013, "47-3344-0011" );
INSERT INTO CLIENTE VALUES(102, "Marcos", "M", 02/03/2014, "" );

INSERT INTO PEDIDO VALUES(1000, 10/11/2014, 2500, 101);

DROP TABLE CLIENTE;
