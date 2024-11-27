package fooli.type;

public abstract class Type {
    public abstract boolean isAssignableFrom(Type other);
    public abstract String toString();
}
