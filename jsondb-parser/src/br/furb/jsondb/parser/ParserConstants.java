package br.furb.jsondb.parser;

public interface ParserConstants
{
    int START_SYMBOL = 44;

    int FIRST_NON_TERMINAL    = 44;
    int FIRST_SEMANTIC_ACTION = 84;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1,  0, -1, -1, -1, -1,  0,  0,  0, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1,  3, -1, -1, -1, -1,  4,  5,  6, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  8,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10 },
        { -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 13, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 15, 16, 17, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 20, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 22, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, 21, 21, 21, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, 24, 24, 24, -1, 24, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, 26, 27, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 29, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 33, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 34, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 38, -1, 38, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, 42, 42, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, 43, 44, 45, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 48, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 49, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 50, -1, -1, -1, -1, -1, -1, -1, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 52, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 53, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 54, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 55, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 57, -1, 56, 57, 57, 57, 57, 57, 57, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 57, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 59, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 58, -1, -1, -1, -1, -1, -1 },
        { -1, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 61, 61, -1, -1, -1, -1 },
        { -1, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 64, 65, 66, 67, 68, 69, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 70, 71, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 72, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 75, -1, -1 },
        { -1, 77, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 76, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 73, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 74 }
    };

    int[][] PRODUCTIONS = 
    {
        {  45,   6, 183 },
        {  46 },
        {  62 },
        {  67 },
        {  79 },
        {  80 },
        {  82,   6 },
        {  18,  47 },
        {  19,   2, 135 },
        {  20,   2,  97,   7,  48,  56,   8 },
        {  43, 150,  81 },
        {  50,  49 },
        {   9,  50,  49 },
        {   0 },
        {   2, 101,  51, 137,  53 },
        {  21,  85,   7,   3,  93,  52,   8 },
        {  22,  86,   7,   3,  93,   8 },
        {  23,  87 },
        {  24,  88,   7,   3,  93,   8 },
        {   9,   3,  92 },
        {   0 },
        {  54,  55, 139 },
        {   0 },
        {  25,   2,  99 },
        {   0 },
        {  26, 104 },
        {  27,  26, 105 },
        {  28,  29, 106 },
        {  30, 107,   2,  97,   7,   2,  96,   8 },
        {  57 },
        {   0 },
        {  25,   2,  99,  59, 139,  58 },
        {   9,  57 },
        {   0 },
        {  28,  29, 106,   7,  60, 100,   8 },
        {  31,  29, 108,   7,  60, 100,   8,  30,   2,  97,   7,  60, 100,   8 },
        {   2,  96,  61 },
        {   9,  60 },
        {   0 },
        {  32,  33,   2,  97,  63,  34,   7, 122,  64, 123,   8 },
        {   7,  94,  60,  95,   8 },
        {   0 },
        {  65,  66 },
        {   3, 116 },
        {   4, 117 },
        {   5, 118 },
        {  26, 104 },
        {   9,  64 },
        {   0 },
        {  35,  68, 102,  36,  60, 103,  73 },
        {  69 },
        {  10 },
        {  71,  70 },
        {   9,  69 },
        {   0 },
        {   2,  96,  72 },
        {  11,   2,  98 },
        {   0 },
        {  37,  74 },
        {   0 },
        {  76,  75 },
        {  78, 115,  74 },
        {   0 },
        {  71,  77, 114,  65 },
        {  12 },
        {  13 },
        {  14 },
        {  15 },
        {  16 },
        {  17 },
        {  38 },
        {  39 },
        {  40,  83 },
        {  20,   2, 145 },
        {  43, 151,  81 },
        {  41,   2, 147 },
        {  42,  19,   2, 149 },
        {   2,  97,   7,   2,  96,   8 }
    };

    String[] PARSER_ERROR =
    {
        "",
        "Era esperado fim de programa",
        "Era esperado id",
        "Era esperado numero",
        "Era esperado literal",
        "Era esperado data",
        "Era esperado \";\"",
        "Era esperado \"(\"",
        "Era esperado \")\"",
        "Era esperado \",\"",
        "Era esperado \"*\"",
        "Era esperado \".\"",
        "Era esperado \"=\"",
        "Era esperado \">\"",
        "Era esperado \"<\"",
        "Era esperado \">=\"",
        "Era esperado \"<=\"",
        "Era esperado \"<>\"",
        "Era esperado CREATE",
        "Era esperado DATABASE",
        "Era esperado TABLE",
        "Era esperado NUMBER",
        "Era esperado VARCHAR",
        "Era esperado DATE",
        "Era esperado CHAR",
        "Era esperado CONSTRAINT",
        "Era esperado NULL",
        "Era esperado NOT",
        "Era esperado PRIMARY",
        "Era esperado KEY",
        "Era esperado REFERENCES",
        "Era esperado FOREIGN",
        "Era esperado INSERT",
        "Era esperado INTO",
        "Era esperado VALUES",
        "Era esperado SELECT",
        "Era esperado FROM",
        "Era esperado WHERE",
        "Era esperado AND",
        "Era esperado OR",
        "Era esperado DROP",
        "Era esperado DESCRIBE",
        "Era esperado SET",
        "Era esperado INDEX",
        "<sentenca> inv�lido",
        "<acao> inv�lido",
        "<criar> inv�lido",
        "<cria_estrutura> inv�lido",
        "<lista_atributos> inv�lido",
        "<lista_atributos_1> inv�lido",
        "<atributo> inv�lido",
        "<tipo> inv�lido",
        "<decimal> inv�lido",
        "<restricao> inv�lido",
        "<nome_restricao_opt> inv�lido",
        "<restricao_constraint> inv�lido",
        "<lista_restricoes_finais_opt> inv�lido",
        "<lista_restricoes_finais> inv�lido",
        "<lista_restricoes_finais_rec> inv�lido",
        "<tipo_restricao_final> inv�lido",
        "<lista_ids> inv�lido",
        "<lista_ids_rec> inv�lido",
        "<incluir> inv�lido",
        "<colunas> inv�lido",
        "<lista_valores> inv�lido",
        "<valor> inv�lido",
        "<lista_valores_rec> inv�lido",
        "<selecionar> inv�lido",
        "<campos> inv�lido",
        "<lista_campos> inv�lido",
        "<lista_campos_rec> inv�lido",
        "<campo> inv�lido",
        "<tabela_opt> inv�lido",
        "<clausula_where> inv�lido",
        "<lista_condicoes> inv�lido",
        "<lista_condicoes_rec> inv�lido",
        "<condicao> inv�lido",
        "<op_relacional> inv�lido",
        "<op_logico> inv�lido",
        "<eliminar> inv�lido",
        "<descrever> inv�lido",
        "<index> inv�lido",
        "<setar_banco> inv�lido",
        "<eliminar_estrutura> inv�lido"
    };
}
