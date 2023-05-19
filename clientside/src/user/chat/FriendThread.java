package user.chat;

import org.jdesktop.swingx.JXDatePicker;
import utils.CreateCommonExpressions;
import utils.InforMationSet;
import utils.OutputControl;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FriendThread extends Thread{
    int maxwz=-1;
    HashMap<Integer,ImageIcon> phomap=new HashMap<Integer,ImageIcon>();
    JLabel pj=new JLabel("0");

    int threadout=0;
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    Box zsbox=Box.createHorizontalBox();

    JButton hypho=new JButton();

    JButton xsum=new JButton();

    JButton xszt=new JButton();

    JTextPane chatar=new JTextPane();

    Box ltfuncbox=Box.createHorizontalBox();

    Box sendwjbox=Box.createVerticalBox();
    JButton sendwjbut=new JButton("发送文件");

    Box receivewjbox=Box.createVerticalBox();
    JButton receivewjbut=new JButton("接收文件");

    Box languagebox=Box.createVerticalBox();
    JButton languagebut=new JButton("常用语");

    Box recordbox=Box.createVerticalBox();
    JButton recordbut=new JButton("聊天记录");

    Box emotibox=Box.createVerticalBox();
    JButton emotibut=new JButton("图片");

    JTextPane infar=new JTextPane();
    StyledDocument doc=infar.getStyledDocument();

    JButton fsbut=new JButton("发送");

    Box funcBox=Box.createVerticalBox();

    Box fun1box=Box.createHorizontalBox();
    JButton perinfbut=new JButton("个人信息");

    Box fun2box=Box.createHorizontalBox();
    JButton schy=new JButton("删除好友");

    //;
    Socket zserver;
    JFrame chatroom;
    Box hygdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    String jlzh;
    JButton jlum;
    String hyzh;
    String hyum;
    ImageIcon imageic;
    String offline;
    JLabel headpicture;

    public FriendThread(Socket zserver,JFrame chatroom,Box hygdbox,JPanel chatjp,JPanel ltfuncpan,JPanel inftexjp,JPanel fsjp,JPanel funcjp,String jlzh,JButton jlum,String hyzh,String hyum,ImageIcon imageic,String offline, JLabel headpicture){
        try {
            this.zserver=zserver;
            this.chatroom=chatroom;
            this.hygdbox=hygdbox;
            this.chatjp=chatjp;
            this.ltfuncpan=ltfuncpan;
            this.inftexjp=inftexjp;
            this.fsjp=fsjp;
            this.funcjp=funcjp;
            this.jlzh=jlzh;
            this.jlum=jlum;
            this.hyzh=hyzh;
            this.hyum=hyum;
            this.imageic=imageic;
            this.offline=offline;
            this.headpicture=headpicture;

            initzj();

            xsum.setText(hyum);
            hygdbox.add(zsbox);
            chatroom.repaint();
            chatroom.setVisible(true);

            System.out.println("好友:"+hyum+"——连接成功");
            initsocket();
            initact();
            initNews(chatar);
        }catch (Exception e){e.printStackTrace();}
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
            InforMationSet cputinfjh = new InforMationSet();
            cputinfjh.setFunction("Close");

            obputFW.writeObject(cputinfjh);
            obputFW.flush();

            server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    private void initzj(){
        hypho.setContentAreaFilled(false);
        hypho.setFocusPainted(false);
        if(imageic!=null){
            hypho.setBorderPainted(false);
            hypho.setText(null);
            hypho.setIcon(imageic);
        }else {
            hypho.setFont(new Font("宋体",Font.PLAIN,20));
            hypho.setText("无");
        }

        xsum.setBorderPainted(false);
        xsum.setContentAreaFilled(false);
        xsum.setFont(new Font("宋体",Font.PLAIN,20));

        if (offline.equals("0"))
            xszt.setText("离线");
        else
            xszt.setText("在线");

        xszt.setFont(new Font("宋体",Font.PLAIN,20));
        xszt.setBorderPainted(false);
        xszt.setFocusPainted(false);
        xszt.setContentAreaFilled(false);
        zsbox.add(hypho);
        zsbox.add(xsum);
        zsbox.add(xszt);

        chatar.setFont(new Font("宋体",Font.PLAIN,20));
        chatar.setEditable(false);
        chatar.setCaretPosition(chatar.getStyledDocument().getLength());

        sendwjbut.setFont(new Font("宋体",Font.PLAIN,20));
        sendwjbut.setContentAreaFilled(false);
        sendwjbut.setFocusPainted(false);
        sendwjbox.add(sendwjbut);

        receivewjbut.setFont(new Font("宋体",Font.PLAIN,20));
        receivewjbut.setContentAreaFilled(false);
        receivewjbut.setFocusPainted(false);
        receivewjbox.add(receivewjbut);

        languagebut.setFont(new Font("宋体",Font.PLAIN,20));
        languagebut.setContentAreaFilled(false);
        languagebut.setFocusPainted(false);
        languagebox.add(languagebut);

        recordbut.setFont(new Font("宋体",Font.PLAIN,20));
        recordbut.setContentAreaFilled(false);
        recordbut.setFocusPainted(false);
        recordbox.add(recordbut);

        emotibut.setFont(new Font("宋体",Font.PLAIN,20));
        emotibut.setContentAreaFilled(false);
        emotibut.setFocusPainted(false);
        emotibox.add(emotibut);

        ltfuncbox.add(sendwjbox);
        ltfuncbox.add(receivewjbox);
        ltfuncbox.add(languagebox);
        ltfuncbox.add(recordbox);
        ltfuncbox.add(emotibox);

        infar.setFont(new Font("宋体",Font.PLAIN,20));

        fsbut.setFont(new Font("宋体",Font.PLAIN,20));
        fsbut.setContentAreaFilled(false);
        fsbut.setFocusPainted(false);

        perinfbut.setContentAreaFilled(false);
        perinfbut.setFocusPainted(false);
        perinfbut.setFont(new Font("宋体",Font.PLAIN,20));
        fun1box.add(perinfbut);

        schy.setContentAreaFilled(false);
        schy.setFocusPainted(false);
        schy.setFont(new Font("宋体",Font.PLAIN,20));
        fun2box.add(schy);

        funcBox.add(fun1box);
        funcBox.add(Box.createVerticalStrut(10));
        funcBox.add(fun2box);
    }

    private void initNews(JTextPane chatar){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("User_get_news");
            putinfjh.setAccount(jlzh);
            putinfjh.setPassword(hyzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true){
                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                if (getinfjh!=null){
                    String state=getinfjh.getState();
                    if (state.equals("1")){
                        String fqr=getinfjh.getNickname();
                        String xx=getinfjh.getContent();
                        ImageIcon icon=getinfjh.getMaxHeadPicture();
                        int dqfs=Integer.parseInt(getinfjh.getCode());

                        if (dqfs==0)
                            if (icon!=null){
                                OutputControl.writePho(fqr,icon,dqfs,chatar,hypho.getIcon());
                            }else
                                OutputControl.write(fqr,xx,dqfs,chatar,hypho.getIcon());
                        else
                            if (icon!=null){
                                OutputControl.writePho(fqr,icon,dqfs,chatar,headpicture.getIcon());
                            }else
                                OutputControl.write(fqr,xx,dqfs,chatar,headpicture.getIcon());
                    }
                    if (state.equals("2"))break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private String changenlxz(int heage,int MM,int dd,int function){
        try {
            if (function==1){
                String smg="";
                char []jl=new char[4];
                int x=0;

                while (true){
                    int zj=heage%10;

                    jl[x++]=(char)('0'+zj);

                    heage/=10;
                    if (heage==0)break;
                }
                jl[x]='\0';

                smg=new String(jl);
                String hsmg=new StringBuffer(smg).reverse().toString();

                return hsmg;
            }

            if (function==2){
                String smg="";
                switch (MM){
                    case 1:if (dd<=20)smg="摩羯座";else smg="水瓶座";break;
                    case 2:if (dd<=19)smg="水瓶座";else smg="双鱼座";break;
                    case 3:if (dd<=20)smg="双鱼座";else smg="白羊座";break;
                    case 4:if (dd<=20)smg="白羊座";else smg="金牛座";break;
                    case 5:if (dd<=21)smg="金牛座";else smg="双子座";break;
                    case 6:if (dd<=21)smg="双子座";else smg="巨蟹座";break;
                    case 7:if (dd<=22)smg="巨蟹座";else smg="狮子座";break;
                    case 8:if (dd<=23)smg="狮子座";else smg="处女座";break;
                    case 9:if (dd<=23)smg="处女座";else smg="天秤座";break;
                    case 10:if (dd<=23)smg="天秤座";else smg="天蝎座";break;
                    case 11:if (dd<=22)smg="天蝎座";else smg="射手座";break;
                    case 12:if (dd<=21)smg="射手座";else smg="摩羯座";break;
                }
                return smg;
            }
        }catch (Exception e){e.printStackTrace();}
        return "";
    }

    private void jsnlxz(JLabel eage,JLabel xz,String birthday){
        try {
            SimpleDateFormat ges=new SimpleDateFormat("yyyy/MM/dd");
            Date birthdate=ges.parse(birthday);
            Date nowdate=new Date();

            Calendar ca=Calendar.getInstance();
            ca.setTime(birthdate);

            int birthyyyy=ca.get(Calendar.YEAR);
            int birthMM=ca.get(Calendar.MONTH)+1;
            int birthdd=ca.get(Calendar.DAY_OF_MONTH);

            ca.setTime(nowdate);

            int nowyyyy=ca.get(Calendar.YEAR);
            int nowMM=ca.get(Calendar.MONTH)+1;
            int nowdd=ca.get(Calendar.DAY_OF_MONTH);


            int heage=nowyyyy-birthyyyy;
            if (nowMM<birthMM)heage--;
            else
            if (nowMM==birthMM&&nowdd<birthdd)heage--;

            String eagesmg=changenlxz(heage,0,0,1);
            String hxz=changenlxz(0,birthMM,birthdd,2);

            eage.setText(eagesmg);
            xz.setText(hxz);
        }catch (Exception e){e.printStackTrace();}
    }

    private void initgrinf(JButton uspho,JLabel um,JTextArea persi,JLabel zh,JLabel xb,JLabel eage,JLabel xz,JLabel birthLa){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Init_user_infor");
            putinfjh.setAccount(hyzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();

            InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
            String nickname=getinfjh.getNickname();
            String spersi=getinfjh.getSignature();
            String sxb=getinfjh.getGender();
            String birthday=getinfjh.getBirthday();
            ImageIcon dimageic=getinfjh.getMaxHeadPicture();

            if (dimageic!=null){
                uspho.setText(null);
                uspho.setBorderPainted(false);
                uspho.setIcon(dimageic);
            }
            else {
                uspho.setBorderPainted(true);
                uspho.setText("快换上头像吧");
            }

            if(spersi==null)spersi="(快来填写，属于自己的签名)";
            if (spersi!=null){
                if (spersi.equals(""))
                    spersi="(快来填写，属于自己的签名)";
            }

            if(sxb==null)sxb="(性别)";
            if (sxb!=null){
                if (sxb.equals(""))
                    sxb="(性别)";
            }

            if (birthday==null)
            {
                birthLa.setText("(生日)");
                eage.setText("(年龄)");
                xz.setText("(星座)");
            }
            else {
                birthLa.setText(birthday);
                jsnlxz(eage,xz,birthday);
            }
            if (birthday!=null){
                if (birthday.equals("")){
                    birthLa.setText("(生日)");
                    eage.setText("(年龄)");
                    xz.setText("(星座)");
                }
            }

            um.setText(nickname);
            persi.setText(spersi);
            zh.setText(jlzh);
            xb.setText(sxb);
        }catch (Exception e){e.printStackTrace();}
    }

    private Boolean judgdata(Date ksdata,Date jsdata){
        try {
            int dqyyyy;int dqMM;int dqdd;
            int ksyyyy;int ksMM;int ksdd;
            int jsyyyy;int jsMM;int jsdd;

            Calendar ca=Calendar.getInstance();

            ca.setTime(new Date());
            dqyyyy=ca.get(Calendar.YEAR);
            dqMM=ca.get(Calendar.MONTH)+1;
            dqdd=ca.get(Calendar.DAY_OF_MONTH);


            ca.setTime(ksdata);
            ksyyyy=ca.get(Calendar.YEAR);
            ksMM=ca.get(Calendar.MONTH)+1;
            ksdd=ca.get(Calendar.DAY_OF_MONTH);

            ca.setTime(jsdata);
            jsyyyy=ca.get(Calendar.YEAR);
            jsMM=ca.get(Calendar.MONTH)+1;
            jsdd=ca.get(Calendar.DAY_OF_MONTH);

            if (jsyyyy>dqyyyy)return false;

            if (jsMM==dqMM){
                if (jsMM>dqMM)return false;

                if (jsMM==dqMM){
                    if (jsdd>dqdd)return false;
                }
            }


            if (ksyyyy>jsyyyy)return false;

            if (ksyyyy==jsyyyy){
                if (ksMM>jsMM)return false;

                if (ksMM==jsMM){
                    if (ksdd>jsdd)return false;
                }
            }

        }catch (Exception e){e.printStackTrace();}
        return true;
    }

    private void  initact(){
        chatroom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    threadout=1;
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    threadout=1;
                }catch (Exception a){a.printStackTrace();}
            }
        });

        xsum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xsum.setForeground(Color.black);
                chatjp.removeAll();
                ltfuncpan.removeAll();
                inftexjp.removeAll();
                fsjp.removeAll();
                funcjp.removeAll();
                chatjp.add(chatar);
                ltfuncpan.add(ltfuncbox);
                inftexjp.add(infar);
                fsjp.add(fsbut);
                funcjp.add(funcBox);
                chatroom.repaint();
                chatroom.setVisible(true);
            }
        });

        sendwjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFileChooser jfc=new JFileChooser();

                    jfc.setPreferredSize( new Dimension(1000, 800));
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jfc.setCurrentDirectory(new File("C:\\"));
                    jfc.setMultiSelectionEnabled(false);

                    int result = jfc.showDialog(null,"选择文件");

                    File file=jfc.getSelectedFile();
                    if (file!=null){
                        String filename= file.getName();

                        Socket server1=new Socket("127.0.0.1",4000);
                        ObjectOutputStream obputFW1=new ObjectOutputStream(server1.getOutputStream());

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Send_user_file");
                        putinfjh.setAccount(jlzh);
                        putinfjh.setPassword(hyzh);
                        putinfjh.setNickname(filename);

                        obputFW1.writeObject(putinfjh);
                        obputFW1.flush();

                        OutputStream putfile=server1.getOutputStream();
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

                        byte[] buf = new byte[1024*1024*1024];
                        int lenth = 0;

                        while( (lenth = bis.read(buf)) != -1 ) {
                            putfile.write(buf,0,lenth);
                            putfile.flush();
                        }
                        putfile.flush();
                        bis.close();
                        server1.close();

                        OutputControl.write(null,"---已成功发送文件---",1,chatar,null);
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });

        receivewjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame filejm=new JFrame("接收文件");
                filejm.setBounds(800,600,500,450);

                JPanel pan=new JPanel();
                JScrollPane gdpan=new JScrollPane(
                        pan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                filejm.add(gdpan);

                Box zbox=Box.createVerticalBox();
                pan.add(zbox);

                CreatUserFile creatUserFile=new CreatUserFile(filejm,zbox,jlzh,hyzh);
                creatUserFile.start();

                filejm.setVisible(true);
            }
        });

        languagebut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame cyyjm=new JFrame("常用语");
                cyyjm.setBounds(800,600,450,540);

                JPanel pan=new JPanel();
                cyyjm.add(pan);

                Box zbox=Box.createVerticalBox();
                pan.add(zbox);

                CreateCommonExpressions creat=new CreateCommonExpressions(cyyjm,zbox,jlzh,infar);
                creat.run();

                cyyjm.repaint();
                cyyjm.setVisible(true);
            }
        });

        recordbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame recordjm=new JFrame("聊天记录");
                recordjm.setBounds(800,600,605,520);

                GridBagLayout bj=new GridBagLayout();
                GridBagConstraints bjkz=new GridBagConstraints();
                JPanel zpan=new JPanel(bj);
                recordjm.add(zpan);

                JPanel funcpan=new JPanel();

                Box funcvbox=Box.createVerticalBox();

                Box funchbox=Box.createHorizontalBox();
                funcvbox.add(funchbox);
                funcpan.add(funcvbox);

                Date date=new Date();

                JLabel ksLa=new JLabel("开 始:");
                ksLa.setFont(new Font("宋体",Font.PLAIN,20));
                JXDatePicker ksjdp=new JXDatePicker();
                ksjdp.setDate(date);
                ksjdp.setFont(new Font("宋体",Font.PLAIN,20));
                ksjdp.setFormats("yyyy/MM/dd");

                JLabel jsLa=new JLabel("结 束:");
                jsLa.setFont(new Font("宋体",Font.PLAIN,20));
                JXDatePicker jsjdp=new JXDatePicker();
                jsjdp.setDate(date);
                jsjdp.setFont(new Font("宋体",Font.PLAIN,20));
                jsjdp.setFormats("yyyy/MM/dd");

                JButton czbut=new JButton("查询");
                czbut.setFont(new Font("宋体",Font.PLAIN,20));
                czbut.setFocusPainted(false);
                czbut.setContentAreaFilled(false);

                funchbox.add(ksLa);
                funchbox.add(Box.createHorizontalStrut(5));
                funchbox.add(ksjdp);
                funchbox.add(Box.createHorizontalStrut(5));
                funchbox.add(jsLa);
                funchbox.add(Box.createHorizontalStrut(5));
                funchbox.add(jsjdp);
                funchbox.add(Box.createHorizontalStrut(5));
                funchbox.add(czbut);

                JPanel chatpan=new JPanel(bj);
                JScrollPane gdchatpan=new JScrollPane(
                        chatpan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );

                JTextPane chatar=new JTextPane();
                chatar.setFont(new Font("宋体",Font.PLAIN,20));

                chatpan.add(chatar);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(chatar,bjkz);

                zpan.add(funcpan);
                zpan.add(gdchatpan);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=0;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(funcpan,bjkz);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=1;
                bj.setConstraints(gdchatpan,bjkz);

                recordjm.repaint();
                recordjm.setVisible(true);

                czbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Date ksdata=ksjdp.getDate();
                        Date jsdata=jsjdp.getDate();

                        if (judgdata(ksdata,jsdata)){
                            chatar.setText(null);
                            String kssmg;
                            String jssmg;

                            Calendar ca=Calendar.getInstance();

                            ca.setTime(ksdata);
                            kssmg=ca.get(Calendar.YEAR)+"/";
                            kssmg+=ca.get(Calendar.MONTH)+1+"/";
                            kssmg+=ca.get(Calendar.DAY_OF_MONTH);

                            ca.setTime(jsdata);
                            jssmg=ca.get(Calendar.YEAR)+"/";
                            jssmg+=ca.get(Calendar.MONTH)+1+"/";
                            jssmg+=ca.get(Calendar.DAY_OF_MONTH);

                            UserChatRecord creat=new UserChatRecord(jlzh,hyzh,chatar,kssmg,jssmg,hypho,headpicture);
                            creat.start();
                        }else {
                            czbut.setText("时间设置错误");
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        czbut.setText("查询");
                    }
                });
            }
        });

        emotibut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFileChooser jfc=new JFileChooser();

                    jfc.setPreferredSize( new Dimension(1000, 800));
                    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jfc.setCurrentDirectory(new File("C:\\"));
                    jfc.addChoosableFileFilter(new FileNameExtensionFilter("image(*.jpg,*.png,*.gif)","jpg","png","gif"));
                    jfc.setMultiSelectionEnabled(false);

                    int result = jfc.showDialog(null,"选择文件");

                    File phofile=jfc.getSelectedFile();
                    if (phofile!=null){
                        String phoname= phofile.getName();
                        String kzm=phoname.substring(phoname.length()-4,phoname.length());

                        if(kzm.equals(".jpg")||kzm.equals(".png")||kzm.equals(".gif")){
                            BufferedImage iamgebuf=ImageIO.read(phofile);
                            Image image=iamgebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                            ImageIcon imageic=new ImageIcon(image);

                            OutputControl.writePho(jlum.getText(),imageic,2,chatar,headpicture.getIcon());

                            Socket server1=new Socket("127.0.0.1",4000);
                            ObjectOutputStream obputFW1=new ObjectOutputStream(server1.getOutputStream());

                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("User_send_pho");
                            putinfjh.setAccount(jlzh);
                            putinfjh.setPassword(hyzh);
                            putinfjh.setNickname(phoname);

                            obputFW1.writeObject(putinfjh);
                            obputFW1.flush();

                            OutputStream putfile=server1.getOutputStream();
                            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(phofile));

                            byte[] buf = new byte[1024*1024*1024];
                            int lenth = 0;

                            while( (lenth = bis.read(buf)) != -1 ) {
                                putfile.write(buf,0,lenth);
                                putfile.flush();
                            }
                            bis.close();
                            server1.close();
                        }
                        else {
                            JFrame zyjm=new JFrame("注意");
                            zyjm.setBounds(800,600,400,150);
                            zyjm.setResizable(false);

                            JPanel pan=new JPanel();
                            zyjm.add(pan);

                            Box box=Box.createVerticalBox();

                            JLabel tsLa=new JLabel("请选择图片(*.jpg/*.png/*.gif)");
                            tsLa.setFont(new Font("宋体",Font.PLAIN,25));

                            box.add(Box.createVerticalStrut(25));
                            box.add(tsLa);

                            pan.add(box);

                            zyjm.repaint();
                            zyjm.setVisible(true);
                        }
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });

        fsbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    xsum.setForeground(Color.black);
                    String smg=infar.getText();
                    if (!smg.equals("")){

                        smg=OutputControl.lineFeedControl(smg);
                        OutputControl.write(jlum.getText(),smg,2,chatar,headpicture.getIcon());

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("User_send_message");
                        putinfjh.setAccount(jlzh);
                        putinfjh.setPassword(hyzh);
                        putinfjh.setContent(smg);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        infar.setText("");
                        phomap.clear();
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });

        perinfbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame grinf=new JFrame("个人信息");
                grinf.setBounds(800,600,500,550);
                grinf.setResizable(false);

                GridBagLayout bj=new GridBagLayout();
                GridBagConstraints bjkz;
                JPanel pan=new JPanel(bj);
                pan.setBackground(Color.white);
                grinf.add(pan);
                //;
                JButton uspho=new JButton();
                uspho.setFont(new Font("宋体",Font.PLAIN,20));
                uspho.setFocusPainted(false);
                uspho.setContentAreaFilled(false);

                Box nasibox=Box.createVerticalBox();

                Box umbox=Box.createHorizontalBox();
                JLabel um=new JLabel();
                um.setFont(new Font("宋体",Font.BOLD,25));
                umbox.add(um);

                JPanel persipan=new JPanel(bj);
                persipan.setBackground(Color.white);
                JTextArea persi=new JTextArea();
                persi.setLineWrap(true);
                persi.setEditable(false);
                persi.setFont(new Font("宋体",Font.PLAIN,20));
                persipan.add(persi);
                JScrollPane nasigdpan=new JScrollPane(
                        persipan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                nasigdpan.setBackground(Color.white);
                nasigdpan.setBorder(null);

                nasibox.add(Box.createVerticalStrut(30));
                nasibox.add(umbox);
                nasibox.add(Box.createVerticalStrut(30));
                nasibox.add(nasigdpan);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(persi,bjkz);

                JPanel infpan=new JPanel();
                infpan.setBackground(Color.white);
                Box infbox=Box.createVerticalBox();
                infpan.add(infbox);

                Box zhbox=Box.createHorizontalBox();
                JLabel zh=new JLabel();
                zh.setFont(new Font("宋体",Font.PLAIN,20));
                zhbox.add(zh);

                Box xbnlbox=Box.createHorizontalBox();
                JLabel xb=new JLabel();
                xb.setFont(new Font("宋体",Font.PLAIN,20));
                JLabel eage=new JLabel();
                eage.setFont(new Font("宋体",Font.PLAIN,20));
                xbnlbox.add(xb);
                xbnlbox.add(Box.createHorizontalStrut(20));
                xbnlbox.add(eage);

                Box xzbox=Box.createHorizontalBox();
                JLabel xz=new JLabel();
                xz.setFont(new Font("宋体",Font.PLAIN,20));
                xzbox.add(xz);

                Box birthbox=Box.createHorizontalBox();
                JLabel birthLa=new JLabel();
                birthLa.setFont(new Font("宋体",Font.PLAIN,20));
                birthbox.add(birthLa);

                infbox.add(Box.createVerticalStrut(20));
                infbox.add(zhbox);
                infbox.add(Box.createVerticalStrut(20));
                infbox.add(xbnlbox);
                infbox.add(Box.createVerticalStrut(20));
                infbox.add(xzbox);
                infbox.add(Box.createVerticalStrut(20));
                infbox.add(birthbox);

                pan.add(uspho);pan.add(nasibox);
                pan.add(infpan);


                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridx=0;bjkz.gridy=0;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0.6;bjkz.weighty=1;
                bj.setConstraints(uspho,bjkz);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridx=1;bjkz.gridy=0;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bj.setConstraints(nasibox,bjkz);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridx=0;bjkz.gridy=1;
                bjkz.gridwidth=2;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bj.setConstraints(infpan,bjkz);

                //;

                initgrinf(uspho,um,persi,zh,xb,eage,xz,birthLa);

                grinf.setVisible(true);
            }
        });

        schy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame schy=new JFrame("删除好友");
                schy.setBounds(800,600,350,120);
                schy.setResizable(false);

                JPanel pan=new JPanel();
                schy.add(pan);

                JButton qrbut=new JButton("确认");
                qrbut.setContentAreaFilled(false);
                qrbut.setFocusPainted(false);
                qrbut.setFont(new Font("宋体",Font.PLAIN,20));

                JButton qxbut=new JButton("取消");
                qxbut.setContentAreaFilled(false);
                qxbut.setFocusPainted(false);
                qxbut.setFont(new Font("宋体",Font.PLAIN,20));

                pan.add(qrbut);
                pan.add(Box.createHorizontalStrut(40));
                pan.add(qxbut);

                schy.setVisible(true);

                qrbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("Delete_friends");
                            putinfjh.setAccount(jlzh);
                            putinfjh.setPassword(hyzh);

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();

                            schy.dispose();
                            hygdbox.remove(zsbox);
                            chatjp.removeAll();
                            ltfuncpan.removeAll();
                            inftexjp.removeAll();
                            fsjp.removeAll();
                            funcjp.removeAll();
                        }catch (Exception a){a.printStackTrace();}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        qrbut.setText("确认");
                    }
                });

                qxbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        schy.dispose();
                    }
                });
            }
        });
    }

    public void run(){
        UserMessage userMessage=new UserMessage(chatroom,chatar,jlzh,hyzh,xsum,hypho);
        userMessage.start();

        UserInfor userInfor=new UserInfor(chatroom,jlzh,hyzh,hypho,xsum,xszt,zsbox,hygdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp);
        userInfor.start();

        while (true) {
            try {
                if (threadout==1||zserver.isClosed()){
                    closeSocket();
                    break;
                }

                Thread.sleep(2000);
            }catch (Exception e){e.printStackTrace();}
        }
    }
}