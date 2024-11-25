package fooli.abstractSyntaxTree.method;

import java.util.List;

import fooli.abstractSyntaxTree.Expression;
import fooli.abstractSyntaxTree.Statement;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class MethodCall implements Expression, Statement {
    private final String methodName;
    private final List<Expression> arguments;
    private Type type;

    public MethodCall(String methodName, List<Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
        this.type = null;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processMethodCall(this);
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}