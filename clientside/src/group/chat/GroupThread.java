package group.chat;

import org.jdesktop.swingx.JXDatePicker;
import utils.CreateCommonExpressions;
import utils.InforMationSet;
import utils.OutputControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class GroupThread extends Thread{
    int threadout=0;
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    Box zsbox=Box.createHorizontalBox();

    JButton qunpho=new JButton();

    JButton xsqmbut=new JButton();

    JTextPane chatar=new JTextPane();

    Box ltfuncbox=Box.createHorizontalBox();

    Box sendwjbox=Box.createVerticalBox();
    JButton sendwjbut=new JButton("发送文件");

    Box receivewjbox=Box.createVerticalBox();
    JButton receivewjbut=new JButton("接收文件");

    Box  languagebox=Box.createVerticalBox();
    JButton languagebut=new JButton("常用语");

    Box recordbox=Box.createVerticalBox();
    JButton recordbut=new JButton("聊天记录");

    Box emotibox=Box.createVerticalBox();
    JButton emotibut=new JButton("图片");

    JTextPane inftexar=new JTextPane();

    JButton fsbut=new JButton("发送");

    Box funcbox=Box.createVerticalBox();

    Box qunzlbox=Box.createHorizontalBox();
    JButton qunzl=new JButton("群资料");

    Box quncybox=Box.createHorizontalBox();
    JButton quncy=new JButton("群成员");

    Box rqsqbox=Box.createHorizontalBox();
    JButton rqsq=new JButton("入群申请");

    Box yqrqbox=Box.createHorizontalBox();
    JButton yqrq=new JButton("邀请入群");

    Box tuqunbox=Box.createHorizontalBox();
    JButton tuqun=new JButton("退出此群");

    Box jsqunbox=Box.createHorizontalBox();
    JButton jsqun=new JButton("解散此群");

    //;
    Socket zserver;
    String qunm;
    String qunid;
    JLabel jb=new JLabel();
    String jbsmg;
    JFrame chatroom;
    Box qungdbox;
    JPanel chatjp;
    JPanel ltfuncpan;
    JPanel inftexjp;
    JPanel fsjp;
    JPanel funcjp;
    String jlzh;
    JButton jlum;
    ImageIcon ximageic;
    ImageIcon dimageic;
    JLabel headpicture;

    public GroupThread(Socket zserver,String qunm, String qunid, String jb, JFrame chatroom, Box qungdbox, JPanel chatjp, JPanel ltfuncpan, JPanel inftexjp, JPanel fsjp, JPanel funcjp, String jlzh,JButton jlum,ImageIcon ximageic, JLabel headpicture){
        try {
            this.zserver=zserver;
            this.qunm=qunm;
            this.qunid=qunid;
            this.jb.setText(jb);
            this.jbsmg=jb;
            this.chatroom=chatroom;
            this.qungdbox=qungdbox;
            this.chatjp=chatjp;
            this.ltfuncpan=ltfuncpan;
            this.inftexjp=inftexjp;
            this.fsjp=fsjp;
            this.funcjp=funcjp;
            this.jlzh=jlzh;
            this.jlum=jlum;
            this.ximageic=ximageic;
            this.headpicture=headpicture;

            initzj();

            xsqmbut.setText(qunm);
            qungdbox.add(zsbox);
            chatroom.repaint();
            chatroom.setVisible(true);

            System.out.println("群聊 "+qunm+" 连接成功");

            initact();
            initsocket();
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
        qunpho.setFocusPainted(false);
        qunpho.setContentAreaFilled(false);
        if (ximageic!=null){
            qunpho.setBorderPainted(false);
            qunpho.setIcon(ximageic);
        }else {
            qunpho.setFont(new Font("宋体",Font.PLAIN,20));
            qunpho.setBorderPainted(true);
            qunpho.setText("无");
        }

        xsqmbut.setBorderPainted(false);
        xsqmbut.setContentAreaFilled(false);
        xsqmbut.setFont(new Font("宋体",Font.PLAIN,20));

        zsbox.add(qunpho);
        zsbox.add(xsqmbut);

        chatar.setFont(new Font("宋体",Font.PLAIN,20));
        chatar.setEditable(false);

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

        inftexar.setFont(new Font("宋体",Font.PLAIN,20));

        fsbut.setFont(new Font("宋体",Font.PLAIN,20));
        fsbut.setContentAreaFilled(false);

        qunzl.setContentAreaFilled(false);
        qunzl.setFont(new Font("宋体",Font.PLAIN,20));
        qunzlbox.add(qunzl);
        funcbox.add(qunzlbox);

        quncy.setContentAreaFilled(false);
        quncy.setFont(new Font("宋体",Font.PLAIN,20));
        quncybox.add(quncy);
        funcbox.add(Box.createVerticalStrut(20));
        funcbox.add(quncybox);

        rqsq.setContentAreaFilled(false);
        rqsq.setFont(new Font("宋体",Font.PLAIN,20));
        rqsqbox.add(rqsq);
        if (jbsmg.equals("1")||jbsmg.equals("2")) {
            funcbox.add(Box.createVerticalStrut(20));
            funcbox.add(rqsqbox);
        }

        yqrq.setContentAreaFilled(false);
        yqrq.setFont(new Font("宋体",Font.PLAIN,20));
        yqrqbox.add(yqrq);
        if (jbsmg.equals("1")||jbsmg.equals("2")) {
            funcbox.add(Box.createVerticalStrut(20));
            funcbox.add(yqrqbox);
        }

        tuqun.setContentAreaFilled(false);
        tuqun.setFont(new Font("宋体",Font.PLAIN,20));
        tuqunbox.add(tuqun);
        if (!jbsmg.equals("1")) {
            funcbox.add(Box.createVerticalStrut(20));
            funcbox.add(tuqunbox);
        }

        jsqun.setContentAreaFilled(false);
        jsqun.setFont(new Font("宋体",Font.PLAIN,20));
        jsqunbox.add(jsqun);
        if (jbsmg.equals("1")) {
            funcbox.add(Box.createVerticalStrut(20));
            funcbox.add(jsqunbox);
        }
    }

    private void initNews(JTextPane chatar){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Group_get_news");
            putinfjh.setAccount(jlzh);
            putinfjh.setGroupid(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();
            while (true){
                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
                String state=getinfjh.getState();
                if (state.equals("1")){
                    String fqr=getinfjh.getNickname();
                    String xx=getinfjh.getContent();
                    ImageIcon icon=getinfjh.getHeadPicture();
                    ImageIcon iconpho=getinfjh.getMaxHeadPicture();
                    int dqfs=Integer.parseInt(getinfjh.getCode());

                    if (dqfs==0)
                        if (iconpho!=null){
                            OutputControl.writePho(fqr,iconpho,dqfs,chatar,icon);
                        }else
                            OutputControl.write(fqr,xx,dqfs,chatar,icon);
                    else
                        if (iconpho!=null){
                            OutputControl.writePho(fqr,iconpho,dqfs,chatar,icon);
                        }else
                            OutputControl.write(fqr,xx,dqfs,chatar,icon);
                }
                if (state.equals("2"))break;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void initqunzl(JButton qundphobut,JLabel qunmLa,JTextArea qunmpi,JLabel qunidLa){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("Init_group_infor");
            putinfjh.setGroupid(qunid);

            obputFW.writeObject(putinfjh);
            obputFW.flush();

            InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();
            String qunname=getinfjh.getNickname();
            String qunqm=getinfjh.getSignature();
            ImageIcon imageic=getinfjh.getMaxHeadPicture();

            if (qunqm==null)qunqm="(群签名)";
            if (qunqm!=null){
                if (qunqm.equals(""))
                    qunqm="(群签名)";
            }

            if (imageic!=null){
                qundphobut.setBorderPainted(false);
                qundphobut.setBorder(null);
                qundphobut.setText(null);
                qundphobut.setIcon(imageic);
                dimageic=imageic;
            }else {
                qundphobut.setBorderPainted(true);
                qundphobut.setText("(快换上群专属头像)");
            }

            qunm=qunname;
            qunidLa.setText(qunid);
            qunmLa.setText(qunname);
            qunmpi.setText(qunqm);
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

    private void initact(){
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

        xsqmbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                xsqmbut.setForeground(Color.black);
                chatjp.removeAll();
                ltfuncpan.removeAll();
                inftexjp.removeAll();
                fsjp.removeAll();
                funcjp.removeAll();
                chatjp.add(chatar);
                ltfuncpan.add(ltfuncbox);
                inftexjp.add(inftexar);
                fsjp.add(fsbut);
                funcjp.add(funcbox);
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
                        putinfjh.setFunction("Send_group_file");
                        putinfjh.setAccount(jlzh);
                        putinfjh.setGroupid(qunid);
                        putinfjh.setNickname(filename);

                        obputFW1.writeObject(putinfjh);
                        obputFW1.flush();

                        OutputStream  putfile=server1.getOutputStream();
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
                JFrame filejm=new JFrame("接收群文件");
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

                CreatGroupFile creatGroupFile=new CreatGroupFile(filejm,zbox,qunid,jb);
                creatGroupFile.start();

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

                CreateCommonExpressions creat=new CreateCommonExpressions(cyyjm,zbox,jlzh,inftexar);
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
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
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

                            GroupChatRecord creat=new GroupChatRecord(jlzh,qunid,chatar,kssmg,jssmg,headpicture);
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
                            putinfjh.setFunction("Group_send_pho");
                            putinfjh.setAccount(jlzh);
                            putinfjh.setGroupid(qunid);
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
                    xsqmbut.setForeground(Color.black);
                    String smg=new String(inftexar.getText());
                    if (!smg.equals("")){
                        inftexar.setText("");

                        smg= OutputControl.lineFeedControl(smg);
                        OutputControl.write(jlum.getText(),smg,2,chatar,headpicture.getIcon());

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Group_send_message");
                        putinfjh.setAccount(jlzh);
                        putinfjh.setGroupid(qunid);
                        putinfjh.setContent(smg);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });

        qunzl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame quninf=new JFrame("群资料");
                quninf.setBounds(800,600,500,550);
                quninf.setResizable(false);

                GridBagLayout bj=new GridBagLayout();
                GridBagConstraints bjkz;
                JPanel pan=new JPanel(bj);
                pan.setBackground(Color.white);
                quninf.add(pan);
                //;
                JButton qunphobut=new JButton();
                qunphobut.setFont(new Font("宋体",Font.PLAIN,20));
                qunphobut.setFocusPainted(false);
                qunphobut.setContentAreaFilled(false);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(qunpho,bjkz);

                Box nasibox=Box.createVerticalBox();

                Box qunmbox=Box.createHorizontalBox();
                JLabel qunmLa=new JLabel("");
                qunmLa.setFont(new Font("宋体",Font.BOLD,25));
                qunmbox.add(qunmLa);

                JPanel qunmpipan=new JPanel(bj);
                qunmpipan.setBackground(Color.white);
                JTextArea qunmpi=new JTextArea("");
                qunmpi.setLineWrap(true);
                qunmpi.setEditable(false);
                qunmpi.setFont(new Font("宋体",Font.PLAIN,20));
                qunmpipan.add(qunmpi);
                JScrollPane nasigdpan=new JScrollPane(
                        qunmpipan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                nasigdpan.setBackground(Color.white);
                nasigdpan.setBorder(null);

                nasibox.add(Box.createVerticalStrut(30));
                nasibox.add(qunmbox);
                nasibox.add(Box.createVerticalStrut(30));
                nasibox.add(nasigdpan);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(qunmpi,bjkz);

                JPanel infpan=new JPanel();
                infpan.setBackground(Color.white);

                Box infbox=Box.createVerticalBox();
                infpan.add(infbox);

                Box qunidbox=Box.createHorizontalBox();
                JLabel qunidLa=new JLabel();
                qunidLa.setFont(new Font("宋体",Font.PLAIN,20));
                qunidbox.add(qunidLa);

                Box pjxxbox=Box.createHorizontalBox();
                JButton pjbut=new JButton("编辑信息");
                pjbut.setFocusPainted(false);
                pjbut.setContentAreaFilled(false);
                pjbut.setFont(new Font("宋体",Font.PLAIN,20));
                pjxxbox.add(pjbut);

                infbox.add(Box.createVerticalStrut(30));
                infbox.add(qunidbox);
                if (jbsmg.equals("1"))
                {
                    infbox.add(Box.createVerticalStrut(30));
                    infbox.add(pjxxbox);
                }

                pan.add(qunphobut);pan.add(nasibox);
                pan.add(infpan);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridx=0;bjkz.gridy=0;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0.6;bjkz.weighty=1;
                bj.setConstraints(qunphobut,bjkz);

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

                initqunzl(qunphobut,qunmLa,qunmpi,qunidLa);

                quninf.repaint();
                quninf.setVisible(true);

                qunphobut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            if (jb.getText().equals("1")){
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
                                    BufferedImage imagebuf= ImageIO.read(phofile);
                                    Image dimage=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                                    Image ximage=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);
                                    dimageic=new ImageIcon(dimage);
                                    ximageic=new ImageIcon(ximage);

                                    qunphobut.setText(null);
                                    qunpho.setBorderPainted(false);
                                    qunphobut.setIcon(dimageic);

                                    qunpho.setText(null);
                                    qunpho.setBorderPainted(false);
                                    qunpho.setIcon(ximageic);

                                    Socket server1=new Socket("127.0.0.1",4000);
                                    ObjectOutputStream obputFW1=new ObjectOutputStream(server1.getOutputStream());

                                    InforMationSet putinfjh=new InforMationSet();
                                    putinfjh.setFunction("Group_photo");
                                    putinfjh.setGroupid(qunid);

                                    obputFW1.writeObject(putinfjh);
                                    obputFW1.flush();

                                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(phofile));
                                    byte[] buf = new byte[1024*1024*1024];
                                    int lenth = 0;

                                    OutputStream putimage=server1.getOutputStream();

                                    while( (lenth = bis.read(buf)) != -1 ) {
                                        putimage.write(buf,0,lenth);
                                        putimage.flush();
                                    }
                                    putimage.flush();
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
                            }
                        }catch (Exception a){a.printStackTrace();}
                    }
                });

                pjbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFrame pjjm=new JFrame("编辑");
                        pjjm.setBounds(800,600,400,400);
                        pjjm.setResizable(false);


                        GridBagLayout bj=new GridBagLayout();
                        GridBagConstraints bjkz;

                        JPanel pan=new JPanel(bj);
                        pjjm.add(pan);

                        JPanel pans=new JPanel();
                        pans.setBackground(Color.white);
                        pan.add(pans);
                        Box box=Box.createVerticalBox();
                        pans.add(box);

                        Box qumbox=Box.createHorizontalBox();
                        JLabel qumLa=new JLabel("群名");
                        qumLa.setFont(new Font("宋体",Font.PLAIN,20));
                        qumbox.add(qumLa);

                        Box qunmtextbox=Box.createHorizontalBox();
                        JTextField qunmtext=new JTextField(15);
                        qunmtext.setText(qunm);
                        qunmtext.setFont(new Font("宋体",Font.PLAIN,20));
                        qunmtextbox.add(qunmtext);

                        Box qunmpbox=Box.createHorizontalBox();
                        JLabel qunmpLa=new JLabel("群名片");
                        qunmpLa.setFont(new Font("宋体",Font.PLAIN,20));
                        qunmpbox.add(qunmpLa);

                        box.add(Box.createVerticalStrut(20));
                        box.add(qunmbox);
                        box.add(Box.createVerticalStrut(10));
                        box.add(qunmtextbox);
                        box.add(Box.createVerticalStrut(30));
                        box.add(qunmpbox);

                        JPanel panx=new JPanel(bj);
                        panx.setBackground(Color.white);
                        pan.add(panx);
                        JTextArea qunmpar=new JTextArea();
                        qunmpar.setLineWrap(true);
                        qunmpar.setText(qunmpi.getText());
                        qunmpar.setFont(new Font("宋体",Font.PLAIN,20));
                        panx.add(qunmpar);

                        JScrollPane panxgd=new JScrollPane(
                                panx,
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                        );
                        pan.add(panxgd);

                        bjkz=new GridBagConstraints();
                        bjkz.fill=GridBagConstraints.BOTH;
                        bjkz.gridwidth=1;bjkz.gridheight=1;
                        bjkz.weightx=1;bjkz.weighty=1;
                        bjkz.gridx=0;bjkz.gridy=0;
                        bj.setConstraints(qunmpar,bjkz);

                        JPanel panzx=new JPanel();
                        Box erbox=Box.createHorizontalBox();
                        JButton erbut=new JButton("确认编辑");
                        erbut.setFocusPainted(false);
                        erbut.setContentAreaFilled(false);
                        erbut.setFont(new Font("宋体",Font.PLAIN,20));
                        erbox.add(erbut);
                        panzx.add(erbox);
                        pan.add(panzx);

                        bjkz=new GridBagConstraints();
                        bjkz.fill=GridBagConstraints.BOTH;
                        bjkz.gridwidth=1;bjkz.gridheight=1;
                        bjkz.weightx=1;bjkz.weighty=1;
                        bjkz.gridx=0;bjkz.gridy=0;
                        bj.setConstraints(pans,bjkz);

                        bjkz=new GridBagConstraints();
                        bjkz.fill=GridBagConstraints.BOTH;
                        bjkz.gridwidth=1;bjkz.gridheight=1;
                        bjkz.weightx=1;bjkz.weighty=2;
                        bjkz.gridx=0;bjkz.gridy=1;
                        bj.setConstraints(panxgd,bjkz);

                        bjkz=new GridBagConstraints();
                        bjkz.fill=GridBagConstraints.BOTH;
                        bjkz.gridwidth=1;bjkz.gridheight=1;
                        bjkz.weightx=1;bjkz.weighty=0.2;
                        bjkz.gridx=0;bjkz.gridy=2;
                        bj.setConstraints(panzx,bjkz);

                        pjjm.setVisible(true);

                        erbut.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                    String qum=qunmtext.getText();
                                    String qunqm=qunmpar.getText();

                                    if (qum.equals("")){
                                        erbut.setText("群名不能为空");
                                    }
                                    else {
                                        InforMationSet putinfjh=new InforMationSet();
                                        putinfjh.setFunction("Edit_data");
                                        putinfjh.setGroupid(qunid);
                                        putinfjh.setNickname(qum);
                                        putinfjh.setSignature(qunqm);

                                        if (qum.equals(qunm))
                                        putinfjh.setState("2");
                                        else {
                                        putinfjh.setState("1");
                                        qunm=qum;
                                        xsqmbut.setText(qunm);
                                        }

                                        obputFW.writeObject(putinfjh);
                                        obputFW.flush();

                                        pjjm.dispose();
                                    }
                                }catch (Exception a){a.printStackTrace();}
                            }
                            @Override
                            public void mouseExited(MouseEvent e) {
                                erbut.setText("确认编辑");
                            }
                        });
                    }
                });

            }
        });

        quncy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Socket server=new Socket("127.0.0.1",4000);
                    ObjectOutputStream obputFW=new ObjectOutputStream(server.getOutputStream());
                    ObjectInputStream obgetFW=new ObjectInputStream(server.getInputStream());

                    quncy.setSelected(false);
                    JFrame cyjm=new JFrame("群成员");
                    cyjm.setBounds(800,600,500,450);
                    cyjm.setResizable(false);

                    JPanel pan=new JPanel();
                    JScrollPane gdpan=new JScrollPane(
                            pan,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                    );
                    cyjm.add(gdpan);

                    Box zbox=Box.createVerticalBox();

                    pan.add(zbox);

                    CreateMember creat=new CreateMember(cyjm,zbox,qunid,jb,obputFW,obgetFW,funcbox,qunzlbox,quncybox,rqsqbox,yqrqbox,tuqunbox,jsqunbox);
                    creat.start();

                    cyjm.setVisible(true);

                    cyjm.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            try {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Close");

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                server.close();
                            }catch (Exception a){a.printStackTrace();}
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Close");

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                server.close();
                            }catch (Exception a){a.printStackTrace();}
                        }
                    });

                }catch (Exception a){a.printStackTrace();}
            }
        });

        rqsq.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Socket server=new Socket("127.0.0.1",4000);
                    ObjectOutputStream obputFW=new ObjectOutputStream(server.getOutputStream());
                    ObjectInputStream obgetFW=new ObjectInputStream(server.getInputStream());

                    JFrame rqsqjm=new JFrame("入群申请");
                    rqsqjm.setResizable(false);
                    rqsqjm.setBounds(800,600,350,400);

                    JPanel pan=new JPanel();
                    JScrollPane gdpan=new JScrollPane(
                            pan,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                    );
                    rqsqjm.add(gdpan);

                    Box rtbox=Box.createVerticalBox();

                    CreateGroupApplication creat=new CreateGroupApplication(rqsqjm,rtbox,qunid,obputFW,obgetFW);
                    creat.start();

                    rqsqjm.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            try {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Close");

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                server.close();
                            }catch (Exception a){a.printStackTrace();}
                        }

                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Close");

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                server.close();
                            }catch (Exception a){a.printStackTrace();}
                        }
                    });


                    pan.add(rtbox);
                    rqsqjm.repaint();
                    rqsqjm.setVisible(true);
                }catch (Exception a){a.printStackTrace();}
            }
        });

        yqrq.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame yqrqjm=new JFrame("邀请入群");
                yqrqjm.setResizable(false);
                yqrqjm.setBounds(800,600,350,220);

                JPanel pan=new JPanel();
                yqrqjm.add(pan);

                Box zbox=Box.createVerticalBox();

                Box qidLabox=Box.createHorizontalBox();
                JLabel qidLa=new JLabel("请输入要邀请人的账号");
                qidLa.setFont(new Font("宋体",Font.PLAIN,20));
                qidLabox.add(qidLa);

                Box qidtextbox=Box.createHorizontalBox();
                JTextField qidtext=new JTextField(12);
                qidtext.setFont(new Font("宋体",Font.PLAIN,20));
                qidtextbox.add(qidtext);

                Box fsbox=Box.createHorizontalBox();
                JButton fasqq=new JButton("发送邀请");
                fasqq.setFont(new Font("宋体",Font.PLAIN,20));
                fasqq.setContentAreaFilled(false);
                fsbox.add(fasqq);

                zbox.add(Box.createVerticalStrut(30));
                zbox.add(qidLabox);
                zbox.add(Box.createVerticalStrut(20));
                zbox.add(qidtextbox);
                zbox.add(Box.createVerticalStrut(20));
                zbox.add(fsbox);

                pan.add(zbox);

                yqrqjm.setVisible(true);

                fasqq.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try{
                            String yqzh=new String(qidtext.getText());

                            if (yqzh.equals(""))
                                fasqq.setText("请填写账号");
                            else{
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Invite_into_group");
                                putinfjh.setGroupid(qunid);
                                putinfjh.setAccount(yqzh);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();

                                String state=getinfjh.getState();
                                if (state.equals("1"))
                                    fasqq.setText("此用户不存在");
                                else
                                if (state.equals("2"))
                                    fasqq.setText("邀请的人已在群中");
                                else
                                if (state.equals("3"))
                                    fasqq.setText("此用户正在申请加入此群");
                                else
                                if (state.equals("4"))
                                    fasqq.setText("请勿重复邀请");
                                else
                                if (state.equals("5"))
                                    fasqq.setText("发送成功，等待同意");
                            }
                        }catch (Exception a){}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {fasqq.setText("发送邀请");
                    }
                });
            }
        });

        tuqun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFrame xs=new JFrame("退群");
                    xs.setBounds(800,600,400,100);
                    xs.setResizable(false);

                    JPanel pan=new JPanel();
                    xs.add(pan);

                    Box box=Box.createHorizontalBox();
                    JButton qr=new JButton("确认");
                    qr.setFont(new Font("宋体",Font.PLAIN,20));
                    qr.setFocusPainted(false);
                    qr.setContentAreaFilled(false);
                    JButton qx=new JButton("取消");
                    qx.setFont(new Font("宋体",Font.PLAIN,20));
                    qx.setFocusPainted(false);
                    qx.setContentAreaFilled(false);
                    box.add(qr);
                    box.add(qx);

                    pan.add(box);

                    xs.setVisible(true);

                    qr.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Drop_out_group");
                                putinfjh.setAccount(jlzh);
                                putinfjh.setGroupid(qunid);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                threadout=1;

                                xs.dispose();
                                qungdbox.remove(zsbox);
                                chatjp.removeAll();
                                ltfuncpan.removeAll();
                                inftexjp.removeAll();
                                fsjp.removeAll();
                                funcjp.removeAll();
                                chatroom.repaint();
                                chatroom.setVisible(true);
                            }catch (Exception a){a.printStackTrace();}
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            qr.setText("确认");
                        }
                    });

                    qx.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            xs.dispose();
                        }
                    });

                }catch (Exception a){}
            }
        });

        jsqun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Member_number");
                    putinfjh.setGroupid(qunid);

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();

                    InforMationSet getinfjh=(InforMationSet)obgetFW.readObject();

                    String state=getinfjh.getState();
                    int rs=Integer.parseInt(state);
                    if (rs>=5){
                        JFrame jsjm=new JFrame("注意");
                        jsjm.setBounds(800,600,250,100);
                        jsjm.setResizable(false);

                        JPanel pan=new JPanel();
                        jsjm.add(pan);


                        JLabel tsLa=new JLabel("人数大于5人,不能解散");
                        tsLa.setFont(new Font("宋体",Font.PLAIN,20));
                        pan.add(tsLa);

                        jsjm.setVisible(true);
                    }
                    else {
                        JFrame jsjm=new JFrame("注意");
                        jsjm.setBounds(800,600,400,150);
                        jsjm.setResizable(false);

                        JPanel pan=new JPanel();
                        jsjm.add(pan);

                        Box vbox=Box.createVerticalBox();
                        Box hbox=Box.createHorizontalBox();

                        JButton qrbut=new JButton("再次确认");
                        qrbut.setFont(new Font("宋体",Font.PLAIN,20));
                        qrbut.setContentAreaFilled(false);
                        qrbut.setFocusPainted(false);

                        JButton xqbut=new JButton("取消");
                        xqbut.setFont(new Font("宋体",Font.PLAIN,20));
                        xqbut.setFocusPainted(false);
                        xqbut.setContentAreaFilled(false);

                        hbox.add(qrbut);
                        hbox.add(Box.createHorizontalStrut(20));
                        hbox.add(xqbut);

                        vbox.add(Box.createVerticalStrut(20));
                        vbox.add(hbox);

                        pan.add(vbox);

                        jsjm.setVisible(true);


                        qrbut.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                    InforMationSet putinfjh=new InforMationSet();
                                    putinfjh.setFunction("Dis_band");
                                    putinfjh.setGroupid(qunid);

                                    obputFW.writeObject(putinfjh);
                                    obputFW.flush();

                                    jsjm.dispose();

                                    JFrame jscgjm=new JFrame("注意");
                                    jscgjm.setBounds(800,600,250,100);
                                    jscgjm.setResizable(false);

                                    JPanel pan=new JPanel();

                                    JLabel tsLa=new JLabel("解散成功");
                                    tsLa.setFont(new Font("宋体",Font.PLAIN,25));

                                    pan.add(tsLa);

                                    jscgjm.setVisible(true);
                                }catch (Exception a){a.printStackTrace();}
                            }
                        });

                        xqbut.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                jsjm.dispose();
                            }
                        });
                    }
                }catch (Exception a){}
            }
        });
    }

    public void run() {
        GroupMessage groupMessage=new GroupMessage(chatroom,chatar,jlzh,qunid,xsqmbut,headpicture);
        groupMessage.start();

        GroupInfor groupInfor=new GroupInfor(jb,chatroom,jlzh,qunid,qunpho,xsqmbut,zsbox,qungdbox, chatjp,ltfuncpan,inftexjp,fsjp,funcjp,funcbox,qunzlbox,quncybox,rqsqbox,yqrqbox,tuqunbox,jsqunbox);
        groupInfor.start();

        while (true){
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