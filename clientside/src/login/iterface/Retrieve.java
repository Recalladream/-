package login.iterface;

import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Retrieve {
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    Box cdbox;
    Box wzbox;
    Box wmailbox;
    Box wgbox;
    //;
    JFrame dluck;
    JPanel pan;
    JLabel bjLab;
    ImageIcon drimage;

    Box zbox;

    JLabel wzcodesmg;
    JLabel wmailcodesmg;
    JLabel wgcodesmg;

    JButton wzcodebut;
    JButton wmailcodebut;
    JButton wgcodebut;

    public Retrieve(JFrame dluck,JPanel pan,JLabel bjLab,ImageIcon drimage,Box zbox,JLabel wzcodesmg,JLabel wmailcodesmg,JLabel wgcodesmg,JButton wzcodebut,JButton wmailcodebut,JButton wgcodebut){
        this.dluck=dluck;
        this.pan=pan;
        this.bjLab=bjLab;
        this.drimage=drimage;
        this.zbox=zbox;
        this.wzcodesmg=wzcodesmg;
        this.wmailcodesmg=wmailcodesmg;
        this.wgcodesmg=wgcodesmg;
        this.wzcodebut=wzcodebut;
        this.wmailcodebut=wmailcodebut;
        this.wgcodebut=wgcodebut;
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
        cdbox=Box.createHorizontalBox();
    }

    private void initwzbox(){
        wzbox=Box.createVerticalBox();

        Box zhbox=Box.createHorizontalBox();
        JLabel zhLa=new JLabel("账 号:");
        zhLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField zhtext=new JTextField(12);
        zhtext.setFont(new Font("宋体",Font.PLAIN,20));
        zhbox.add(zhLa);
        zhbox.add(Box.createHorizontalStrut(20));
        zhbox.add(zhtext);

        Box mailbox=Box.createHorizontalBox();
        JLabel mailLa=new JLabel("qq邮箱:");
        mailLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField mailtext=new JTextField(18);
        mailtext.setFont(new Font("宋体",Font.PLAIN,20));
        wzcodebut.setFont(new Font("宋体",Font.BOLD,20));
        wzcodebut.setContentAreaFilled(false);
        wzcodebut.setFocusPainted(false);
        mailbox.add(mailLa);
        mailbox.add(Box.createHorizontalStrut(3));
        mailbox.add(mailtext);
        mailbox.add(Box.createHorizontalStrut(2));
        mailbox.add(wzcodebut);

        Box codebox=Box.createHorizontalBox();
        JLabel codeLa=new JLabel("验证码:");
        codeLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField codetext=new JTextField();
        codetext.setFont(new Font("宋体",Font.PLAIN,20));
        codebox.add(codeLa);
        codebox.add(Box.createHorizontalStrut(20));
        codebox.add(codetext);

        Box infindbox=Box.createHorizontalBox();
        JLabel infindLa=new JLabel("新密码:");
        infindLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField infindtext=new JTextField(12);
        infindtext.setFont(new Font("宋体",Font.PLAIN,20));
        infindbox.add(infindLa);
        infindbox.add(Box.createHorizontalStrut(20));
        infindbox.add(infindtext);

        Box anuo=Box.createHorizontalBox();
        JButton findh=new JButton("确认找回");
        findh.setFont(new Font("宋体",Font.BOLD,20));
        findh.setContentAreaFilled(false);
        findh.setBorderPainted(false);
        JButton ret=new JButton("返回");
        ret.setFont(new Font("宋体",Font.BOLD,20));
        ret.setBorderPainted(false);
        ret.setContentAreaFilled(false);
        anuo.add(findh);
        anuo.add(Box.createHorizontalStrut(20));
        anuo.add(ret);

        wzbox.add(cdbox);
        wzbox.add(Box.createVerticalStrut(60));
        wzbox.add(zhbox);
        wzbox.add(Box.createVerticalStrut(25));
        wzbox.add(mailbox);
        wzbox.add(Box.createVerticalStrut(25));
        wzbox.add(codebox);
        wzbox.add(Box.createVerticalStrut(25));
        wzbox.add(infindbox);
        wzbox.add(Box.createVerticalStrut(25));
        wzbox.add(anuo);

        wzcodebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                String mail=mailtext.getText();
                int maillen=mail.length();
                String mailpd="";
                if (maillen>=8)
                    mailpd=mail.substring(maillen-7,maillen);

                if (mail.equals("")||!mailpd.equals("@qq.com"))
                    findh.setText("邮箱错误");
                else{
                    if (wzcodebut.getText().equals("获得")) {
                        CountDownThread creat = new CountDownThread(120,wzcodebut,wzcodesmg);
                        creat.start();

                        GetVerificationCode getVerificationCode=new GetVerificationCode("通过账号找回",mail,wzcodesmg,obputFW,obgetFW);
                        getVerificationCode.start();

                    }
                    else {findh.setText("请勿重复请求");}
                }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                findh.setText("确认找回");
            }
        });

        ret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                dluck.setTitle("QQ登录");
                pan.removeAll();
                bjLab.setIcon(drimage);
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

        findh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String account =zhtext.getText();
                    String mail=mailtext.getText();
                    String codema =codetext.getText();
                    String xma=infindtext.getText();

                    int maillen=mail.length();
                    String pdmail="";
                    if (maillen>=8)
                        pdmail=mail.substring(maillen-7,maillen);

                    if (!Norm.normAtiveJudgment(account,1))
                        Norm.prompt(1);
                    else
                    if (!Norm.normAtiveJudgment(xma,2))
                        Norm.prompt(2);
                    else
                    if (mail.equals("")||!pdmail.equals("@qq.com"))
                        findh.setText("邮箱错误");
                    else if (codema.equals("") || !codema.equals(wzcodesmg.getText()))
                        findh.setText("验证码错误");
                    else
                    if (xma.equals(""))
                        findh.setText("新密码为空");
                    else {
                        String jmmima= MD5.md5(xma);

                        InforMationSet putinfjh = new InforMationSet();
                        putinfjh.setFunction("Account_retrieval");
                        putinfjh.setAccount(account);
                        putinfjh.setPassword(jmmima);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh = (InforMationSet) obgetFW.readObject();
                        String zt = getinfjh.getState();

                        if (zt.equals("1"))
                            findh.setText("操作成功");
                        else
                            if (zt.equals("2"))
                                findh.setText("新密码和原密码相同");
                            else
                                if (zt.equals("3"))
                                    findh.setText("没有此用户");
                    }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                findh.setText("确认找回");
            }
        });
    }

    private void initwmailbox(){
        wmailbox=Box.createVerticalBox();

        Box mailbox=Box.createHorizontalBox();
        JLabel mailLa=new JLabel("qq邮箱:");
        mailLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField mailtext=new JTextField(18);
        mailtext.setFont(new Font("宋体",Font.PLAIN,20));
        wmailcodebut.setFont(new Font("宋体",Font.BOLD,20));
        wmailcodebut.setContentAreaFilled(false);
        wmailcodebut.setFocusPainted(false);
        mailbox.add(mailLa);
        mailbox.add(Box.createHorizontalStrut(3));
        mailbox.add(mailtext);
        mailbox.add(Box.createHorizontalStrut(2));
        mailbox.add(wmailcodebut);

        Box codebox=Box.createHorizontalBox();
        JLabel codeLa=new JLabel("验证码:");
        codeLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField codetext=new JTextField();
        codetext.setFont(new Font("宋体",Font.PLAIN,20));
        codebox.add(codeLa);
        codebox.add(Box.createHorizontalStrut(20));
        codebox.add(codetext);

        Box infindmbox=Box.createHorizontalBox();
        JLabel infindmLa=new JLabel("新密码:");
        infindmLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField infindmtext=new JTextField(12);
        infindmtext.setFont(new Font("宋体",Font.PLAIN,20));
        infindmbox.add(infindmLa);
        infindmbox.add(Box.createHorizontalStrut(20));
        infindmbox.add(infindmtext);

        Box infindzbox=Box.createHorizontalBox();
        JLabel infindzLa=new JLabel("已找回账号:");
        infindzLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField infindztext=new JTextField(12);
        infindztext.setFont(new Font("宋体",Font.PLAIN,20));
        infindzbox.add(infindzLa);
        infindzbox.add(Box.createHorizontalStrut(20));
        infindzbox.add(infindztext);


        Box anuo=Box.createHorizontalBox();
        JButton findh=new JButton("确认找回");
        findh.setFont(new Font("宋体",Font.BOLD,20));
        findh.setContentAreaFilled(false);
        findh.setBorderPainted(false);
        JButton ret=new JButton("返回");
        ret.setFont(new Font("宋体",Font.BOLD,20));
        ret.setBorderPainted(false);
        ret.setContentAreaFilled(false);
        anuo.add(findh);
        anuo.add(Box.createHorizontalStrut(20));
        anuo.add(ret);

        wmailbox.add(cdbox);
        wmailbox.add(Box.createVerticalStrut(60));
        wmailbox.add(mailbox);
        wmailbox.add(Box.createVerticalStrut(25));
        wmailbox.add(codebox);
        wmailbox.add(Box.createVerticalStrut(25));
        wmailbox.add(infindmbox);
        wmailbox.add(Box.createVerticalStrut(25));
        wmailbox.add(infindzbox);
        wmailbox.add(Box.createVerticalStrut(25));
        wmailbox.add(anuo);

        wmailcodebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                String mail=mailtext.getText();
                int maillen=mail.length();
                String mailpd="";
                if (maillen>=8)
                    mailpd=mail.substring(maillen-7,maillen);

                if (mail.equals("")||!mailpd.equals("@qq.com"))
                    findh.setText("邮箱错误");
                else{
                    if (wmailcodebut.getText().equals("获得")) {
                        CountDownThread creat = new CountDownThread(120,wmailcodebut,wmailcodesmg);
                        creat.start();

                        GetVerificationCode getVerificationCode=new GetVerificationCode("通过邮箱找回",mail,wmailcodesmg,obputFW,obgetFW);
                        getVerificationCode.start();
                    }
                    else {findh.setText("请勿重复请求");}
                }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                findh.setText("确认找回");
            }
        });

        ret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    dluck.setTitle("QQ登录");
                    pan.removeAll();
                    bjLab.setIcon(drimage);
                    pan.add(zbox);
                    dluck.repaint();
                    dluck.setVisible(true);

                    InforMationSet cputinfjh = new InforMationSet();
                    cputinfjh.setFunction("Close");

                    obputFW.writeObject(cputinfjh);
                    obputFW.flush();

                    closeSocket();
                } catch (Exception a) {a.printStackTrace();}
            }
        });

        findh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String mail=mailtext.getText();
                    String codema=codetext.getText();
                    String xma=infindmtext.getText();

                    int maillen=mail.length();
                    String pdmail="";
                    if (maillen>=8)
                        pdmail=mail.substring(maillen-7,maillen);

                    if (mail.equals("")||!pdmail.equals("@qq.com"))
                        findh.setText("邮箱错误");
                    else
                    if (codema.equals("")||!codema.equals(wmailcodesmg.getText()))
                        findh.setText("验证码错误");
                    else
                    if (xma.equals(""))
                        findh.setText("新密码为空");
                    else {
                        String jmmima=MD5.md5(xma);

                        InforMationSet putinfjh = new InforMationSet();
                        putinfjh.setFunction("Email_retrieval");
                        putinfjh.setMailbox(mail);
                        putinfjh.setPassword(jmmima);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh = (InforMationSet) obgetFW.readObject();
                        String zt = getinfjh.getState();

                        if (zt.equals("2"))
                            findh.setText("新密码和原密码相同");
                        else
                        if (zt.equals("3"))
                            findh.setText("此邮箱未绑定账号");
                        else
                        {
                            String zhzh =zt;
                            infindztext.setText(zhzh);
                            findh.setText("操作成功");
                        }
                    }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                findh.setText("确认找回");
            }
        });

    }

    private void initwgbox(){
        wgbox=Box.createVerticalBox();

        Box zhbox=Box.createHorizontalBox();
        JLabel zhLa=new JLabel("账 号:");
        zhLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField zhtext=new JTextField(12);
        zhtext.setFont(new Font("宋体",Font.PLAIN,20));
        zhbox.add(zhLa);
        zhbox.add(Box.createHorizontalStrut(20));
        zhbox.add(zhtext);

        Box ypassbox=Box.createHorizontalBox();
        JLabel ypassLa=new JLabel("原密码:");
        ypassLa.setFont(new Font("宋体",Font.BOLD,20));
        JPasswordField ypasstext=new JPasswordField(12);
        ypasstext.setFont(new Font("宋体",Font.PLAIN,20));
        ypassbox.add(ypassLa);
        ypassbox.add(Box.createHorizontalStrut(20));
        ypassbox.add(ypasstext);

        Box xpassbox=Box.createHorizontalBox();
        JLabel xpassLa=new JLabel("新密码:");
        xpassLa.setFont(new Font("宋体",Font.BOLD,20));
        JPasswordField xpasstext=new JPasswordField(12);
        xpasstext.setFont(new Font("宋体",Font.PLAIN,20));
        xpassbox.add(xpassLa);
        xpassbox.add(Box.createHorizontalStrut(20));
        xpassbox.add(xpasstext);

        Box mailbox=Box.createHorizontalBox();
        JLabel mailLa=new JLabel("qq邮箱:");
        mailLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField mailtext=new JTextField(18);
        mailtext.setFont(new Font("宋体",Font.PLAIN,20));
        wgcodebut.setFont(new Font("宋体",Font.BOLD,20));
        wgcodebut.setContentAreaFilled(false);
        wgcodebut.setFocusPainted(false);
        mailbox.add(mailLa);
        mailbox.add(Box.createHorizontalStrut(3));
        mailbox.add(mailtext);
        mailbox.add(Box.createHorizontalStrut(2));
        mailbox.add(wgcodebut);

        Box codebox=Box.createHorizontalBox();
        JLabel codeLa=new JLabel("验证码:");
        codeLa.setFont(new Font("宋体",Font.BOLD,20));
        JTextField codetext=new JTextField();
        codetext.setFont(new Font("宋体",Font.PLAIN,20));
        codebox.add(codeLa);
        codebox.add(Box.createHorizontalStrut(20));
        codebox.add(codetext);

        Box anuo=Box.createHorizontalBox();
        JButton change=new JButton("确认更改");
        change.setFont(new Font("宋体",Font.BOLD,20));
        change.setContentAreaFilled(false);
        change.setBorderPainted(false);
        JButton ret=new JButton("返回");
        ret.setFont(new Font("宋体",Font.BOLD,20));
        ret.setBorderPainted(false);
        ret.setContentAreaFilled(false);
        anuo.add(change);
        anuo.add(Box.createHorizontalStrut(20));
        anuo.add(ret);

        wgbox.add(cdbox);
        wgbox.add(Box.createVerticalStrut(50));
        wgbox.add(zhbox);
        wgbox.add(Box.createVerticalStrut(25));
        wgbox.add(ypassbox);
        wgbox.add(Box.createVerticalStrut(25));
        wgbox.add(xpassbox);
        wgbox.add(Box.createVerticalStrut(25));
        wgbox.add(mailbox);
        wgbox.add(Box.createVerticalStrut(25));
        wgbox.add(codebox);
        wgbox.add(Box.createVerticalStrut(25));
        wgbox.add(anuo);

        wgcodebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                String mail=mailtext.getText();
                int maillen=mail.length();
                String mailpd="";
                if (maillen>=8)
                    mailpd=mail.substring(maillen-7,maillen);

                if (mail.equals("")||!mailpd.equals("@qq.com"))
                    change.setText("邮箱错误");
                else{
                    if (wgcodebut.getText().equals("获得")) {
                        CountDownThread creat = new CountDownThread(120,wgcodebut,wgcodesmg);
                        creat.start();

                        GetVerificationCode getVerificationCode=new GetVerificationCode("更改密码",mail,wgcodesmg,obputFW,obgetFW);
                        getVerificationCode.start();
                    }
                    else {change.setText("请勿重复请求");}
                }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                change.setText("确认更改");
            }
        });

        ret.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                dluck.setTitle("QQ登录");
                pan.removeAll();
                bjLab.setIcon(drimage);
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

        change.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String account=zhtext.getText();
                    String ypass=ypasstext.getText();
                    String xpass=xpasstext.getText();
                    String codema=codetext.getText();
                    String mail=mailtext.getText();

                    int maillen=mail.length();
                    String pdmail="";
                    if (maillen>=8)
                        pdmail=mail.substring(maillen-7,maillen);

                    if(!Norm.normAtiveJudgment(account,1))
                        Norm.prompt(1);
                    else
                    if (!Norm.normAtiveJudgment(ypass,2))
                        Norm.prompt(2);
                    else
                    if (!Norm.normAtiveJudgment(xpass,2))
                        Norm.prompt(2);
                    else
                    if (mail.equals("")||!pdmail.equals("@qq.com"))
                        change.setText("邮箱错误");
                    else
                    if (codema.equals("")||!codema.equals(wgcodesmg.getText()))
                        change.setText("验证码错误");
                    else {
                        String yjmmima=MD5.md5(ypass);
                        String xjmmima=MD5.md5(xpass);


                        InforMationSet putinfjh = new InforMationSet();
                        putinfjh.setFunction("Change_password");
                        putinfjh.setAccount(account);
                        putinfjh.setPassword(yjmmima);
                        putinfjh.setState(xjmmima);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh = (InforMationSet) obgetFW.readObject();
                        String zt = getinfjh.getState();

                        if (zt.equals("1"))
                            change.setText("原密码错误");
                        else
                        if (zt.equals("2"))
                            change.setText("账号不存在");
                        else
                        if (zt.equals("3"))
                            change.setText("新密码和原密码相同");
                        else
                        if (zt.equals("4")){

                            JFrame zccg=new JFrame("更改密码");
                            zccg.setVisible(true);
                            zccg.setResizable(false);

                            zccg.setBounds(800,600,200,120);

                            JPanel pan=new JPanel();

                            Box zbox=Box.createHorizontalBox();

                            JLabel bq=new JLabel("更改成功");
                            bq.setFont(new Font("宋体",Font.BOLD,25));
                            bq.setSize(100,100);
                            zbox.add(bq);

                            pan.add(zbox);

                            zccg.add(pan);
                        }
                    }
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                change.setText("确认更改");
            }
        });

    }

    public void retrievalInterface(){
        dluck.setTitle("账号或密码找回");
        bjLab.setIcon(null);

        cdbox=Box.createHorizontalBox();
        JMenuBar cdlan=new JMenuBar();
        cdlan.setOpaque(false);

        JMenu cdcy1=new JMenu("通过账号找回");
        cdcy1.setFont(new Font("宋体",Font.BOLD,20));
        JMenu cdcy2=new JMenu("通过邮箱找回");
        cdcy2.setFont(new Font("宋体",Font.BOLD,20));
        JMenu cdcy3=new JMenu("更改密码");
        cdcy3.setFont(new Font("宋体",Font.BOLD,20));
        cdlan.add(cdcy1);
        cdlan.add(cdcy2);
        cdlan.add(cdcy3);
        cdbox.add(cdlan);

        pan.add(cdbox);

        cdcy1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.removeAll();
                initwzbox();
                pan.add(wzbox);
                dluck.repaint();
                dluck.setVisible(true);
            }
        });

        cdcy2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.removeAll();
                initwmailbox();
                pan.add(wmailbox);
                dluck.repaint();
                dluck.setVisible(true);
            }
        });

        cdcy3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.removeAll();
                initwgbox();
                pan.add(wgbox);
                dluck.repaint();
                dluck.setVisible(true);
            }
        });
    }
}
