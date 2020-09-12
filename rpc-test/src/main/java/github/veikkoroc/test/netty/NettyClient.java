package github.veikkoroc.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 16:06
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个时间循环组
        NioEventLoopGroup group = new NioEventLoopGroup();

        try{
            //客户端启动对象
            //客户端使用BootStrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//加入自定义处理器
                        }
                    });
            System.out.println("-----Client is OK-----");

            //启动客户端去连接服务器
            //ChannelFuture后面还要分析，设计Netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            //监听通道的关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
