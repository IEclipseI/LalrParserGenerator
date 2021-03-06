package example.non;
import lombok.Value;
import parser.node.TypedNode;
import util.Pair;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerNon {
    List<Pair<String, String>> terminals = List.of(
        new Pair<>("A", "a"),
        new Pair<>("B", "b"),
        new Pair<>("E", "e")
    );

    public Queue<TypedNode> tokens(String input) {
        Queue<TypedNode> queue = new ArrayDeque<>();
        int pos = 0;

        while (input.length() != 0) {
            for (Pair<String, String> terminal : terminals) {
                Pattern pattern = Pattern.compile("^" + terminal.getValue());
                Matcher matcher = pattern.matcher(input);
                if (matcher.find()) {
                    pos = matcher.end();
                    queue.add(new TypedNode(terminal.getKey(), matcher.group(), ""));
                    break;
                }
            }
            if (pos == 0) {
                throw new IllegalArgumentException("Doesn't match grammar");
            } else {
                input = input.substring(pos);
            }
            pos = 0;
        }
        queue.add(new TypedNode("END", "", ""));
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
