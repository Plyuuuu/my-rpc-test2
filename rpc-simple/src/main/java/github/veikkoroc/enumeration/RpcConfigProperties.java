package github.veikkoroc.enumeration;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 8:56
 */
public enum RpcConfigProperties {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;


    RpcConfigProperties(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

}