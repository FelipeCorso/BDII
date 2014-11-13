SELECT campoA, 'campoB', 'tabela1'.'campoa', tabela1.'campob' FROM tabela1, tabela2;
CREATE DATABASE 'database';
SET DATABASE 'nome_base';
SET DATABASE nome_base;
SELECT * FROM tabela 
	WHERE	campoA = 1 
		OR	campoB = "texto" 
		AND	campoC <> 2.12 
		OR	campoD > "texto2" 
		OR	campoE < 3 
		OR	tabela.'campoF' <= 7 
		OR	'tabela'.campoG >= 6
		AND	'campoH' = 25/10/2014;
SELECT * FROM 'tabela1', tabela2, 'tabela3';
INSERT INTO 'table' VALUES ("literal simples", "\\", "\"", "\\\"");
DROP TABLE 'tabela';
DROP TABLE tabela;
DROP INDEX 'novo_indice' ON 'tabela';
DROP INDEX novo_indice ON tabela;
DESCRIBE tabela;
CREATE TABLE 'tabela' (
	col_1 NUMBER(010, 10) PRIMARY KEY,
	col_2 NUMBER(0020),
	col_3 VARCHAR (001) CONSTRAINT ccol_3_nn NOT NULL,
	col_4 DATE constraint 'constraint' NULL,
	col_5 CHAR (010) references 'table'(col_pk),
	CONSTRAINT c1 FOREIGN KEY (col_1, 'col_2') REFERENCES 'table2'(other_col1, other_col2),
	constraint c2 PRIMARY KEY (cola, colb)
);
CREATE INDEX 'novo_indice' ON 'tabela'('campo');
CREATE INDEX novo_indice ON tabela(campo);


;STOP;