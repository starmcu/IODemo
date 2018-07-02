package com.starmcu.study.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer2 {

    private static int PORT = 8888;

    public static void main(String[] args) {
        ServerSocket ss = null;




        try {
            ss = new ServerSocket(PORT);
            System.out.println("服务端2启动");
            HandlerExecutorPool pool =new HandlerExecutorPool(50,1000);

            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Socket socket = ss.accept();
                System.out.println("客户端进来了");

                pool.executor(new BIOServerHandler(socket));
                //     new Thread(new BIOServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            ss = null;
        }


    }

}
