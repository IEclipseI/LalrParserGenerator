package parser.rule;


import lombok.EqualsAndHashCode;
import parser.node.Node;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class FinalRule extends Rule {
    public FinalRule(int ind, Node left, List<Node> right) {
        super(ind, left, right);
    }
}
