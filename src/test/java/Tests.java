import example.add.ParserAdd;
//import example.logic.ParserLogic;
//import example.logic.ParserLogic;
import org.junit.Assert;
import org.junit.Test;

public class Tests {
    public static void main(String[] args) {

    }

    @Test
    public void t1() {
        Assert.assertEquals(1, (int)ParserAdd.parse("1"));
        Assert.assertEquals(1000_000, (int)ParserAdd.parse("1000000"));
        Assert.assertEquals(120, (int)ParserAdd.parse("1*2*3*4*5"));
        Assert.assertEquals(-120, (int)ParserAdd.parse("1*2*3*4*5-1*2*3*4*5-1*2*3*4*5"));
        Assert.assertEquals(0, (int)ParserAdd.parse("1*2*3*4*5-1*2*3*4*5+1*2*3*4*5-1*2*3*4*5"));
        Assert.assertEquals(1, (int)ParserAdd.parse("(1)"));
        Assert.assertEquals(1, (int)ParserAdd.parse("((((((1))))))"));
        Assert.assertEquals(200, (int)ParserAdd.parse("(0-100)*(1-3)"));
        Assert.assertEquals(-200, (int)ParserAdd.parse("(0-100)*(3-1)"));
        Assert.assertEquals(-13, (int)ParserAdd.parse("1-2-3-4-5"));
        Assert.assertEquals(-21, (int)ParserAdd.parse("1-2-3*2-4-5*2"));
        Assert.assertEquals(8, (int)ParserAdd.parse("2^3"));
        Assert.assertEquals(256, (int)ParserAdd.parse("2^2^3"));
    }
    @Test
    public void t2() {
//        TreeVisualizator.show(ParserLogic.parse("a|a|a|a"));
//        TreeVisualizator.show(ParserLogic.parse("a|a&b|a|a"));
//        TreeVisualizator.show(ParserLogic.parse("(!a|b)&a&(a|!(b^c))"));
//        TreeVisualizator.show(ParserLogic.parse("a|a&b|!(a|a)|(c^d^b)&e^w"));
    }
}
