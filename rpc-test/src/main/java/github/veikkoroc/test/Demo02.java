package github.veikkoroc.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/5 16:45
 */
public class Demo02 {
    public static void main(String[] args) {
        System.out.println(1);
        HashMap h = new HashMap();
        ArrayList arrayList = new ArrayList();
        new String();

    }
    @Test
    public void test01(){
        Demo02 demo02 = new Demo02();
        //328638398
        System.out.println(demo02.hashCode());
        Demo02 demo021 = new Demo02();
        System.out.println(demo021.hashCode());
    }
    @Test
    public  void test02(){
        int y = 10;
        int x = 10;
        x--;
        System.out.printf("%d,%d",--y,x--);
    }
}
