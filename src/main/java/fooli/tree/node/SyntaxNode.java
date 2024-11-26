package fooli.tree.node;

import fooli.type.Type;

public interface SyntaxNode extends Node {
    Type getType();
    void setType(Type type);
}