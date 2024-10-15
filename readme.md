# FoolLexer Project

This project uses Java and JFlex to generate and run a lexer.

## Project Description

Build a lexical analyzer for a simple object-oriented language called FOOL (Fake Object-Oriented Language) using JFlex. The language has the constructions specified below. Your task is:

1. Identify which elements should be processed by the lexical analyzer (which classes of tokens);
2. Create and test the corresponding `.flex` specification. At this stage, to facilitate testing, create a standalone prototype. Later, we will integrate it with the parser.

## Prerequisites

- Java Development Kit (JDK) installed

## Setup and Running

1. **Generate the Lexer:**

   ```sh
   java -jar libs/jflex-full-1.9.1.jar FoolLexer.flex
   ```

2. **Compile AST and Node**

```sh
 javac Node.java
 javac AST.java
```

2. **Generate the Parser:**

   ```sh
   java -jar libs/java-cup-11b.jar -parser FoolParser -symbols sym FoolParser.cup
   ```

3. **Compile the Lexer and Parser**

   ```sh
   javac -cp ".;libs/*" *.java
   ```

4. **Run the Parser on a Test File**
   ```sh
   java -cp ".;libs/*" FoolParser test2.fool
   ```
