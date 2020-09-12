package github.veikkoroc.remoterpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 18:23
 */
public class RpcRequest implements Serializable{

    private static final long serialVersionUID = 202009101856L;
    private String className;
    private String methodName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
