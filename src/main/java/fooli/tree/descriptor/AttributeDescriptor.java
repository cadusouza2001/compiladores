package fooli.tree.descriptor;

import fooli.tree.instruction.Instruction;
import fooli.type.Type;
import fooli.processor.NodeProcessor;

public class AttributeDescriptor implements Instruction {

    private final Type attributeType;
    private final String attributeName;

    public AttributeDescriptor(Type attributeType, String attributeName) {
        this.attributeType = attributeType;
        this.attributeName = attributeName;
    }

    @Override
    public void process(NodeProcessor processor) {
        processor.processAttributeDescriptor(this);
    }

    public Type getAttributeType() {
        return attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }
}