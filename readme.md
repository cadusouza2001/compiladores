# FoolLexer Project

This project uses Java and JFlex to generate and run a lexer.

## Project Description

Build a lexical analyzer for a simple object-oriented language called FOOL (Fake Object-Oriented Language) using JFlex. The language has the constructions specified below. Your task is:

1. Identify which elements should be processed by the lexical analyzer (which classes of tokens);
2. Create and test the corresponding `.flex` specification. At this stage, to facilitate testing, create a standalone prototype. Later, we will integrate it with the parser.

## Prerequisites

- Java Development Kit (JDK) installed
- JFlex installed

## Setup and Running

1. **Generate the Lexer:**

   ```sh
   jflex FoolLexer.flex
   ```

2. **Compile the Lexer:**

   ```sh
   javac FoolLexer.java
   ```

3. **Compile and Run the Test:**
   ```sh
   javac TokenPrinter.java
   java TokenPrinter
   ```
