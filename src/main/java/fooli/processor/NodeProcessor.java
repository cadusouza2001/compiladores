package fooli.processor;

import fooli.abstractSyntaxTree.statement.AssignmentStatement;
import fooli.abstractSyntaxTree.expression.BinaryExpression;
import fooli.abstractSyntaxTree.declaration.ClassDeclaration;
import fooli.abstractSyntaxTree.expression.ConstantExpression;
import fooli.abstractSyntaxTree.statement.IfElseStatement;
import fooli.abstractSyntaxTree.statement.IfStatement;
import fooli.abstractSyntaxTree.method.Method;
import fooli.abstractSyntaxTree.method.MethodCall;
import fooli.abstractSyntaxTree.statement.ReturnStatement;
import fooli.abstractSyntaxTree.expression.UnaryExpression;
import fooli.abstractSyntaxTree.declaration.VariableDeclaration;
import fooli.abstractSyntaxTree.expression.VariableExpression;
import fooli.abstractSyntaxTree.statement.WhileStatement;

public interface NodeProcessor {
    void processClassDeclaration(ClassDeclaration node);
    void processVariableDeclaration(VariableDeclaration node);
    void processMethod(Method node);
    void processAssignmentStatement(AssignmentStatement node);
    void processIfStatement(IfStatement node);
    void processIfElseStatement(IfElseStatement node);
    void processWhileStatement(WhileStatement node);
    void processReturnStatement(ReturnStatement node);
    void processMethodCall(MethodCall node);
    void processConstantExpression(ConstantExpression node);
    void processVariableExpression(VariableExpression node);
    void processUnaryExpression(UnaryExpression node);
    void processBinaryExpression(BinaryExpression node);
}