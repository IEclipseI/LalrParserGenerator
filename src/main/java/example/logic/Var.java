package example.logic;

import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
public class Var implements Logic {
    String name;
    @Override
    public List<Logic> ch() {
        return List.of();
    }

    @Override
    public String toString() {
        return name;
    }
}
