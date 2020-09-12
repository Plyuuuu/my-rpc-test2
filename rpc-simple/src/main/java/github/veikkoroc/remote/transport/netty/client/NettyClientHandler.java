package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.enumeration.RpcMessageType;
import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 自定义客户端ChannelHandler以处理服务器发送的数据
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 17:09
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private final UnProcessedRequests unProcessedRequests;
    private final ChannelProvider channelProvider;

    public NettyClientHandler() {
        this.unProcessedRequests = SingletonFactory.getInstance(UnProcessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    /**
     * 阅读服务器发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       try{
        log.info("==========客户端接收到消息：[{}]",msg);
        if (msg instanceof RpcResponse){
            RpcResponse<Object> rpcResponse = (RpcResponse<Object>) msg;
            unProcessedRequests.complete(rpcResponse);
        }
       }finally {
           ReferenceCountUtil.release(msg);
       }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent)evt).state();
            if (state == IdleState.WRITER_IDLE){
                log.info("==========客户端写数据发生闲置[{}]",ctx.channel().remoteAddress());
                Channel channel = channelProvider.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                RpcRequest rpcRequest = RpcRequest.builder().rpcMessageType(RpcMessageType.HEART_BEAT).build();
                channel.writeAndFlush(rpcRequest).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("==========客户端发生异常：",cause);
        cause.printStackTrace();
        ctx.close();
    }


}
