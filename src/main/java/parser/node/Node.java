package parser.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Node implements Serializable {
    public String name;

    @Override
    public String toString() {
        return name;
    }

    public final boolean isEpsilon() {
        return name.equals("EPS");
    }
}
