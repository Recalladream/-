package main.iterface;

import user.chat.FriendThread;
import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class CreateFriendRequest extends Thread{
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //；
    JFrame hysqjm;
    Box rtbox;
    String jlzh;
    //;
    Socket zserver;
    JFrame chatroom;
    Box hygdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    JButton jlum;
    JLabel headpicture;


    public CreateFriendRequest(Socket zserver,JFrame chatroom,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp,JButton jlum,JFrame hysqjm,Box rtbox,String jlzh,ObjectOutputStream obputFW,ObjectInputStream obgetFW, JLabel headpicture){
        this.hysqjm=hysqjm;
        this.rtbox=rtbox;
        this.jlzh=jlzh;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        //;
        this.zserver=zserver;
        this.chatroom=chatroom;
        this.hygdbox=hygdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;
        this.jlum=jlum;
        this.headpicture=headpicture;
    }

    public void run(){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Friend_request");
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while(true){

                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                String state=getinfjh.getState();
                if (state.equals("2"))break;

                String hyzh=getinfjh.getAccount();
                String hyum=getinfjh.getNickname();

                FriendRequestThread creat=new FriendRequestThread(zserver,chatroom,hygdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlum,hysqjm,rtbox,jlzh,hyzh,hyum,obputFW,obgetFW,headpicture);
                creat.start();
            }
        }catch (Exception e){e.printStackTrace();}
    }
}

class FriendRequestThread extends Thread{
    int k=0;
    Box xsbox=Box.createHorizontalBox();
    JButton xsbut=new JButton();
    JButton tybut=new JButton("同意");
    JButton jjbut=new JButton("拒绝");
    //;
    JFrame hysqjm;
    Box rtbox;
    String jlzh;
    String hyzh;
    String hyum;
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
    JButton jlum;
    JLabel headpicture;

    public void initzj(){
        xsbut.setFont(new Font("宋体",Font.PLAIN,20));
        xsbut.setFocusPainted(false);
        xsbut.setBorderPainted(false);
        xsbut.setContentAreaFilled(false);

        tybut.setFont(new Font("宋体",Font.PLAIN,20));
        tybut.setContentAreaFilled(false);
        tybut.setFocusPainted(false);

        jjbut.setFont(new Font("宋体",Font.PLAIN,20));
        jjbut.setContentAreaFilled(false);
        jjbut.setFocusPainted(false);

        xsbox.add(xsbut);
    }

    public FriendRequestThread(Socket zserver,JFrame chatroom,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp,JButton jlum,JFrame hysqjm,Box rtbox,String jlzh,String hyzh,String hyum, ObjectOutputStream obputFW,ObjectInputStream obgetFW, JLabel headpicture) {
        this.hysqjm=hysqjm;
        this.rtbox=rtbox;
        this.jlzh=jlzh;
        this.hyzh=hyzh;
        this.hyum=hyum;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        //;
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

        xsbut.setText(hyum);
        initzj();

        rtbox.add(xsbox);
        hysqjm.repaint();
        hysqjm.setVisible(true);

        initact();
    }

    private void initact(){
        xsbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(k==0){
                    xsbox.add(tybut);
                    xsbox.add(jjbut);
                    hysqjm.repaint();
                    hysqjm.setVisible(true);
                    k=1;
                }
                else
                if(k==1){
                    xsbox.remove(tybut);
                    xsbox.remove(jjbut);
                    hysqjm.repaint();
                    hysqjm.setVisible(true);
                    k=0;
                }
            }
        });

        tybut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    rtbox.remove(xsbox);
                    hysqjm.repaint();
                    hysqjm.setVisible(true);

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Agree_friend_request");
                    putinfjh.setAccount(jlzh);
                    putinfjh.setPassword(hyzh);

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();

                    InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();

                    String hyzh=getinfjh.getAccount();
                    String hyum=getinfjh.getNickname();
                    ImageIcon imageic=getinfjh.getHeadPicture();
                    String offline=getinfjh.getOffline();

                    FriendThread friendThread=new FriendThread(zserver,chatroom, hygdbox, chatjp,ltfuncpan ,inftexjp, fsjp, funcjp,jlzh,jlum, hyzh, hyum,imageic,offline,headpicture);
                    friendThread.start();
                }catch (Exception a){a.printStackTrace();}
            }
        });

        jjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    rtbox.remove(xsbox);
                    hysqjm.repaint();
                    hysqjm.setVisible(true);

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Reject_friend_request");
                    putinfjh.setAccount(jlzh);
                    putinfjh.setPassword(hyzh);

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    public void run(){

    }
}