package example.logic;

import lombok.ToString;
import lombok.Value;

import java.util.List;

public class Or implements Logic {
    List<Logic> ch;

    public Or(Logic l, Logic r) {
        ch = List.of(l, r);
    }

    @Override
    public String toString() {
        return "Or";
    }

    @Override
    public List<Logic> ch() {
        return ch;
    }
}
