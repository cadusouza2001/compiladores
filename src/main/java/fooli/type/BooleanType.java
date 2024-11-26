package fooli.type;

public class BooleanType extends Type {
    @Override
    public boolean isAssignableFrom(Type other) {
        return other instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "boolean";
    }
}