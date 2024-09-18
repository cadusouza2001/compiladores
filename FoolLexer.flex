/* Arquivo: FoolLexer.flex */
/*
 * Carlos Souza e Murilo Schuck
 * Compiladores - Trabalho GA1
 */

%%

%standalone
%class FoolLexer
%unicode

digit = -?[0-9]+
letter = [a-zA-Z]
ident = {letter}({letter}|{digit})*

%%

/* Palavras-chave */
"class"        { return 1; }
"int"          { return 2; }
"bool"         { return 3; }
"void"         { return 4; }
"if"           { return 5; }
"else"         { return 6; }
"return"       { return 7; }
"True"         { return 8; }
"False"        { return 9; }
"not"          { return 10; }
"and"          { return 11; }
"or"           { return 12; }
"while"        { return 13; }

/* Operadores */
"="            { return 14; }
"=="           { return 15; }
"<"            { return 16; }
">"            { return 17; }
"+"            { return 18; }
"*"            { return 19; }
"("            { return 20; }
")"            { return 21; }
"{"            { return 22; }
"}"            { return 23; }
";"            { return 24; }
","            { return 25; }
"-"            { return 26; }
"/"            { return 27; }
"!="           { return 28; }

/* Identificadores */
{ident}        { return 29; }

/* Números */
{digit}+       { return 30; }

/* Comentários e espaços em branco */
"//".*         { /* Ignorar comentários de linha única */ }
"/*"([^*]|[*][^/])*"*/" { /* Ignorar comentários de bloco */ }
[ \t\n\r\f]+   { /* Ignorar espaços em branco */ }

.              { throw new Error("Caractere inválido: " + yytext()); }