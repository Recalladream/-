package user.chat;
import utils.InforMationSet;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserInfor extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame chatroom;
    String jlzh;
    String hyzh;
    JButton hypho;
    JButton xsum;
    JButton xszt;
    Box zsbox;
    Box hygdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;


    public UserInfor(JFrame chatroom, String jlzh, String hyzh, JButton hypho, JButton xsum, JButton xszt,Box zsbox,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp){
        this.chatroom=chatroom;
        this.jlzh=jlzh;
        this.hyzh=hyzh;
        this.hypho=hypho;
        this.xsum=xsum;
        this.xszt=xszt;
        this.zsbox=zsbox;
        this.hygdbox=hygdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;


        initSocket();
        initact();
    }

    private void initSocket(){
        try {
            server=new Socket("127.0.0.1",4000);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());

            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Infor");
            putinfjh.setAccount(jlzh);
            putinfjh.setChatobject(hyzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            InforMationSet cputinfjh = new InforMationSet();
            cputinfjh.setFunction("Close_infor");
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
                if (function.equals("Close")){
                    server.close();
                    break;
                }
                if (function.equals("image")){
                    ImageIcon imageic=getinfjh.getHeadPicture();
                    hypho.setText(null);
                    hypho.setBorderPainted(false);
                    hypho.setIcon(imageic);
                }
                if (function.equals("name")){
                    String nickname=getinfjh.getNickname();
                    xsum.setText(nickname);
                }
                if (function.equals("off-line")){
                    String state=getinfjh.getState();
                    if (state.equals("0"))
                    xszt.setText("离线");
                    else
                    xszt.setText("在线");
                }
                if (function.equals("Delete_friends")){
                    hygdbox.remove(zsbox);
                    chatjp.removeAll();
                    ltfuncpan.removeAll();
                    inftexjp.removeAll();
                    fsjp.removeAll();
                    funcjp.removeAll();
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }

}