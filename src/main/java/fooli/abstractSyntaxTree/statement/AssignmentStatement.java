package fooli.abstractSyntaxTree.statement;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.processor.NodeProcessor;

public class AssignmentStatement implements Statement {
    private String identifier;
    private Expression expression;

    public AssignmentStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processAssignmentStatement(this);
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}