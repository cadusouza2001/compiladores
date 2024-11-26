package fooli.analysis;

import java.util.ArrayList;
import java.util.List;

import fooli.tree.Operator;
import fooli.tree.descriptor.*;
import fooli.tree.node.*;
import fooli.tree.function.Function;
import fooli.tree.function.FunctionCall;
import fooli.tree.function.FunctionReturn;
import fooli.tree.instruction.*;
import fooli.exception.SemanticException;
import fooli.processor.NodeProcessor;
import fooli.scope.*;
import fooli.type.*;

public class SemanticAnalyzer implements NodeProcessor {
    private Scope globalScope;
    private Scope currentScope;
    private List<String> errors;
    private Type currentMethodReturnType;

    public SemanticAnalyzer() {
        this.globalScope = new Scope();
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

    private void enterScope(Scope newScope) {
        currentScope = newScope;
    }

    private void exitScope() {
        currentScope = currentScope.getParent();
    }

    @Override
    public void processClassDescriptor(ClassDescriptor node) {
        node.getAttributes().forEach(field -> field.process(this));
        node.getFunctions().forEach(method -> method.process(this));
    }

    @Override
    public void processAttributeDescriptor(AttributeDescriptor node) {
        declareVariable(node.getAttributeName(), node.getAttributeType());
    }

    @Override
    public void processMethod(Function node) {
        declareMethod(node);
        enterScope(currentScope.createChildScope());
        currentMethodReturnType = node.getReturnType();
        node.getParameters().forEach(param -> declareVariable(param.getAttributeName(), param.getAttributeType()));
        node.getBody().forEach(stmt -> stmt.process(this));
        exitScope();
        currentMethodReturnType = null;
    }

    @Override
    public void processAssignmentStatement(AssignmentInstruction node) {
        Descriptor varSymbol = currentScope.lookup(node.getIdentifier());
        if (varSymbol == null) {
            reportError("Variable '" + node.getIdentifier() + "' not declared.");
            return;
        }
        if (!(varSymbol instanceof VariableDescriptor)) {
            reportError("Symbol '" + node.getIdentifier() + "' is not a variable.");
            return;
        }
        node.getAssignment().process(this);
        Type exprType = node.getAssignment().getType();
        if (!varSymbol.getType().isAssignableFrom(exprType)) {
            reportError("Type mismatch in assignment to variable '" + node.getIdentifier() + "'. Expected " + varSymbol.getType() + ", found " + exprType + ".");
        }
    }

    @Override
    public void processIfStatement(SimpleConditionalInstruction node) {
        validateCondition(node.getCondition());
        node.getInstruction().process(this);
    }

    @Override
    public void processIfElseStatement(ConditionalInstruction node) {
        validateCondition(node.getCondition());
        node.getThenBranch().process(this);
        node.getElseBranch().process(this);
    }

    @Override
    public void processWhileStatement(LoopInstruction node) {
        validateCondition(node.getCondition());
        node.getBody().process(this);
    }

    @Override
    public void processReturnStatement(FunctionReturn node) {
        if (currentMethodReturnType == null) {
            reportError("Return statement outside of a method.");
            return;
        }
        node.getExpression().process(this);
        Type exprType = node.getExpression().getType();
        if (!currentMethodReturnType.isAssignableFrom(exprType)) {
            reportError("Type mismatch in return statement. Expected " + currentMethodReturnType + ", found " + exprType + ".");
        }
    }

    @Override
    public void processMethodCall(FunctionCall node) {
        Descriptor methodSymbol = currentScope.lookup(node.getMethodName());
        if (methodSymbol == null) {
            reportError("Method '" + node.getMethodName() + "' not declared.");
            node.setType(new InvalidType());
            return;
        }
        if (!(methodSymbol instanceof MethodDescriptor)) {
            reportError("Symbol '" + node.getMethodName() + "' is not a method.");
            node.setType(new InvalidType());
            return;
        }
        MethodDescriptor method = (MethodDescriptor) methodSymbol;
        validateMethodCallArguments(node, method);
        node.setType(method.getType());
    }

    @Override
    public void processConstantExpression(ConstantSyntaxNode node) {
        Object value = node.getValue();
        if (value instanceof Integer) {
            node.setType(new IntegerType());
        } else if (value instanceof Boolean) {
            node.setType(new BooleanType());
        } else {
            reportError("Unknown constant type: " + value);
            node.setType(new InvalidType());
        }
    }

    @Override
    public void processVariableExpression(VariableSyntaxNode node) {
        Descriptor varSymbol = currentScope.lookup(node.getName());
        if (varSymbol == null) {
            reportError("Variable '" + node.getName() + "' not declared.");
            node.setType(new InvalidType());
            return;
        }
        if (!(varSymbol instanceof VariableDescriptor)) {
            reportError("Symbol '" + node.getName() + "' is not a variable.");
            node.setType(new InvalidType());
            return;
        }
        node.setType(varSymbol.getType());
    }

    @Override
    public void processUnaryExpression(UnarySyntaxNode node) {
        node.getExpression().process(this);
        Type exprType = node.getExpression().getType();
        if (node.getOperator() == Operator.NOT) {
            if (!(exprType instanceof BooleanType)) {
                reportError("Operator 'not' requires a boolean operand.");
            }
            node.setType(new BooleanType());
        } else {
            reportError("Unknown unary operator '" + node.getOperator() + "'.");
            node.setType(new InvalidType());
        }
    }

    @Override
    public void processBinaryExpression(BinarySyntaxNode node) {
        node.getLeft().process(this);
        node.getRight().process(this);
        Type leftType = node.getLeft().getType();
        Type rightType = node.getRight().getType();
        switch (node.getOperator()) {
            case PLUS:
            case TIMES:
                validateBinaryExpression(node, leftType, rightType, IntegerType.class, "integer");
                node.setType(new IntegerType());
                break;
            case AND:
            case OR:
                validateBinaryExpression(node, leftType, rightType, BooleanType.class, "boolean");
                node.setType(new BooleanType());
                break;
            case EQ:
            case LT:
            case GT:
                if (!leftType.isAssignableFrom(rightType)) {
                    reportError("Operator '" + node.getOperator() + "' requires operands of the same type.");
                }
                node.setType(new BooleanType());
                break;
            default:
                reportError("Unknown operator '" + node.getOperator() + "'.");
                node.setType(new InvalidType());
                break;
        }
    }

    private void declareVariable(String name, Type type) {
        VariableDescriptor varSymbol = new VariableDescriptor(name, type);
        try {
            currentScope.declare(varSymbol);
        } catch (SemanticException e) {
            reportError("Variable '" + name + "': " + e.getMessage());
        }
    }

    private void declareMethod(Function node) {
        List<ParameterDescriptor> params = node.getParameters().stream()
                .map(param -> new ParameterDescriptor(param.getAttributeName(), param.getAttributeType()))
                .toList();
        MethodDescriptor methodSymbol = new MethodDescriptor(node.getFunctionName(), node.getReturnType(), params);
        try {
            currentScope.declare(methodSymbol);
        } catch (SemanticException e) {
            reportError("Method " + node.getFunctionName() + ": " + e.getMessage());
        }
    }

    private void validateCondition(SyntaxNode condition) {
        condition.process(this);
        if (!(condition.getType() instanceof BooleanType)) {
            reportError("Condition must be of type bool.");
        }
    }

    private void validateMethodCallArguments(FunctionCall node, MethodDescriptor method) {
        List<ParameterDescriptor> parameters = method.getParameters();
        List<SyntaxNode> arguments = node.getArguments();
        if (parameters.size() != arguments.size()) {
            reportError("Method '" + node.getMethodName() + "' expects " + parameters.size() + " arguments, but got " + arguments.size() + ".");
        } else {
            for (int i = 0; i < parameters.size(); i++) {
                arguments.get(i).process(this);
                Type argType = arguments.get(i).getType();
                Type paramType = parameters.get(i).getType();
                if (!paramType.isAssignableFrom(argType)) {
                    reportError("Argument " + (i + 1) + " of method '" + node.getMethodName() + "' expects type " + paramType + ", but got " + argType + ".");
                }
            }
        }
    }

    private void validateBinaryExpression(BinarySyntaxNode node, Type leftType, Type rightType, Class<? extends Type> expectedType, String typeName) {
        if (!expectedType.isInstance(leftType) || !expectedType.isInstance(rightType)) {
            reportError("Operator '" + node.getOperator() + "' requires " + typeName + " operands.");
        }
    }
}