package group.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CreateMember extends Thread{
    //;
    JFrame cyjm;
    Box zbox;
    String qunid;
    JLabel jb;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    Box funcbox;
    Box qunzlbox;
    Box quncybox;
    Box rqsqbox;
    Box yqrqbox;
    Box tuqunbox;
    Box jsqunbox;

    CreateMember(JFrame cyjm, Box zbox, String qunid, JLabel jb, ObjectOutputStream obputFW, ObjectInputStream obgetFW,Box funcbox,Box qunzlbox,Box quncybox,Box rqsqbox,Box yqrqbox,Box tuqunbox,Box jsqunbox){
        this.cyjm=cyjm;
        this.zbox=zbox;
        this.qunid=qunid;
        this.jb=jb;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        this.funcbox=funcbox;
        this.qunzlbox=qunzlbox;
        this.quncybox=quncybox;
        this.rqsqbox=rqsqbox;
        this.yqrqbox=yqrqbox;
        this.tuqunbox=tuqunbox;
        this.jsqunbox=jsqunbox;
    }

    public void run(){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Member");
            putinfjh.setGroupid(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true){
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();

                String state=getinfjh.getState();

                if (state.equals("1")){
                    String cym=getinfjh.getNickname();
                    String cyzh=getinfjh.getAccount();
                    String cyjb=getinfjh.getMemberlevel();

                    MemberThread creat=new MemberThread(cyjm,zbox,qunid,jb,cym,cyzh,cyjb,obputFW,obgetFW,funcbox,qunzlbox,quncybox,rqsqbox,yqrqbox,tuqunbox,jsqunbox);
                    creat.start();
                }
                if (state.equals("2"))break;
            }
        }catch (Exception e){e.printStackTrace();}
    }
}

class MemberThread extends Thread{
    int k=0;
    String smg;
    Box cybox=Box.createHorizontalBox();

    JLabel qzsf=new JLabel("");

    JButton cymbut=new JButton("");

    JButton sfset=new JButton("身份设置");

    JButton tiqun=new JButton("踢出此群");

    //;
    JFrame cyjm;
    Box zbox;
    String qunid;
    JLabel jb;
    String cym;
    String cyzh;
    JLabel cyjb=new JLabel();
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    Box funcbox;
    Box qunzlbox;
    Box quncybox;
    Box rqsqbox;
    Box yqrqbox;
    Box tuqunbox;
    Box jsqunbox;

    public MemberThread(JFrame cyjm,Box zbox,String qunid,JLabel jb,String cym,String cyzh,String cyjb,ObjectOutputStream obputFW,ObjectInputStream obgetFW,Box funcbox,Box qunzlbox,Box quncybox,Box rqsqbox,Box yqrqbox,Box tuqunbox,Box jsqunbox){
        this.cyjm=cyjm;
        this.zbox=zbox;
        this.qunid=qunid;
        this.jb=jb;
        this.cym=cym;
        this.cyzh=cyzh;
        this.cyjb.setText(cyjb);
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        this.funcbox=funcbox;
        this.qunzlbox=qunzlbox;
        this.quncybox=quncybox;
        this.rqsqbox=rqsqbox;
        this.yqrqbox=yqrqbox;
        this.tuqunbox=tuqunbox;
        this.jsqunbox=jsqunbox;


        initzj();
        initact();
    }

    private void initzj(){
        if(cyjb.getText().equals("1"))
            smg="群主";
        else if (cyjb.getText().equals("2"))
            smg="管理员";
        else if (cyjb.getText().equals("3"))
            smg="成员";
        qzsf.setText(smg);
        qzsf.setFont(new Font("宋体",Font.PLAIN,20));

        cymbut.setText(cym);
        cymbut.setFont(new Font("宋体",Font.PLAIN,20));
        cymbut.setBorderPainted(false);
        cymbut.setContentAreaFilled(false);

        cybox.add(qzsf);
        cybox.add(Box.createHorizontalStrut(10));
        cybox.add(cymbut);

        zbox.add(cybox);
        cyjm.repaint();
        cyjm.setVisible(true);

        sfset.setFont(new Font("宋体",Font.PLAIN,20));
        sfset.setContentAreaFilled(false);
        sfset.setFocusPainted(false);

        tiqun.setFont(new Font("宋体",Font.PLAIN,20));
        tiqun.setContentAreaFilled(false);
        tiqun.setFocusPainted(false);
    }

