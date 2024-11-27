package fooli.tree.function;

import java.util.List;

import fooli.tree.node.Node;
import fooli.tree.instruction.Instruction;
import fooli.tree.descriptor.AttributeDescriptor;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class Function implements Node {
    private final String functionName;
    private final List<AttributeDescriptor> parameters;
    private final List<Instruction> body;
    private final Type returnType;

    public Function(Type returnType, String functionName, List<AttributeDescriptor> parameters, List<Instruction> body) {
        this.returnType = returnType;
        this.functionName = functionName;
        this.parameters = parameters == null ? List.of() : parameters;
        this.body = body;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processFunction(this);
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<AttributeDescriptor> getParameters() {
        return parameters;
    }

    public List<Instruction> getBody() {
        return body;
    }
}
