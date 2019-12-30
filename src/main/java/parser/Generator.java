package parser;


//import example.logic.ParserLogic;

//import example.logic.ParserLogic;

import example.non.ParserNon;

public class Generator {
    public static void main(String[] args) {
        ParserGenerator.generateParser("add.txt", "Add", "example.add");
        ParserGenerator.generateParser("logic.txt", "Logic", "example.logic");
//        ParserGenerator.generateParser("notlalr.txt", "Non", "example.non");
//        System.out.println(ParserNon.parse("aea"));
//        System.out.println(ParserLogic.parse("a|b"));
    }
}
