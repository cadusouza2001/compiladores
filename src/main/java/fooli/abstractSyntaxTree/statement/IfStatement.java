package fooli.abstractSyntaxTree.statement;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.processor.NodeProcessor;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Statement then;

    public IfStatement(Expression condition, Statement then) {
        this.condition = condition;
        this.then = then;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processIfStatement(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThen() {
        return then;
    }
}