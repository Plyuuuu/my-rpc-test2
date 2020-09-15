package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;
import github.veikkoroc.remote.transport.netty.codec.KryoDecoder;
import github.veikkoroc.remote.transport.netty.codec.KryoEncoder;
import github.veikkoroc.remote.transport.netty.server.NettyServerHandler;
import github.veikkoroc.serialize.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 16:58
 */
@Slf4j
public class NettyClient {
    /**
     * 客户端启动对象
     */
    private final Bootstrap bootstrap  = new Bootstrap();
    /**
     * 事件线程组
     */
    EventLoopGroup eventLoopGroup;

    /**
     * 初始化资源
     */

    public NettyClient() {
       start();
    }
    @Test
    public void start(){

        this.eventLoopGroup = new NioEventLoopGroup();
        KryoSerializer kryoSerializer = new KryoSerializer();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //如果15秒钟内没有数据发送到服务器，则发送心跳请求
                        channel.pipeline().addLast(new IdleStateHandler(0,5,0, TimeUnit.SECONDS));
                        //配置自定义序列化编解码器
                        channel.pipeline().addLast(new KryoEncoder(kryoSerializer, RpcRequest.class));
                        channel.pipeline().addLast(new KryoDecoder(kryoSerializer, RpcResponse.class));
                        channel.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }
    public void close(){
        eventLoopGroup.shutdownGracefully();
    }

    /**
     * 连接服务器并获取频道，以便向服务器发送rpc消息
     * @param inetSocketAddress
     * @return
     */
    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress){
        log.info("==========客户端将要连接:[{}]",inetSocketAddress);
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        log.info("==========ip:[{}] port:[{}]",inetSocketAddress.getAddress(),inetSocketAddress.getPort());
        //连接到服务器
        ChannelFuture future = bootstrap.connect(inetSocketAddress).sync();
        /*bootstrap.connect(inetSocketAddress.getAddress(),inetSocketAddress.getPort()).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("==========客户端连接[{}]成功",inetSocketAddress.toString());
                    completableFuture.complete(channelFuture.channel());
                } else {
                    throw new IllegalStateException("==========客户端连接["+inetSocketAddress.toString()+"失败");
                }
            }
        });*/
        return future.channel();

    }

}
