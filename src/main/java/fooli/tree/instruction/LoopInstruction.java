package fooli.tree.instruction;

import fooli.tree.node.SyntaxNode;
import fooli.processor.NodeProcessor;

public class LoopInstruction implements Instruction {
    private final SyntaxNode condition;
    private Instruction body;

    public LoopInstruction(SyntaxNode condition, Instruction body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processLoopInstruction(this);
    }

    public SyntaxNode getCondition() {
        return condition;
    }

    public Instruction getBody() {
        return body;
    }
}