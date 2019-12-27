package example.add;

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

public class ParserAdd {
    public static List<Function<List<Object>, Object>> f = List.of(    l -> {
       return  ((Integer)l.get(0)) ;
}
, 
    l -> {
       return ((Integer)l.get(0));
}
, 
    l -> {
       return ((Integer)l.get(0)) + ((Integer)l.get(2));
}
, 
    l -> {
       return ((Integer)l.get(0)) - ((Integer)l.get(2));
}
, 
    l -> {
       return ((Integer)l.get(0)) * ((Integer)l.get(2));
}
, 
    l -> {
       return ((Integer)l.get(0));
}
, 
    l -> {
       return Integer.parseInt(((String)l.get(0)));
}
, 
    l -> {
       return ((Integer)l.get(1));
}
);
;
    public static String map = "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";

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

    public static Integer parse(String input) {
        LexerAdd lexer = new LexerAdd();
        Queue<TypedNode> tokens = lexer.tokens(input);
        int curState = 0;
        Deque<Integer> state = new ArrayDeque<>();
        Deque<Object> stack = new ArrayDeque<>();
        state.addFirst(0);
        while (!(example.get(new Pair<>(state.peekFirst(), tokens.peek())) instanceof Accept)) {
            Action action = example.get(new Pair<>(state.peekFirst(), tokens.peek()));
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
    return (Integer)stack.pollFirst();
    }
}
