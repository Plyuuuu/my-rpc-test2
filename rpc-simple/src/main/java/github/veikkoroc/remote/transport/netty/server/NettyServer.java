package github.veikkoroc.remote.transport.netty.server;

import github.veikkoroc.config.CustomShutdownHook;
import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.provider.impl.ServiceProviderImpl;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.codec.KryoDecoder;
import github.veikkoroc.remote.transport.netty.codec.KryoEncoder;
import github.veikkoroc.serialize.KryoSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/11 16:20
 */
@Slf4j
@Component//把NettyServer实例化到IOC
public class NettyServer implements InitializingBean {
    private final KryoSerializer kryoSerializer = new KryoSerializer();
    //NettyServer的端口9998
    public static final int port = 9998;
    //服务端的Ip地址
    public static String host = "127.0.0.1";//InetAddress.getLocalHost().getHostAddress();
    private ServiceProvider serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);

    //注册服务====>服务对象   当前服务的属性
    public void registerService(Object service, RpcServiceProperties rpcServiceProperties){
        serviceProvider.publishService(service,rpcServiceProperties);
    }

    //启动NettyServer服务器
    @SneakyThrows//不用担心抛出的异常
    @Test
    public void startNettyServer(){
        //System.out.println(InetAddress.getLocalHost().getHostAddress());

        //两个线程组
        EventLoopGroup bossGroup =  new NioEventLoopGroup();//设置线程数为1

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程给服务器启动对象配置
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel 作为服务器的通道实现
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据块，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG,128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 当客户端第一次进行请求的时候才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            // 30 秒之内没有收到客户端请求的话就关闭连接
                            ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast("KryoDecoder",new KryoDecoder(kryoSerializer, RpcRequest.class));
                            ch.pipeline().addLast("KryoEncoder",new KryoEncoder(kryoSerializer, RpcResponse.class));
                            ch.pipeline().addLast(new NettyServerHandler());

                        }
                    }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器
            log.info("------服务器 Is Ready------");

            //3、启动服务器(并绑定端口)
            ChannelFuture channelFuture = serverBootstrap.bind(host,port).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();


        } catch (Exception e) {
            log.error("==========启动NettyServer时发生异常[{}]",e.getMessage());
        } finally {
            log.info("==========关闭 workerGroup、bossGroup ");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }



    /**
     * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
     *
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        CustomShutdownHook.getCustomShutdownHook().clearAll();
    }
}
