package parser.node;


import lombok.EqualsAndHashCode;

public class TypedNode extends Node {
    @EqualsAndHashCode.Exclude
    public String value;
    @EqualsAndHashCode.Exclude
    public String type;

    public TypedNode(String name, String value, String type) {
        super(name);
        this.value = value;
        this.type = type;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    protected boolean canEqual(Object other) {
        return other instanceof Node;
    }
}
