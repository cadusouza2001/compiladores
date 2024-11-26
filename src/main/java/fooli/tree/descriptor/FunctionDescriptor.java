package fooli.tree.descriptor;

import java.util.List;
import java.util.stream.Collectors;

import fooli.type.Type;

public class FunctionDescriptor extends Descriptor {
    private final List<ArgumentDescriptor> arguments;

    public FunctionDescriptor(String name, Type returnType, List<ArgumentDescriptor> arguments) {
        super(name, returnType);
        this.arguments = arguments;
    }

    public List<ArgumentDescriptor> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        String args = this.arguments.stream()
            .map(param -> param.getType() + " " + param.getName())
            .collect(Collectors.joining(", "));

        return String.format("Function(name='%s', return=%s, args=(%s))", getName(), getType(), args);
    }
}