package fooli.processor;

import fooli.tree.node.*;
import fooli.tree.function.Function;
import fooli.tree.instruction.AssignmentInstruction;
import fooli.tree.node.BinarySyntaxNode;
import fooli.tree.descriptor.ClassDescriptor;
import fooli.tree.node.ConstantSyntaxNode;
import fooli.tree.instruction.ConditionalInstruction;
import fooli.tree.instruction.SimpleConditionalInstruction;
import fooli.tree.function.FunctionCall;
import fooli.tree.function.FunctionReturn;
import fooli.tree.descriptor.AttributeDescriptor;
import fooli.tree.instruction.LoopInstruction;

public interface NodeProcessor {
    void processClassDescriptor(ClassDescriptor node);
    void processAttributeDescriptor(AttributeDescriptor node);
    void processFunction(Function node);
    void processAssignmentInstruction(AssignmentInstruction node);
    void processSimpleConditionalInstruction(SimpleConditionalInstruction node);
    void processConditionalInstruction(ConditionalInstruction node);
    void processLoopInstruction(LoopInstruction node);
    void processFunctionReturn(FunctionReturn node);
    void processFunctionCall(FunctionCall node);
    void processConstantSyntaxNode(ConstantSyntaxNode node);
    void processVariableSyntaxNode(VariableSyntaxNode node);
    void processUnarySyntaxNode(UnarySyntaxNode node);
    void processBinarySyntaxNode(BinarySyntaxNode node);
}