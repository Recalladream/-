package server.main.program;

import com.sun.org.apache.bcel.internal.generic.Select;
import conrtroller.InformationClassification;
import dao.DataBase;
import utils.InforMationSet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread{
    HashMap<String,Socket>map;
    HashMap<Socket,ObjectOutputStream>putmap;
    Socket KH;
    ObjectInputStream obgetkh;
    ObjectOutputStream obputkh;

    public void initSocket(){
        try {
            obgetkh = new ObjectInputStream(KH.getInputStream());
            obputkh = new ObjectOutputStream(KH.getOutputStream());
            System.out.println("正常连接");
        }catch (Exception e){e.printStackTrace();}
    }

    public ServerThread(Socket KH, HashMap<String,Socket> map, HashMap<Socket,ObjectOutputStream>putmap){
        try {
            this.KH=KH;
            this.map=map;
            this.putmap=putmap;
            initSocket();
        }catch (Exception e){e.printStackTrace();}
    }

    public void run(){
        while (true){
            try {
                if (KH.isClosed()){
                    System.out.println("正常断开");
                    break;
                }
                else {
                    InforMationSet getinfjh = (InforMationSet) obgetkh.readObject();
                    InformationClassification inffl = new InformationClassification(getinfjh,KH,obputkh,map,putmap);
                    inffl.functionalresolution();
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}