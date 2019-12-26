package parser;

import lombok.*;
import parser.node.Node;
import parser.rule.ExtendedRule;
import parser.rule.Rule;

import java.util.*;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemSet {
    @EqualsAndHashCode.Exclude
    public int id;
    Set<ExtendedRule> set = new HashSet<>();

    public void addRule(ExtendedRule rule) {
        set.add(rule);
    }

    public List<ExtendedRule> rules() {
        return new ArrayList<>(set);
    }

    public ItemSet copy() {
        return new ItemSet(id, new HashSet<>(set));
    }

    public ItemSet applyNode(Node node, Map<Node, List<Rule>> rules) {
        ItemSet itemSet = new ItemSet();
        itemSet.set = set.stream()
                .filter(er -> er.curDotPos < er.right.size() && er.right.get(er.curDotPos).equals(node))
                .map(er -> new ExtendedRule(er, er.curDotPos + 1))
                .collect(Collectors.toSet());
        itemSet.expandSet(rules);
        return itemSet;
    }

    public void expandSet(Map<Node, List<Rule>> rules) {
        ItemSet prev = null;
        while (!equals(prev)) {
            prev = copy();
            List<ExtendedRule> r = rules();
            for (ExtendedRule extendedRule : r) {
                if (extendedRule.curDotPos < extendedRule.right.size()) {
                    List<Rule> tmp = rules.get(extendedRule.right.get(extendedRule.curDotPos));
                    if (tmp != null)
                        tmp.forEach(rl -> addRule(new ExtendedRule(rl)));
                }
            }
        }
    }
}
