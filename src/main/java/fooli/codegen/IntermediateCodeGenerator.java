package fooli.codegen;

import java.util.Map;
import fooli.abstractSyntaxTree.*;
import fooli.abstractSyntaxTree.declaration.ClassDeclaration;
import fooli.abstractSyntaxTree.declaration.VariableDeclaration;
import fooli.abstractSyntaxTree.expression.BinaryExpression;
import fooli.abstractSyntaxTree.expression.ConstantExpression;
import fooli.abstractSyntaxTree.expression.UnaryExpression;
import fooli.abstractSyntaxTree.expression.VariableExpression;
import fooli.abstractSyntaxTree.method.Method;
import fooli.abstractSyntaxTree.method.MethodCall;
import fooli.abstractSyntaxTree.statement.*;
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
    public void processClassDeclaration(ClassDeclaration classDecl) {
        classDecl.getMethods().forEach(method -> method.process(this));
        generateLabels();
    }

    @Override
    public void processVariableDeclaration(VariableDeclaration varDecl) {
        // No code generation needed for variable declarations
    }

    @Override
    public void processMethod(Method method) {
        appendCode("\n" + method.getName() + ":");
        method.getArguments().forEach(arg -> appendCode("param " + arg.getName()));
        method.getStatements().forEach(stmt -> stmt.process(this));
    }

    @Override
    public void processAssignmentStatement(AssignmentStatement assignStmt) {
        assignStmt.getExpression().process(this);
        appendCode(String.format("%s = %s", assignStmt.getIdentifier(), context.getTemporaryForNode(assignStmt.getExpression())));
    }

    @Override
    public void processIfStatement(IfStatement ifStmt) {
        ifStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifStmt.getThen(), "then");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifStmt.getCondition()), thenLabel));
    }

    @Override
    public void processIfElseStatement(IfElseStatement ifElseStmt) {
        ifElseStmt.getCondition().process(this);
        String thenLabel = context.createLabelForSubtree(ifElseStmt.getThen(), "then");
        String elseLabel = context.createLabelForSubtree(ifElseStmt.getOtherwise(), "else");
        appendCode(String.format("ifTrue %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), thenLabel));
        appendCode(String.format("ifFalse %s goto %s", context.getTemporaryForNode(ifElseStmt.getCondition()), elseLabel));
    }

    @Override
    public void processWhileStatement(WhileStatement whileStmt) {
        whileStmt.getCondition().process(this);
        String bodyLabel = context.createLabelForSubtree(whileStmt.getBody(), "body");
        appendCode(String.format("while %s goto %s", context.getTemporaryForNode(whileStmt.getCondition()), bodyLabel));
    }

    @Override
    public void processReturnStatement(ReturnStatement returnStmt) {
        returnStmt.getExpression().process(this);
        appendCode(String.format("return %s", context.getTemporaryForNode(returnStmt.getExpression())));
    }

    @Override
    public void processMethodCall(MethodCall methodCall) {
        methodCall.getArguments().forEach(arg -> arg.process(this));
        appendCode(String.format("%s = call %s", context.getTemporaryForNode(methodCall), methodCall.getMethodName()));
    }

    @Override
    public void processConstantExpression(ConstantExpression constExpr) {
        // No code generation needed for constant expressions
    }

    @Override
    public void processVariableExpression(VariableExpression varExpr) {
        // No code generation needed for variable expressions
    }

    @Override
    public void processUnaryExpression(UnaryExpression unaryExpr) {
        unaryExpr.getExpression().process(this);
        appendCode(String.format("%s = %s %s", context.getTemporaryForNode(unaryExpr), unaryExpr.getOperator(), context.getTemporaryForNode(unaryExpr.getExpression())));
    }

    @Override
    public void processBinaryExpression(BinaryExpression binaryExpr) {
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