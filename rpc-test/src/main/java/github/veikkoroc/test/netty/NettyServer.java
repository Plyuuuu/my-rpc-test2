package github.veikkoroc.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 15:07
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1、创建BoosGroup和WorkerGroup
        //说明
        //  1. 创建两个线程组 bossGroup 和 workerGroup
        //  2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //  3. 两个都是无限循环
        //  4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        //   默认实际 cpu核数 * 2 (8)
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup =  new NioEventLoopGroup(1);//设置线程数为1
        try{
            //2、创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程设置
            serverBootstrap.group(workerGroup,bossGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象(匿名对象)
                        @Override//给pipeline 设置处理器
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info("客户的 SocketChannel HashCode = [{}]",socketChannel.hashCode());
                            //可以用集合管理 SocketChannel ，再推送消息时，可以将业务加入到各个Channel对应的NioEventGroup
                            //的TaskQueue 或者 scheduleTaskQueue

                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });// 给我们的workerGroup 的 EventLoop 对应的管道设置处理器

            System.out.println(".....服务器 is ready...");

            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            //3、启动服务器(并绑定端口)
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            //给channelFuture注册监听器，监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        log.info("监听端口 [6668] 成功");
                    }else {
                        log.info("监听端口 [6668] 失败");
                    }
                }
            });


            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

}
