package com.starmcu.study.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {



    private  static int PORT =8888;
    private  static String IP ="127.0.0.1";


    public static void main(String[] args) {

        BufferedReader br =null;
        PrintWriter pw =null;
        Socket socket =null;
        BufferedReader input =null;

        try {
            //连接服务器端;
            socket =new Socket(IP,PORT);
            pw =new PrintWriter(socket.getOutputStream(),true);
            br =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input =new BufferedReader(new InputStreamReader(System.in));

            pw.println(input.readLine());
            //不是马上就是执行。

            System.out.println("发送完毕！");
            String res =br.readLine();

            System.out.println("读取成功！"+res);
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
            if (input!=null){
                try {
                    input.close();
                } catch (Exception e) {
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


