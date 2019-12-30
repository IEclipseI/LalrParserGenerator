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
       return (int)Math.pow(((Integer)l.get(0)), ((Integer)l.get(2)));
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
    public static String map = "rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAGB3CAAAAIAAAABUc3IACXV0aWwuUGFpcoKSvhNz6hlfAgACTAADa2V5dAASTGphdmEvbGFuZy9PYmplY3Q7TAAFdmFsdWVxAH4AA3hwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAZzcgAQcGFyc2VyLm5vZGUuTm9kZdFyWzR722NLAgABTAAEbmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO3hwdAADc3Vtc3IAEnBhcnNlci5hY3Rpb24uR29Ub308xXjUMKzxAgABSQACdG94cAAAAApzcQB+AAJzcQB+AAUAAAACc3EAfgAIdAADUE9Xc3IAE3BhcnNlci5hY3Rpb24uU2hpZnQ9tYB1RYdPTQIAAUkAAnRveHAAAAALc3EAfgACcQB+AA9zcQB+AAh0AANFTkRzcgAUcGFyc2VyLmFjdGlvbi5SZWR1Y2UXAT6Pt04MtAIAAkwABmFjdGlvbnEAfgAJTAAEcnVsZXQAEkxwYXJzZXIvcnVsZS9SdWxlO3hwdAACJDBzcgAQcGFyc2VyLnJ1bGUuUnVsZV2VAhOhSagLAgAFSQADaW5kTAAGYWN0aW9ucQB+AAlMAARsZWZ0dAASTHBhcnNlci9ub2RlL05vZGU7TAAFcmlnaHR0ABBMamF2YS91dGlsL0xpc3Q7TAAEdHlwZXEAfgAJeHAAAAAHcQB+ABpzcQB+AAh0AANwb3dzcgATamF2YS51dGlsLkFycmF5TGlzdHiB0h2Zx2GdAwABSQAEc2l6ZXhwAAAAAXcEAAAAAXNxAH4ACHQAA251bXh0AAdJbnRlZ2Vyc3EAfgACc3EAfgAFAAAAEHNxAH4ACHQAA0FERHNxAH4AF3QAByQwIC0gJDJzcQB+ABsAAAADcQB+ACtzcQB+AAh0AANzdW1zcQB+ACEAAAADdwQAAAADc3EAfgAIdAADc3Vtc3EAfgAIdAADU1VCc3EAfgAIdAADbXVseHQAB0ludGVnZXJzcQB+AAJzcQB+AAUAAAAIc3EAfgAIdAADcG93c3EAfgAMAAAABHNxAH4AAnNxAH4ABQAAAA9xAH4AFXNxAH4AF3QAFShpbnQpTWF0aC5wb3coJDAsICQyKXNxAH4AGwAAAAZxAH4AP3EAfgA5c3EAfgAhAAAAA3cEAAAAA3NxAH4ACHQAA251bXNxAH4ACHQAA1BPV3NxAH4ACHQAA3Bvd3h0AAdJbnRlZ2Vyc3EAfgACc3EAfgAFAAAAA3NxAH4ACHQAA0FERHNxAH4AEgAAAAhzcQB+AAJxAH4APXNxAH4ACHQAAlJQc3EAfgAXcQB+AD9xAH4AQHNxAH4AAnEAfgAPcQB+AE9zcQB+ABdxAH4AGnEAfgAec3EAfgACc3EAfgAFAAAADnNxAH4ACHEAfgApc3EAfgAXdAAHJDAgKyAkMnNxAH4AGwAAAAJxAH4AWHNxAH4ACHQAA3N1bXNxAH4AIQAAAAN3BAAAAANzcQB+AAh0AANzdW1zcQB+AAhxAH4AKXNxAH4ACHQAA211bHh0AAdJbnRlZ2Vyc3EAfgACcQB+AAdxAH4AOXNxAH4ADAAAAARzcQB+AAJzcQB+AAUAAAANcQB+ABVzcQB+ABd0AAckMCAqICQyc3EAfgAbAAAABHEAfgBoc3EAfgAIdAADbXVsc3EAfgAhAAAAA3cEAAAAA3NxAH4ACHQAA211bHNxAH4ACHQAA01VTHNxAH4ACHQAA3Bvd3h0AAdJbnRlZ2Vyc3EAfgACc3EAfgAFAAAAAXEAfgBWc3EAfgAXdAAUSW50ZWdlci5wYXJzZUludCgkMClzcQB+ABsAAAAIcQB+AHdzcQB+AAh0AANudW1zcQB+ACEAAAABdwQAAAABc3EAfgAIdAADTlVNeHQAB0ludGVnZXJzcQB+AAJxAH4AZnEAfgBPc3EAfgAXcQB+AGhxAH4AaXNxAH4AAnEAfgA4c3EAfgAIdAADTlVNc3EAfgASAAAAAXNxAH4AAnNxAH4ABQAAAAxxAH4AKHNxAH4AF3QAAiQxc3EAfgAbAAAACXEAfgCIc3EAfgAIdAADbnVtc3EAfgAhAAAAA3cEAAAAA3NxAH4ACHQAAkxQc3EAfgAIdAADc3Vtc3EAfgAIcQB+AFB4dAAHSW50ZWdlcnNxAH4AAnNxAH4ABQAAAABxAH4AanNxAH4ADAAAAAVzcQB+AAJzcQB+AAUAAAAFc3EAfgAIcQB+ADNzcQB+ABd0AAIkMHNxAH4AGwAAAAFxAH4AmnEAfgAKc3EAfgAhAAAAAXcEAAAAAXNxAH4ACHQAA211bHh0AAdJbnRlZ2Vyc3EAfgACcQB+AAdxAH4AgnNxAH4AEgAAAAFzcQB+AAJxAH4AlHEAfgAKc3EAfgAMAAAAA3NxAH4AAnEAfgCXc3EAfgAIdAADTVVMc3EAfgASAAAACXNxAH4AAnNxAH4ABQAAAApxAH4AS3NxAH4AEgAAAAhzcQB+AAJxAH4ASnNxAH4ACHQAA1NVQnNxAH4AEgAAAAdzcQB+AAJxAH4AJ3NxAH4ACHEAfgAzc3EAfgAXcQB+ACtxAH4ALHNxAH4AAnEAfgAncQB+AKVzcQB+ABIAAAAJc3EAfgACcQB+AJRxAH4AOXNxAH4ADAAAAARzcQB+AAJxAH4AdXEAfgCYc3EAfgAXcQB+AHdxAH4AeHNxAH4AAnEAfgBVcQB+AJhzcQB+ABdxAH4AWHEAfgBZc3EAfgACcQB+ADhzcQB+AAh0AAJMUHNxAH4AEgAAAAZzcQB+AAJxAH4AdXNxAH4ACHEAfgBwc3EAfgAXcQB+AHdxAH4AeHNxAH4AAnEAfgBVcQB+AKVzcQB+ABIAAAAJc3EAfgACcQB+ADhxAH4AeXNxAH4ADAAAAAJzcQB+AAJzcQB+AAUAAAALcQB+ADlzcQB+AAwAAAAPc3EAfgACc3EAfgAFAAAAB3EAfgBqc3EAfgAMAAAAEHNxAH4AAnEAfgCXcQB+ABVzcQB+ABdxAH4AmnEAfgCbc3EAfgACcQB+AJdxAH4AT3NxAH4AF3EAfgCacQB+AJtzcQB+AAJxAH4AhnEAfgCwc3EAfgAXcQB+AIhxAH4AiXNxAH4AAnEAfgAHcQB+ALtzcQB+ABIAAAAGc3EAfgACcQB+AJRxAH4AgnNxAH4AEgAAAAFzcQB+AAJxAH4AB3EAfgB5c3EAfgAMAAAAAnNxAH4AAnEAfgCGc3EAfgAIcQB+AHBzcQB+ABdxAH4AiHEAfgCJc3EAfgACc3EAfgAFAAAACXEAfgA5c3EAfgAMAAAADXNxAH4AAnEAfgAncQB+ABVzcQB+ABdxAH4AK3EAfgAsc3EAfgACc3EAfgAFAAAABHEAfgAoc3EAfgAXdAACJDBzcQB+ABsAAAAFcQB+AOJzcQB+AAh0AANtdWxzcQB+ACEAAAABdwQAAAABc3EAfgAIdAADcG93eHQAB0ludGVnZXJzcQB+AAJxAH4ASnEAfgAVc3IAFHBhcnNlci5hY3Rpb24uQWNjZXB09pdtjYNOOCsCAAB4cHNxAH4AAnEAfgDGcQB+AIJzcQB+ABIAAAABc3EAfgACcQB+ACdxAH4AT3NxAH4AF3EAfgArcQB+ACxzcQB+AAJxAH4AqXEAfgCsc3EAfgASAAAAB3NxAH4AAnEAfgB1c3EAfgAIcQB+AEVzcQB+ABdxAH4Ad3EAfgB4c3EAfgACcQB+AMlxAH4AOXNxAH4ADAAAAARzcQB+AAJxAH4AVXEAfgAVc3EAfgAXcQB+AFhxAH4AWXNxAH4AAnEAfgAPcQB+AFZzcQB+ABdxAH4AGnEAfgAec3EAfgACcQB+AHVxAH4AFXNxAH4AF3EAfgB3cQB+AHhzcQB+AAJxAH4APXEAfgBWc3EAfgAXcQB+AD9xAH4AQHNxAH4AAnEAfgDbcQB+AIJzcQB+ABIAAAABc3EAfgACcQB+AFVxAH4AT3NxAH4AF3EAfgBYcQB+AFlzcQB+AAJxAH4AdXEAfgBPc3EAfgAXcQB+AHdxAH4AeHNxAH4AAnEAfgCGcQB+APRzcQB+ABdxAH4AiHEAfgCJc3EAfgACcQB+AIZxAH4AFXNxAH4AF3EAfgCIcQB+AIlzcQB+AAJxAH4AZnEAfgAoc3EAfgAXcQB+AGhxAH4AaXNxAH4AAnEAfgDJcQB+AIJzcQB+ABIAAAABc3EAfgACcQB+AIZxAH4AT3NxAH4AF3EAfgCIcQB+AIlzcQB+AAJxAH4AlHEAfgC7c3EAfgASAAAABnNxAH4AAnEAfgCUcQB+AHlzcQB+AAwAAAACc3EAfgACcQB+AKlzcQB+AAh0AAJSUHNxAH4AEgAAAAxzcQB+AAJxAH4AxnEAfgC7c3EAfgASAAAABnNxAH4AAnEAfgDgcQB+ALBzcQB+ABdxAH4A4nEAfgDjc3EAfgACcQB+AMZxAH4AeXNxAH4ADAAAAAJzcQB+AAJxAH4A4HEAfgC/c3EAfgAXcQB+AOJxAH4A43NxAH4AAnEAfgA9cQB+AJhzcQB+ABdxAH4AP3EAfgBAc3EAfgACcQB+ANtxAH4Au3NxAH4AEgAAAAZzcQB+AAJxAH4AD3EAfgCwc3EAfgAXcQB+ABpxAH4AHnNxAH4AAnEAfgDbcQB+AHlzcQB+AAwAAAACc3EAfgACcQB+AD1zcQB+AAhxAH4AcHNxAH4AF3EAfgA/cQB+AEBzcQB+AAJxAH4AD3EAfgDYc3EAfgAXcQB+ABpxAH4AHnNxAH4AAnEAfgA4cQB+AGpzcQB+AAwAAAAOc3EAfgACcQB+AGZxAH4AsHNxAH4AF3EAfgBocQB+AGlzcQB+AAJxAH4AyXEAfgC7c3EAfgASAAAABnNxAH4AAnEAfgDJcQB+AHlzcQB+AAwAAAACc3EAfgACcQB+AGZxAH4A2HNxAH4AF3EAfgBocQB+AGlzcQB+AAJxAH4A4HEAfgAVc3EAfgAXcQB+AOJxAH4A43NxAH4AAnEAfgAHcQB+AGpzcQB+AAwAAAAFc3EAfgACcQB+AJdxAH4AVnNxAH4AF3EAfgCacQB+AJtzcQB+AAJxAH4A4HEAfgBPc3EAfgAXcQB+AOJxAH4A43g=";

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
    return (Integer)stack.pollFirst();
    }
}
