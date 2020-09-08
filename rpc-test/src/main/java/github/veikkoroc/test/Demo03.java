package github.veikkoroc.test;

import org.junit.Test;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/7 15:26
 */
public class Demo03 {
    static {
        int x = 5;

    }
    static int x,y;

    public static void main(String[] args) {
        x--;
        System.out.println(x);
        System.out.println(y);
        myMethod();
        System.out.println(x);
        System.out.println(y);
        System.out.println(x+y+ ++x);
    }
    public static void myMethod(){
        y = x++ + ++x;
    }

}
