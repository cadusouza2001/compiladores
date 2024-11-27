package fooli.codegen;

import java.util.HashMap;
import java.util.Map;

import fooli.tree.node.Node;

public class CodeGenerationContext {
    private int tempCounter = 0;
    private int labelCounter = 0;
    private final Map<Node, String> nodeTempMap;
    private final Map<String, Node> labelNodeMap;

    public CodeGenerationContext() {
        this.nodeTempMap = new HashMap<>();
        this.labelNodeMap = new HashMap<>();
    }

    public String getTemporaryForNode(Node node) {
        return nodeTempMap.computeIfAbsent(node, n -> generateNewTemp());
    }

    public String createLabelForSubtree(Node node, String prefix) {
        String label = generateNewLabel(prefix);
        labelNodeMap.put(label, node);
        return label;
    }

    public Map<String, Node> getLabelNodeMap() {
        return labelNodeMap;
    }

    private String generateNewLabel(String prefix) {
        return prefix + "_" + labelCounter++;
    }

    private String generateNewTemp() {
        return "t" + tempCounter++;
    }
}