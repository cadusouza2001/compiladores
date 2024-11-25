# Analisador Sintático e Gerador de Código para FOOLI

Este projeto implementa um analisador sintático e gerador de código intermediário para a linguagem de programação **FOOLI** (Fake Object-Oriented Language with Iteration), uma extensão da linguagem FOOL. O objetivo é reconhecer construções da linguagem, realizar análise semântica e gerar código intermediário no formato TAC (Three-Address Code).

## Especificação da Linguagem FOOLI

### Novos Recursos

FOOLI adiciona dois novos elementos em relação à FOOL:

- Comando while: Um laço de repetição que avalia uma expressão booleana antes de cada iteração.
- Função principal main: O ponto de entrada do programa.

### Elementos da Linguagem

1. Declaração de Classe:
    - Uma classe é definida com atributos e métodos que operam sobre eles.
    - Exemplo: Uma classe pode conter atributos inteiros ou booleanos, além de métodos que realizam cálculos ou controlam o fluxo do programa.

2. Tipos de Dados:
    - int: Para números inteiros.
    - bool: Para valores booleanos (True ou False).
    - void: Usado como tipo de retorno para métodos que não retornam valores.

3. Comandos de Controle:
    - Comando if-else: Executa uma ação com base em uma condição booleana.
    - Comando while: Repete uma ação enquanto uma condição booleana for verdadeira.
    - Comando return: Finaliza um método e retorna um valor, quando necessário.

4. Operadores:
    - Aritméticos: Soma, subtração, multiplicação e divisão.
    - Relacionais: Igualdade e comparações.
    - Lógicos: Operadores como AND, OR e NOT.

5. Chamadas de Métodos:
    - Métodos podem ser chamados dentro de outros comandos para realizar cálculos ou alterações em variáveis.

### Exemplo de Programa FOOLI

Um exemplo simples demonstra a criação de uma classe com atributos, métodos e controle de fluxo no método principal.

## Configuração do Projeto

### Dependências

Certifique-se de ter as seguintes ferramentas instaladas:

- Java Development Kit (JDK), versão 17 ou superior.
- JFlex, versão 1.9.1.
- Java CUP, versão 11b.

### Comandos para Gerar os Arquivos

1. Gerar o Analisador Léxico:
```sh
java -jar lib/jflex-full-1.9.1.jar .\src\main\java\fooli\FoolLexer.flex
```

2. Gerar o Analisador Sintático:
```sh
java -jar lib/java-cup-11b.jar -parser FoolParser -symbols sym -destdir .\src\main\java\fooli .\src\main\java\fooli\FoolParser.cup
```

### Executando o Projeto

1. Compile o projeto usando os arquivos gerados.
2. Execute a classe `Main` para analisar um arquivo de código-fonte FOOLI. Um exemplo pode ser encontrado em `src/main/resources/test.fooli`.

## Autores

- Murilo Schuck
- Carlos Souza
