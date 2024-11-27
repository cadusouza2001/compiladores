package fooli.tree.descriptor;

import java.util.List;

import fooli.tree.function.Function;
import fooli.tree.node.Node;
import fooli.processor.NodeProcessor;

public class ClassDescriptor implements Node {

    private final String className;
    private final List<AttributeDescriptor> attributes;
    private final List<Function> functions;

    public ClassDescriptor(String className, List<AttributeDescriptor> attributes, List<Function> functions) {
        this.className = className;
        this.attributes = attributes;
        this.functions = functions;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processClassDescriptor(this);
    }

    public String getClassName() {
        return className;
    }

    public List<AttributeDescriptor> getAttributes() {
        return attributes;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}