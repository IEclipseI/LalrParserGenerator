package example.logic;

import lombok.SneakyThrows;
import parser.action.*;
import parser.node.Node;
import parser.node.TypedNode;
import parser.rule.Rule;
import util.Pair;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.function.Function;

public class ParserLogic {
    public static List<Function<List<Object>, Object>> f = List.of(    l -> {
       return ((Logic)l.get(0));
}
, 
    l -> {
       return new Or(((Logic)l.get(0)), ((Logic)l.get(2)));
}
, 
    l -> {
       return ((Logic)l.get(0));
}
, 
    l -> {
       return new Xor(((Logic)l.get(0)), ((Logic)l.get(2)));
}
, 
    l -> {
       return ((Logic)l.get(0));
}
, 
    l -> {
       return new And(((Logic)l.get(0)), ((Logic)l.get(2)));
}
, 
    l -> {
       return ((Logic)l.get(0));
}
, 
    l -> {
       return new Var(((String)l.get(0)));
}
, 
    l -> {
       return new Not(((Logic)l.get(1)));
}
, 
    l -> {
       return ((Logic)l.get(1));
}
);
;
    public static String map = "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AH4AOHNxAH4AInEAfgDYcQB+ANlzcQB+AAJxAH4AT3EAfgAbc3EAfgAXAAAAE3NxAH4AAnEAfgDWcQB+AEtzcQB+ACJxAH4A2HEAfgDZc3EAfgACcQB+AA9xAH4ACnNxAH4ADAAAABJzcQB+AAJxAH4Aa3EAfgCfc3EAfgAXAAAAGXNxAH4AAnEAfgBmcQB+ABtzcQB+ABcAAAATc3EAfgACcQB+AIdxAH4AP3NxAH4AInEAfgCUcQB+AJVzcQB+AAJxAH4AD3EAfgAbc3EAfgAXAAAAE3NxAH4AAnEAfgAacQB+AFBzcQB+ABcAAAAXc3EAfgACcQB+ABpxAH4AVHNxAH4AFwAAABFzcQB+AAJxAH4AtXEAfgBwc3EAfgAicQB+AMFxAH4AwnNxAH4AAnEAfgDTcQB+AApzcQB+AAwAAAAac3EAfgACcQB+AKlxAH4AcHNxAH4AInEAfgCscQB+AK1zcQB+AAJxAH4AB3EAfgBQc3EAfgAXAAAAF3NxAH4AAnEAfgAHcQB+AFRzcQB+ABcAAAARc3EAfgACcQB+AE9xAH4AjnNxAH4ADAAAAB9zcQB+AAJxAH4A1nEAfgBwc3EAfgAicQB+ANhxAH4A2XNxAH4AAnEAfgA+c3EAfgAIdAADWE9Sc3EAfgAXAAAAG3NxAH4AAnEAfgA+cQB+ADhzcQB+ACJxAH4AQnEAfgBDc3EAfgACcQB+ANNxAH4AG3NxAH4AFwAAABNzcQB+AAJxAH4APnEAfgBLc3EAfgAicQB+AEJxAH4AQ3NxAH4AAnEAfgBYcQB+ACBzcQB+ACJxAH4AWnEAfgBbc3EAfgACcQB+AFhxAH4AknNxAH4AInEAfgBacQB+AFtzcQB+AAJxAH4AWHEAfgA4c3EAfgAicQB+AFpxAH4AW3NxAH4AAnEAfgBmcQB+AI5zcQB+AAwAAAAUc3EAfgACcQB+AFhxAH4AS3NxAH4AInEAfgBacQB+AFtzcQB+AAJxAH4AZnEAfgCjc3EAfgAMAAAAHnNxAH4AAnEAfgB8cQB+ACBzcQB+ACJxAH4AfnEAfgB/c3EAfgACcQB+AG9xAH4BCnNxAH4AFwAAABtzcQB+AAJxAH4Ab3EAfgA4c3EAfgAicQB+AHJxAH4Ac3NxAH4AAnEAfgAPcQB+AI5zcQB+AAwAAAAUc3EAfgACcQB+ABpxAH4ACnNxAH4ADAAAABJzcQB+AAJxAH4AfHEAfgA0c3EAfgAicQB+AH5xAH4Af3NxAH4AAnEAfgB8cQB+ADhzcQB+ACJxAH4AfnEAfgB/c3EAfgACcQB+AG9xAH4AS3NxAH4AInEAfgBycQB+AHNzcQB+AAJxAH4AD3EAfgCjc3EAfgAMAAAAFXNxAH4AAnEAfgB8cQB+AEtzcQB+ACJxAH4AfnEAfgB/eA==";

    @SneakyThrows
    private static Object deserialize(String s) {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static Map<Pair<Integer, Node>, Action> example = ((Map<Pair<Integer, Node>, Action>) deserialize(map));

    public static Logic parse(String input) {
        LexerLogic lexer = new LexerLogic();
        Queue<TypedNode> tokens = lexer.tokens(input);
        int curState = 0;
        Deque<Integer> state = new ArrayDeque<>();
        Deque<Object> stack = new ArrayDeque<>();
        state.addFirst(0);
        while (!(example.get(new Pair<>(state.peekFirst(), tokens.peek())) instanceof Accept)) {
            Action action = example.get(new Pair<>(state.peekFirst(), tokens.peek()));
            if (action == null)
                throw new UnsupportedOperationException("Grammar possibly is not LALR(1)");
                        if (action instanceof Shift) {
                state.addFirst(((Shift) action).getTo());
                stack.addFirst(tokens.poll().value);
            } else if (action instanceof Reduce) {
                Reduce reduce = (Reduce) action;
                List<Object> arg = new ArrayList<>();
                for (int i = 0; i < reduce.getRule().right.size(); i++) {
                    arg.add(stack.pollFirst());
                    state.removeFirst();
                }
                Collections.reverse(arg);
                Rule rule = reduce.getRule();
                int ind = rule.ind;
                state.addFirst(((GoTo) example.get(new Pair<>(state.peekFirst(), rule.left))).getTo());
                stack.addFirst(f.get(ind).apply(arg));

            }
        }
    return (Logic)stack.pollFirst();
    }
}
