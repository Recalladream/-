package utils;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GetVerificationCode extends Thread{

    //;
    String func;
    String mail;
    JLabel codesmg;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    public GetVerificationCode(String func,String mail,JLabel codesmg,ObjectOutputStream obputFW, ObjectInputStream obgetFW){
        this.func=func;
        this.mail=mail;
        this.codesmg=codesmg;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
    }

    public void run(){
        try {
        InforMationSet putinfjh = new InforMationSet();
        putinfjh.setFunction("Get_mail");
        putinfjh.setState(func);
        putinfjh.setMailbox(mail);

        obputFW.writeObject(putinfjh);
        obputFW.flush();

        InforMationSet getinfjh = (InforMationSet) obgetFW.readObject();
        String zt = getinfjh.getState();

        if (zt.equals("1")) {
            String code = getinfjh.getCode();
            codesmg.setText(code);
        }

        if (zt.equals("0")) {
            JFrame xs = new JFrame("结果");
            xs.setBounds(800, 600, 400, 100);
            xs.setResizable(false);

            JPanel pan = new JPanel();
            xs.add(pan);

            JLabel jg = new JLabel("获取验证码失败");
            jg.setFont(new Font("宋体", Font.BOLD, 25));
            pan.add(jg);

            xs.setVisible(true);
        }
        if (zt.equals("1")) {
            JFrame xs = new JFrame("结果");
            xs.setBounds(800, 600, 400, 100);
            xs.setResizable(false);

            JPanel pan = new JPanel();
            xs.add(pan);

            JLabel jg = new JLabel("获取验证码成功,注意查收");
            jg.setFont(new Font("宋体", Font.BOLD, 25));
            pan.add(jg);

            xs.setVisible(true);
        }
        }catch (Exception e){e.printStackTrace();}
    }

}
