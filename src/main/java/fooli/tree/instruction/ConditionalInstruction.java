package fooli.tree.instruction;

import fooli.tree.node.SyntaxNode;
import fooli.processor.NodeProcessor;

public class ConditionalInstruction implements Instruction {
    private final SyntaxNode condition;
    private final Instruction thenBranch;
    private final Instruction elseBranch;

    public ConditionalInstruction(SyntaxNode condition, Instruction thenBranch, Instruction elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processIfElseStatement(this);
    }

    public SyntaxNode getCondition() {
        return condition;
    }

    public Instruction getThenBranch() {
        return thenBranch;
    }

    public Instruction getElseBranch() {
        return elseBranch;
    }
}