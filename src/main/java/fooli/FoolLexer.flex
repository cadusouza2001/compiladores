package fooli;

/* Arquivo: FoolLexer.flex */
/*
 * Carlos Souza e Murilo Schuck
 * Compiladores - Trabalho GB
 */

import java_cup.runtime.Symbol;

%%

%cup
%public
%class FoolLexer

%%

// Ignore whitespaces like tabs, newlines, and spaces
[ \t\n\r]+          { /* Ignore whitespaces */ }

// Numeric constants (integers)
[0-9]+              { return new Symbol(sym.INT_CONST, Integer.parseInt(yytext())); }

// Operators and special symbols
"+"                 { return new Symbol(sym.PLUS, yytext()); }
"*"                 { return new Symbol(sym.TIMES, yytext()); }
"not"               { return new Symbol(sym.NOT, yytext()); }
"="                 { return new Symbol(sym.ASSIGN, yytext()); }
"}"                 { return new Symbol(sym.RBRACE, yytext()); }
"or"                { return new Symbol(sym.OR, yytext()); }
"=="                { return new Symbol(sym.EQ, yytext()); }
"<"                 { return new Symbol(sym.LT, yytext()); }
">"                 { return new Symbol(sym.GT, yytext()); }
"("                 { return new Symbol(sym.LPAREN, yytext()); }
")"                 { return new Symbol(sym.RPAREN, yytext()); }
"and"               { return new Symbol(sym.AND, yytext()); }
","                 { return new Symbol(sym.COMMA, yytext()); }
"{"                 { return new Symbol(sym.LBRACE, yytext()); }
";"                 { return new Symbol(sym.SEMICOLON, yytext()); }

// Reserved words (keywords)
"while"             { return new Symbol(sym.WHILE, yytext()); }
"void"              { return new Symbol(sym.VOID, yytext()); }
"class"             { return new Symbol(sym.CLASS, yytext()); }
"int"               { return new Symbol(sym.INT, yytext()); }
"if"                { return new Symbol(sym.IF, yytext()); }
"True"              { return new Symbol(sym.TRUE, Boolean.parseBoolean(yytext())); }
"else"              { return new Symbol(sym.ELSE, yytext()); }
"bool"              { return new Symbol(sym.BOOL, yytext()); }
"return"            { return new Symbol(sym.RETURN, yytext()); }
"False"             { return new Symbol(sym.FALSE, Boolean.parseBoolean(yytext())); }


// Identifiers for variables, functions, and classes
[a-zA-Z_][a-zA-Z0-9_]* { return new Symbol(sym.IDENTIFIER, yytext()); }

// Unexpected symbols (error handling)
.                   { System.err.println("Unexpected token: " + yytext()); }