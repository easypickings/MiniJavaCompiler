//
// Generated by JTB 1.3.2
//

package spiglet.syntaxtree;

import spiglet.visitor.GJNoArguVisitor;
import spiglet.visitor.GJVisitor;
import spiglet.visitor.GJVoidVisitor;
import spiglet.visitor.Visitor;

/**
 * Represents a grammar choice, e.g. ( A | B )
 */
public class NodeChoice implements Node {
    private static final long serialVersionUID = -7685408160011608634L;
    public Node choice;
    public int which;

    public NodeChoice(Node node) {
        this(node, -1);
    }

    public NodeChoice(Node node, int whichChoice) {
        choice = node;
        which = whichChoice;
    }

    @Override
    public void accept(Visitor v) {
        choice.accept(v);
    }

    @Override
    public <R, A> R accept(GJVisitor<R, A> v, A argu) {
        return choice.accept(v, argu);
    }

    @Override
    public <R> R accept(GJNoArguVisitor<R> v) {
        return choice.accept(v);
    }

    @Override
    public <A> void accept(GJVoidVisitor<A> v, A argu) {
        choice.accept(v, argu);
    }
}

