package main.iterface;


import org.jdesktop.swingx.JXDatePicker;
import utils.InforMationSet;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PersonalInformation {

    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    String jlzh;
    JButton jlum;
    JLabel headpicture;

    public PersonalInformation(String jlzh,JButton jlum,JLabel headpicture){
        try {
            this.jlzh=jlzh;
            this.jlum=jlum;
            this.headpicture=headpicture;
            initSocket();
        }catch (Exception e){e.printStackTrace();}
    }

    private void initSocket(){
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
            putinfjh.setAccount(jlzh);

            obputFW.writeObject(putinfjh);
            obputFW.flush();

            InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
            String nickname=getinfjh.getNickname();
            String spersi=getinfjh.getSignature();
            String sxb=getinfjh.getGender();
            String birthday=getinfjh.getBirthday();
            ImageIcon icon=getinfjh.getMaxHeadPicture();

            if (icon!=null){
                uspho.setText(null);
                uspho.setBorderPainted(false);
                uspho.setIcon(icon);
            }
            else {
                uspho.setBorderPainted(true);
                uspho.setText("快换上头像吧");
            }

            if(spersi==null)spersi="(快来填写，属于自己的签名)";
            if (spersi!=null){
                if (spersi.equals("")){
                    spersi="(快来填写，属于自己的签名)";
                }
            }

            if(sxb==null)sxb="(性别)";
            if (sxb!=null){
                if (sxb.equals(""))
                    sxb="(性别)";
            }

            if (birthday==null)
            {birthLa.setText("(生日)");
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

    private void setbirthday(String birthdaysmg,Calendar ca){
        try {
            int k=1;
            int wz=1;
            int year=0,month=0,day=0;
            char []jl=birthdaysmg.toCharArray();

            for(int i=0;i<birthdaysmg.length();i++){
                if (jl[i]=='/'){
                    k=0;
                    wz++;
                }else k=1;

                if(wz==1&&k==1){
                    year*=10;
                    year+=jl[i]-'0';
                }
                if (wz==2&&k==1){
                    month*=10;
                    month+=jl[i]-'0';
                }
                if (wz==3&&k==1){
                    day*=10;
                    day+=jl[i]-'0';
                }

            }
            ca.set(year,month-1,day);
        }catch (Exception e){e.printStackTrace();}
    }

    private boolean judgBirth(String birthday){
        try {
            Calendar birthdateca=Calendar.getInstance();
            setbirthday(birthday,birthdateca);

            Calendar nowdateca=Calendar.getInstance();
            Date nowdate=new Date();
            nowdateca.setTime(nowdate);

            int birthyyyy=birthdateca.get(Calendar.YEAR);
            int birthMM=birthdateca.get(Calendar.MONTH)+1;
            int birthdd=birthdateca.get(Calendar.DAY_OF_MONTH);

            int nowyyyy=nowdateca.get(Calendar.YEAR);
            int nowMM=nowdateca.get(Calendar.MONTH)+1;
            int nowdd=nowdateca.get(Calendar.DAY_OF_MONTH);

            if(birthyyyy>nowyyyy)return false;
            else {
                if (birthyyyy==nowyyyy){
                    if (birthMM>nowMM)return false;
                    else {
                        if (birthMM==nowMM){
                            if (birthdd>nowdd)return false;
                        }
                    }
                }
            }

        }catch (Exception e){e.printStackTrace();}
        return true;
    }

    public void interFace(){
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

        Box pjbox=Box.createHorizontalBox();
        JButton pjbut=new JButton("编辑资料");
        pjbut.setFont(new Font("宋体",Font.PLAIN,20));
        pjbut.setContentAreaFilled(false);
        pjbut.setFocusPainted(false);
        pjbox.add(pjbut);

        infbox.add(Box.createVerticalStrut(20));
        infbox.add(zhbox);
        infbox.add(Box.createVerticalStrut(20));
        infbox.add(xbnlbox);
        infbox.add(Box.createVerticalStrut(20));
        infbox.add(xzbox);
        infbox.add(Box.createVerticalStrut(20));
        infbox.add(birthbox);
        infbox.add(Box.createVerticalStrut(20));
        infbox.add(pjbox);

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

        uspho.addMouseListener(new MouseAdapter() {
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
                        BufferedImage imagebuf= ImageIO.read(phofile);
                        Image image=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                        ImageIcon imageic=new ImageIcon(image);

                        uspho.setText(null);
                        uspho.setBorderPainted(false);
                        uspho.setIcon(imageic);
                        headpicture.setIcon(imageic);

                        Socket server1=new Socket("127.0.0.1",4000);
                        ObjectOutputStream obputFW1=new ObjectOutputStream(server1.getOutputStream());

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("User_photo");
                        putinfjh.setAccount(jlzh);

                        obputFW1.writeObject(putinfjh);
                        obputFW1.flush();

                        OutputStream  putimage=server1.getOutputStream();
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(phofile));
                        byte[] buf = new byte[1024*1024*1024];
                        int lenth = 0;

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
                }catch (Exception a){a.printStackTrace();}
            }
        });

        pjbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame pjjm=new JFrame("信息编辑");
                pjjm.setResizable(false);
                pjjm.setBounds(800,600,550,450);

                GridBagLayout bj=new GridBagLayout();
                GridBagConstraints bjkz;
                JPanel pan=new JPanel(bj);
                pjjm.add(pan);

                Box yhbox=Box.createHorizontalBox();
                JLabel umLa=new JLabel("昵 称:");
                umLa.setFont(new Font("宋体",Font.PLAIN,20));
                JTextField umtext=new JTextField(15);
                umtext.setText(um.getText());
                umtext.setFont(new Font("宋体",Font.PLAIN,20));

                yhbox.add(umLa);
                yhbox.add(Box.createHorizontalStrut(20));
                yhbox.add(umtext);

                Box rhbox=Box.createHorizontalBox();
                JLabel GenderLa=new JLabel("性 别:");
                GenderLa.setFont(new Font("宋体",Font.PLAIN,20));

                ButtonGroup GenderGroup=new ButtonGroup();
                JRadioButton menGender=new JRadioButton("男");
                menGender.setFont(new Font("宋体",Font.PLAIN,20));
                menGender.setContentAreaFilled(false);
                GenderGroup.add(menGender);
                JRadioButton femaleGender=new JRadioButton("女");
                femaleGender.setFont(new Font("宋体",Font.PLAIN,20));
                femaleGender.setContentAreaFilled(false);
                GenderGroup.add(femaleGender);
                if (!xb.getText().equals("(性别)"))
                if (xb.getText().equals("男"))
                    menGender.setSelected(true);
                else
                    femaleGender.setSelected(true);

                rhbox.add(GenderLa);
                rhbox.add(Box.createHorizontalStrut(20));
                rhbox.add(menGender);
                rhbox.add(Box.createHorizontalStrut(20));
                rhbox.add(femaleGender);

                Box chbox=Box.createHorizontalBox();
                JLabel birthday=new JLabel("生 日:");
                birthday.setFont(new Font("宋体",Font.PLAIN,20));

                Calendar ca=Calendar.getInstance();
                String birthdaysmg=birthLa.getText();
                if (!birthdaysmg.equals("(生日)"))
                setbirthday(birthdaysmg,ca);
                Date date=ca.getTime();

                JXDatePicker jdp=new JXDatePicker();
                jdp.setDate(date);
                jdp.setFont(new Font("宋体",Font.PLAIN,20));
                jdp.setFormats("yyyy/MM/dd");

                chbox.add(birthday);
                chbox.add(Box.createHorizontalStrut(20));
                chbox.add(jdp);

                Box shbox=Box.createHorizontalBox();
                JLabel qmLa=new JLabel("签 名");
                qmLa.setFont(new Font("宋体",Font.PLAIN,20));

                shbox.add(qmLa);

                JPanel qmpan=new JPanel(bj);
                qmpan.setBackground(Color.white);
                JTextArea qmar=new JTextArea();
                qmar.setLineWrap(true);
                qmar.setText(persi.getText());
                qmar.setFont(new Font("宋体",Font.PLAIN,20));
                qmpan.add(qmar);
                JScrollPane qmgdpan=new JScrollPane(
                        qmpan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=1;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(qmar,bjkz);


                Box whbox=Box.createHorizontalBox();
                JButton tjbut=new JButton("确认编辑");
                tjbut.setFont(new Font("宋体",Font.PLAIN,20));
                tjbut.setFocusPainted(false);
                tjbut.setContentAreaFilled(false);

                whbox.add(tjbut);

                pan.add(yhbox);
                pan.add(rhbox);
                pan.add(chbox);
                pan.add(shbox);
                pan.add(qmgdpan);
                pan.add(whbox);


                bjkz=new GridBagConstraints();
                //bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=0;
                bj.setConstraints(yhbox,bjkz);

                bjkz=new GridBagConstraints();
                //bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=1;
                bj.setConstraints(rhbox,bjkz);

                bjkz=new GridBagConstraints();
                //bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=2;
                bj.setConstraints(chbox,bjkz);

                bjkz=new GridBagConstraints();
                //bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=3;
                bj.setConstraints(shbox,bjkz);

                bjkz=new GridBagConstraints();
                bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=4;
                bj.setConstraints(qmgdpan,bjkz);

                bjkz=new GridBagConstraints();
               // bjkz.fill=GridBagConstraints.BOTH;
                bjkz.gridwidth=1;bjkz.gridheight=1;
                bjkz.weightx=0;bjkz.weighty=1;
                bjkz.gridx=0;bjkz.gridy=5;
                bj.setConstraints(whbox,bjkz);

                pjjm.setVisible(true);

                tjbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String usm=umtext.getText();
                        String gender=menGender.isSelected()?"男":femaleGender.isSelected()?"女":"";
                        String gxqm=qmar.getText();
                        Date date=jdp.getDate();
                        Calendar ca=Calendar.getInstance();
                        ca.setTime(date);
                        String birthday=new String();
                        birthday+=ca.get(Calendar.YEAR)+"/";
                        birthday+=ca.get(Calendar.MONTH)+1+"/";
                        birthday+=ca.get(Calendar.DAY_OF_MONTH);

                        if (usm.equals(""))
                            tjbut.setText("昵称不能为空");
                        else if (!judgBirth(birthday)){
                            tjbut.setText("生日设置错误");
                        }
                        else {
                            try {

                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setFunction("Edit_infor");
                            putinfjh.setAccount(jlzh);
                            putinfjh.setNickname(usm);
                            putinfjh.setGender(gender);
                            putinfjh.setBirthday(birthday);
                            putinfjh.setSignature(gxqm);

                            if (usm.equals(jlum.getText()))
                            putinfjh.setState("2");
                            else {
                            putinfjh.setState("1");
                            jlum.setText(usm);
                            }

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();

                            pjjm.dispose();
                            grinf.dispose();
                            pjbut.setText("编辑成功");
                            }catch (Exception a){a.printStackTrace();}
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        tjbut.setText("确认编辑");
                    }
                });
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                pjbut.setText("编辑资料");
            }
        });

        grinf.addWindowListener(new WindowAdapter() {
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
