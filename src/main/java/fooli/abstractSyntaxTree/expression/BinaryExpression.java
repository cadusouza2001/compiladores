package fooli.abstractSyntaxTree.expression;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Operator;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class BinaryExpression implements Expression {

    private final Operator operator;
    private final Expression left;
    private final Expression right;
    private Type type;

    public BinaryExpression(Operator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.type = null;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processBinaryExpression(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