    private void initact(){
        cymbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jb.getText().equals("1")){
                    if (Integer.parseInt(jb.getText())<Integer.parseInt(cyjb.getText())){
                        if (k==0){
                            cybox.add(sfset);
                            cybox.add(tiqun);
                            k=1;
                        } else {
                            cybox.remove(sfset);
                            cybox.remove(tiqun);
                            k=0;
                        }
                    }
                }
                else if (jb.getText().equals("2")){
                    if (Integer.parseInt(jb.getText())<Integer.parseInt(cyjb.getText())){
                        if(k==0){
                            cybox.add(tiqun);
                            k=1;
                        }else {
                            cybox.remove(tiqun);
                            k=0;
                        }
                    }
                }

                cyjm.repaint();
                cyjm.setVisible(true);
            }
        });

        sfset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame sfjm=new JFrame("设置");
                sfjm.setBounds(900,500,350,300);
                sfjm.setResizable(false);

                JPanel pan=new JPanel();
                sfjm.add(pan);

                Box vbox=Box.createVerticalBox();
                pan.add(vbox);

                Box hbox1=Box.createHorizontalBox();
                JButton but1=new JButton("转让群主");
                but1.setFocusPainted(false);
                but1.setContentAreaFilled(false);
                but1.setFont(new Font("宋体",Font.PLAIN,20));
                hbox1.add(but1);

                Box hbox2=Box.createHorizontalBox();
                JButton but2=new JButton("设为成员");
                but2.setFocusPainted(false);
                but2.setContentAreaFilled(false);
                but2.setFont(new Font("宋体",Font.PLAIN,20));
                hbox2.add(but2);

                Box hbox3=Box.createHorizontalBox();
                JButton but3=new JButton("设为管理员");
                but3.setFocusPainted(false);
                but3.setContentAreaFilled(false);
                but3.setFont(new Font("宋体",Font.PLAIN,20));
                hbox3.add(but3);

                if (cyjb.getText().equals("2")){
                    vbox.add(Box.createVerticalStrut(40));
                    vbox.add(hbox1);
                    vbox.add(Box.createVerticalStrut(20));
                    vbox.add(hbox2);
                }
                if (cyjb.getText().equals("3")){
                    vbox.add(Box.createVerticalStrut(40));
                    vbox.add(hbox1);
                    vbox.add(Box.createVerticalStrut(20));
                    vbox.add(hbox3);
                }

                sfjm.setVisible(true);

                but1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("Set_level");
                            putinfjh.setAccount(cyzh);
                            putinfjh.setMemberlevel("1");
                            putinfjh.setGroupid(qunid);

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();

                            funcbox.removeAll();

                            funcbox.add(qunzlbox);
                            funcbox.add(Box.createVerticalStrut(20));
                            funcbox.add(quncybox);
                            funcbox.add(Box.createVerticalStrut(20));
                            funcbox.add(tuqunbox);

                            funcbox.repaint();
                            funcbox.setVisible(true);

                            jb.setText("3");
                            sfjm.dispose();
                            cyjm.dispose();
                        }catch (Exception a){a.printStackTrace();}
                    }
                });

                but2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("Set_level");
                            putinfjh.setAccount(cyzh);
                            putinfjh.setMemberlevel("3");
                            putinfjh.setGroupid(qunid);

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();

                            qzsf.setText("成员");
                            cyjb.setText("3");
                            sfjm.dispose();
                        }catch (Exception a){a.printStackTrace();}
                    }
                });

                but3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("Set_level");
                            putinfjh.setAccount(cyzh);
                            putinfjh.setMemberlevel("2");
                            putinfjh.setGroupid(qunid);

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();

                            qzsf.setText("管理员");
                            cyjb.setText("2");
                            sfjm.dispose();
                        }catch (Exception a){a.printStackTrace();}
                    }
                });

            }
        });

        tiqun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame tcjm=new JFrame("再次确认");
                tcjm.setBounds(800,600,350,100);
                tcjm.setResizable(false);

                JPanel pan=new JPanel();
                tcjm.add(pan);

                JButton erbut=new JButton("确认踢出");
                erbut.setFont(new Font("宋体",Font.PLAIN,20));
                erbut.setFocusPainted(false);
                erbut.setContentAreaFilled(false);
                pan.add(erbut);

                tcjm.setVisible(true);

                erbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Kick_out");
                        putinfjh.setAccount(cyzh);
                        putinfjh.setGroupid(qunid);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        tcjm.dispose();
                        cyjm.dispose();
                        }catch (Exception a){a.printStackTrace();}
                    }
                });
            }
        });
    }

    public void run(){

    }
}