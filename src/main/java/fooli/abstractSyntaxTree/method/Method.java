package fooli.abstractSyntaxTree.method;

import java.util.List;

import fooli.abstractSyntaxTree.Node;
import fooli.abstractSyntaxTree.Statement;
import fooli.abstractSyntaxTree.declaration.VariableDeclaration;
import fooli.type.Type;
import fooli.type.VoidType;
import fooli.processor.NodeProcessor;

public class Method implements Node {
    private final Type returnType;
    private final String name;
    private final List<VariableDeclaration> arguments;
    private final List<Statement> statements;

    public Method(Type returnType, String name, List<VariableDeclaration> arguments, List<Statement> statements) {
        this.returnType = returnType;
        this.name = name;
        this.arguments = arguments == null ? List.of() : arguments;
        this.statements = statements;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processMethod(this);
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public List<VariableDeclaration> getArguments() {
        return arguments;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean isMain() {
        return name.equals("main") && arguments.size() == 0 && returnType instanceof VoidType;
    }
}
