package fooli.tree.descriptor;

import java.util.List;
import java.util.stream.Collectors;

import fooli.type.Type;

public class MethodDescriptor extends Descriptor {
    private final List<ParameterDescriptor> parameters;

    public MethodDescriptor(String name, Type returnType, List<ParameterDescriptor> parameters) {
        super(name, returnType);
        this.parameters = parameters;
    }

    public List<ParameterDescriptor> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        String params = parameters.stream()
            .map(param -> param.getType() + " " + param.getName())
            .collect(Collectors.joining(", "));

        return String.format("Method(name='%s', return=%s, params=(%s))", getName(), getType(), params);
    }
}