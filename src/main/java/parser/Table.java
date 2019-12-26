package parser;

import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.node.Node;
import parser.node.NumeratedNode;
import parser.rule.ExtendedRule;
import parser.rule.Rule;
import util.GrammarParserLexer;
import util.GrammarParserParser;
import util.GrammarParserParser.*;
import util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    @SneakyThrows
    public static void main(String[] args) {
        generate(CharStreams.fromFileName("input.txt"));
    }

    public static int ids = 1;

    public static final Node EPS = new Node("EPS");
    public static final Node END = new Node("END");

    public static Table generate(CharStream stream) {
        GrammarParserLexer lexer = new GrammarParserLexer(stream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        GrammarParserParser parser = new GrammarParserParser(tokenStream);

        List<Terminal> terminals = getTerminals(parser.terminals());
        List<Node> terminalsNodes = terminals.stream()
                .map(t -> new Node(t.getName()))
                .collect(Collectors.toList());
        terminalsNodes.addAll(List.of(new Node("EPS")));
        List<Rule> rules = getRules(parser.rules());
        Set<Node> allNodes = new HashSet<>(terminalsNodes);
        rules.forEach(r -> allNodes.add(r.left));
        Map<Node, List<Rule>> rulesStartsFromNode = rules
                .stream()
                .collect(Collectors.toMap(
                        Rule::getLeft,
                        rule -> new ArrayList<>(List.of(rule)),
                        (l, v) -> {
                            l.addAll(v);
                            return l;
                        }));
        ItemSet itemSet0 = makeInitialItemSet(rules.get(0), rulesStartsFromNode);
        Map<Pair<ItemSet, Node>, ItemSet> edges = new HashMap<>();
        Set<ItemSet> itemSets = itemSets(itemSet0, allNodes, rulesStartsFromNode, edges);
        for (ItemSet itemSet : itemSets) {
            System.out.println(itemSet);
        }

        Set<Rule> numeratedRules = numeratedRules(itemSets, edges);
        Map<Node, Set<Node>> firstSets = firstSets(new HashSet<>(terminalsNodes), numeratedRules);

        Map<Node, Set<Node>> followSets = followSets(firstSets, numeratedRules, new HashSet<>(terminalsNodes));
        mergeRules(numeratedRules, followSets);


        return new Table();
    }

    public static Map<Rule, Set<Node>> mergeRules(Set<Rule> rules, Map<Node, Set<Node>> followSets) {
        List<List<Rule>> res = new ArrayList<>();
        for (Rule rule : rules) {
            boolean added = false;
            for (List<Rule> group : res) {
                boolean good = true;
                for (Rule tmp : group) {
                    if (tmp.left.name.equals(rule.left.name)) {
                        if (tmp.right.size() == (rule.right.size())) {
                            for (int i = 0; i < rule.right.size(); i++) {
                                if (!rule.right.get(i).name.equals(tmp.right.get(i).name)) {
                                    good = false;
                                    break;
                                }
                            }
                            if (good) {
                                if (((NumeratedNode) tmp.right.get(tmp.right.size() - 1)).to
                                        != ((NumeratedNode) rule.right.get(rule.right.size() - 1)).to) {
                                    added = true;
                                    group.add(rule);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (!added)
                res.add(new ArrayList<>(List.of(rule)));
        }
        List<Rule> merged = res.stream().map(l -> l.get(0)).collect(Collectors.toList());
        return null;
    }

    public static Map<Node, Set<Node>> followSets
            (Map<Node, Set<Node>> firstSets, Set<Rule> rules, Set<Node> terminals) {
        Map<Node, Set<Node>> result = new HashMap<>();
        List<Rule> sorted = rules.stream()
                .sorted(Comparator.comparing(Rule::getInd))
                .collect(Collectors.toList());
        for (Rule rule : rules) {
            result.put(rule.left, new HashSet<>());
        }
        result.get(sorted.get(0).left).add(END);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Rule rule : rules) {
                for (int i = 0; i < rule.right.size(); i++) {
                    if (!terminals.contains(new Node(rule.right.get(i).name))) {
                        Set<Node> cur = result.get(rule.right.get(i));
                        Set<Node> prev = new HashSet<>(cur);
                        if (i + 1 == rule.right.size()) {
                            cur.addAll(result.get(rule.left));
                        } else {
                            cur.addAll(firstSets.get(rule.right.get(i + 1)));
                            cur = cur.stream().filter(r -> !r.name.equals(EPS.name)).collect(Collectors.toSet());
                            if (firstSets.get(rule.right.get(i + 1)).contains(EPS)) {
                                cur.addAll(result.get(rule.left));
                            }
                        }
                        if (!cur.equals(prev))
                            changed = true;
                    }
                }
//                cur.addAll(first(rule.right, result));
            }
        }

        return result;
    }

    public static Map<Node, Set<Node>> firstSets(Set<Node> terminals, Set<Rule> rules) {
        Map<Node, Set<Node>> result = new HashMap<>();
//        result.put(EPS, Set.of(EPS));
        for (Rule rule : rules) {
            result.put(rule.left, new HashSet<>());
            for (Node node : rule.right) {
                if (!terminals.contains(new Node(node.name))) {
                    result.put(node, new HashSet<>());
                } else {
                    result.put(node, new HashSet<>(Set.of(new Node(node.name))));
                }
            }
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Rule rule : rules) {
                Set<Node> cur = result.get(rule.getLeft());
                Set<Node> prev = new HashSet<>(cur);
                cur.addAll(first(rule.right, result));
                if (!cur.equals(prev))
                    changed = true;
            }
        }
        return result;
    }

    public static Set<Node> first(List<Node> alpha, Map<Node, Set<Node>> firstSets) {
        if (alpha.size() == 0) {
            return Set.of(EPS);
        }
        Set<Node> res = new HashSet<>();
        for (Node node : alpha) {
            if (firstSets.get(node).stream().noneMatch(r -> r.name.equals(EPS.name))) {
                res = res.stream().filter(r -> !r.name.equals(EPS.name)).collect(Collectors.toSet());
                res.addAll(firstSets.get(node));
                break;
            } else {
                res.addAll(firstSets.get(node));
            }
        }
        return res;
    }

    public static Set<Rule> numeratedRules(Set<ItemSet> itemSets, Map<Pair<ItemSet, Node>, ItemSet> edges) {
        List<ItemSet> sortedSets = itemSets.stream().sorted(Comparator.comparing(ItemSet::getId)).collect(Collectors.toList());
        int[] ruleId = new int[]{0};
        ExtendedRule startRule = sortedSets.get(0).set.stream()
                .filter(r -> r.ind == 0)
                .findAny()
                .get();
        Node tmp = startRule.right.get(0);
        int to = edges.get(new Pair<>(sortedSets.get(0), tmp)).id;
        Rule rule = new Rule(ruleId[0]++, new NumeratedNode(startRule.left.name, 0, -1), List.of(new NumeratedNode(tmp.name, 0, to)));

        Set<Rule> newRules = new HashSet<>(Set.of(rule));
        for (ItemSet set : sortedSets) {
            List<ExtendedRule> rules = set.set.stream()
                    .filter(r -> r.ind != 0 && r.curDotPos == 0)
                    .collect(Collectors.toList());
            rules.forEach(
                    r -> {
                        NumeratedNode newLeft = new NumeratedNode(r.left.name, set.id, edges.get(new Pair<>(set, r.left)).id);
                        List<Node> newRight = new ArrayList<>();
                        int curInd = 0;
                        ItemSet curSet = set;
                        while (curInd != r.right.size()) {
                            Node curNode = r.right.get(curInd);
                            ItemSet nextSet = edges.get(new Pair<>(curSet, curNode));
                            newRight.add(new NumeratedNode(curNode.name, curSet.id, nextSet.id));
                            curSet = nextSet;
                            curInd++;
                        }
                        newRules.add(new Rule(ruleId[0]++, newLeft, newRight));
                    }
            );

        }
        List<Rule> rules = newRules.stream().sorted(Comparator.comparing(Rule::getInd)).collect(Collectors.toList());
        rules.forEach(System.out::println);
        return newRules;
    }

    public static Set<ItemSet> itemSets(ItemSet itemSet0,
                                        Set<Node> nodes,
                                        Map<Node, List<Rule>> rulesStartsFromNode,
                                        Map<Pair<ItemSet, Node>, ItemSet> edges) {
        Set<ItemSet> itemSets = new HashSet<>(Set.of(itemSet0));
        Set<ItemSet> queue = new HashSet<>(Set.of(itemSet0));
        while (!queue.isEmpty()) {
            Set<ItemSet> newQueue = new HashSet<>();
            for (ItemSet itemSet : queue) {
                for (Node node : nodes) {
                    ItemSet tmp = itemSet.applyNode(node, rulesStartsFromNode);
                    if (tmp.set.size() == 0)
                        continue;
                    if (!itemSets.contains(tmp)) {
                        newQueue.add(tmp);
                        itemSets.add(tmp);
                        tmp.id = ids++;
                        edges.put(new Pair<>(itemSet, node), tmp);
                    } else {
                        ItemSet inSet = itemSets.stream().filter(is -> is.equals(tmp)).findAny().get();
                        if (!edges.containsKey(new Pair<>(itemSet, node))) {
                            edges.put(new Pair<>(itemSet, node), inSet);
                        }
                    }
                }
            }
            queue = newQueue;
        }
        return itemSets;
    }

    public static ItemSet makeInitialItemSet(Rule rule, Map<Node, List<Rule>> rules) {
        ItemSet itemSet = new ItemSet();
        itemSet.addRule(new ExtendedRule(rule));
        ItemSet prev = null;
        while (!itemSet.equals(prev)) {
            prev = itemSet.copy();
            List<ExtendedRule> r = itemSet.rules();
            for (ExtendedRule extendedRule : r) {
                List<Rule> tmp = rules.get(extendedRule.right.get(0));
                if (tmp != null)
                    tmp.forEach(rl -> itemSet.addRule(new ExtendedRule(rl)));
            }
        }
        return itemSet;
    }

    public static List<Rule> getRules(RulesContext ctx) {
        List<Rule> rules = new ArrayList<>();
        StartContext start = ctx.start();
        rules.add(new Rule(0, new Node(
                start.children.get(0).getText()),
                List.of(new Node(start.getChild(2).getText()))));
        for (int i = 0; i < ctx.rul().size(); i++) {
            RulContext rul = ctx.rul(i);
            Node left = new Node(rul.getChild(0).getText());
            List<Node> right = new ArrayList<>();
            for (int j = 2; j < rul.children.size() - 1; j++) {
                right.add(new Node(rul.getChild(j).getText()));
            }
            rules.add(new Rule(i + 1, left, right));
        }
        for (Rule rule : rules) {
            System.out.println(rule);
        }
        return rules;
    }

    public static List<Terminal> getTerminals(TerminalsContext ctx) {
        List<Terminal> terminals = new ArrayList<>();
        List<Terminal_defContext> terminalDefContexts = ctx.terminal_def();
        for (Terminal_defContext terminal : terminalDefContexts) {
            String name = terminal.children.get(0).getText();

            String pattern = terminal.children.get(2).getText();
            pattern = pattern.replaceAll("\\\\\"", "\"");
            pattern = pattern.replaceAll("\\\\\\\\", "\\\\");
            pattern = pattern.substring(1, pattern.length() - 1);

            terminals.add(new Terminal(name, pattern));
            System.out.println(new Terminal(name, pattern));
        }

        return terminals;
    }
}
