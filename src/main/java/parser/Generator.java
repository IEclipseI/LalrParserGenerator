package parser;


//import example.logic.ParserLogic;

//import example.logic.ParserLogic;

public class Generator {
    public static void main(String[] args) {
        ParserGenerator.generateParser("add.txt", "Add", "example.add");
        ParserGenerator.generateParser("logic.txt", "Logic", "example.logic");
//        System.out.println(ParserLogic.parse("a|b"));
    }
}
