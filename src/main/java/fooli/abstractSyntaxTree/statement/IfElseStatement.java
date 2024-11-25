package fooli.abstractSyntaxTree.statement;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.processor.NodeProcessor;

public class IfElseStatement implements Statement {
    private final Expression condition;
    private final Statement then;
    private final Statement otherwise;

    public IfElseStatement(Expression condition, Statement then, Statement otherwise) {
        this.condition = condition;
        this.then = then;
        this.otherwise = otherwise;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processIfElseStatement(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThen() {
        return then;
    }

    public Statement getOtherwise() {
        return otherwise;
    }
}