package fooli;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fooli.tree.descriptor.ClassDescriptor;
import fooli.codegen.IntermediateCodeGenerator;
import fooli.analysis.SemanticAnalyzer;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void writeToFile(String fileName, String... contents) {
    try (FileWriter writer = new FileWriter(fileName)) {
        for (String content : contents) {
            writer.write(content);
            writer.write("\n\n"); // Add some space between sections
        }
    } catch (IOException e) {
        System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
    }
}

private void run() {
    try {
        String resourcePath = getResourcePath("test.fooli");
        FoolLexer lexer = new FoolLexer(new FileReader(resourcePath));
        SymbolFactory symbolFactory = new DefaultSymbolFactory();

        ClassDescriptor ast = parse(lexer, symbolFactory);
        if (ast == null) return;

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        if (!analyzeSemantics(ast, semanticAnalyzer)) return;

        IntermediateCodeGenerator codeGenerator = new IntermediateCodeGenerator();
        generateCode(ast, codeGenerator);

        String symbolTableHeader = "=== Symbol Table ===\n";
        String symbolTableContent = semanticAnalyzer.getSerializedSymbolTable();
        String codeHeader = "=== Intermediate Code ===\n";
        String codeContent = codeGenerator.getCode();

        writeToFile("results/output.txt", symbolTableHeader + symbolTableContent, codeHeader + codeContent);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private String getResourcePath(String resourceName) {
        return FoolLexer.class.getClassLoader().getResource(resourceName).getFile();
    }

    private ClassDescriptor parse(FoolLexer lexer, SymbolFactory symbolFactory) {
        try {
            FoolParser parser = new FoolParser(lexer, symbolFactory);
            return (ClassDescriptor) parser.parse().value;
        } catch (Exception e) {
            System.err.println("Error during parsing: " + e.getMessage());
            return null;
        }
    }

    private boolean analyzeSemantics(ClassDescriptor ast, SemanticAnalyzer semanticAnalyzer) {
        ast.process(semanticAnalyzer);
        if (semanticAnalyzer.hasErrors()) {
            semanticAnalyzer.printErrors();
            return false;
        }
        return true;
    }

    private void generateCode(ClassDescriptor ast, IntermediateCodeGenerator codeGenerator) {
        ast.process(codeGenerator);
    }
}