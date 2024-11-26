package fooli.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fooli.tree.descriptor.Descriptor;
import fooli.exception.SemanticException;

public class Scope implements ScopeEntry {
    private final Scope parent;
    private final List<ScopeEntry> entries;

    public Scope() {
        this(null);
    }

    public Scope(Scope parent) {
        this.parent = parent;
        this.entries = new ArrayList<>();
    }

    public void declare(Descriptor symbol) throws SemanticException {
        if (isSymbolDeclared(symbol.getName())) {
            throw new SemanticException("Symbol '" + symbol.getName() + "' already declared in this scope.");
        }
        entries.add(symbol);
    }

    public Descriptor lookup(String name) {
        return findSymbolInCurrentScope(name).orElseGet(() -> parent != null ? parent.lookup(name) : null);
    }

    public Scope getParent() {
        return parent;
    }

    public Scope createChildScope() {
        Scope childScope = new Scope(this);
        entries.add(childScope);
        return childScope;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serializeHelper(sb, 0);
        return sb.toString();
    }

    private void serializeHelper(StringBuilder sb, int indentLevel) {
        String indent = "  ".repeat(indentLevel);
        for (ScopeEntry entry : entries) {
            if (entry instanceof Descriptor) {
                sb.append(indent).append("  ").append(entry).append("\n");
            } else if (entry instanceof Scope) {
                ((Scope) entry).serializeHelper(sb, indentLevel + 1);
            }
        }
    }

    private boolean isSymbolDeclared(String name) {
        return entries.stream()
                .filter(entry -> entry instanceof Descriptor)
                .map(entry -> (Descriptor) entry)
                .anyMatch(symbol -> symbol.getName().equals(name));
    }

    private Optional<Descriptor> findSymbolInCurrentScope(String name) {
        return entries.stream()
                .filter(entry -> entry instanceof Descriptor)
                .map(entry -> (Descriptor) entry)
                .filter(symbol -> symbol.getName().equals(name))
                .findFirst();
    }
}