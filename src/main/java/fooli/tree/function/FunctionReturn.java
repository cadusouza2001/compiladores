package fooli.tree.function;

import fooli.tree.node.SyntaxNode;
import fooli.tree.instruction.Instruction;
import fooli.processor.NodeProcessor;

public class FunctionReturn implements Instruction {
    private final SyntaxNode syntaxNode;

    public FunctionReturn(SyntaxNode syntaxNode) {
        this.syntaxNode = syntaxNode;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processReturnStatement(this);
    }

    public SyntaxNode getExpression() {
        return syntaxNode;
    }
}