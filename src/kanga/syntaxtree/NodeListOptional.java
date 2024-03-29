//
// Generated by JTB 1.3.2
//

package kanga.syntaxtree;

import kanga.visitor.GJNoArguVisitor;
import kanga.visitor.GJVisitor;
import kanga.visitor.GJVoidVisitor;
import kanga.visitor.Visitor;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Represents an optional grammar list, e.g. ( A )*
 */
public class NodeListOptional implements NodeListInterface {
    private static final long serialVersionUID = 870195028909858864L;
    public Vector<Node> nodes;

    public NodeListOptional() {
        nodes = new Vector<Node>();
    }

    public NodeListOptional(Node firstNode) {
        nodes = new Vector<Node>();
        addNode(firstNode);
    }

    @Override
    public void addNode(Node n) {
        nodes.addElement(n);
    }

    @Override
    public Enumeration<Node> elements() { return nodes.elements(); }

    @Override
    public Node elementAt(int i) { return nodes.elementAt(i); }

    @Override
    public int size() { return nodes.size(); }

    public boolean present() { return nodes.size() != 0; }

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

