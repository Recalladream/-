package main.iterface;

import user.chat.FriendThread;
import utils.InforMationSet;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FriendListening extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    Socket zserver;
    JFrame chatroom;
    Box hygdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    String jlzh;
    JButton jlum;
    JLabel headpicture;

    public FriendListening(Socket zserver,JFrame chatroom,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp,String jlzh,JButton jlum, JLabel headpicture){
        this.zserver=zserver;
        this.chatroom=chatroom;
        this.hygdbox=hygdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;
        this.jlzh=jlzh;
        this.jlum=jlum;
        this.headpicture=headpicture;

        initSocket();
        initact();
    }

    private void initSocket(){
        try {
            server=new Socket("127.0.0.1",4000);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());

            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Monitor");
            putinfjh.setAccount(jlzh);
            putinfjh.setChatobject("friend");

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            InforMationSet cputinfjh = new InforMationSet();
            cputinfjh.setFunction("Close_monitor");
            cputinfjh.setAccount(jlzh);
            cputinfjh.setChatobject("friend");

            obputFW.writeObject(cputinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void initact(){
        chatroom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    public void run(){
        while (true) {
            try {
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String function=getinfjh.getFunction();

                if (function!=null)
                if (function.equals("Close")){
                    server.close();
                    break;
                }

                String hyzh=getinfjh.getAccount();
                String hyum=getinfjh.getNickname();
                ImageIcon imageic=getinfjh.getHeadPicture();
                String offline=getinfjh.getOffline();

                FriendThread creat = new FriendThread(zserver,chatroom, hygdbox, chatjp,ltfuncpan ,inftexjp, fsjp, funcjp,jlzh,jlum, hyzh, hyum,imageic,offline,headpicture);
                creat.start();
            }catch (Exception e){e.printStackTrace();}
        }
    }
}