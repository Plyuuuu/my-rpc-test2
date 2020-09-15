package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.remote.transport.netty.server.NettyServer;
import io.netty.channel.Channel;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 16:32
 */
public class ChannelProvider {
    /**
     * 存放通道
     */
    private final Map<String, Channel> channelMap;
    private final NettyClient nettyClient;
    public ChannelProvider() {
        channelMap = new ConcurrentHashMap<>();
        nettyClient = SingletonFactory.getInstance(NettyClient.class);
    }

    /**
     * 获得与服务器通信的通道
     * @return 通道
     */
    public Channel getChannel(InetSocketAddress inetSocketAddress){
        //key = "/127.0.0.1:6668"
        String key = inetSocketAddress.toString();
        //确定相应地址是否存在连接
        if (channelMap.containsKey(key)){
            Channel channel = channelMap.get(key);
            //通道可以用就直接返回，否则移除掉
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }
        //Map中没有就重新获取缓存
        Channel channel = nettyClient.doConnect(inetSocketAddress);
        channelMap.put(key, channel);
        return channel;
    }

}
