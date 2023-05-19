package user.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CreateFriend extends Thread{
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

    public CreateFriend(Socket zserver,JFrame chatroom,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp,String jlzh,JButton jlum, JLabel headpicture){
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
            putinfjh.setFunction("Show_friends");
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true) {
                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                String state=getinfjh.getState();

                if (state.equals("1")) {
                    String hyzh=getinfjh.getAccount();
                    String hyum=getinfjh.getNickname();
                    ImageIcon imageic=getinfjh.getHeadPicture();
                    String offline=getinfjh.getOffline();

                    FriendThread creat = new FriendThread(zserver,chatroom, hygdbox, chatjp,ltfuncpan ,inftexjp, fsjp, funcjp,jlzh,jlum, hyzh, hyum,imageic,offline,headpicture);
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