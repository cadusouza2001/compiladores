package fooli.abstractSyntaxTree.statement;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.processor.NodeProcessor;

public class ReturnStatement implements Statement {
    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processReturnStatement(this);
    }

    public Expression getExpression() {
        return expression;
    }
}