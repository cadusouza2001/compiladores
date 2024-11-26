package fooli.tree.instruction;

import fooli.tree.node.SyntaxNode;
import fooli.processor.NodeProcessor;

public class SimpleConditionalInstruction implements Instruction {
    private final SyntaxNode condition;
    private final Instruction instruction;

    public SimpleConditionalInstruction(SyntaxNode condition, Instruction then) {
        this.condition = condition;
        this.instruction = then;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processIfStatement(this);
    }

    public SyntaxNode getCondition() {
        return condition;
    }

    public Instruction getInstruction() {
        return instruction;
    }
}