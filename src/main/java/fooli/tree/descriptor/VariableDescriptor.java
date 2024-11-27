package fooli.tree.descriptor;

import fooli.type.Type;

public class VariableDescriptor extends Descriptor {

    public VariableDescriptor(String name, Type type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Variable(name='")
            .append(getName())
            .append("', type=")
            .append(getType())
            .append(")")
            .toString();
    }
}