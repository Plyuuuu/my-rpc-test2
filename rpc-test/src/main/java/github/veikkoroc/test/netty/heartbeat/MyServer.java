package github.veikkoroc.test.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/14 16:37
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //在bossGroup中添加日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //加入一个 Netty 提供的 IdleStateHandler
                            /*
                                说明：
                                    1、IdleStateHandler 是Netty提供的处理空闲状态的处理器
                                    2、long readerIdleTime, 表示服务器多长时间  没有读 ，就会发送一个心跳检测包检测是否连接
                                    3、long writerIdleTime,表示服务器多长时间  没有写  ，就会发送一个心跳检测包检测是否连接
                                    4、long allIdleTime,表示服务器多长时间  没有读写  ，就会发送一个心跳检测包检测是否连接

                                    5、当IdleStateEvent 触发后，就会传递给管道的下一个handler处理
                                        通过调用（触发）下一个handler 的 userEventTiggered，在该方法中处理 IdleStateEvent（读空闲、写空闲、读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            //加入一个空闲检测进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            //绑定端口，且异步处理
            ChannelFuture future = bootstrap.bind(7777).sync();
            //监听通道关闭事件
            future.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
