
-- Erro multi pk

CREATE TABLE PRODUTO2(
CODIGO NUMBER(6) PRIMARY KEY,
DESCRICAO VARCHAR(50) PRIMARY KEY,
VALOR NUMBER(6,2)
);


-- Erro tabela não existe
CREATE INDEX IDX_X ON xxx(NOME);

-- Erro coluna não existe
CREATE INDEX IDX_X ON CLIENTE(XXX);

-- Erro já existe índice com este nome
CREATE INDEX IDX_NOME ON CLIENTE(NOME);

-- Erro não informou valor para todas as colunas
INSERT INTO PRODUTO VALUES(9, "Mouse usb");

-- Erro, não informou valor para campo not null
INSERT INTO PRODUTO(CODIGO, VALOR) VALUES(15, 41.90);

-- Erro tamanho invalido na precisao
INSERT INTO PRODUTO VALUES(5, "Teclado usb", 35.555);

-- Erro tabela não existe
DROP TABLE xxx;
