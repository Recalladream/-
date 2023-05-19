package group.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class GroupInfor extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JLabel jb;
    JFrame chatroom;
    String jlzh;
    String qunid;
    JButton qunpho;
    JButton xsqmbut;
    Box zsbox;
    Box qungdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    Box qunzlbox;
    Box quncybox;
    Box funcbox;
    Box rqsqbox;
    Box yqrqbox;
    Box tuqunbox;
    Box jsqunbox;

    public GroupInfor(JLabel jb,JFrame chatroom,String jlzh,String qunid,JButton qunpho,JButton xsqmbut,Box zsbox, Box qungdbox, JPanel chatjp, JPanel ltfuncpan, JPanel inftexjp, JPanel fsjp, JPanel funcjp,Box funcbox,Box qunzlbox,Box quncybox,Box rqsqbox,Box yqrqbox,Box tuqunbox,Box jsqunbox){
        this.jb=jb;
        this.chatroom=chatroom;
        this.jlzh=jlzh;
        this.qunid=qunid;
        this.qunpho=qunpho;
        this.xsqmbut=xsqmbut;
        this.zsbox=zsbox;
        this.qungdbox=qungdbox;
        this.chatjp=chatjp;
        this.ltfuncpan=ltfuncpan;
        this.inftexjp=inftexjp;
        this.fsjp=fsjp;
        this.funcjp=funcjp;
        this.qunzlbox=qunzlbox;
        this.quncybox=quncybox;
        this.funcbox=funcbox;
        this.rqsqbox=rqsqbox;
        this.yqrqbox=yqrqbox;
        this.tuqunbox=tuqunbox;
        this.jsqunbox=jsqunbox;

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
            putinfjh.setChatobject(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            InforMationSet cputinfjh = new InforMationSet();
            cputinfjh.setFunction("Close_infor");
            cputinfjh.setAccount(jlzh);
            cputinfjh.setChatobject(qunid);

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
                    qunpho.setText(null);
                    qunpho.setBorderPainted(false);
                    qunpho.setIcon(imageic);
                }
                if (function.equals("name")){
                    String nickname=getinfjh.getNickname();
                    xsqmbut.setText(nickname);
                }
                if (function.equals("Kick_group")){
                    qungdbox.remove(zsbox);
                    chatjp.removeAll();
                    ltfuncpan.removeAll();
                    inftexjp.removeAll();
                    fsjp.removeAll();
                    funcjp.removeAll();
                }
                if (function.equals("Set_level")){
                    String level=getinfjh.getMemberlevel();

                    funcbox.removeAll();

                    if (level.equals("1")){
                        funcbox.add(qunzlbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(quncybox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(rqsqbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(yqrqbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(jsqunbox);
                        jb.setText("1");
                    }
                    else
                    if (level.equals("2")){
                        funcbox.add(qunzlbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(quncybox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(rqsqbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(yqrqbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(tuqunbox);
                        jb.setText("2");
                    }
                    else
                    if (level.equals("3")){
                        funcbox.add(qunzlbox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(quncybox);
                        funcbox.add(Box.createVerticalStrut(20));
                        funcbox.add(tuqunbox);
                        jb.setText("3");
                    }
                    funcbox.repaint();
                    funcbox.setVisible(true);
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }

}