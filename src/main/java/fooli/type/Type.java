package fooli.type;

public abstract class Type {
    public abstract boolean isCompatible(Type other);
    public abstract String toString();
}
