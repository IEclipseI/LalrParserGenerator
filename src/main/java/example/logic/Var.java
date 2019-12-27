package example.logic;

import lombok.ToString;

import java.util.List;

@ToString
public class Var implements Logic {
    @Override
    public List<Logic> ch() {
        return List.of();
    }
}
