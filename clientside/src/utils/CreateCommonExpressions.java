package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CreateCommonExpressions {
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame cyyjm;
    Box zbox;
    String jlzh;
    JTextPane inftexar;

    public CreateCommonExpressions(JFrame cyyjm,Box zbox,String jlzh,JTextPane inftexar){
        this.cyyjm=cyyjm;
        this.zbox=zbox;
        this.jlzh=jlzh;
        this.inftexar=inftexar;

        initSocket();
    }

    public void initSocket(){
        try {
            server=new Socket("127.0.0.1",4000);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    public void closeSocket(){
        try {
            server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void run(){
        try {
            InforMationSet putinfjh = new InforMationSet();
            putinfjh.setFunction("Get_common_expressions");
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();

            while (true){
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String state=getinfjh.getState();

                if (state.equals("1")){
                    String content=getinfjh.getContent();

                    CommonExpressions creat=new CommonExpressions(cyyjm,zbox,content,inftexar);
                    creat.start();
                }else
                if (state.equals("2")){
                    closeSocket();
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

}

class CommonExpressions extends Thread{

    Box hbox=Box.createHorizontalBox();

    JButton xsbut=new JButton();
    //;
    JFrame cyyjm;
    Box zbox;
    String content;
    JTextPane inftexar;

    public CommonExpressions(JFrame cyyjm,Box zbox,String content,JTextPane inftexar){
        this.cyyjm=cyyjm;
        this.zbox=zbox;
        this.content=content;
        this.inftexar=inftexar;

        initzj();
        initact();
    }

    public void initzj(){
        xsbut.setText(content);
        xsbut.setFont(new Font("宋体",Font.PLAIN,20));
        xsbut.setFocusPainted(false);
        xsbut.setBorderPainted(false);
        xsbut.setContentAreaFilled(false);

        hbox.add(xsbut);

        zbox.add(Box.createVerticalStrut(10));
        zbox.add(hbox);

        cyyjm.repaint();
        cyyjm.setVisible(true);
    }

    public void initact(){
        xsbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    OutputControl.writeCommonExpression(xsbut.getText(),0,inftexar);
                } catch (Exception a){a.printStackTrace();}
            }
        });

    }

    public void run(){

    }
}