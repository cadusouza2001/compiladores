package fooli.tree.node;

import fooli.tree.Operator;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class BinarySyntaxNode implements SyntaxNode {

    private final Operator operator;
    private final SyntaxNode left;
    private final SyntaxNode right;
    private Type type;

    public BinarySyntaxNode(Operator operator, SyntaxNode left, SyntaxNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.type = null;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processBinarySyntaxNode(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public SyntaxNode getLeft() {
        return left;
    }

    public SyntaxNode getRight() {
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
