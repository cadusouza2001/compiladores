package fooli.tree.descriptor;

import fooli.scope.ScopeEntry;
import fooli.type.Type;

public abstract class Descriptor implements ScopeEntry {
    private final String name;
    private final Type type;

    public Descriptor(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Symbol(name='")
            .append(name)
            .append("', type=")
            .append(type)
            .append(")")
            .toString();
    }
}