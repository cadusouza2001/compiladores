package fooli.tree.node;

import fooli.type.BooleanType;
import fooli.type.IntegerType;
import fooli.processor.NodeProcessor;
import fooli.type.Type;

public class ConstantSyntaxNode implements SyntaxNode {
    private final Object value;
    private Type type;

    public ConstantSyntaxNode(Integer value) {
        this.value = value;
        this.type = new IntegerType();
    }

    public ConstantSyntaxNode(Boolean value) {
        this.value = value;
        this.type = new BooleanType();
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processConstantSyntaxNode(this);
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}