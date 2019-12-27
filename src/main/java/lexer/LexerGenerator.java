package lexer;

import lombok.SneakyThrows;
import lombok.Value;
import parser.Terminal;
import parser.node.Node;
import util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerGenerator {
    @SneakyThrows
    public static void generateLexer(String name, List<Terminal> terminals, String pack) {
        String file = String.format(pattern, pack, name, terms(terminals));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/" + pack.replaceAll("\\.", "/") + "/Lexer" + name + ".java"));
        bufferedWriter.write(file);
        bufferedWriter.close();
    }

    private static String terms(List<Terminal> terminals) {
        StringBuilder res = new StringBuilder();
        String tab = "        ";
        if (terminals.size() > 0) {
            res.append(tab).append("new Pair<>(\"").append(terminals.get(0).getName()).append("\", \"").append(terminals.get(0).getPattern()).append("\")");
        }
        for (int i = 1; i < terminals.size(); i++) {
            res.append(",\n").append(tab).append("new Pair<>(\"").append(terminals.get(i).getName()).append("\", \"").append(terminals.get(i).getPattern()).append("\")");
        }
        return res.toString();
    }

    List<Pair<String, String>> terminals = List.of(
            new Pair<>("EQ", "="),
            new Pair<>("X", "x"),
            new Pair<>("ARK", "\\*")
    );

    public Queue<Node> tokens(String input1) {
        Queue<Node> queue = new ArrayDeque<>();
        StringView input = new StringView(input1, 0, input1.length());
        int pos = 0;

        while (input.length() != 0) {
            for (Pair<String, String> terminal : terminals) {
                Pattern pattern = Pattern.compile("^" + terminal.getValue());
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    pos = matcher.end();
                    queue.add(new Node(terminal.getKey()));
                    break;
                }
            }
            if (pos == 0) {
                throw new IllegalArgumentException("mda");
            } else {
                input = new StringView(input.str, input.from + pos, input.str.length());
            }
            pos = 0;
        }
        queue.add(new Node("END"));
        return queue;
    }

    @Value
    public static class StringView implements CharSequence {
        String str;
        int from;
        int to;

        @Override
        public int length() {
            return to - from;
        }

        @Override
        public char charAt(int index) {
            return str.charAt(from + index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return new StringView(str, start, end);
        }
    }

    private static final String pattern =
            "package %s;\n" +
                    "import lombok.Value;\n" +
                    "import parser.node.TypedNode;\n" +
                    "import util.Pair;\n" +
                    "\n" +
                    "import java.util.ArrayDeque;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Queue;\n" +
                    "import java.util.regex.Matcher;\n" +
                    "import java.util.regex.Pattern;\n" +
                    "\n" +
                    "public class Lexer%s {\n" +
                    "    List<Pair<String, String>> terminals = List.of(\n" +
                    "%s\n" +
                    "    );\n" +
                    "\n" +
                    "    public Queue<TypedNode> tokens(String input) {\n" +
                    "        Queue<TypedNode> queue = new ArrayDeque<>();\n" +
                    "        int pos = 0;\n" +
                    "\n" +
                    "        while (input.length() != 0) {\n" +
                    "            for (Pair<String, String> terminal : terminals) {\n" +
                    "                Pattern pattern = Pattern.compile(\"^\" + terminal.getValue());\n" +
                    "                Matcher matcher = pattern.matcher(input);\n" +
                    "                if (matcher.find()) {\n" +
                    "                    pos = matcher.end();\n" +
                    "                    queue.add(new TypedNode(terminal.getKey(), matcher.group(), \"\"));\n" +
                    "                    break;\n" +
                    "                }\n" +
                    "            }\n" +
                    "            if (pos == 0) {\n" +
                    "                throw new IllegalArgumentException(\"mda\");\n" +
                    "            } else {\n" +
                    "                input = input.substring(pos);\n" +
                    "            }\n" +
                    "            pos = 0;\n" +
                    "        }\n" +
                    "        queue.add(new TypedNode(\"END\", \"\", \"\"));\n" +
                    "        return queue;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Value\n" +
                    "    public static class StringView implements CharSequence {\n" +
                    "        String str;\n" +
                    "        int from;\n" +
                    "        int to;\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public int length() {\n" +
                    "            return to - from;\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public char charAt(int index) {\n" +
                    "            return str.charAt(from + index);\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public CharSequence subSequence(int start, int end) {\n" +
                    "            return new StringView(str, start, end);\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n";
}
