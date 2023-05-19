package group.chat;

import utils.InforMationSet;
import utils.OutputControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class GroupMessage extends Thread{
    int threadout=0;
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame chatroom;
    JTextPane chatar;
    String jlzh;
    String qunid;
    JButton xsqmbut;
    JLabel headpicture;

    public GroupMessage(JFrame chatroom, JTextPane chatar, String jlzh, String qunid,JButton xsqmbut,JLabel headpicture){
        this.chatroom=chatroom;
        this.chatar=chatar;
        this.jlzh=jlzh;
        this.qunid=qunid;
        this.xsqmbut=xsqmbut;
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
            putinfjh.setFunction("Chat");
            putinfjh.setAccount(jlzh);
            putinfjh.setChatobject(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            InforMationSet putinfjh = new InforMationSet();
            putinfjh.setFunction("Close_chat");
            putinfjh.setAccount(jlzh);
            putinfjh.setChatobject(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void initact(){
        chatroom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    threadout=1;
                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    threadout=1;
                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    public void run(){
        while (true){
            try {
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String function=getinfjh.getFunction();
                ImageIcon iconpho=getinfjh.getMaxHeadPicture();
                String sf=getinfjh.getCode();

                if (function!=null)
                if (function.equals("Close")){
                    server.close();
                    break;
                }

                if (sf.equals("0")) {
                    ImageIcon icon=getinfjh.getHeadPicture();

                    if (iconpho!=null){
                        OutputControl.writePho(getinfjh.getNickname(),iconpho,0,chatar,icon);
                    }else
                        OutputControl.write(getinfjh.getNickname(),getinfjh.getContent(),0,chatar,icon);
                }
                if (sf.equals("1"))
                    OutputControl.write(null,getinfjh.getContent(),1,chatar,null);

                xsqmbut.setForeground(Color.red);
            } catch (Exception e){e.printStackTrace();}
        }
    }
}