package parser.rule;

import lombok.*;
import parser.node.Node;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Rule {
    public int ind;
    public Node left;
    public List<Node> right;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ind).append(": ").append(left).append(" -> ");
        right.forEach(n -> sb.append(n).append(" "));
        return sb.toString();
    }
}
