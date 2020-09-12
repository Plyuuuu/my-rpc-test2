package github.veikkoroc.remoterpc;

import java.io.Serializable;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 17:56
 */
public class User implements Serializable {
    private static final long serialVersionID = 202009101758L;
     private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
