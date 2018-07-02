package com.starmcu.study.aio;

public class TimeServer {


    public static void main(String[] args) {
        int port = 8080;


        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }


}
