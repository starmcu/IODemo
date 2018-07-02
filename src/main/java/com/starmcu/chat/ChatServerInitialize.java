package com.starmcu.chat;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class ChatServerInitialize extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        //当有客户端连接服务器时，NEetty会自动调用这个初始化器的InitChannel方法out
        System.out.println(System.currentTimeMillis() + ":当前有客户接入!" + channel.remoteAddress());


        ChannelPipeline pipeline = channel.pipeline();


        /*
            管道中发送的数据最终都是010101010101010无缝的流动
            所以在数据量大的时候，我们需要将数据分帧
          1.定长
          2.使用固定的分隔符
          3.将消息分为消息头和消息体两部分，在消息头中用一个数据说明消息体的长度
          4.或者其他复杂的协议

         */

        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringEncoder());
        pipeline.addLast("handler", new ChatServerHandler());



    }
}
