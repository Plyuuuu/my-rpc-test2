package github.veikkoroc.test.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 9:08
 */
@Slf4j
public class TestServer {
    public static void main(String[] args) {
        //创建两个线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //创建服务端启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //配置启动参数
            serverBootstrap.group(boosGroup,workerGroup).channel(NioServerSocketChannel.class)
                            .childHandler(new TestServerInitializer());
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(6669).sync();

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        }catch(Exception e){
            log.error(e.getMessage());
        }finally{
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
