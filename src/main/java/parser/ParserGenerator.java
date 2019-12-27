package parser;

//import example.ParserAdd;

import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStreams;
import parser.action.Action;
import parser.node.Node;
import parser.node.TypedNode;
import parser.rule.Rule;
import util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static parser.Table.generateTable;


public class ParserGenerator {
    List<Function<List<Object>, Object>> o = List.of(l -> 2);

    @SneakyThrows
    public static void generateParser(String grammarFile, String name, String pack) {
        Files.createDirectories(Path.of("src/main/java/" + pack.replaceAll("\\.", "/")));
        Table.Res res = generateTable(CharStreams.fromFileName(grammarFile), name, pack);
        Map<Pair<Integer, Node>, Action> example = res.table;
        String serialize = serialize((Serializable) example);
        String functions = "public static List<Function<List<Object>, Object>> f = List.of(";
        int j = 0;
        for (Rule rule : res.rules.stream().sorted(Comparator.comparing(Rule::getInd)).collect(Collectors.toList())) {
            String format = String.format(funcTemplate, rule.action);
            for (int i = 0; i < rule.right.size(); i++) {
                int ii = i;
                if (res.nodes.contains(rule.right.get(i))) {
                    TypedNode typedNode = res.nodes.stream().filter(n -> n.name.equals(rule.right.get(ii).name)).findAny().get();
                    format = format.replaceAll("\\$" + i, "((" + typedNode.type + ")l.get(" + i + "))");
                } else {
                    format = format.replaceAll("\\$" + i, "((" + "String" + ")l.get(" + i + "))");
                }

            }
            functions += format;
            if (j + 1 != res.rules.size()) {
                functions += ", \n";
            }
            j++;
        }
        functions += ");\n";
        String ress = String.format(pattern, pack, name, functions, serialize, res.nodes.stream().filter(s -> s.name.equals("start")).findAny().get().type,
                name, name,
                res.nodes.stream().filter(s -> s.name.equals("start")).findAny().get().type);

        System.out.println(ress);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/" + pack.replaceAll("\\.", "/") + "/Parser" + name + ".java"));
        bufferedWriter.write(ress);
        bufferedWriter.close();
    }

