package main.iterface;

/*import userchat.CreateFriend;
import chat.CreateGroupChat;*/
import user.chat.CreateFriend;
import group.chat.CreateGroup;
import utils.InforMationSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class MainInterface {

    Socket zserver;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    JFrame chatroom;
    //;
    String jlzh;
    JButton jlum=new JButton();
    JLabel headpicture=new JLabel();

    public MainInterface(String jlzh,String jlum,ImageIcon headpicture){
        try {
            this.jlzh=jlzh;
            this.jlum.setText(jlum);
            this.headpicture.setIcon(headpicture);

            initSocket();
        }catch (Exception e){e.printStackTrace();}
    }

    public void initSocket(){
        try {
            zserver=new Socket("127.0.0.1",4000);
            obputFW=new ObjectOutputStream(zserver.getOutputStream());
            obgetFW=new ObjectInputStream(zserver.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    public void closeSocket(){
        try {
            zserver.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void ChatRoom(){
        chatroom=new JFrame("QQ聊天室");
        chatroom.setBounds(700,300,1400,1100);

        GridBagLayout pj=new GridBagLayout();
        GridBagConstraints pjkz;
        JPanel pan=new JPanel(pj);
        chatroom.add(pan);

        Box fz1box=Box.createHorizontalBox();
        JButton grinf=new JButton("个人信息");
        grinf.setFont(new Font("宋体",Font.PLAIN,20));
        grinf.setBorderPainted(false);
        grinf.setContentAreaFilled(false);

        JButton hycaht=new JButton("好友私聊");
        hycaht.setFont(new Font("宋体",Font.PLAIN,20));
        hycaht.setContentAreaFilled(false);
        hycaht.setBorderPainted(false);

        JButton quncaht=new JButton("群聊");
        quncaht.setFont(new Font("宋体",Font.PLAIN,20));
        quncaht.setBorderPainted(false);
        quncaht.setContentAreaFilled(false);
        fz1box.add(grinf);fz1box.add(hycaht);fz1box.add(quncaht);

        JPanel gdrt=new JPanel();
        Box hygdbox=Box.createVerticalBox();
        Box qungdbox=Box.createVerticalBox();
        JScrollPane zgd=new JScrollPane(
                gdrt,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel chatjp=new JPanel(new BorderLayout());
        chatjp.setBorder(BorderFactory.createLineBorder(Color.white, 2));
        JScrollPane chatgdjp=new JScrollPane(
                chatjp,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        JPanel ltfuncpan=new JPanel(new BorderLayout());

        JPanel inftexjp=new JPanel(new BorderLayout());
        inftexjp.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        JScrollPane inftexgd=new JScrollPane(
                inftexjp,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel fsjp=new JPanel(new BorderLayout());
        fsjp.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        JPanel funcjp=new JPanel(new BorderLayout());
        funcjp.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        Box fz2box=Box.createHorizontalBox();
        JButton tjhybut=new JButton("添加好友");
        tjhybut.setFont(new Font("宋体",Font.PLAIN,20));
        tjhybut.setBorderPainted(false);
        tjhybut.setContentAreaFilled(false);

        JButton hysqbut=new JButton("好友申请");
        hysqbut.setFont(new Font("宋体",Font.PLAIN,20));
        hysqbut.setBorderPainted(false);
        hysqbut.setContentAreaFilled(false);

        JButton joinqunbut=new JButton("加入群聊");
        joinqunbut.setFont(new Font("宋体",Font.PLAIN,20));
        joinqunbut.setBorderPainted(false);
        joinqunbut.setContentAreaFilled(false);

        JButton qunyqbut=new JButton("群聊邀请");
        qunyqbut.setFont(new Font("宋体",Font.PLAIN,20));
        qunyqbut.setBorderPainted(false);
        qunyqbut.setContentAreaFilled(false);

        JButton creatqunbut=new JButton("创建群聊");
        creatqunbut.setFont(new Font("宋体",Font.PLAIN,20));
        creatqunbut.setBorderPainted(false);
        creatqunbut.setContentAreaFilled(false);
        fz2box.add(tjhybut);fz2box.add(hysqbut);fz2box.add(joinqunbut);fz2box.add(qunyqbut);fz2box.add(creatqunbut);

        pan.add(fz1box);
        pan.add(zgd);
        pan.add(chatgdjp);
        pan.add(ltfuncpan);
        pan.add(inftexgd);
        pan.add(fsjp);
        pan.add(funcjp);
        pan.add(fz2box);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=7;pjkz.gridheight=1;
        pjkz.weightx=0;pjkz.weighty=0.5;
        pjkz.gridx=0;pjkz.gridy=0;
        pj.setConstraints(fz1box,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=2;pjkz.gridheight=5;
        pjkz.weightx=0.5;pjkz.weighty=9;
        pjkz.gridx=0;pjkz.gridy=1;
        pj.setConstraints(zgd,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=4;pjkz.gridheight=2;
        pjkz.weightx=3.8;pjkz.weighty=9;
        pjkz.gridx=2;pjkz.gridy=1;
        pj.setConstraints(chatgdjp,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=4;pjkz.gridheight=1;
        pjkz.weightx=3.8;pjkz.weighty=0.3;
        pjkz.gridx=2;pjkz.gridy=4;
        pj.setConstraints(ltfuncpan,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=3;pjkz.gridheight=1;
        pjkz.weightx=2.8;pjkz.weighty=1.4;
        pjkz.gridx=2;pjkz.gridy=5;
        pj.setConstraints(inftexgd,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=1;pjkz.gridheight=1;
        pjkz.weightx=0.05;pjkz.weighty=1.4;
        pjkz.gridx=5;pjkz.gridy=5;
        pj.setConstraints(fsjp,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=1;pjkz.gridheight=5;
        pjkz.weightx=0.4;pjkz.weighty=9;
        pjkz.gridx=6;pjkz.gridy=1;
        pj.setConstraints(funcjp,pjkz);

        pjkz=new GridBagConstraints();
        pjkz.fill=GridBagConstraints.BOTH;
        pjkz.gridwidth=7;pjkz.gridheight=1;
        pjkz.weightx=0;pjkz.weighty=0.5;
        pjkz.gridx=0;pjkz.gridy=6;
        pj.setConstraints(fz2box,pjkz);

        CreateFriend createFriend=new CreateFriend(zserver,chatroom,hygdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlzh,jlum,headpicture);
        createFriend.start();

        CreateGroup createGroup=new CreateGroup(zserver,chatroom,qungdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlzh,jlum,headpicture);
        createGroup.start();

        FriendListening friendListening=new FriendListening(zserver,chatroom,hygdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlzh,jlum,headpicture);
        friendListening.start();

        GroupMonitoring groupMonitoring=new GroupMonitoring(zserver,chatroom,qungdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlzh,jlum,headpicture);
        groupMonitoring.start();

        chatroom.repaint();
        chatroom.setVisible(true);

        grinf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PersonalInformation personalInformation=new PersonalInformation(jlzh,jlum,headpicture);
                personalInformation.interFace();
            }
        });

        hycaht.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gdrt.removeAll();
                chatjp.removeAll();
                ltfuncpan.removeAll();
                inftexjp.removeAll();
                fsjp.removeAll();
                funcjp.removeAll();
                gdrt.add(hygdbox);
                chatroom.repaint();
                chatroom.setVisible(true);
            }
        });

        quncaht.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gdrt.removeAll();
                chatjp.removeAll();
                ltfuncpan.removeAll();
                inftexjp.removeAll();
                fsjp.removeAll();
                funcjp.removeAll();
                gdrt.add(qungdbox);
                chatroom.repaint();
                chatroom.setVisible(true);
            }
        });

        tjhybut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame tjjm=new JFrame("添加好友");
                tjjm.setResizable(false);
                tjjm.setBounds(800,600,400,250);


                GridBagLayout pj=new GridBagLayout();
                GridBagConstraints pjkz;
                JPanel pan=new JPanel(pj);
                tjjm.add(pan);

                JPanel zhpan=new JPanel();
                Box zhbox=Box.createVerticalBox();
                JLabel zhLa=new JLabel("请输入对方账号");
                zhLa.setFont(new Font("宋体",Font.PLAIN,20));
                zhbox.add(Box.createVerticalStrut(10));
                zhbox.add(zhLa);
                zhpan.add(zhbox);

                JPanel zhtextpan=new JPanel();
                Box zhtextbox=Box.createVerticalBox();
                JTextField zhtext=new JTextField(18);
                zhtext.setFont(new Font("宋体",Font.PLAIN,20));
                zhtextbox.add(zhtext);
                zhtextpan.add(zhtextbox);

                JPanel fspan=new JPanel();
                Box fsbox=Box.createVerticalBox();
                JButton fasqq=new JButton("发送请求");
                fasqq.setFont(new Font("宋体",Font.PLAIN,20));
                fasqq.setContentAreaFilled(false);
                fasqq.setFocusPainted(false);
                fsbox.add(fasqq);
                fspan.add(fsbox);

                pan.add(zhpan);
                pan.add(zhtextpan);
                pan.add(fspan);

                pjkz=new GridBagConstraints();
                pjkz.fill=GridBagConstraints.BOTH;
                pjkz.gridwidth=1;pjkz.gridheight=1;
                pjkz.weightx=1;pjkz.weighty=1;
                pjkz.gridx=0;pjkz.gridy=0;
                pj.setConstraints(zhpan,pjkz);

                pjkz=new GridBagConstraints();
                pjkz.fill=GridBagConstraints.BOTH;
                pjkz.gridwidth=1;pjkz.gridheight=1;
                pjkz.weightx=1;pjkz.weighty=1;
                pjkz.gridx=0;pjkz.gridy=1;
                pj.setConstraints(zhtextpan,pjkz);

                pjkz=new GridBagConstraints();
                pjkz.fill=GridBagConstraints.BOTH;
                pjkz.gridwidth=1;pjkz.gridheight=1;
                pjkz.weightx=1;pjkz.weighty=1;
                pjkz.gridx=0;pjkz.gridy=2;
                pj.setConstraints(fspan,pjkz);

                tjjm.setVisible(true);

                fasqq.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String dzh=new String(zhtext.getText());

                            if (jlzh.equals(dzh))
                                fasqq.setText("不能添加自己");
                            else
                            if (dzh.equals(""))
                                fasqq.setText("请填写对方账号");
                            else{
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Add_friend");
                                putinfjh.setAccount(jlzh);
                                putinfjh.setPassword(dzh);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                                String zt=getinfjh.getState();

                                if (zt.equals("2"))
                                    fasqq.setText("此用户不存在");
                                else
                                if (zt.equals("3"))
                                    fasqq.setText("已是好友");
                                else
                                if (zt.equals("4"))
                                    fasqq.setText("请勿重复发送");
                                else
                                if (zt.equals("5"))
                                    fasqq.setText("发送成功，等待对方确认");
                                else
                                if (zt.equals("6"))
                                    fasqq.setText("对方正在向你请求添加");
                            }
                        }catch (Exception a){a.printStackTrace();}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        fasqq.setText("发送请求");
                    }
                });
            }
        });

        hysqbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                   Socket server=new Socket("127.0.0.1",4000);
                   ObjectOutputStream  obputFW=new ObjectOutputStream(server.getOutputStream());
                   ObjectInputStream obgetFW=new ObjectInputStream(server.getInputStream());

                   JFrame hysqjm=new JFrame("好友申请");
                   hysqjm.setResizable(false);
                   hysqjm.setBounds(800,600,400,400);

                   JPanel pan=new JPanel();
                   JScrollPane gdpan=new JScrollPane(
                           pan,
                           ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                           ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                   );
                   hysqjm.add(gdpan);

                   Box rtbox=Box.createVerticalBox();

                   CreateFriendRequest creat=new CreateFriendRequest(zserver,chatroom,hygdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlum,hysqjm,rtbox,jlzh,obputFW,obgetFW,headpicture);
                   creat.start();

                   pan.add(rtbox);
                   hysqjm.repaint();
                   hysqjm.setVisible(true);

                   hysqjm.addWindowListener(new WindowAdapter() {
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
                   });
                }catch (Exception a){a.printStackTrace();}
            }
        });

        qunyqbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Socket server=new Socket("127.0.0.1",4000);
                    ObjectOutputStream  obputFW=new ObjectOutputStream(server.getOutputStream());
                    ObjectInputStream obgetFW=new ObjectInputStream(server.getInputStream());

                    JFrame qunyqjm=new JFrame("群聊邀请");
                    qunyqjm.setResizable(false);
                    qunyqjm.setBounds(800,600,400,400);

                    JPanel pan=new JPanel();
                    JScrollPane gdpan=new JScrollPane(
                            pan,
                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                    );
                    qunyqjm.add(gdpan);

                    Box rtbox=Box.createVerticalBox();

                    CreateGroupInvitation creat=new CreateGroupInvitation(zserver,chatroom,qungdbox,chatjp,ltfuncpan,inftexjp,fsjp,funcjp,jlum,qunyqjm,rtbox,jlzh,obputFW,obgetFW,headpicture);
                    creat.start();

                    pan.add(rtbox);
                    qunyqjm.repaint();
                    qunyqjm.setVisible(true);

                    qunyqjm.addWindowListener(new WindowAdapter() {
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
                    });

                }catch (Exception a){a.printStackTrace();}
            }
        });

        creatqunbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame creatqunjm=new JFrame("创建群聊");
                creatqunjm.setResizable(false);
                creatqunjm.setBounds(800,600,400,300);

                JPanel pan=new JPanel();
                creatqunjm.add(pan);

                Box zbox=Box.createVerticalBox();
                pan.add(zbox);

                Box qmbox=Box.createHorizontalBox();
                JLabel qmLa=new JLabel("群名:");
                qmLa.setFont(new Font("宋体",Font.PLAIN,20));
                JTextField qmtex=new JTextField(15);
                qmtex.setFont(new Font("宋体",Font.PLAIN,20));
                qmbox.add(qmLa);
                qmbox.add(Box.createHorizontalStrut(20));
                qmbox.add(qmtex);

                Box creatbox=Box.createHorizontalBox();
                JButton creatbut=new JButton("创建群聊");
                creatbut.setFont(new Font("宋体",Font.PLAIN,20));
                creatbut.setFocusPainted(false);
                creatbut.setContentAreaFilled(false);
                creatbox.add(creatbut);

                zbox.add(Box.createVerticalStrut(50));
                zbox.add(qmbox);
                zbox.add(Box.createVerticalStrut(30));
                zbox.add(creatbox);

                creatqunjm.repaint();
                creatqunjm.setVisible(true);

                creatbut.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String qunm=new String(qmtex.getText());

                            if (qunm.equals(""))
                                creatbut.setText("群名不能为空");
                            else {
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Creat_group");
                                putinfjh.setAccount(jlzh);
                                putinfjh.setNickname(qunm);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                creatbut.setText("创建成功");
                            }
                        }catch (Exception a){}
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        creatbut.setText("创建群聊");
                    }
                });
            }
        });

        joinqunbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame tjjm=new JFrame("加入群聊");
                tjjm.setResizable(false);
                tjjm.setBounds(800,600,400,300);

                JPanel pan=new JPanel();
                tjjm.add(pan);

                Box zbox=Box.createVerticalBox();

                Box qidLabox=Box.createHorizontalBox();
                JLabel qidLa=new JLabel("请输入加入的群ID");
                qidLa.setFont(new Font("宋体",Font.PLAIN,20));
                qidLabox.add(qidLa);

                Box qidtextbox=Box.createHorizontalBox();
                JTextField qidtext=new JTextField(12);
                qidtext.setFont(new Font("宋体",Font.PLAIN,20));
                qidtextbox.add(qidtext);

                Box fsbox=Box.createHorizontalBox();
                JButton fasqq=new JButton("发送请求");
                fasqq.setFont(new Font("宋体",Font.PLAIN,20));
                fasqq.setContentAreaFilled(false);
                fsbox.add(fasqq);

                zbox.add(Box.createVerticalStrut(40));
                zbox.add(qidLabox);
                zbox.add(Box.createVerticalStrut(20));
                zbox.add(qidtextbox);
                zbox.add(Box.createVerticalStrut(20));
                zbox.add(fsbox);

                pan.add(zbox);

                tjjm.setVisible(true);

                fasqq.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            String qid=new String(qidtext.getText());

                            if (qid.equals(""))
                                fasqq.setText("请填写群ID");
                            else{
                                InforMationSet putinfjh=new InforMationSet();
                                putinfjh.setFunction("Add_group");
                                putinfjh.setAccount(jlzh);
                                putinfjh.setGroupid(qid);

                                obputFW.writeObject(putinfjh);
                                obputFW.flush();

                                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                                String zt=getinfjh.getState();

                                if (zt.equals("1"))
                                    fasqq.setText("此群不存在");
                                else
                                if (zt.equals("2"))
                                    fasqq.setText("已在群中");
                                else
                                if (zt.equals("3"))
                                    fasqq.setText("请勿重复发送");
                                else
                                if (zt.equals("4"))
                                    fasqq.setText("此群正在邀请你加入");
                                else
                                if (zt.equals("5"))
                                    fasqq.setText("发送成功，等待同意");
                            }
                        }catch (Exception a){}
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {fasqq.setText("发送请求");
                    }
                });
            }
        });

        chatroom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    InforMationSet olputinfjh=new InforMationSet();
                    olputinfjh.setFunction("off_line");
                    olputinfjh.setAccount(jlzh);

                    obputFW.writeObject(olputinfjh);
                    obputFW.flush();

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Close");

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();

                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    InforMationSet olputinfjh=new InforMationSet();
                    olputinfjh.setFunction("off_line");
                    olputinfjh.setAccount(jlzh);

                    obputFW.writeObject(olputinfjh);
                    obputFW.flush();

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("Close");

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();

                    closeSocket();
                }catch (Exception a){a.printStackTrace();}
            }
        });

    }
}