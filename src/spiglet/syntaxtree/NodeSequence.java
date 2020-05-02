//
// Generated by JTB 1.3.2
//

package spiglet.syntaxtree;

import spiglet.visitor.GJNoArguVisitor;
import spiglet.visitor.GJVisitor;
import spiglet.visitor.GJVoidVisitor;
import spiglet.visitor.Visitor;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Represents a sequence of nodes nested within a choice, list,
 * optional list, or optional, e.g. ( A B )+ or [ C D E ]
 */
public class NodeSequence implements NodeListInterface {
    private static final long serialVersionUID = -2145017073089687256L;
    public Vector<Node> nodes;

    public NodeSequence(int n) {
        nodes = new Vector<>(n);
    }

    public NodeSequence(Node firstNode) {
        nodes = new Vector<>();
        addNode(firstNode);
    }

    @Override
    public void addNode(Node n) {
        nodes.addElement(n);
    }

    @Override
    public Node elementAt(int i) { return nodes.elementAt(i); }

    @Override
    public Enumeration<Node> elements() { return nodes.elements(); }

    @Override
    public int size() { return nodes.size(); }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public <R, A> R accept(GJVisitor<R, A> v, A argu) {
        return v.visit(this, argu);
    }

    @Override
    public <R> R accept(GJNoArguVisitor<R> v) {
        return v.visit(this);
    }

    @Override
    public <A> void accept(GJVoidVisitor<A> v, A argu) {
        v.visit(this, argu);
    }
}

