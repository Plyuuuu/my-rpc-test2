package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.remote.transport.netty.server.NettyServer;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 16:32
 */
public class ChannelProvider {
    private final Map<String, Channel> channelMap;
    private final NettyClient nettyClient;
    public ChannelProvider() {
        channelMap = new ConcurrentHashMap<>();
        nettyClient = SingletonFactory.getInstance(NettyClient.class);
    }

    /**
     * 获得通道
     * @return
     */
    public Channel getChannel(InetSocketAddress inetSocketAddress){
        String key = inetSocketAddress.toString();
        //确定相应地址是否存在连接
        if (channelMap.containsKey(key)){
            Channel channel = channelMap.get(key);
            //如果可以，请确定连接是否可用，如果可以，请直接获取连接
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }
        //否则，请重新连接以获取频道
        Channel channel = nettyClient.doConnect(inetSocketAddress);
        channelMap.put(key, channel);
        return null;
    }
}
