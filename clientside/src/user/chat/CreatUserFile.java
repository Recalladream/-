package user.chat;

import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class CreatUserFile extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    JFrame filejm;
    Box zbox;
    String jlzh;
    String hyzh;

    public CreatUserFile(JFrame filejm,Box zbox,String jlzh,String hyzh){
        this.filejm=filejm;
        this.zbox=zbox;
        this.jlzh=jlzh;
        this.hyzh=hyzh;

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
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Get_user_file");
            putinfjh.setAccount(jlzh);
            putinfjh.setPassword(hyzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true){
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String state=getinfjh.getState();
                if (state.equals("1")){
                    String filename=getinfjh.getNickname();
                    String path=getinfjh.getFunction();
                    String number=getinfjh.getAccount();

                    UserFile userFile=new UserFile(filejm,zbox,filename,number,path);
                    userFile.start();
                }
                if (state.equals("2")){
                    closeSocket();
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
}

class UserFile extends Thread{
    int k=0;
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    Box zxbox=Box.createHorizontalBox();

    JButton namebut=new JButton();

    JButton receivebut=new JButton("接收");

    JButton deletbut=new JButton("删除");
    //;
    JFrame filejm;
    Box zbox;
    String filename;
    String number;
    String path;

    public UserFile(JFrame filejm,Box zbox,String filename,String number,String path){
        this.filejm=filejm;
        this.zbox=zbox;
        this.filename=filename;
        this.number=number;
        this.path=path;

        initzj();
        initact();
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

    public void initzj(){
        namebut.setText(filename);
        namebut.setFont(new Font("宋体",Font.PLAIN,20));
        namebut.setFocusPainted(false);
        namebut.setBorderPainted(false);
        namebut.setContentAreaFilled(false);
        zxbox.add(namebut);

        zbox.add(zxbox);
        filejm.repaint();
        filejm.setVisible(true);

        receivebut.setFont(new Font("宋体",Font.PLAIN,20));
        receivebut.setFocusPainted(false);
        receivebut.setContentAreaFilled(false);

        deletbut.setFont(new Font("宋体",Font.PLAIN,20));
        deletbut.setFocusPainted(false);
        deletbut.setContentAreaFilled(false);
    }

    public void initact(){
        namebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (k==0){
                    zxbox.add(receivebut);
                    zxbox.add(deletbut);
                    k=1;
                }else if (k==1){
                    zxbox.remove(receivebut);
                    zxbox.remove(deletbut);
                    k=0;
                }

              filejm.repaint();
              filejm.setVisible(true);
            }
        });

        receivebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    initSocket();

                    JFileChooser jfc=new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
                    jfc.setCurrentDirectory(new File("C:\\"));
                    jfc.setMultiSelectionEnabled(false);
                    jfc.showDialog(null,"保存路径");

                    File filepath=jfc.getSelectedFile();
                    if (filepath!=null){
                        JFrame tsjm=new JFrame("注意");
                        tsjm.setBounds(800,600,400,200);

                        JPanel pan=new JPanel();
                        tsjm.add(pan);

                        Box vbox=Box.createVerticalBox();
                        pan.add(vbox);

                        Box hbox=Box.createHorizontalBox();
                        JLabel scz=new JLabel("正在下载中...");
                        scz.setFont(new Font("宋体",Font.PLAIN,20));
                        hbox.add(scz);
                        vbox.add(hbox);
                        tsjm.repaint();
                        tsjm.setVisible(true);

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("receive_user_file");
                        putinfjh.setAccount(number);
                        putinfjh.setAvatarState(path);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        String smg=filepath.toString()+"\\"+filename;
                        File file=new File(smg);

                        InputStream getfile=server.getInputStream();
                        FileOutputStream flieout=new FileOutputStream(file);

                        byte []buff=new byte[1024*1024*1024];
                        int lenth=0;

                        while ((lenth=getfile.read(buff))!=-1){
                            flieout.write(buff,0,lenth);
                        }
                        flieout.flush();
                        flieout.close();
                        closeSocket();

                        zbox.remove(zxbox);
                        filejm.repaint();
                        filejm.setVisible(true);

                        scz.setText("已下载完毕");
                        tsjm.repaint();
                        tsjm.setVisible(true);
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });

        deletbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    initSocket();

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("delet_user_file");
                    putinfjh.setAccount(number);
                    putinfjh.setAvatarState(path);

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();

                    zbox.remove(zxbox);
                    filejm.repaint();
                    filejm.setVisible(true);

                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    public void run(){
    }
}