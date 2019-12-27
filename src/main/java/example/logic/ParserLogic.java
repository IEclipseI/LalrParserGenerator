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
    public static String map = "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AAAbcQB+AKNzcQB+AE0AAAAYc3EAfgACcQB+AOJxAH4AMnNxAH4ADHQADm5ldyBPcigkMCwgJDIpc3EAfgAQAAAAAXEAfgDmcQB+ALVzcQB+ABYAAAADdwQAAAADc3EAfgAIdAACb3JzcQB+AAhxAH4AOHNxAH4ACHQAA3hvcnh0AAVMb2dpY3NxAH4AAnEAfgB5cQB+AChzcQB+AB8AAAASc3EAfgACcQB+AOJxAH4AUHNxAH4ADHEAfgDmcQB+AOdzcQB+AAJxAH4A03EAfgBQc3EAfgAMcQB+ANVxAH4A1nNxAH4AAnEAfgCbcQB+AB1zcQB+AB8AAAAac3EAfgACcQB+AHlxAH4AtXNxAH4AHwAAABlzcQB+AAJzcQB+AAUAAAAZc3EAfgAIdAACUlBzcQB+AE0AAAAdc3EAfgACcQB+AEpxAH4AV3NxAH4ATQAAABRzcQB+AAJxAH4ANnEAfgBcc3EAfgAMcQB+ADpxAH4AO3NxAH4AAnEAfgBKcQB+AGpzcQB+AE0AAAAOc3EAfgACcQB+AKJxAH4AhnNxAH4ADHEAfgCocQB+AKlzcQB+AAJxAH4ANnEAfgAjc3EAfgAMcQB+ADpxAH4AO3NxAH4AAnEAfgA2cQB+ADJzcQB+AAxxAH4AOnEAfgA7c3EAfgACcQB+AJtxAH4AS3NxAH4ATQAAABBzcQB+AAJxAH4ANnEAfgBQc3EAfgAMcQB+ADpxAH4AO3NxAH4AAnEAfgAccQB+AFdzcQB+AE0AAAAUc3EAfgACcQB+ABxxAH4AanNxAH4ATQAAAA5zcQB+AAJxAH4Au3EAfgCGc3EAfgAMcQB+AL1xAH4AvnNxAH4AAnEAfgBucQB+AFdzcQB+AE0AAAAUc3EAfgACcQB+AG5xAH4AanNxAH4ATQAAAA5zcQB+AAJxAH4A4nEAfgA3c3EAfgAMcQB+AOZxAH4A53NxAH4AAnEAfgB+cQB+ADJzcgAUcGFyc2VyLmFjdGlvbi5BY2NlcHT2l22Ng044KwIAAHhwc3EAfgACcQB+ANNxAH4AhnNxAH4ADHEAfgDVcQB+ANZzcQB+AAJxAH4AeXEAfgBXc3EAfgBNAAAAFHNxAH4AAnEAfgB5cQB+AGpzcQB+AE0AAAAOc3EAfgACcQB+AIVzcQB+AAh0AANBTkRzcQB+AE0AAAAVc3EAfgACcQB+APpxAH4Af3NxAH4ATQAAABZzcQB+AAJxAH4ASnEAfgAdc3EAfgAfAAAAD3NxAH4AAnEAfgCFcQB+AEVzcQB+AAxxAH4AiHEAfgCJc3EAfgACcQB+AIVxAH4AMnNxAH4ADHEAfgCIcQB+AIlzcQB+AAJxAH4AhXEAfgBQc3EAfgAMcQB+AIhxAH4AiXNxAH4AAnEAfgAicQB+ASRzcQB+AE0AAAAVeA==";

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
