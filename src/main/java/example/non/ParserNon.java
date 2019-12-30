package example.non;

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

public class ParserNon {
    public static List<Function<List<Object>, Object>> f = List.of(    l -> {
       return ((String)l.get(0));
}
, 
    l -> {
       return "a" + ((String)l.get(1)) + "a";
}
, 
    l -> {
       return "b" + ((String)l.get(1)) + "b";
}
, 
    l -> {
       return "a" + ((String)l.get(1)) + "b";
}
, 
    l -> {
       return "b" + ((String)l.get(1)) + "a";
}
, 
    l -> {
       return "e";
}
, 
    l -> {
       return "e";
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

    public static String parse(String input) {
        LexerNon lexer = new LexerNon();
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
    return (String)stack.pollFirst();
    }
}
