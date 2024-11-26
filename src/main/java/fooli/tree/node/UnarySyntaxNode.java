package fooli.tree.node;

import fooli.tree.Operator;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class UnarySyntaxNode implements SyntaxNode {
    private final Operator operator;
    private final SyntaxNode syntaxNode;

    public UnarySyntaxNode(Operator operator, SyntaxNode syntaxNode) {
        this.operator = operator;
        this.syntaxNode = syntaxNode;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processUnaryExpression(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public SyntaxNode getExpression() {
        return syntaxNode;
    }

    @Override
    public Type getType() {
        return syntaxNode.getType();
    }

    @Override
    public void setType(Type type) {
        syntaxNode.setType(type);
    }
}
