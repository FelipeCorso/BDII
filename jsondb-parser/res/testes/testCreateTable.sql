CREATE TABLE 'tabela' (
	col_1 NUMBER(010, 10) PRIMARY KEY,
	col_2 NUMBER(0020),
	col_3 VARCHAR (001) CONSTRAINT ccol_3_nn NOT NULL,
	col_4 DATE constraint 'constraint' NULL,
	col_5 CHAR (010) references 'table'(col_pk),
	CONSTRAINT c1 FOREIGN KEY (col_1, 'col_2') REFERENCES 'table2'(other_col1, other_col2),
	constraint c2 PRIMARY KEY (cola, colb)
);