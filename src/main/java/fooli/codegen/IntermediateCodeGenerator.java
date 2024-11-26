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

    public String getCode() {
        return codeBuilder.toString();
    }

    @Override
    public void processClassDescriptor(ClassDescriptor classDecl) {
        classDecl.getFunctions().forEach(method -> method.process(this));
        generateLabels();
    }

    @Override
    public void processAttributeDescriptor(AttributeDescriptor varDecl) {
        // No code generation needed for variable declarations
    }

    @Override
    public void processMethod(Function function) {
        appendCode("\n" + function.getFunctionName() + ":");
        function.getParameters().forEach(arg -> appendCode("param " + arg.getAttributeName()));
        function.getBody().forEach(stmt -> stmt.process(this));
    }

    @Override
    public void processAssignmentStatement(AssignmentInstruction assignStmt) {
        assignStmt.getAssignment().process(this);
        appendCode(String.format("%s = %s", assignStmt.getIdentifier(), context.getTemporaryForNode(assignStmt.getAssignment())));
    }

    @Override
    public void processIfStatement(SimpleConditionalInstruction ifStmt) {
        ifStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifStmt.getInstruction(), "then");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifStmt.getCondition()), thenLabel));
    }

    @Override
    public void processIfElseStatement(ConditionalInstruction ifElseStmt) {
        ifElseStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifElseStmt.getThenBranch(), "then");
        String elseLabel = context.createLabelForSubtree(ifElseStmt.getElseBranch(), "else");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), thenLabel));
        appendCode(String.format("ifFalse %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), elseLabel));
    }

    @Override
    public void processWhileStatement(LoopInstruction whileStmt) {
        whileStmt.getCondition().process(this);
        String bodyLabel = context.createLabelForSubtree(whileStmt.getBody(), "body");
        appendCode(String.format("while %s goto %s", context.getTemporaryForNode(whileStmt.getCondition()), bodyLabel));
    }

    @Override
    public void processReturnStatement(FunctionReturn returnStmt) {
        returnStmt.getExpression().process(this);
        appendCode(String.format("return %s", context.getTemporaryForNode(returnStmt.getExpression())));
    }

    @Override
    public void processMethodCall(FunctionCall functionCall) {
        functionCall.getArguments().forEach(arg -> arg.process(this));
        appendCode(String.format("%s = call %s", context.getTemporaryForNode(functionCall), functionCall.getMethodName()));
    }

    @Override
    public void processConstantExpression(ConstantSyntaxNode constExpr) {
        // No code generation needed for constant expressions
    }

    @Override
    public void processVariableExpression(VariableSyntaxNode varExpr) {
        // No code generation needed for variable expressions
    }

    @Override
    public void processUnaryExpression(UnarySyntaxNode unaryExpr) {
        unaryExpr.getExpression().process(this);
        appendCode(String.format("%s = %s %s", context.getTemporaryForNode(unaryExpr), unaryExpr.getOperator(), context.getTemporaryForNode(unaryExpr.getExpression())));
    }

    @Override
    public void processBinaryExpression(BinarySyntaxNode binaryExpr) {
        binaryExpr.getLeft().process(this);
        binaryExpr.getRight().process(this);
        appendCode(String.format("%s = %s %s %s", context.getTemporaryForNode(binaryExpr), context.getTemporaryForNode(binaryExpr.getLeft()), binaryExpr.getOperator(), context.getTemporaryForNode(binaryExpr.getRight())));
    }

    private void appendCode(String code) {
        codeBuilder.append(code).append("\n");
    }

    private void generateLabels() {
        for (Map.Entry<String, Node> entry : context.getLabelNodeMap().entrySet()) {
            appendCode("\n" + entry.getKey() + ":");
            entry.getValue().process(this);
        }
    }
}