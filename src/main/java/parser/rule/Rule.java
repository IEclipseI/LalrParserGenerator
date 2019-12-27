package parser.rule;

import lombok.*;
import parser.node.Node;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Rule implements Serializable {
    public int ind;
    public Node left;
    public List<Node> right;
    @EqualsAndHashCode.Exclude
    public String action;
    @EqualsAndHashCode.Exclude
    public String type;
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(ind).append(": ").append(left).append(" -> ");
        right.forEach(n -> sb.append(n).append(" "));
        return sb.toString();
    }
}
