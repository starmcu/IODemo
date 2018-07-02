package com.starmcu.study.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOServerHandler implements Runnable{

    private Socket socket;

    public  BIOServerHandler(Socket s){
        this.socket=s;
    }


    public void run() {
                    BufferedReader br= null;
                    PrintWriter pw=null;//JAVA和JAVA间字符流传输
                    try {
                        br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        pw =new PrintWriter(socket.getOutputStream(),true);
                        //从客户端读入数据
                        String word =br.readLine();
                        System.out.println("客户端发送的消息："+word);

                        pw.println("你好客户端!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (br!=null){
                            try {
                                br.close();
                            } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (pw!=null){
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}

