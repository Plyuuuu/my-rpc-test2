package github.veikkoroc.test.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/14 17:15
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx   上下文
     * @param evt   事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
       if (evt instanceof IdleStateEvent){
           //将evt向下转型
           IdleStateEvent event = (IdleStateEvent) evt;
           String evenType = null;
           switch (event.state()){
               case READER_IDLE:
                   evenType = "服务器读空闲";
                   break;
               case WRITER_IDLE:
                   evenType = "服务器写空闲";
                   break;
               case ALL_IDLE:
                   evenType = "服务器读写空闲";
                   break;
           }
           System.out.println(ctx.channel().remoteAddress()+"---超时时间---"+evenType);
           System.out.println("服务器坐了相应处理");


       }
    }
}
