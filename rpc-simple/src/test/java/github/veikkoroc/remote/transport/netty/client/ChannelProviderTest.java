package github.veikkoroc.remote.transport.netty.client;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/15 10:29
 */
public class ChannelProviderTest {
    public static void main(String[] args) {
        ChannelProvider channelProvider = new ChannelProvider();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",9998);
        Channel channel = channelProvider.getChannel(socketAddress);
        System.out.println(channel);
    }
}
