package github.veikkoroc.test.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/14 11:09
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler {

    /**
     * 定义一个channel组，管理所有与客户端连接的通道
     *     GlobalEventExecutor.INSTANCE 是全局的时间执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    /**
     * 读取数据
     * @param ctx
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        //获得当前的channel
        Channel channel = ctx.channel();
        //这时候我们遍历channelGroup,根据不同的情况，回不同的消息

        channelGroup.forEach(ch -> {
            if (channel != ch){
                //转发消息
                ch.writeAndFlush("[客户]"+channel.remoteAddress()+" 发送了消息：" + o +"\n");
            }else{
                //回显消息
                ch.writeAndFlush("[我]发送了消息:"+o+"\n");
            }
        });

    }

    /**
     * handlerAdded 表示连接建立，一旦连接，第一个被执行
     * 将当前channel 加入到 channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线客户端
        //该方法会将channelGroup中所有的channel遍历，并发送消息，不需要自己遍历
        channelGroup.writeAndFlush("[客户端:]"+channel.remoteAddress()+" 加入聊天\n"+sdf.format(new Date())+"\n");
        channelGroup.add(channel);
        System.out.println("当前群人数："+channelGroup.size());
    }

    /**
     * 断开连接，将xxx客户离开消息推送给当前在线用户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端:]"+channel.remoteAddress()+" 退出群聊\n");
        System.out.println("当前群人数："+channelGroup.size());
    }

    /**
     * 表示channel处于活动的状态，提示xxx上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    /**
     * 表示channel 处于不活动状态，提示 xx下线了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了-");
    }

    /**
     * 发送异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭
        ctx.close();
    }


}