    @SneakyThrows
    private static String serialize(Serializable o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @SneakyThrows
    private static Object deserialize(String s) {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    static String funcTemplate =
            "    l -> {\n" +
                    "       return %s;\n" +
                    "}\n";

    static String fileTemplate = "package example;\n" +
            "public class Parser%s {\n" +
            "%s" +
            "public static String map = \"%s\";\n" +
            " @SneakyThrows\n" +
            "    private static Object deserialize(String s) {\n" +
            "        byte[] data = Base64.getDecoder().decode(s);\n" +
            "        ObjectInputStream ois = new ObjectInputStream(\n" +
            "                new ByteArrayInputStream(data));\n" +
            "        Object o = ois.readObject();\n" +
            "        ois.close();\n" +
            "        return o;\n" +
            "    }\n" +
            "public static Map<Pair<Integer, Node>, Action> example = ((Map<Pair<Integer, Node>, Action>)deserialize(map);\n" +
            "%s" +
            "}";

    static String parseTemplate =
            "public static %s parse(String input) {\n" +
                    "Queue<TypedNode> tokens = lexer.tokens(input);\n" +
                    "        int curState = 0;\n" +
                    "        Deque<Integer> state = new ArrayDeque<>();\n" +
                    "        Deque<Object> stack = new ArrayDeque<>();\n" +
                    "        state.addFirst(0);\n" +
                    "        while (!(example.get(new Pair<>(state.peekFirst(), tokens.peek())) instanceof Accept)) {\n" +
                    "            Action action = example.get(new Pair<>(state.peekFirst(), tokens.peek()));\n" +
                    "            if (action instanceof Shift) {\n" +
                    "                state.addFirst(((Shift) action).getTo());\n" +
                    "                stack.addFirst(tokens.poll().value);\n" +
                    "            } else if (action instanceof Reduce) {\n" +
                    "                Reduce reduce = (Reduce) action;\n" +
                    "                List<Object> arg = new ArrayList<>();\n" +
                    "                for (int i = 0; i < reduce.getRule().right.size(); i++) {\n" +
                    "                    arg.add(stack.pollFirst());\n" +
                    "                    state.removeFirst();\n" +
                    "                }\n" +
                    "                Collections.reverse(arg);\n" +
                    "                Rule rule = reduce.getRule();\n" +
                    "                int ind = rule.ind;\n" +
                    "                state.addFirst(((GoTo) example.get(new Pair<>(state.peekFirst(), rule.left))).getTo());\n" +
                    "                stack.addFirst(f.get(ind).apply(arg));\n" +
                    "\n" +
                    "            }" +
                    "}\n";

    private static final String pattern =
            "package %s;\n" +
                    "\n" +
                    "import lombok.SneakyThrows;\n" +
                    "import parser.action.*;\n" +
                    "import parser.node.Node;\n" +
                    "import parser.node.TypedNode;\n" +
                    "import parser.rule.Rule;\n" +
                    "import util.Pair;\n" +
                    "\n" +
                    "import java.io.ByteArrayInputStream;\n" +
                    "import java.io.ObjectInputStream;\n" +
                    "import java.util.*;\n" +
                    "import java.util.function.Function;\n" +
                    "\n" +
                    "public class Parser%s {\n" +
                    "    %s;\n" +
                    "    public static String map = \"%s\";\n" +
                    "\n" +
                    "    @SneakyThrows\n" +
                    "    private static Object deserialize(String s) {\n" +
                    "        byte[] data = Base64.getDecoder().decode(s);\n" +
                    "        ObjectInputStream ois = new ObjectInputStream(\n" +
                    "                new ByteArrayInputStream(data));\n" +
                    "        Object o = ois.readObject();\n" +
                    "        ois.close();\n" +
                    "        return o;\n" +
                    "    }\n" +
                    "\n" +
                    "    public static Map<Pair<Integer, Node>, Action> example = ((Map<Pair<Integer, Node>, Action>) deserialize(map));\n" +
                    "\n" +
                    "    public static %s parse(String input) {\n" +
                    "        Lexer%s lexer = new Lexer%s();\n" +
                    "        Queue<TypedNode> tokens = lexer.tokens(input);\n" +
                    "        int curState = 0;\n" +
                    "        Deque<Integer> state = new ArrayDeque<>();\n" +
                    "        Deque<Object> stack = new ArrayDeque<>();\n" +
                    "        state.addFirst(0);\n" +
                    "        while (!(example.get(new Pair<>(state.peekFirst(), tokens.peek())) instanceof Accept)) {\n" +
                    "            Action action = example.get(new Pair<>(state.peekFirst(), tokens.peek()));\n" +
                    "            if (action instanceof Shift) {\n" +
                    "                state.addFirst(((Shift) action).getTo());\n" +
                    "                stack.addFirst(tokens.poll().value);\n" +
                    "            } else if (action instanceof Reduce) {\n" +
                    "                Reduce reduce = (Reduce) action;\n" +
                    "                List<Object> arg = new ArrayList<>();\n" +
                    "                for (int i = 0; i < reduce.getRule().right.size(); i++) {\n" +
                    "                    arg.add(stack.pollFirst());\n" +
                    "                    state.removeFirst();\n" +
                    "                }\n" +
                    "                Collections.reverse(arg);\n" +
                    "                Rule rule = reduce.getRule();\n" +
                    "                int ind = rule.ind;\n" +
                    "                state.addFirst(((GoTo) example.get(new Pair<>(state.peekFirst(), rule.left))).getTo());\n" +
                    "                stack.addFirst(f.get(ind).apply(arg));\n" +
                    "\n" +
                    "            }\n" +
                    "        }\n" +
                    "    return (%s)stack.pollFirst();\n" +
                    "    }\n" +
                    "}\n";
}
