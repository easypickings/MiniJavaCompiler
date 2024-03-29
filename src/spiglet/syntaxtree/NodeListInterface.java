//
// Generated by JTB 1.3.2
//

package spiglet.syntaxtree;

import spiglet.visitor.GJNoArguVisitor;
import spiglet.visitor.GJVisitor;
import spiglet.visitor.GJVoidVisitor;
import spiglet.visitor.Visitor;

/**
 * The interface which NodeList, NodeListOptional, and NodeSequence
 * implement.
 */
public interface NodeListInterface extends Node {
    void addNode(Node n);

    Node elementAt(int i);

    java.util.Enumeration<Node> elements();

    int size();

    @Override
    void accept(Visitor v);

    @Override
    <R, A> R accept(GJVisitor<R, A> v, A argu);

    @Override
    <R> R accept(GJNoArguVisitor<R> v);

    @Override
    <A> void accept(GJVoidVisitor<A> v, A argu);
}

