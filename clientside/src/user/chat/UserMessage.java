package user.chat;
import utils.InforMationSet;
import utils.OutputControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserMessage extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame chatroom;
    JTextPane chatar;
    String jlzh;
    String hyzh;
    JButton xsum;
    JButton hypho;

    public UserMessage(JFrame chatroom, JTextPane chatar, String jlzh, String hyzh,JButton xsum,JButton hypho){
        this.chatroom=chatroom;
        this.chatar=chatar;
        this.jlzh=jlzh;
        this.hyzh=hyzh;
        this.xsum=xsum;
        this.hypho=hypho;

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
            putinfjh.setChatobject(hyzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            InforMationSet cputinfjh = new InforMationSet();
            cputinfjh.setFunction("Close_chat");
            cputinfjh.setAccount(jlzh);
            cputinfjh.setChatobject(hyzh);

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
        while (true){
            try {
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String function=getinfjh.getFunction();
                String sf=getinfjh.getCode();
                ImageIcon icon=getinfjh.getMaxHeadPicture();

                if (function!=null)
                if (function.equals("Close")){
                    server.close();
                    break;
                }

                if (sf.equals("0")){
                    if (icon!=null) {
                        OutputControl.writePho(getinfjh.getNickname(),icon,0,chatar,hypho.getIcon());
                    }else
                        OutputControl.write(getinfjh.getNickname(),getinfjh.getContent(),0,chatar,hypho.getIcon());
                }
                if (sf.equals("1"))
                OutputControl.write(null,getinfjh.getContent(),1,chatar,null);

                xsum.setForeground(Color.red);
            }catch (Exception e){e.printStackTrace();}
        }
    }
}