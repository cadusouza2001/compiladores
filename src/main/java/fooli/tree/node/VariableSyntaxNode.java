package fooli.tree.node;

import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class VariableSyntaxNode implements SyntaxNode {
    private final String name;
    private Type type;

    @Override
    public void process(NodeProcessor processor) {
        processor.processVariableExpression(this);
    }

    public VariableSyntaxNode(String name) {
        this.name = name;
        this.type = null;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }
}