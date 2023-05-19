package main.iterface;

import group.chat.GroupThread;
import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class CreateGroupInvitation extends Thread{
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame qunyqjm;
    Box rtbox;
    String jlzh;
    //;
    Socket zserver;
    JFrame chatroom;
    Box qungdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    JButton jlum;
    JLabel headpicture;

    public CreateGroupInvitation(Socket zserver,JFrame chatroom, Box qungdbox, JPanel chatjp, JPanel ltfuncpan, JPanel inftexjp, JPanel fsjp, JPanel funcjp,JButton jlum,JFrame qunyqjm,Box rtbox,String jlzh,ObjectOutputStream obputFW,ObjectInputStream obgetFW,JLabel headpicture){
        this.qunyqjm=qunyqjm;
        this.rtbox=rtbox;
        this.jlzh=jlzh;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        //;
        this.zserver=zserver;
        this.chatroom=chatroom;
        this.qungdbox=qungdbox;
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
            putinfjh.setFunction("Group_invitation");
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while(true){

                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                String state=getinfjh.getState();
                if (state.equals("2"))break;;

                String qunid=getinfjh.getGroupid();
                String qunm=getinfjh.getNickname();

                GroupInvitationThread creat=new GroupInvitationThread(zserver,chatroom,qungdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlum,qunid,qunm,qunyqjm,rtbox,jlzh,obputFW,obgetFW,headpicture);
                creat.start();
            }
        }catch (Exception e){e.printStackTrace();}
    }
}

class GroupInvitationThread extends Thread{
    int k=0;
    Box xsbox=Box.createHorizontalBox();
    JButton xsbut=new JButton();
    JButton tybut=new JButton("同意");
    JButton jjbut=new JButton("拒绝");
    //;
    String qunid;
    String qunm;
    JFrame qunyqjm;
    Box rtbox;
    String jlzh;
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
    JButton jlum;
    JLabel headpicture;

    public GroupInvitationThread(Socket zserver,JFrame chatroom, Box qungdbox, JPanel chatjp, JPanel ltfuncpan, JPanel inftexjp, JPanel fsjp, JPanel funcjp,JButton jlum,String qunid,String qunm,JFrame qunyqjm,Box rtbox,String jlzh,ObjectOutputStream obputFW,ObjectInputStream obgetFW,JLabel headpicture){
        this.qunid=qunid;
        this.qunm=qunm;
        this.qunyqjm=qunyqjm;
        this.rtbox=rtbox;
        this.jlzh=jlzh;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        //;
        this.zserver=zserver;
        this.chatroom=chatroom;
        this.qungdbox=qungdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;
        this.jlum=jlum;
        this.headpicture=headpicture;

        xsbut.setText(qunm);
        initzj();

        rtbox.add(xsbox);
        qunyqjm.repaint();
        qunyqjm.setVisible(true);

        initact();
    }

    public void initzj(){
        xsbut.setFont(new Font("宋体",Font.PLAIN,20));
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

    private void  initact(){
        xsbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(k==0){
                    xsbox.add(tybut);
                    xsbox.add(jjbut);
                    k=1;
                }
                else
                if (k==1){
                    xsbox.remove(tybut);
                    xsbox.remove(jjbut);
                    k=0;
                }

                qunyqjm.repaint();
                qunyqjm.setVisible(true);
            }
        });

        tybut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                rtbox.remove(xsbox);
                qunyqjm.repaint();
                qunyqjm.setVisible(true);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Agree_invitation");
                putinfjh.setGroupid(qunid);
                putinfjh.setAccount(jlzh);

                obputFW.writeObject(putinfjh);
                obputFW.flush();

                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();

                String qunm=getinfjh.getNickname();
                String qunid=getinfjh.getGroupid();
                String memberjb=getinfjh.getMemberlevel();
                ImageIcon ximageic=getinfjh.getHeadPicture();

                GroupThread creat = new GroupThread(zserver,qunm,qunid,memberjb,chatroom, qungdbox, chatjp,ltfuncpan,inftexjp, fsjp, funcjp,jlzh,jlum,ximageic,headpicture);
                creat.start();
                }catch (Exception a){a.printStackTrace();}
            }
        });

        jjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                rtbox.remove(xsbox);
                qunyqjm.repaint();
                qunyqjm.setVisible(true);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Decline_invitation");
                putinfjh.setGroupid(qunid);
                putinfjh.setAccount(jlzh);

                obputFW.writeObject(putinfjh);
                obputFW.flush();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    public void run(){

    }
}