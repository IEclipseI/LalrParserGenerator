package parser.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class NumeratedNode extends Node {
    public int from;
    public int to;

    public NumeratedNode(String name, int from, int to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from + name + to;
    }
}
