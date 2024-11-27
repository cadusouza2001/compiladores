package fooli.type;

public class VoidType extends Type {
    @Override
    public boolean isAssignableFrom(Type other) {
        return other instanceof VoidType;
    }

    @Override
    public String toString() {
        return "void";
    }
}