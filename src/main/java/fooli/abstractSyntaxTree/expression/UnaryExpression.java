package fooli.abstractSyntaxTree.expression;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Operator;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class UnaryExpression implements Expression {
    private final Operator operator;
    private final Expression expression;

    public UnaryExpression(Operator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processUnaryExpression(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Type getType() {
        return expression.getType();
    }

    @Override
    public void setType(Type type) {
        expression.setType(type);
    }
}
