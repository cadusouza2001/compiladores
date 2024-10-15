import java.util.Stack;

public class AST {

    private Node root = null;
    private Stack<Node> stack = null;

    public AST() {
        root = null;
        stack = new Stack<Node>();
    }

    public Node GetRoot() {
        return root;
    }

    public void SetRoot(Node root) {
        this.root = root;
    }

    public String GetExpression() {
        return root.EvaluateToString();
    }
}