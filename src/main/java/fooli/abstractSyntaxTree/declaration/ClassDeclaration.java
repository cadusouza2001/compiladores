package fooli.abstractSyntaxTree.declaration;

import java.util.List;

import fooli.abstractSyntaxTree.method.Method;
import fooli.abstractSyntaxTree.Node;
import fooli.processor.NodeProcessor;

public class ClassDeclaration implements Node {

    private final String name;
    private final List<VariableDeclaration> fields;
    private final List<Method> methods;

    public ClassDeclaration(String name, List<VariableDeclaration> fields, List<Method> methods) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processClassDeclaration(this);
    }

    public String getName() {
        return name;
    }

    public List<VariableDeclaration> getFields() {
        return fields;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
