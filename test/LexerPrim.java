import lombok.Value;
import parser.node.Node;
import util.Pair;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerPrim {
    List<Pair<String, String>> terminals = List.of(
            new Pair<>("ARK", "ARK"),
new Pair<>("EQ", "EQ"),
new Pair<>("X", "X")
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
}
