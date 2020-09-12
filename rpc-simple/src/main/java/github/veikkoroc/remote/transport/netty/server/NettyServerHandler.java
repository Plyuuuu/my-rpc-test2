package github.veikkoroc.remote.transport.netty.server;

import github.veikkoroc.enumeration.RpcMessageType;
import github.veikkoroc.enumeration.RpcResponseCode;
import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;
import github.veikkoroc.remote.handler.RpcRequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定制服务器的ChannelHandler以处理客户端发送的数据
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/11 16:21
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final RpcRequestHandler rpcRequestHandler;

    public NettyServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }
    /**
     * 读取数据（读取客户端发送的数据）
     * 1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
     * 2. Object msg: 就是客户端发送的数据 默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try{
            log.info("NettyServer 接收的信息 [{}]",msg);
            RpcRequest rpcRequest = (RpcRequest) msg;
            log.info("NettyServer 接收的信息 [{}]",rpcRequest);

            //如果接收到心跳信息,直接return
            if (rpcRequest.getRpcMessageType()== RpcMessageType.HEART_BEAT){
                log.info("接收到客户端的心跳信息");
                return;
            }
            //执行目标方法（客户端需要执行的方法）并返回方法结果
            Object result = rpcRequestHandler.handle(rpcRequest);
            log.info(String.format("从服务器获得的结果: %s", result.toString()));

            if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                RpcResponse<Object> rpcResponse = RpcResponse.success(result, rpcRequest.getRequestId());
                ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                RpcResponse<Object> rpcResponse = RpcResponse.fail(RpcResponseCode.FAIL);
                ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                log.error("现在无法写，消息已删除");
            }


        }finally{
            //确保已释放ByteBuf，否则可能会发生内存泄漏
            ReferenceCountUtil.release(msg);
        }


    }

    @Override//出现异常
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("NettyServer 处理客户端访问出现异常");
        cause.printStackTrace();
        ctx.close();
    }
    @Override//触发用户事件
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("发生空闲检查，因此关闭连接");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
