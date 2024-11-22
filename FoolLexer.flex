/* Arquivo: FoolLexer.flex */
/*
 * Carlos Souza e Murilo Schuck
 * Compiladores - Trabalho GA2
 */

import java_cup.runtime.Symbol;

%%

%class FoolLexer
%unicode
%cup

digit = -?[0-9]+
letter = [a-zA-Z]
ident = {letter}({letter}|{digit})*

%%

/* Palavras-chave */
"class"        { return new Symbol(sym.CLASS); }
"int"          { return new Symbol(sym.INT); }
"bool"         { return new Symbol(sym.BOOL); }
"void"         { return new Symbol(sym.VOID); }
"if"           { return new Symbol(sym.IF); }
"else"         { return new Symbol(sym.ELSE); }
"return"       { return new Symbol(sym.RETURN); }
"True"         { return new Symbol(sym.TRUE); }
"False"        { return new Symbol(sym.FALSE); }
"not"          { return new Symbol(sym.NOT); }
"and"          { return new Symbol(sym.AND); }
"or"           { return new Symbol(sym.OR); }
"while"        { return new Symbol(sym.WHILE); }
"main"         { return new Symbol(sym.MAIN); }

/* Operadores */
"="            { return new Symbol(sym.ASSIGN); }
"=="           { return new Symbol(sym.EQ); }
"<"            { return new Symbol(sym.LT); }
">"            { return new Symbol(sym.GT); }
"+"            { return new Symbol(sym.PLUS); }
"*"            { return new Symbol(sym.TIMES); }
"("            { return new Symbol(sym.LPAREN); }
")"            { return new Symbol(sym.RPAREN); }
"{"            { return new Symbol(sym.LBRACE); }
"}"            { return new Symbol(sym.RBRACE); }
";"            { return new Symbol(sym.SEMICOLON); }
","            { return new Symbol(sym.COMMA); }
"-"            { return new Symbol(sym.MINUS); }
"/"            { return new Symbol(sym.DIVIDE); }
"!="           { return new Symbol(sym.NEQ); }

/* Identificadores */
{ident}        { return new Symbol(sym.IDENTIFIER); }

/* Números */
{digit}+       { return new Symbol(sym.INT_CONST); }

/* Comentários e espaços em branco */
"//".*         { /* Ignorar comentários de linha única */ }
"/*"([^*]|[*][^/])*"*/" { /* Ignorar comentários de bloco */ }
[ \t\n\r\f]+   { /* Ignorar espaços em branco */ }

.              { throw new Error("Caractere inválido: " + yytext()); }