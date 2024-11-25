package fooli.abstractSyntaxTree.declaration;

import fooli.abstractSyntaxTree.Statement;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class VariableDeclaration implements Statement {
    private final Type type;
    private final String name;

    @Override
    public void process(NodeProcessor processor) {
        processor.processVariableDeclaration(this);
    }

    public VariableDeclaration(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
