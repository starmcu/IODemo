package com.starmcu.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChatRoomServer {


    private int port =8888;

    public ChatRoomServer(int port){
        this.port=port;
    }

    public void start(){

        //创建两个线程组
        EventLoopGroup boss=new NioEventLoopGroup();//负责客户端的连接
        EventLoopGroup work=new NioEventLoopGroup();//负责与已连接的客户端通讯


        ServerBootstrap boot =new ServerBootstrap();

        //配置服务器
        boot.group(boss,work)
                .childHandler(new ChatServerInitialize()) //设置客户端请求的回掉类
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .option(ChannelOption.SO_KEEPALIVE,true);
        System.out.println("服务器已经启动");
        try {
            ChannelFuture future =boot.bind(this.port).sync();


            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();

        }



    }


    public static void main(String[] args) {
        new ChatRoomServer(8888).start();
    }

}
