package fooli.tree.instruction;

import fooli.tree.node.SyntaxNode;
import fooli.processor.NodeProcessor;

public class AssignmentInstruction implements Instruction {
    private String identifier;
    private SyntaxNode assignment;

    public AssignmentInstruction(String identifier, SyntaxNode assignment) {
        this.identifier = identifier;
        this.assignment = assignment;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processAssignmentInstruction(this);
    }

    public String getIdentifier() {
        return identifier;
    }

    public SyntaxNode getAssignment() {
        return assignment;
    }
}