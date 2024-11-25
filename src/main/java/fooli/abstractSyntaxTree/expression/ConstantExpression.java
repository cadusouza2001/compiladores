package fooli.abstractSyntaxTree.expression;

import fooli.abstractSyntaxTree.Expression;
import fooli.type.BoolType;
import fooli.type.IntType;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class ConstantExpression implements Expression {
    private final Object value;
    private Type type;

    public ConstantExpression(Integer value) {
        this.value = value;
        this.type = new IntType();
    }

    public ConstantExpression(Boolean value) {
        this.value = value;
        this.type = new BoolType();
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processConstantExpression(this);
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