package github.veikkoroc.test.serialize;

import java.io.Serializable;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 8:55
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 202009060859L;

    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
