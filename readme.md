# FoolLexer Project

This project uses Java and JFlex to generate and run a lexer.

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
