package parser.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import parser.rule.Rule;

@AllArgsConstructor
@Data
public class Reduce implements Action {
    String action;
    Rule rule;
}
