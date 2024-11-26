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
    void processMethod(Function node);
    void processAssignmentStatement(AssignmentInstruction node);
    void processIfStatement(SimpleConditionalInstruction node);
    void processIfElseStatement(ConditionalInstruction node);
    void processWhileStatement(LoopInstruction node);
    void processReturnStatement(FunctionReturn node);
    void processMethodCall(FunctionCall node);
    void processConstantExpression(ConstantSyntaxNode node);
    void processVariableExpression(VariableSyntaxNode node);
    void processUnaryExpression(UnarySyntaxNode node);
    void processBinaryExpression(BinarySyntaxNode node);
}