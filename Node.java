public class Node {
    private String value;
    private String type;
    private Boolean visited;
    private Node leftChild;
    private Node rightChild;
    private Node middleChild;

    // Default constructor
    public Node() {
        this(null, null, null, null, null, null);
    }

    // Constructor with three child nodes
    public Node(String value, String type, Boolean visited, Node leftChild, Node middleChild, Node rightChild) {
        this.value = value;
        this.type = type;
        this.visited = visited;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }

    // Constructor with two child nodes
    public Node(String value, String type, Boolean visited, Node leftChild, Node rightChild) {
        this(value, type, visited, leftChild, null, rightChild);
    }

    // Constructor with one child node
    public Node(String value, String type, Boolean visited, Node leftChild) {
        this(value, type, visited, leftChild, null, null);
    }

    // Constructor with value, type, and visited flag
    public Node(String value, String type, Boolean visited) {
        this(value, type, visited, null, null, null);
    }

    // Constructor with value, type, and one child node
    public Node(String value, String type, Node leftChild) {
        this(value, type, false, leftChild, null, null);
    }

    // Setters and getters
    public void SetValue(String value) {
        this.value = value;
    }

    public String GetValue() {
        return value;
    }

    public void SetType(String type) {
        this.type = type;
    }

    public String GetType() {
        return type;
    }

    public void SetVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean GetVisited() {
        return visited;
    }

    public void SetLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node GetLeftChild() {
        return leftChild;
    }

    public void SetRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public Node GetRightChild() {
        return rightChild;
    }

    public void SetMiddleChild(Node middleChild) {
        this.middleChild = middleChild;
    }

    public Node GetMiddleChild() {
        return middleChild;
    }

    public String EvaluateToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node(");
        sb.append("value=").append(value).append(", ");
        sb.append("type=").append(type).append(", ");
        sb.append("visited=").append(visited).append(", ");
        if (leftChild != null) {
            sb.append("leftChild=").append(leftChild.EvaluateToString()).append(", ");
        }
        if (middleChild != null) {
            sb.append("middleChild=").append(middleChild.EvaluateToString()).append(", ");
        }
        if (rightChild != null) {
            sb.append("rightChild=").append(rightChild.EvaluateToString());
        }
        sb.append(")");
        return sb.toString();
    }
}