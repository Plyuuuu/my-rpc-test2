package github.veikkoroc.test.dubboRPC.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 11:13
 */
public class NettyServer {

    /**
     *  编写一个方法，完成对NettyServer 的初始化和启动
     */
    private static void startServer0(String hostname,int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
