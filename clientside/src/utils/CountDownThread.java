package utils;

import javax.swing.*;

public class CountDownThread extends Thread{
    int js;
    String b;
    JButton codebut;
    JLabel jlcodema;

    public CountDownThread(int js,JButton codebut,JLabel jlcodema){
        this.js=js;
        this.codebut=codebut;
        this.jlcodema=jlcodema;
    }
    public void run(){
        try {
            while (true){
                char []a=new char[3];
                int z=js,i=0;
                while (true){
                    a[i++]=(char)(z%10+'0');
                    z/=10;
                    if (z==0)break;
                }
                b=new String(a);
                b=new StringBuffer(b).reverse().toString();
                codebut.setText(b);
                if (js<=0)break;
                js--;

                codebut.repaint();
                codebut.setVisible(true);
                Thread.sleep(1000);
            }
            codebut.setText("获得");
            jlcodema.setText("!@#$%^&*");
        }catch (Exception e){}
    }
}