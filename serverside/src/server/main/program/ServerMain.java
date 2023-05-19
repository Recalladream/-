package server.main.program;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {
    public static void main(String[]args){
        try {
            HashMap<String,Socket> map=new HashMap<String,Socket>();
            HashMap<Socket, ObjectOutputStream>putmap=new HashMap<Socket,ObjectOutputStream>();
            ServerSocket server=new ServerSocket(4000);
            while(true) {
                ServerThread creat=new ServerThread(server.accept(),map,putmap);
                creat.start();
            }
        }catch (Exception e){e.printStackTrace();}
    }
}