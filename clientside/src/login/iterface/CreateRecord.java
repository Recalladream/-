package login.iterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CreateRecord extends Thread{
    //

    Box box;
    JFrame lsjljm;
    JTextField yhmk;
    JTextField mamk;

    CreateRecord(Box box,JFrame lsjljm,JTextField yhmk,JTextField mamk){
        this.box=box;
        this.lsjljm=lsjljm;
        this.yhmk=yhmk;
        this.mamk=mamk;
    }

    public void run(){
        try {
            int wz=0;
            String smg=new String("E:\\java\\代码\\ResourceFile\\Client_Side\\LoginHistory.txt");
            File file=new File(smg);
            FileReader fileget=new FileReader(file);
            BufferedReader filegetbuf=new BufferedReader(fileget);
            while (filegetbuf.ready()){
                String nc=filegetbuf.readLine();
                String zh=filegetbuf.readLine();
                String pass=filegetbuf.readLine();

                RecordingThread creat=new RecordingThread(box,lsjljm,yhmk,mamk,nc,zh,pass,wz);
                creat.start();
                wz++;
            }
        }catch (Exception e){}
    }
}

class RecordingThread extends Thread{
    int k=0;
    int wz;
    Box xsbox=Box.createHorizontalBox();

    Box nczhbox=Box.createVerticalBox();
    JLabel ncLa=new JLabel();
    JLabel zhLa=new JLabel();

    JButton qurn=new JButton("确认");

    JButton sacu=new JButton("删除");
    //;

    Box box;
    JFrame lsjljm;
    String nc;
    String zh;
    String pass;
    JTextField yhmk;
    JTextField mamk;

    void initzj(){
        ncLa.setText(nc);
        ncLa.setFont(new Font("宋体",Font.PLAIN,20));

        zhLa.setText(zh);
        zhLa.setFont(new Font("宋体",Font.PLAIN,20));

        nczhbox.add(ncLa);
        nczhbox.add(zhLa);

        xsbox.add(nczhbox);
        xsbox.add(Box.createHorizontalStrut(10));
        box.add(xsbox);

        qurn.setFont(new Font("宋体",Font.PLAIN,20));
        qurn.setFocusPainted(false);
        qurn.setContentAreaFilled(false);

        sacu.setFont(new Font("宋体",Font.PLAIN,20));
        sacu.setFocusPainted(false);
        sacu.setContentAreaFilled(false);
    }

    void initact(){

        nczhbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (k==0){
                    xsbox.add(qurn);
                    xsbox.add(sacu);
                    lsjljm.repaint();
                    lsjljm.setVisible(true);
                    k=1;
                }
                else
                if (k==1)
                {
                    xsbox.remove(qurn);
                    xsbox.remove(sacu);
                    lsjljm.repaint();
                    lsjljm.setVisible(true);
                    k=0;
                }
            }
        });

        qurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                yhmk.setText(zh);
                mamk.setText(pass);
                lsjljm.dispose();
            }
        });

        sacu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    box.remove(xsbox);
                    lsjljm.repaint();
                    lsjljm.setVisible(true);

                    int x=0;
                    String []jl=new String[1000];

                    String smg=new String("E:\\java\\代码\\ResourceFile\\Client_Side\\LoginHistory.txt");
                    File file=new File(smg);

                    FileReader fileget=new FileReader(file);
                    BufferedReader filegetbuf=new BufferedReader(fileget);

                    int dqwz=0;
                    while (filegetbuf.ready()){
                        String hnc=filegetbuf.readLine();
                        if (dqwz!=wz)
                            jl[x++]=hnc;
                        String hzh=filegetbuf.readLine();
                        if (dqwz!=wz)
                            jl[x++]=hzh;
                        String hpass=filegetbuf.readLine();
                        if (dqwz!=wz)
                            jl[x++]=pass;

                        dqwz++;
                    }
                    file.delete();
                    filegetbuf.close();

                    FileOutputStream creatfile=new FileOutputStream(file);
                    creatfile.close();

                    FileWriter fileput=new FileWriter(file,true);
                    BufferedWriter fileputbuf=new BufferedWriter(fileput);

                    for(int i=0;i<x;i++){
                        fileputbuf.write(jl[i]+"\n");
                    }
                    fileputbuf.close();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    RecordingThread(Box box, JFrame lsjljm, JTextField yhmk, JTextField mamk, String nc, String zh, String pass, int wz){
        this.box=box;
        this.lsjljm=lsjljm;
        this.nc=nc;
        this.zh=zh;
        this.pass=pass;
        this.yhmk=yhmk;
        this.mamk=mamk;
        this.wz=wz;

        initzj();
        initact();

        lsjljm.repaint();
        lsjljm.setVisible(true);
    }

    public void run(){

    }
}