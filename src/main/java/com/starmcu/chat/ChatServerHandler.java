package com.starmcu.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);



    //当有客户端消息写入时间
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel client =ctx.channel();
//      channels.add(client);
        for (Channel ch:channels){
            if (ch!=client){
                ch.writeAndFlush("[用户说：]："+client.remoteAddress()+msg+"\n");
            }else {
                ch.writeAndFlush("我说："+msg+"\n");
            }
        }


    }

    //当监听到客户端活动时
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("["+incoming.remoteAddress()+"]:在线中！\n");


    }


    //当客户端没活动 离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("["+incoming.remoteAddress()+"]:离线中！\n");

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    //当有客户端连接时执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        channels.add(incoming);
        for (Channel ch:channels){
            if (ch!=incoming){
                ch.writeAndFlush("欢迎："+incoming.remoteAddress()+"进入聊天室\n");
            }
        }

    }

    //断开连接的时候执行
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.add(incoming);
        for (Channel ch:channels){
            if (ch!=incoming){
                ch.writeAndFlush("[再见：]："+incoming.remoteAddress()+"离开聊天室\n");
            }
        }
        channels.remove(incoming);
    }



}
