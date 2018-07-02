package com.starmcu.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServer implements Runnable {

    private void accpet(SelectionKey key) {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        try {
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void read(SelectionKey key) {

        try {
            //清空缓冲区
            buffer.clear();
            //获取通道
            SocketChannel sc = (SocketChannel) key.channel();
            //将通道中获取的数据写入缓冲区
            int count = sc.read(buffer);

            if (count==-1){
                //没有读到数据
                key.channel().close();
                key.channel();
                return;
            }

            buffer.flip();//数据复位一下
            byte[] bytes=new byte[buffer.remaining()];
            buffer.get(bytes);
            String msg =new String(bytes).trim();
            System.out.println("客户端说："+msg);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                //轮寻所有已经注册通道
                selector.select();
                //收货所有已经注册的key值。
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {

                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) {

                        //如果就绪状态
                        if (key.isAcceptable()) {
                            accpet(key);

                        }

                        if (key.isReadable()) {
                            read(key);
                        }


                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private Selector selector = null;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NIOServer(int port) {
        //打开Selector
        try {
            selector = Selector.open();
            //打开channel
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置通道为非阻塞式。
            ssc.configureBlocking(false);
            //绑定端口
            ssc.bind(new InetSocketAddress(port));
            //注册通道
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器已经启动");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {


//自动轮寻线程
        new Thread(new NIOServer(8888)).start();


    }


}
