package parser.rule;

import lombok.*;
import parser.node.Node;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class ExtendedRule extends Rule {
    public int curDotPos;

    public ExtendedRule(int ind, Node left, List<Node> right, int curDotPos) {
        super(ind, left, right);
        this.curDotPos = curDotPos;
    }

    public ExtendedRule(Rule rule, int curDotPos) {
        this(rule);
        this.curDotPos = curDotPos;
    }

    public ExtendedRule(Rule rule) {
        super(rule.ind, rule.left, rule.right);
        this.curDotPos = 0;
    }

    public ExtendedRule acceptNode(Node node) {
        if (curDotPos != right.size()) {
            if (right.get(curDotPos).equals(node)) {
                return new ExtendedRule(ind, left, right, curDotPos++);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        String s = left.toString() + " -> ";
        for (int i = 0; i < right.size(); i++) {
            if (i == curDotPos)
                s += "• ";
            s += right.get(i) + " ";
        }
        if (curDotPos == right.size())
            s += "• ";
        return s;
    }
}
