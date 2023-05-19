package login.iterface;

import main.iterface.MainInterface;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LogInInterface {
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    JFrame dluck;
    JLabel bjLab;
    JPanel pan;

    Box zbox;

    ImageIcon drimage;
    ImageIcon zcimage;
    ImageIcon zhbsimage;
    ImageIcon passbsimage;
    ImageIcon xsimage;
    ImageIcon ycimage;

    JLabel zccodesmg=new JLabel("!@#$%^&*");
    JLabel wzcodesmg=new JLabel("!@#$%^&*");
    JLabel wmailcodesmg=new JLabel("!@#$%^&*");
    JLabel wgcodesmg=new JLabel("!@#$%^&*");
    JLabel maildrsmg=new JLabel("!@#$%^&*");

    JButton zccodebut=new JButton("获得");
    JButton wzcodebut=new JButton("获得");
    JButton wmailcodebut=new JButton("获得");
    JButton wgcodebut=new JButton("获得");
    JButton maildrcodebut=new JButton("获取");

    public LogInInterface(){
        initsocket();
        initimage();
    }

    private void initsocket(){
        try {
        server=new Socket("127.0.0.1",4000);
        obputFW=new ObjectOutputStream(server.getOutputStream());
        obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    private void initimage(){
        bjLab=new JLabel();
        drimage = new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\LoginBackground.png");
        zcimage = new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\RegistrationBackground.png");
        zhbsimage=new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\AccountIdentification.png");
        passbsimage=new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\PasswordIdentification.png");
        xsimage=new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\Display.png");
        ycimage=new ImageIcon("E:\\java\\代码\\ResourceFile\\Client_Side\\InterfaceGraphicsLibrary\\Hide.png");
    }

    private void closeSocket(){
        try {
        server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void InitialInterface(){
        dluck = new JFrame("QQ登录");
        dluck.setResizable(false);

        bjLab.setIcon(drimage);
        bjLab.setSize(drimage.getIconWidth(),drimage.getIconHeight());
        dluck.getLayeredPane().add(bjLab, new Integer(Integer.MIN_VALUE));

        pan = (JPanel) dluck.getContentPane();
        pan.setOpaque(false);
        pan.setLayout(new FlowLayout());

        zbox=Box.createVerticalBox();

        Box ubox=Box.createHorizontalBox();
        JLabel yh=new JLabel(zhbsimage);
        JTextField yhmk=new JTextField(15);
        yhmk.setFont(new Font("宋体",Font.PLAIN,20));
        JButton lsjl=new JButton("历史");
        lsjl.setFont(new Font("宋体",Font.PLAIN,20));
        lsjl.setBorderPainted(false);
        lsjl.setContentAreaFilled(false);
        ubox.add(yh);
        ubox.add(Box.createHorizontalStrut(20));
        ubox.add(yhmk);
        ubox.add(lsjl);

        Box mbox=Box.createHorizontalBox();
        JLabel mima=new JLabel(passbsimage);
        JPasswordField mamk=new JPasswordField(15);
        mamk.setEchoChar('*');
        mamk.setFont(new Font("宋体",Font.PLAIN,20));
        JButton ycmibut=new JButton(ycimage);
        ycmibut.setContentAreaFilled(false);
        ycmibut.setBorderPainted(false);
        JButton xsmibut=new JButton(xsimage);
        xsmibut.setContentAreaFilled(false);
        xsmibut.setBorderPainted(false);
        mbox.add(mima);
        mbox.add(Box.createHorizontalStrut(20));
        mbox.add(mamk);
        mbox.add(Box.createHorizontalStrut(9));
        mbox.add(ycmibut);

        Box gnbox=Box.createHorizontalBox();
        JCheckBox jzma=new JCheckBox("记住密码");
        jzma.setSelected(true);
        jzma.setFocusPainted(false);
        jzma.setFont(new Font("宋体",Font.PLAIN,20));
        JButton zuce=new JButton("注册");
        zuce.setFont(new Font("宋体",Font.PLAIN,20));
        zuce.setContentAreaFilled(false);
        zuce.setBorderPainted(false);
        JButton wjpass=new JButton("找回");
        wjpass.setFont(new Font("宋体",Font.PLAIN,20));
        wjpass.setContentAreaFilled(false);
        wjpass.setBorderPainted(false);

        gnbox.add(jzma);
        gnbox.add(zuce);
        gnbox.add(wjpass);

        Box annobox=Box.createHorizontalBox();
        JButton dru=new JButton("安全登录");
        dru.setFont(new Font("宋体",Font.PLAIN,20));
        dru.setContentAreaFilled(false);
        dru.setBorderPainted(false);

        JButton maildru=new JButton("邮箱登录");
        maildru.setFont(new Font("宋体",Font.PLAIN,20));
        maildru.setContentAreaFilled(false);
        maildru.setBorderPainted(false);
        annobox.add(dru);
        annobox.add(Box.createHorizontalStrut(20));
        annobox.add(maildru);

        zbox.add(Box.createVerticalStrut(270));
        zbox.add(ubox);
        zbox.add(Box.createVerticalStrut(10));
        zbox.add(mbox);
        zbox.add(Box.createVerticalStrut(6));
        zbox.add(gnbox);
        zbox.add(Box.createVerticalStrut(6));
        zbox.add(annobox);

        pan.add(zbox);

        dluck.setSize(670,510);
        dluck.setLocationRelativeTo(null);
        dluck.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dluck.setVisible(true);

        lsjl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame lsjljm=new JFrame("登录历史");
                lsjljm.setBounds(800,600,400,400);
                lsjljm.setResizable(false);

                JPanel pan=new JPanel();
                JScrollPane gdpan=new JScrollPane(
                        pan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                lsjljm.add(gdpan);

                Box box=Box.createVerticalBox();
                pan.add(box);

                CreateRecord creat=new CreateRecord(box,lsjljm,yhmk,mamk);
                creat.start();

                lsjljm.setVisible(true);
            }
        });

        ycmibut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mbox.remove(ycmibut);
                mbox.add(xsmibut);
                mamk.setEchoChar((char)0);
                dluck.repaint();
                dluck.setVisible(true);
            }
        });

        xsmibut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mbox.remove(xsmibut);
                mbox.add(ycmibut);
                mamk.setEchoChar('*');
                dluck.repaint();
                dluck.setVisible(true);
            }
        });

        dru.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String account=yhmk.getText();
                    String password=mamk.getText();

                    if(account.equals("")||password.equals(""))
                        dru.setText("账号或密码不能为空");
                    else{
                        String jmmima= MD5.md5(password);

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Login_check");
                        putinfjh.setAccount(account);
                        putinfjh.setPassword(jmmima);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                        String zt=getinfjh.getState();

                        if(zt.equals("2")){dru.setText("密码错误");}
                        else
                        if(zt.equals("3")){dru.setText("账号不存在");}
                        else
                        if (zt.equals("4")){dru.setText("此账号已登录");}
                        else
                        {
                            dluck.dispose();

                            InforMationSet olputinfjh=new InforMationSet();
                            olputinfjh.setFunction("online");
                            olputinfjh.setAccount(account);

                            obputFW.writeObject(olputinfjh);
                            obputFW.flush();

                            InforMationSet cputinfjh=new InforMationSet();
                            cputinfjh.setFunction("Close");

                            obputFW.writeObject(cputinfjh);
                            obputFW.flush();

                            closeSocket();

                            if (jzma.isSelected()){
                                AddRecord addRecord=new AddRecord(zt,account,password);
                                addRecord.Joinrecord();
                            }

                            MainInterface mainInterface=new MainInterface(account,zt,getinfjh.getHeadPicture());
                            mainInterface.ChatRoom();
                        }
                    }
                }catch (Exception a){a.printStackTrace();}
            }
            @Override
            public void mouseExited(MouseEvent e) {
                dru.setText("安全登录");
            }
        });

        maildru.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dluck.setTitle("邮箱登录");

                bjLab.setIcon(null);
                pan.remove(zbox);
                dluck.repaint();
                dluck.setVisible(true);

                Box mailzbox=Box.createVerticalBox();
                pan.add(mailzbox);

                Box mailbox=Box.createHorizontalBox();
                JLabel mailLa=new JLabel("邮箱:");
                mailLa.setFont(new Font("宋体",Font.PLAIN,20));
                JTextField mailtext=new JTextField(15);
                mailtext.setFont(new Font("宋体",Font.PLAIN,20));
                mailbox.add(mailLa);
                mailbox.add(Box.createHorizontalStrut(20));
                mailbox.add(mailtext);

                Box yzmabox=Box.createHorizontalBox();
                JLabel yzmaLa=new JLabel("验证码：");
                yzmaLa.setFont(new Font("宋体",Font.PLAIN,20));
                JTextField yzmatext=new JTextField(15);
                yzmatext.setFont(new Font("宋体",Font.PLAIN,20));
                maildrcodebut.setFont(new Font("宋体",Font.PLAIN,20));
                maildrcodebut.setContentAreaFilled(false);
                maildrcodebut.setFocusPainted(false);
                yzmabox.add(yzmaLa);
                yzmabox.add(yzmatext);
                yzmabox.add(maildrcodebut);

                Box drubox=Box.createHorizontalBox();
                JButton dru=new JButton("登录");
                dru.setFont(new Font("宋体",Font.PLAIN,20));
                dru.setContentAreaFilled(false);
                dru.setFocusPainted(false);
                JButton fanhui=new JButton("返回");
                fanhui.setFont(new Font("宋体",Font.PLAIN,20));
                fanhui.setContentAreaFilled(false);
                fanhui.setFocusPainted(false);
                drubox.add(dru);
                drubox.add(Box.createHorizontalStrut(40));
                drubox.add(fanhui);

                mailzbox.add(Box.createVerticalStrut(100));
                mailzbox.add(mailbox);
                mailzbox.add(Box.createVerticalStrut(35));
                mailzbox.add(yzmabox);
                mailzbox.add(Box.createVerticalStrut(35));
                mailzbox.add(drubox);

                maildrcodebut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String mail = mailtext.getText();
                            int maillen = mail.length();
                            String pdmail = "";
                            if (maillen >= 8)
                                pdmail = mail.substring(maillen - 7, maillen);

                            if (maillen < 8 || mail.equals("") || !pdmail.equals("@qq.com")) {
                                dru.setText("邮箱错误");
                                mailtext.setText(null);
                            } else {
                                if (new String(maildrcodebut.getText()).equals("获取")) {
                                    CountDownThread countDownThread = new CountDownThread(120, maildrcodebut, maildrsmg);
                                    countDownThread.start();

                                    GetVerificationCode getVerificationCode=new GetVerificationCode("邮箱登录",mail,maildrsmg,obputFW,obgetFW);
                                    getVerificationCode.start();

                                }
                                else {
                                    dru.setText("请勿重复请求");
                                }
                            }
                        }catch (Exception a){a.printStackTrace();}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        dru.setText("登录");
                    }
                });

                dru.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String mail=mailtext.getText();
                            String codma=yzmatext.getText();
                            int maillen=mail.length();
                            String pdmail="";
                            if (maillen>=8)
                                pdmail=mail.substring(maillen-7,maillen);

                            if (maillen<8||mail.equals("")||!pdmail.equals("@qq.com"))
                                dru.setText("邮箱错误");
                            else if (!codma.equals(maildrsmg.getText())||codma.equals(""))
                                dru.setText("验证码错误");
                            else {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Email_login");
                                putinfjh.setAccount(mail);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                                String zt=getinfjh.getState();

                                if (zt.equals("1")){
                                    dluck.dispose();

                                    InforMationSet olputinfjh=new InforMationSet();
                                    olputinfjh.setFunction("online");
                                    olputinfjh.setAccount(mail);

                                    obputFW.writeObject(olputinfjh);
                                    obputFW.flush();

                                    InforMationSet cputinfjh=new InforMationSet();
                                    cputinfjh.setFunction("Close");

                                    obputFW.writeObject(cputinfjh);
                                    obputFW.flush();

                                    closeSocket();

                                    MainInterface mainInterface=new MainInterface(mail,getinfjh.getNickname(),getinfjh.getHeadPicture());
                                    mainInterface.ChatRoom();
                                }
                                else if (zt.equals("2")){
                                    JFrame tsjm=new JFrame("注意");
                                    tsjm.setResizable(false);
                                    tsjm.setBounds(800,600,350,200);

                                    JPanel zypan=new JPanel();
                                    tsjm.add(zypan);

                                    Box zbox=Box.createVerticalBox();
                                    zypan.add(zbox);

                                    Box tsbox=Box.createHorizontalBox();
                                    JLabel tsLa=new JLabel("此邮箱未注册过-是否注册");
                                    tsLa.setFont(new Font("宋体",Font.BOLD,20));
                                    tsbox.add(tsLa);

                                    Box erbox=Box.createHorizontalBox();
                                    JButton erbut=new JButton("确认注册");
                                    erbut.setFont(new Font("宋体",Font.PLAIN,20));
                                    erbut.setFocusPainted(false);
                                    erbut.setContentAreaFilled(false);
                                    erbox.add(erbut);

                                    zbox.add(Box.createVerticalStrut(20));
                                    zbox.add(tsbox);
                                    zbox.add(Box.createVerticalStrut(20));
                                    zbox.add(erbox);

                                    tsjm.setVisible(true);

                                    erbut.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            try {
                                                InforMationSet putinfjh=new InforMationSet();
                                                putinfjh.setFunction("Email_register");
                                                putinfjh.setAccount(mail);
                                                putinfjh.setMailbox(mail);

                                                obputFW.writeObject(putinfjh);
                                                obputFW.flush();

                                                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                                                String zt=getinfjh.getState();

                                                if (zt.equals("1")){
                                                    tsjm.dispose();

                                                    dluck.dispose();

                                                    InforMationSet olputinfjh=new InforMationSet();
                                                    olputinfjh.setFunction("online");
                                                    olputinfjh.setAccount(mail);

                                                    obputFW.writeObject(olputinfjh);
                                                    obputFW.flush();

                                                    InforMationSet cputinfjh=new InforMationSet();
                                                    cputinfjh.setFunction("Close");

                                                    obputFW.writeObject(cputinfjh);
                                                    obputFW.flush();

                                                    closeSocket();

                                                    MainInterface mainInterface=new MainInterface(mail,"(昵称)",getinfjh.getHeadPicture());
                                                    mainInterface.ChatRoom();
                                                }
                                            }catch (Exception a){a.printStackTrace();}
                                        }
                                    });
                                }
                                else {
                                    dru.setText("已在线");
                                }
                            }
                        }catch (Exception a){}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        dru.setText("登录");
                    }
                });

                fanhui.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        dluck.setTitle("QQ登录");
                        pan.removeAll();
                        bjLab.setIcon(drimage);
                        pan.add(zbox);
                        dluck.repaint();
                        dluck.setVisible(true);
                    }
                });
            }
        });

        zuce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.remove(zbox);
                dluck.repaint();
                dluck.setVisible(true);
                Register register=new Register(dluck,pan,bjLab,zccodesmg,zbox,drimage,zcimage,zccodebut);
                register.registrationInterface();
            }
        });

        wjpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.remove(zbox);
                dluck.repaint();
                dluck.setVisible(true);
                Retrieve retrieve=new Retrieve(dluck,pan,bjLab,drimage,zbox,wzcodesmg,wmailcodesmg,wgcodesmg,wzcodebut,wmailcodebut,wgcodebut);
                retrieve.retrievalInterface();
            }
        });

        dluck.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                InforMationSet cputinfjh=new InforMationSet();
                cputinfjh.setFunction("Close");

                obputFW.writeObject(cputinfjh);
                obputFW.flush();

                closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }
}