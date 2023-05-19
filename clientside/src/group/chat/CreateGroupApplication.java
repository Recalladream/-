package group.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class CreateGroupApplication extends Thread{
    //;
    JFrame rqsqjm;
    Box rtbox;
    String qunid;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    public CreateGroupApplication(JFrame rqsqjm, Box rtbox, String qunid,ObjectOutputStream obputFW, ObjectInputStream obgetFW){
        this.rqsqjm=rqsqjm;
        this.rtbox=rtbox;
        this.qunid=qunid;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
    }

    public void run(){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Group_application");
            putinfjh.setGroupid(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while(true){
                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();

                String state=getinfjh.getState();
                if(state.equals("1")){
                    String sqzh=getinfjh.getAccount();
                    String squm=getinfjh.getNickname();

                    GroupApplicationThread creat=new GroupApplicationThread(qunid,rqsqjm,rtbox,sqzh,squm,obputFW,obgetFW);
                    creat.start();
                }
                if(state.equals("2"))break;
            }
        }catch (Exception e){e.printStackTrace();}
    }
}

class GroupApplicationThread extends Thread{
    int k=0;
    Box xsbox=Box.createHorizontalBox();

    JButton xsbut=new JButton();

    JButton tybut=new JButton("同意");

    JButton jjbut=new JButton("拒绝");
    //;
    String qunid;
    JFrame rqsqjm;
    Box rtbox;
    String sqzh;
    String squm;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    public GroupApplicationThread(String qunid,JFrame rqsqjm,Box rtbox,String sqzh,String squm,ObjectOutputStream obputFW,ObjectInputStream obgetFW){
        this.qunid=qunid;
        this.rqsqjm=rqsqjm;
        this.rtbox=rtbox;
        this.sqzh=sqzh;
        this.squm=squm;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;

        xsbut.setText(squm);
        initzj();

        rtbox.add(xsbox);
        rqsqjm.repaint();
        rqsqjm.setVisible(true);
        initact();
    }

    private void initzj(){
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

    private void initact(){
        xsbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (k==0) {
                    xsbox.add(tybut);
                    xsbox.add(jjbut);
                    k=1;
                }
                else {
                    xsbox.remove(tybut);
                    xsbox.remove(jjbut);
                    k=0;
                }

                rqsqjm.repaint();
                rqsqjm.setVisible(true);
            }
        });

        tybut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                rtbox.remove(xsbox);
                rqsqjm.repaint();
                rqsqjm.setVisible(true);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Consent_application");
                putinfjh.setGroupid(qunid);
                putinfjh.setAccount(sqzh);

                obputFW.writeObject(putinfjh);
                obputFW.flush();
                }catch (Exception a){a.printStackTrace();}
            }
        });

        jjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    rtbox.remove(xsbox);
                    rqsqjm.repaint();
                    rqsqjm.setVisible(true);

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Refusal_application");
                    putinfjh.setGroupid(qunid);
                    putinfjh.setAccount(sqzh);

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();
                }catch (Exception a){a.printStackTrace();}
            }
        });

    }

    public void run(){

    }
}