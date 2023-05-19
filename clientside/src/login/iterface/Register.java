package login.iterface;

import utils.CountDownThread;
import utils.Norm;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Register {
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    Box fbox;
    //;
    JFrame dluck;
    JPanel pan;
    JLabel bjLab;
    Box zbox;

    ImageIcon drimage;
    ImageIcon zcimage;

    JButton zccodebut;
    JLabel zccodesmg;

    public Register(JFrame dluck,JPanel pan,JLabel bjLab,JLabel zccodesmg,Box zbox,ImageIcon drimage,ImageIcon zcimage,JButton zccodebut){
        this.dluck=dluck;
        this.pan=pan;
        this.bjLab=bjLab;
        this.zbox=zbox;
        this.drimage=drimage;
        this.zcimage=zcimage;
        this.zccodebut=zccodebut;
        this.zccodesmg=zccodesmg;
        initzj();
        initsocket();
    }

    private void initsocket(){
        try {
        server=new Socket("127.0.0.1",4000);
        obputFW=new ObjectOutputStream(server.getOutputStream());
        obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    private void closeSocket(){
        try {
            server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    private void initzj(){
        fbox=Box.createVerticalBox();
    }

    public void registrationInterface(){
        dluck.setTitle("用户注册");

        bjLab.setIcon(zcimage);
        bjLab.setSize(zcimage.getIconWidth(),zcimage.getIconHeight());

        fbox= Box.createVerticalBox();

        Box userbox=Box.createHorizontalBox();
        JLabel userLa=new JLabel("昵  称:");
        userLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField usertext=new JTextField(18);
        usertext.setFont(new Font("宋体",Font.BOLD,20));
        userbox.add(userLa);
        userbox.add(Box.createHorizontalStrut(20));
        userbox.add(usertext);

        Box passbox=Box.createHorizontalBox();
        JLabel passLa=new JLabel("密  码:");
        passLa.setFont(new Font("宋体",Font.BOLD,20));
        JPasswordField passtsxt=new JPasswordField(18);
        passtsxt.setFont(new Font("宋体",Font.BOLD,20));
        passbox.add(passLa);
        passbox.add(Box.createHorizontalStrut(20));
        passbox.add(passtsxt);

        Box fpassbox=Box.createHorizontalBox();
        JLabel fpassLa=new JLabel("重复密码:");
        fpassLa.setFont(new Font("宋体",Font.BOLD,20));
        JPasswordField fpasstsxt=new JPasswordField(18);
        fpasstsxt.setFont(new Font("宋体",Font.BOLD,20));
        fpassbox.add(fpassLa);
        fpassbox.add(Box.createHorizontalStrut(20));
        fpassbox.add(fpasstsxt);

        Box phonebox=Box.createHorizontalBox();
        JLabel phoneLa=new JLabel("电  话:");
        phoneLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField phonetext=new JTextField(18);
        phonetext.setFont(new Font("宋体",Font.BOLD,20));
        phonebox.add(phoneLa);
        phonebox.add(Box.createHorizontalStrut(20));
        phonebox.add(phonetext);

        Box mailbox=Box.createHorizontalBox();
        JLabel mailLa=new JLabel("qq邮箱:");
        mailLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField mailtext=new JTextField(18);
        mailtext.setFont(new Font("宋体",Font.BOLD,18));
        zccodebut.setFont(new Font("宋体",Font.BOLD,20));
        zccodebut.setContentAreaFilled(false);
        zccodebut.setFocusPainted(false);
        mailbox.add(mailLa);
        mailbox.add(Box.createHorizontalStrut(1));
        mailbox.add(mailtext);
        mailbox.add(Box.createHorizontalStrut(1));
        mailbox.add(zccodebut);


        Box codebox=Box.createHorizontalBox();
        JLabel codeLa=new JLabel("验证码:");
        codeLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField codetext=new JTextField(18);
        codetext.setFont(new Font("宋体",Font.BOLD,20));
        codebox.add(codeLa);
        codebox.add(Box.createHorizontalStrut(20));
        codebox.add(codetext);

        Box enrobox=Box.createHorizontalBox();
        JButton enrobut=new JButton("确认注册");
        enrobut.setFont(new Font("宋体",Font.BOLD,20));
        JButton outbut=new JButton("取消注册");
        outbut.setFont(new Font("宋体",Font.BOLD,20));
        enrobut.setContentAreaFilled(false);
        enrobut.setBorderPainted(false);
        outbut.setContentAreaFilled(false);
        outbut.setBorderPainted(false);
        enrobox.add(enrobut);
        enrobox.add(Box.createHorizontalStrut(20));
        enrobox.add(outbut);

        fbox.add(Box.createVerticalStrut(60));
        fbox.add(userbox);
        fbox.add(Box.createVerticalStrut(20));
        fbox.add(passbox);
        fbox.add(Box.createVerticalStrut(20));
        fbox.add(fpassbox);
        fbox.add(Box.createVerticalStrut(20));
        fbox.add(phonebox);
        fbox.add(Box.createVerticalStrut(20));
        fbox.add(mailbox);
        fbox.add(Box.createVerticalStrut(20));
        fbox.add(codebox);
        fbox.add(Box.createVerticalStrut(15));
        fbox.add(enrobox);

        pan.add(fbox);
        dluck.setVisible(true);

        zccodebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                String mail=mailtext.getText();
                int maillen=mail.length();
                String mailpd="";
                if (maillen>=8)
                    mailpd=mail.substring(maillen-7,maillen);

                if (mail.equals("")||!mailpd.equals("@qq.com"))
                    enrobut.setText("邮箱错误");
                else{
                    if (zccodebut.getText().equals("获得")) {
                        CountDownThread creat = new CountDownThread(120, zccodebut,zccodesmg);
                        creat.start();

                        GetVerificationCode getVerificationCode=new GetVerificationCode("注册",mail,zccodesmg,obputFW,obgetFW);
                        getVerificationCode.start();

                    }
                    else {enrobut.setText("请勿重复请求");}
                }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enrobut.setText("确认注册");
            }
        });

        outbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                bjLab.setIcon(drimage);
                bjLab.setSize(drimage.getIconWidth(),drimage.getIconHeight());

                dluck.setTitle("QQ");
                pan.remove(fbox);
                pan.add(zbox);
                dluck.repaint();
                dluck.setVisible(true);

                InforMationSet cputinfjh=new InforMationSet();
                cputinfjh.setFunction("Close");

                obputFW.writeObject(cputinfjh);
                obputFW.flush();

                closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });

        enrobut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String nickname=usertext.getText();
                    String pass1=passtsxt.getText();
                    String pass2=fpasstsxt.getText();
                    String phone=phonetext.getText();
                    String mail=mailtext.getText();
                    String code=codetext.getText();

                    int maillen=mail.length();
                    String pdmail="";
                    if (maillen>=8)
                        pdmail=mail.substring(maillen-7,maillen);

                    int k=0;
                    if(nickname.equals(""))
                        enrobut.setText("昵称不能为空");
                    else
                    if(!Norm.normAtiveJudgment(pass1,2))
                        Norm.prompt(2);
                    else
                    if(!pass1.equals(pass2))
                        enrobut.setText("重复密码错误");
                    else
                    if (!Norm.normAtiveJudgment(phone,3))
                        enrobut.setText("电话错误");
                    else
                    if (mail.equals("")||!pdmail.equals("@qq.com")){
                        enrobut.setText("邮箱错误");
                    }
                    else
                    if (code.equals("")||!code.equals(zccodesmg.getText()))
                    {   codetext.setText(null);
                        enrobut.setText("验证码错误");
                    }
                    else{
                        String jmmima= MD5.md5(pass1);

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("User_register");
                        putinfjh.setNickname(nickname);
                        putinfjh.setPassword(jmmima);
                        putinfjh.setTelephone(phone);
                        putinfjh.setMailbox(mail);


                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                        String zt=getinfjh.getState();
                        String account=getinfjh.getAccount();

                        if(zt.equals("1"))
                            enrobut.setText("此号码已注册");
                        else
                        if (zt.equals("2")){
                            enrobut.setText("此邮箱已绑定");
                        }
                        else
                        if (zt.equals("3")) {
                            k=1;
                            JFrame zccg=new JFrame("注册");
                            zccg.setVisible(true);
                            zccg.setResizable(false);
                            zccg.setLocationRelativeTo(null);
                            zccg.setBounds(800,600,250,200);

                            JPanel pan=new JPanel();
                            zccg.add(pan);

                            Box zbox=Box.createVerticalBox();
                            pan.add(zbox);

                            Box ts1box=Box.createHorizontalBox();
                            JLabel ts1La=new JLabel("注册成功");
                            ts1La.setFont(new Font("宋体",Font.BOLD,20));
                            ts1box.add(ts1La);

                            Box ts2box=Box.createHorizontalBox();
                            JLabel ts2La=new JLabel("账号");
                            ts2La.setFont(new Font("宋体",Font.BOLD,20));
                            ts2box.add(ts2La);

                            Box ts3box=Box.createHorizontalBox();
                            JLabel ts3La=new JLabel(account);
                            ts3La.setFont(new Font("宋体",Font.BOLD,20));
                            ts3box.add(ts3La);

                            zbox.add(ts1box);
                            zbox.add(Box.createVerticalStrut(10));
                            zbox.add(ts2box);
                            zbox.add(Box.createVerticalStrut(10));
                            zbox.add(ts3box);
                        }
                    }
                    if(k==1){
                        bjLab.setIcon(drimage);
                        bjLab.setSize(drimage.getIconWidth(),drimage.getIconHeight());

                        dluck.setTitle("QQ登录");
                        pan.remove(fbox);
                        pan.add(zbox);
                        dluck.repaint();
                        dluck.setVisible(true);

                        InforMationSet cputinfjh=new InforMationSet();
                        cputinfjh.setFunction("Close");

                        obputFW.writeObject(cputinfjh);
                        obputFW.flush();

                        closeSocket();
                    }
                }catch (Exception a){a.printStackTrace();}
            }
            @Override
            public void mouseExited(MouseEvent e) {
                enrobut.setText("确认注册");
            }
        });
    }

}
