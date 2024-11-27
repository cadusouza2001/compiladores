package fooli.tree.function;

import java.util.List;

import fooli.tree.node.SyntaxNode;
import fooli.tree.instruction.Instruction;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class FunctionCall implements SyntaxNode, Instruction {
    private final String functionName;
    private final List<SyntaxNode> arguments;
    private Type type;

    public FunctionCall(String functionName, List<SyntaxNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments == null ? List.of() : arguments;
        this.type = null;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processFunctionCall(this);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<SyntaxNode> getArguments() {
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