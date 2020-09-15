package github.veikkoroc.remote.transport.netty.server;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/15 10:27
 */
public class NettyServerTest {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.startNettyServer();
    }
}
