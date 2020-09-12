package github.veikkoroc.inherit;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 8:15
 */
public class Father {
    static {
        System.out.println("Father Static {}");
    }
    {
        System.out.println("Father {}");
    }

    public Father() {
        System.out.println("Father constructor");
    }

    public static void main(String[] args) {
        Father father = new Father();
    }
}
