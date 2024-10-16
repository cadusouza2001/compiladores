import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private String value;
    private List<Node> children;

    public Node(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Node(String value, Node... children) {
        this.value = value;
        this.children = Arrays.asList(children);
    }

    public Node(String value, List<Node> children) {
        this.value = value;
        this.children = children;
    }

    public Node(String value, String... children) {
        this.value = value;
        this.children = new ArrayList<>();
        for (String child : children) {
            this.children.add(new Node(child));
        }
    }

    // New constructors
    public Node(String value, String str, Node node) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(new Node(str));
        this.children.add(node);
    }

    public Node(String value, Node node, String str) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(node);
        this.children.add(new Node(str));
    }

    public Node(String value, Node node1, String str, Node node2) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(node1);
        this.children.add(new Node(str));
        this.children.add(node2);
    }

    public Node(String value, Node node1, Node node2, String str) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(node1);
        this.children.add(node2);
        this.children.add(new Node(str));
    }

    // Additional constructors to handle the new cases
    public Node(String value, String str1, String str2, Node node1, Node node2) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(new Node(str1));
        this.children.add(new Node(str2));
        this.children.add(node1);
        this.children.add(node2);
    }

    public Node(String value, Node node1, String str, Node node2, Node node3) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(node1);
        this.children.add(new Node(str));
        this.children.add(node2);
        this.children.add(node3);
    }

    // Constructor to handle the specific case
    public Node(String value, String str1, Node node1, Node node2) {
        this.value = value;
        this.children = new ArrayList<>();
        this.children.add(new Node(str1));
        this.children.add(node1);
        this.children.add(node2);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}