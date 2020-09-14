package github.veikkoroc.test.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/14 10:35
 */
public class GroupChatServer {
    /**
     * 服务器监听端口
     */
    private int port;

    /**
     * 创建服务器时传入端口
     * @param port
     */
    public GroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 编写Run方法处理客户端请求
     *
     */
    public void run() throws InterruptedException {
        //创建两个线程组,老板和员工
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //服务端启动对象
            ServerBootstrap b = new ServerBootstrap();
            //配置服务器启动参数
            //设置老板和员工线程组
            b.group(bossGroup,workerGroup)
                    //设置服务器通道的实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列等待的线程个数
                    .option(ChannelOption.SO_BACKLOG,128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    //给pipeline设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline添加解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline添加编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己的业务处理Handler
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });

            System.out.println("Netty 服务器已经启动.....");

            //绑定端口并做异步处理
            ChannelFuture channelFuture = b.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



    public static void main(String[] args) {

        try {
            //启动服务器
            new GroupChatServer(7777).run();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
