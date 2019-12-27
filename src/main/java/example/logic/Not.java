package example.logic;

import lombok.ToString;

import java.util.List;

@ToString
public class Not implements Logic {
    List<Logic> ch;

    public Not(Logic l) {
        ch = List.of(l);
    }

    @Override
    public List<Logic> ch() {
        return ch;
    }

    @Override
    public String toString() {
        return "Not";
    }
}
