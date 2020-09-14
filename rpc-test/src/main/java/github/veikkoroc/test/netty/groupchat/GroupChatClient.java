package github.veikkoroc.test.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/14 15:46
 */
public class GroupChatClient {
    /**
     * 属性：IP 和 端口
     */
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {
        //客户端需要一个事件循环中组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            //客户端启动对象
            Bootstrap b = new Bootstrap();
            //配置参数
            b.group(eventLoopGroup)
                    //设置客户端通道实现类
                    .channel(NioSocketChannel.class)
                    //设置处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //获得pipeline
                            ChannelPipeline pipeline = channel.pipeline();
                            //加入相关Handler、编码解码器
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast("decoder",new StringDecoder());
                            //加入自定义Handler
                            pipeline.addLast(new GroupChatClientHandler());

                        }
                    });
            //连接到服务器
            ChannelFuture future = b.connect(host, port).sync();
            //获得channel
            Channel channel = future.channel();
            System.out.println("--------------------"+channel.localAddress()+"--------------------");
            //客户端输入
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String message = scanner.nextLine();
                //通过channel，发送到服务器端
                channel.writeAndFlush(message+"\r\n");
            }
        }finally {
            eventLoopGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1",7777).run();
    }
}
