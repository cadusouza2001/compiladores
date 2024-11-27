package fooli.type;

public class IntegerType extends Type {
    @Override
    public boolean isAssignableFrom(Type other) {
        return other instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "integer";
    }
}