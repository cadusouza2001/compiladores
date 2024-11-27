package fooli.type;

public class InvalidType extends Type {
    @Override
    public boolean isAssignableFrom(Type other) {
        return false;
    }

    @Override
    public String toString() {
        return "invalid";
    }
}