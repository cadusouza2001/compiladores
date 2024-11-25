package fooli.analysis;

import java.util.ArrayList;
import java.util.List;

import fooli.abstractSyntaxTree.declaration.ClassDeclaration;
import fooli.abstractSyntaxTree.declaration.VariableDeclaration;
import fooli.abstractSyntaxTree.expression.*;
import fooli.abstractSyntaxTree.method.Method;
import fooli.abstractSyntaxTree.method.MethodCall;
import fooli.abstractSyntaxTree.statement.*;
import fooli.exception.SemanticException;
import fooli.abstractSyntaxTree.*;
import fooli.processor.NodeProcessor;
import fooli.symbol.*;
import fooli.type.*;

public class SemanticAnalyzer implements NodeProcessor {
    private SymbolTable globalScope;
    private SymbolTable currentScope;
    private List<String> errors;
    private Type currentMethodReturnType;

    public SemanticAnalyzer() {
        this.globalScope = new SymbolTable();
        this.currentScope = globalScope;
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void printErrors() {
        errors.forEach(System.err::println);
    }

    public String getSerializedSymbolTable() {
        return globalScope.serialize();
    }

    private void reportError(String message) {
        errors.add(message);
    }

    private void enterScope(SymbolTable newScope) {
        currentScope = newScope;
    }

    private void exitScope() {
        currentScope = currentScope.getParent();
    }

    @Override
    public void processClassDeclaration(ClassDeclaration node) {
        node.getFields().forEach(field -> field.process(this));
        node.getMethods().forEach(method -> method.process(this));
    }

    @Override
    public void processVariableDeclaration(VariableDeclaration node) {
        declareVariable(node.getName(), node.getType());
    }

    @Override
    public void processMethod(Method node) {
        declareMethod(node);
        enterScope(currentScope.createChildScope());
        currentMethodReturnType = node.getReturnType();
        node.getArguments().forEach(param -> declareVariable(param.getName(), param.getType()));
        node.getStatements().forEach(stmt -> stmt.process(this));
        exitScope();
        currentMethodReturnType = null;
    }

    @Override
    public void processAssignmentStatement(AssignmentStatement node) {
        SymbolInfo varSymbol = currentScope.lookup(node.getIdentifier());
        if (varSymbol == null) {
            reportError("Variable '" + node.getIdentifier() + "' not declared.");
            return;
        }
        if (!(varSymbol instanceof VariableSymbolInfo)) {
            reportError("Symbol '" + node.getIdentifier() + "' is not a variable.");
            return;
        }
        node.getExpression().process(this);
        Type exprType = node.getExpression().getType();
        if (!varSymbol.getType().isCompatible(exprType)) {
            reportError("Type mismatch in assignment to variable '" + node.getIdentifier() + "'. Expected " + varSymbol.getType() + ", found " + exprType + ".");
        }
    }

    @Override
    public void processIfStatement(IfStatement node) {
        validateCondition(node.getCondition());
        node.getThen().process(this);
    }

    @Override
    public void processIfElseStatement(IfElseStatement node) {
        validateCondition(node.getCondition());
        node.getThen().process(this);
        node.getOtherwise().process(this);
    }

    @Override
    public void processWhileStatement(WhileStatement node) {
        validateCondition(node.getCondition());
        node.getBody().process(this);
    }

    @Override
    public void processReturnStatement(ReturnStatement node) {
        if (currentMethodReturnType == null) {
            reportError("Return statement outside of a method.");
            return;
        }
        node.getExpression().process(this);
        Type exprType = node.getExpression().getType();
        if (!currentMethodReturnType.isCompatible(exprType)) {
            reportError("Type mismatch in return statement. Expected " + currentMethodReturnType + ", found " + exprType + ".");
        }
    }

    @Override
    public void processMethodCall(MethodCall node) {
        SymbolInfo methodSymbol = currentScope.lookup(node.getMethodName());
        if (methodSymbol == null) {
            reportError("Method '" + node.getMethodName() + "' not declared.");
            node.setType(new ErrorType());
            return;
        }
        if (!(methodSymbol instanceof MethodSymbolInfo)) {
            reportError("Symbol '" + node.getMethodName() + "' is not a method.");
            node.setType(new ErrorType());
            return;
        }
        MethodSymbolInfo method = (MethodSymbolInfo) methodSymbol;
        validateMethodCallArguments(node, method);
        node.setType(method.getType());
    }

    @Override
    public void processConstantExpression(ConstantExpression node) {
        Object value = node.getValue();
        if (value instanceof Integer) {
            node.setType(new IntType());
        } else if (value instanceof Boolean) {
            node.setType(new BoolType());
        } else {
            reportError("Unknown constant type: " + value);
            node.setType(new ErrorType());
        }
    }

    @Override
    public void processVariableExpression(VariableExpression node) {
        SymbolInfo varSymbol = currentScope.lookup(node.getName());
        if (varSymbol == null) {
            reportError("Variable '" + node.getName() + "' not declared.");
            node.setType(new ErrorType());
            return;
        }
        if (!(varSymbol instanceof VariableSymbolInfo)) {
            reportError("Symbol '" + node.getName() + "' is not a variable.");
            node.setType(new ErrorType());
            return;
        }
        node.setType(varSymbol.getType());
    }

    @Override
    public void processUnaryExpression(UnaryExpression node) {
        node.getExpression().process(this);
        Type exprType = node.getExpression().getType();
        if (node.getOperator() == Operator.NOT) {
            if (!(exprType instanceof BoolType)) {
                reportError("Operator 'not' requires a boolean operand.");
            }
            node.setType(new BoolType());
        } else {
            reportError("Unknown unary operator '" + node.getOperator() + "'.");
            node.setType(new ErrorType());
        }
    }

    @Override
    public void processBinaryExpression(BinaryExpression node) {
        node.getLeft().process(this);
        node.getRight().process(this);
        Type leftType = node.getLeft().getType();
        Type rightType = node.getRight().getType();
        switch (node.getOperator()) {
            case PLUS:
            case TIMES:
                validateBinaryExpression(node, leftType, rightType, IntType.class, "integer");
                node.setType(new IntType());
                break;
            case AND:
            case OR:
                validateBinaryExpression(node, leftType, rightType, BoolType.class, "boolean");
                node.setType(new BoolType());
                break;
            case EQ:
            case LT:
            case GT:
                if (!leftType.isCompatible(rightType)) {
                    reportError("Operator '" + node.getOperator() + "' requires operands of the same type.");
                }
                node.setType(new BoolType());
                break;
            default:
                reportError("Unknown operator '" + node.getOperator() + "'.");
                node.setType(new ErrorType());
                break;
        }
    }

    private void declareVariable(String name, Type type) {
        VariableSymbolInfo varSymbol = new VariableSymbolInfo(name, type);
        try {
            currentScope.declare(varSymbol);
        } catch (SemanticException e) {
            reportError("Variable '" + name + "': " + e.getMessage());
        }
    }

    private void declareMethod(Method node) {
        List<ParameterSymbolInfo> params = node.getArguments().stream()
                .map(param -> new ParameterSymbolInfo(param.getName(), param.getType()))
                .toList();
        MethodSymbolInfo methodSymbol = new MethodSymbolInfo(node.getName(), node.getReturnType(), params);
        try {
            currentScope.declare(methodSymbol);
        } catch (SemanticException e) {
            reportError("Method " + node.getName() + ": " + e.getMessage());
        }
    }

    private void validateCondition(Expression condition) {
        condition.process(this);
        if (!(condition.getType() instanceof BoolType)) {
            reportError("Condition must be of type bool.");
        }
    }

    private void validateMethodCallArguments(MethodCall node, MethodSymbolInfo method) {
        List<ParameterSymbolInfo> parameters = method.getParameters();
        List<Expression> arguments = node.getArguments();
        if (parameters.size() != arguments.size()) {
            reportError("Method '" + node.getMethodName() + "' expects " + parameters.size() + " arguments, but got " + arguments.size() + ".");
        } else {
            for (int i = 0; i < parameters.size(); i++) {
                arguments.get(i).process(this);
                Type argType = arguments.get(i).getType();
                Type paramType = parameters.get(i).getType();
                if (!paramType.isCompatible(argType)) {
                    reportError("Argument " + (i + 1) + " of method '" + node.getMethodName() + "' expects type " + paramType + ", but got " + argType + ".");
                }
            }
        }
    }

    private void validateBinaryExpression(BinaryExpression node, Type leftType, Type rightType, Class<? extends Type> expectedType, String typeName) {
        if (!expectedType.isInstance(leftType) || !expectedType.isInstance(rightType)) {
            reportError("Operator '" + node.getOperator() + "' requires " + typeName + " operands.");
        }
    }
}