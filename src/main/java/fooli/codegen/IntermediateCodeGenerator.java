package fooli.codegen;

import java.util.Map;
import fooli.tree.node.Node;
import fooli.tree.descriptor.ClassDescriptor;
import fooli.tree.descriptor.AttributeDescriptor;
import fooli.tree.node.*;
import fooli.tree.node.ConstantSyntaxNode;
import fooli.tree.node.UnarySyntaxNode;
import fooli.tree.function.Function;
import fooli.tree.function.FunctionCall;
import fooli.tree.function.FunctionReturn;
import fooli.tree.instruction.*;
import fooli.processor.NodeProcessor;

public class IntermediateCodeGenerator implements NodeProcessor {

    private final CodeGenerationContext context;
    private final StringBuilder codeBuilder;

    public IntermediateCodeGenerator() {
        this.context = new CodeGenerationContext();
        this.codeBuilder = new StringBuilder();
    }
    @Override
    public void processVariableSyntaxNode(VariableSyntaxNode varExpr) {
        // No code generation needed for variable expressions
    }

    @Override
    public void processBinarySyntaxNode(BinarySyntaxNode binaryExpr) {
        binaryExpr.getLeft().process(this);
        binaryExpr.getRight().process(this);
        appendCode(String.format("%s = %s %s %s", context.getTemporaryForNode(binaryExpr), context.getTemporaryForNode(binaryExpr.getLeft()), binaryExpr.getOperator(), context.getTemporaryForNode(binaryExpr.getRight())));
    }

    @Override
    public void processUnarySyntaxNode(UnarySyntaxNode unaryExpr) {
        unaryExpr.getExpression().process(this);
        appendCode(String.format("%s = %s %s", context.getTemporaryForNode(unaryExpr), unaryExpr.getOperator(), context.getTemporaryForNode(unaryExpr.getExpression())));
    }

    @Override
    public void processFunctionCall(FunctionCall functionCall) {
        functionCall.getArguments().forEach(arg -> arg.process(this));
        appendCode(String.format("%s = call %s", context.getTemporaryForNode(functionCall), functionCall.getFunctionName()));
    }

    @Override
    public void processFunctionReturn(FunctionReturn returnStmt) {
        returnStmt.getExpression().process(this);
        appendCode(String.format("return %s", context.getTemporaryForNode(returnStmt.getExpression())));
    }

    @Override
    public void processAssignmentInstruction(AssignmentInstruction assignStmt) {
        assignStmt.getAssignment().process(this);
        appendCode(String.format("%s = %s", assignStmt.getIdentifier(), context.getTemporaryForNode(assignStmt.getAssignment())));
    }

    @Override
    public void processConstantSyntaxNode(ConstantSyntaxNode constExpr) {
        // No code generation needed for constant expressions
    }

    @Override
    public void processClassDescriptor(ClassDescriptor classDecl) {
        classDecl.getFunctions().forEach(function -> function.process(this));
        generateLabels();
    }

    @Override
    public void processLoopInstruction(LoopInstruction whileStmt) {
        whileStmt.getCondition().process(this);
        String bodyLabel = context.createLabelForSubtree(whileStmt.getBody(), "body");
        appendCode(String.format("while %s goto %s", context.getTemporaryForNode(whileStmt.getCondition()), bodyLabel));
    }

    @Override
    public void processFunction(Function function) {
        appendCode("\n" + function.getFunctionName() + ":");
        function.getParameters().forEach(arg -> appendCode("param " + arg.getAttributeName()));
        function.getBody().forEach(stmt -> stmt.process(this));
    }

    @Override
    public void processSimpleConditionalInstruction(SimpleConditionalInstruction ifStmt) {
        ifStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifStmt.getInstruction(), "then");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifStmt.getCondition()), thenLabel));
    }

    @Override
    public void processAttributeDescriptor(AttributeDescriptor varDecl) {
        // No code generation needed for variable declarations
    }

    private void appendCode(String code) {
        codeBuilder.append(code).append("\n");
    }

    @Override
    public void processConditionalInstruction(ConditionalInstruction ifElseStmt) {
        ifElseStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifElseStmt.getThenBranch(), "then");
        String elseLabel = context.createLabelForSubtree(ifElseStmt.getElseBranch(), "else");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), thenLabel));
        appendCode(String.format("ifFalse %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), elseLabel));
    }

    private void generateLabels() {
        for (Map.Entry<String, Node> entry : context.getLabelNodeMap().entrySet()) {
            appendCode("\n" + entry.getKey() + ":");
            entry.getValue().process(this);
        }
    }

    public String getCode() {
        return codeBuilder.toString();
    }
}
