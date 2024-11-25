package fooli.abstractSyntaxTree;

import fooli.abstractSyntaxTree.Node;
import fooli.type.Type;

public interface Expression extends Node {
    Type getType();
    void setType(Type type);
}