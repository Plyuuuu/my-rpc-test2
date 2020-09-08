package github.veikkoroc.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 16:25
 */
@Slf4j
public class Demo01 {
        public static void main(String[] args) {
            System.out.println(Test2.a);
        }
}
 class Test2{
    public static final String a="JD";
        static {
            System.out.print("OK");
        }
}
