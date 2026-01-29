package api.visitor;

import api.operators.JoinOperator;
import api.operators.SqlOperator;
import api.operators.TextOperator;

/**
 * Visitor qui "explique" (pretty-print) un plan d'exécution (arbre d'opérateurs).
 * Usage:
 *   ExplainVisitor ev = new ExplainVisitor();
 *   root.accept(ev);
 *   System.out.println(ev.getPlan());
 */
public class ExplainVisitor implements OperatorVisitor {

    private final StringBuilder sb = new StringBuilder();
    private int indent = 0;

    private void line(String s) {
        for (int i = 0; i < indent; i++) sb.append("  ");
        sb.append(s).append("\n");
    }

    public String getPlan() {
        return sb.toString();
    }

    // -------- VISITS --------

    @Override
    public void visit(SqlOperator op) throws Exception{
        // ⚠️ adapte le getter si ton SqlOperator n'a pas getSql()
        String sql = safe(op.getSqlPart());
        line("SQL[" + shorten(sql, 120) + "]");
    }

    @Override
    public void visit(TextOperator op) throws Exception{
        // ⚠️ adapte le getter si ton TextOperator n'a pas getTextQuery()
        String q = safe(op.getTextPart());
        line("TEXT[" + shorten(q, 120) + "]");
    }

    @Override
    public void visit(JoinOperator op) throws Exception{
        // Tu peux afficher ici le type de plan (order lucene), etc.
        line("JOIN(order=lucene)");
        indent++;

        // ⚠️ adapte les getters si besoin: getLeft()/getRight()
        if (op.getLeft() != null) op.getLeft().accept(this);
        else line("(null-left)");

        if (op.getRight() != null) op.getRight().accept(this);
        else line("(null-right)");

        indent--;
    }

    // -------- helpers --------

    private static String safe(String s) {
        return s == null ? "null" : s.trim();
    }

    private static String shorten(String s, int max) {
        if (s == null) return "null";
        if (s.length() <= max) return s;
        return s.substring(0, max - 3) + "...";
    }
}
