package com.starmcu.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {

    private String host;
    private int port;

    public ChatClient(String host, int port) {

        this.host = host;
        this.port=port;
    }


    public void start() {
        EventLoopGroup worker = new NioEventLoopGroup();//负责与已连接的客户端通讯
        Bootstrap boot = new Bootstrap();
        try {
            boot.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitialize());
            //连接服务器
            Channel channel = boot.connect(host, port).sync().channel();

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                channel.writeAndFlush(input.readLine() + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new ChatClient("127.0.0.1",8888).start();

    }
}
