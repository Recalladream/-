package group.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class CreateGroup extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    Socket zserver;
    JFrame chatroom;
    Box qungdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    String jlzh;
    JButton jlum;
    JLabel headpicture;

    public CreateGroup(Socket zserver,JFrame chatroom, Box qungdbox, JPanel chatjp, JPanel ltfuncpan, JPanel inftexjp, JPanel fsjp, JPanel funcjp, String jlzh,JButton jlum, JLabel headpicture){
        this.zserver=zserver;
        this.chatroom=chatroom;
        this.qungdbox=qungdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;
        this.jlzh=jlzh;
        this.jlum=jlum;
        this.headpicture=headpicture;

        initSocket();
    }

    private void initSocket(){
        try {
            server=new Socket("127.0.0.1",4000);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void run(){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Show_group");
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true) {
                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                String state=getinfjh.getState();

                if (state.equals("1")) {
                    String qunm=getinfjh.getNickname();
                    String qunid=getinfjh.getGroupid();
                    String memberjb=getinfjh.getMemberlevel();
                    ImageIcon ximageic=getinfjh.getHeadPicture();

                    GroupThread creat = new GroupThread(zserver,qunm,qunid,memberjb,chatroom, qungdbox, chatjp,ltfuncpan,inftexjp, fsjp, funcjp,jlzh,jlum,ximageic,headpicture);
                    creat.start();
                }

                if (state.equals("2")){
                    closeSocket();
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
}