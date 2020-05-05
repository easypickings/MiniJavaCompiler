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
 * Represents a single token in the grammar.  If the "-tk" option
 * is used, also contains a Vector of preceding special tokens.
 */
public class NodeToken implements Node {
    private static final long serialVersionUID = -2200426771314034263L;
    public String tokenImage;
    // Stores a list of NodeTokens
    public Vector<NodeToken> specialTokens;
    // -1 for these ints means no position info is available.
    public int beginLine, beginColumn, endLine, endColumn;
    // Equal to the JavaCC token "kind" integer.
    // -1 if not available.
    public int kind;

    public NodeToken(String s) {
        this(s, -1, -1, -1, -1, -1);
    }

    public NodeToken(String s, int kind, int beginLine, int beginColumn,
                     int endLine, int endColumn) {
        tokenImage = s;
        specialTokens = null;
        this.kind = kind;
        this.beginLine = beginLine;
        this.beginColumn = beginColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public NodeToken getSpecialAt(int i) {
        if (specialTokens == null)
            throw new java.util.NoSuchElementException("No specials in token");
        return specialTokens.elementAt(i);
    }

    public int numSpecials() {
        if (specialTokens == null) return 0;
        return specialTokens.size();
    }

    public void addSpecial(NodeToken s) {
        if (specialTokens == null) specialTokens = new Vector<NodeToken>();
        specialTokens.addElement(s);
    }

    public void trimSpecials() {
        if (specialTokens == null) return;
        specialTokens.trimToSize();
    }

    @Override
    public String toString() { return tokenImage; }

    public String withSpecials() {
        if (specialTokens == null)
            return tokenImage;

        StringBuilder buf = new StringBuilder();

        for (Enumeration<NodeToken> e = specialTokens.elements(); e.hasMoreElements(); )
            buf.append(e.nextElement().toString());

        buf.append(tokenImage);
        return buf.toString();
    }

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

