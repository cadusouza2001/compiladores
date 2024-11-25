package fooli.abstractSyntaxTree.statement;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.processor.NodeProcessor;

public class WhileStatement implements Statement {
    private final Expression condition;
    private Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processWhileStatement(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }
}