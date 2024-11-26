package fooli.tree;

public enum Operator {

    PLUS("+"),
    TIMES("*"),
    EQ("=="),
    LT("<"),
    GT(">"),
    AND("and"),
    OR("or"),
    NOT("not");

    private final String representation;

    Operator(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public static Operator fromString(String symbol) {
        for (Operator op : Operator.values()) {
        if (op.representation.equals(symbol)) {
            return op;
        }
        }
        return null;
    }

    public boolean isUnary() {
        return this == NOT;
    }

}
