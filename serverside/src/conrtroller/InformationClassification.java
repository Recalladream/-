package conrtroller;

import dao.DataBase;
import utils.Email;
import utils.InforMationSet;
import utils.SnowflakeAlgorithm;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class InformationClassification {
    HashMap<String,Socket>map;
    HashMap<Socket,ObjectOutputStream>putmap;
    Socket KH;
    InforMationSet getinfjh;
    ObjectOutputStream obputkh;

    public InformationClassification(InforMationSet getinfjh, Socket KH, ObjectOutputStream obputkh, HashMap<String,Socket>map,HashMap<Socket,ObjectOutputStream>putmap){
        this.KH=KH;
        this.getinfjh=getinfjh;
        this.obputkh=obputkh;
        this.map=map;
        this.putmap=putmap;
    }

    private void closeSocket(){
        try {
            KH.close();
        }catch (Exception e){e.printStackTrace();}
    }

    private String getID(String function){
        String state;
        Long id;
        String ID="";
        try {
            while (true) {
             id=new SnowflakeAlgorithm().nextId();
             ID=new StringBuffer(id.toString()).reverse().toString().substring(0,10);

             DataBase dataBase=new DataBase();
             state=dataBase.snowflakeJudg(ID,function);

             if (state.equals("2")){
                 break;
             }
            }
        }catch (Exception e){e.printStackTrace();}
        return ID;
    }

    private ImageIcon getHeadPicture(String path){
        ImageIcon imageic=new ImageIcon();
        try {
            BufferedImage imagebuf= ImageIO.read(new File(path));
            Image iamge=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);
            imageic=new ImageIcon(iamge);
        }catch (Exception e){e.printStackTrace();}
        return imageic;
    }

    public void functionalresolution(){
        String function=getinfjh.getFunction();

        if (function.equals("Close")){
            closeSocket();
        }

        if (function.equals("Chat")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();
                map.put(account+chatobject+"chat",KH);
                putmap.put(KH,obputkh);
            }catch (Exception e){e.printStackTrace();}
        }
        if (function.equals("Close_chat")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();

                Socket chatsocket=map.get(account+chatobject+"chat");
                ObjectOutputStream obputKHchat=putmap.get(chatsocket);

                if (chatsocket!=null&&obputKHchat!=null){
                    map.remove(account+chatobject+"chat",chatsocket);
                    putmap.remove(chatsocket,obputKHchat);
                }

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Close");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                KH.close();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Infor")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();
                map.put(account+chatobject+"infor",KH);
                putmap.put(KH,obputkh);
            }catch (Exception e){e.printStackTrace();}
        }
        if (function.equals("Close_infor")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();

                Socket inforsocket=map.get(account+chatobject+"infor");
                ObjectOutputStream obputKHinfor=putmap.get(inforsocket);

                if (inforsocket!=null&&obputKHinfor!=null){
                    map.remove(account+chatobject+"infor",inforsocket);
                    putmap.remove(inforsocket,obputKHinfor);
                }

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Close");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                KH.close();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Monitor")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();
                map.put(account+chatobject,KH);
                putmap.put(KH,obputkh);
            }catch (Exception e){e.printStackTrace();}
        }
        if (function.equals("Close_monitor")){
            try {
                String account=getinfjh.getAccount();
                String chatobject=getinfjh.getChatobject();

                Socket monitorsocket=map.get(account+chatobject);
                ObjectOutputStream obputKHmonitor=putmap.get(monitorsocket);

                if (monitorsocket!=null&&obputKHmonitor!=null){
                    map.remove(account+chatobject,monitorsocket);
                    putmap.remove(monitorsocket,obputKHmonitor);
                }

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setFunction("Close");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                KH.close();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("online")){
            try {
                String account=getinfjh.getAccount();

                map.put(account,KH);
                System.out.println(account+": 上线");

                DataBase dataBase=new DataBase();

                dataBase.forwardOffline(account,"1",map,putmap);

                dataBase.online(account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("off_line")){
            try {
                String account=getinfjh.getAccount();

                System.out.println(account+": 离线");

                DataBase dataBase=new DataBase();

                dataBase.forwardOffline(account,"0",map,putmap);

                dataBase.offLine(account);

                map.remove(account,KH);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Login_check")){
            try {
            String account=getinfjh.getAccount();
            String pass=getinfjh.getPassword();

            DataBase dataBase=new DataBase();
            String state=dataBase.loginCheck(account,pass);

            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setState(state);

            if (!state.equals("2")&&!state.equals("3")&&!state.equals("4")){
                String path=dataBase.avatarPath(account);
                if (!path.equals("2")){
                    ImageIcon headpicture=getHeadPicture(path);
                    putinfjh.setHeadPicture(headpicture);
                }
                else{
                    ImageIcon headpicture=new ImageIcon();
                    putinfjh.setHeadPicture(headpicture);
                }
            }

            obputkh.writeObject(putinfjh);
            obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Get_mail")){
            try {
                String smg=getinfjh.getState();
                String mail=getinfjh.getMailbox();

                String code= Email.getCode();
                String state=Email.sendEmail(mail,code,smg);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);
                putinfjh.setCode(code);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Email_login")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                String state=dataBase.emailLogin(account);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                if (state.equals("1")){
                    String jlum=new DataBase().getName(account);
                    putinfjh.setNickname(jlum);
                    String path=dataBase.avatarPath(account);
                    if (!path.equals("2")){
                        ImageIcon headpicture=getHeadPicture(path);
                        putinfjh.setHeadPicture(headpicture);
                    }
                    else{
                        ImageIcon headpicture=new ImageIcon();
                        putinfjh.setHeadPicture(headpicture);
                    }
                }

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Email_register")){
            try {
                String account=getinfjh.getAccount();
                String mail=getinfjh.getMailbox();

                DataBase dataBase=new DataBase();
                String state=dataBase.emailRegister(account,mail);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                ImageIcon headpicture=new ImageIcon();
                putinfjh.setHeadPicture(headpicture);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_register")){
            try {
                String account=getID("1");
                String nickname=getinfjh.getNickname();
                String password=getinfjh.getPassword();
                String telephone=getinfjh.getTelephone();
                String mail=getinfjh.getMailbox();

                DataBase dataBase=new DataBase();
                String state=dataBase.userRegister(account,nickname,password,telephone,mail);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);
                putinfjh.setAccount(account);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Account_retrieval")){
            try {
                String account=getinfjh.getAccount();
                String xpass=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                String state=dataBase.accountRetrieval(account,xpass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Email_retrieval")){
            try {
                String mail=getinfjh.getMailbox();
                String xpass=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                String state=dataBase.emailRetrieval(mail,xpass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Change_password")){
            try {
                String account=getinfjh.getAccount();
                String ypass=getinfjh.getPassword();
                String xpass=getinfjh.getState();

                DataBase dataBase=new DataBase();
                String state=dataBase.changePassword(account,ypass,xpass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Init_user_infor")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();

                InforMationSet putinfjh=new InforMationSet();

                dataBase.initUserInfor(account,putinfjh);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_photo")){
            try {
                String account=getinfjh.getAccount();

                String yxzh="";

                String path="";

                if (account.length()>10){
                    yxzh="@"+account.substring(0,account.length()-7);
                    path="E:\\java\\代码\\ResourceFile\\Server_Side\\AvatarLibrary\\user"+yxzh+".jpg";
                }
                else {
                    path="E:\\java\\代码\\ResourceFile\\Server_Side\\AvatarLibrary\\user"+account+".jpg";
                }

                try {
                    File file=new File(path);
                    file.delete();
                }catch (Exception a){a.printStackTrace();}

                FileImageOutputStream filephoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                InputStream getiamge=KH.getInputStream();

                while ((lenth=getiamge.read(buff)) !=-1){
                    filephoout.write(buff,0,lenth);
                }
                filephoout.flush();
                filephoout.close();
                KH.close();

                BufferedImage imagebuf=ImageIO.read(new File(path));
                Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);
                ImageIcon imageic=new ImageIcon(image);

                DataBase dataBase=new DataBase();

                dataBase.forwardUserImage(account,path,imageic,map,putmap);

                dataBase.userPhoto(account,path);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Edit_infor")){
            try {
                String account=getinfjh.getAccount();
                String nickname=getinfjh.getNickname();
                String gender=getinfjh.getGender();
                String birthday=getinfjh.getBirthday();
                String signature=getinfjh.getSignature();

                DataBase dataBase=new DataBase();

                if (getinfjh.getState().equals("1"))
                dataBase.forwardInfor(account,nickname,map,putmap);

                dataBase.editInfor(account,nickname,gender,birthday,signature);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Add_friend")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                String state=dataBase.addFriend(account,hyaccount);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Creat_group")){
            try {
                String groupofid=getID("2");
                String account=getinfjh.getAccount();
                String groupofname=getinfjh.getNickname();

                DataBase dataBase=new DataBase();
                dataBase.creatGroup(groupofid,groupofname,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Add_group")){
            try {
                String account=getinfjh.getAccount();
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                String state=dataBase.addGroup(account,groupofid);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Friend_request")){
            try {
            String account=getinfjh.getAccount();

            DataBase dataBase=new DataBase();
            dataBase.friendRequest(account,obputkh);

            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setState("2");

            obputkh.writeObject(putinfjh);
            obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Agree_friend_request")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                DataBase dataBase=new DataBase();

                Socket hysocket=map.get(hyaccount);
                if (hysocket!=null) {
                    Socket monitorsocket = map.get(hyaccount + "friend");
                    ObjectOutputStream obputKH=putmap.get(monitorsocket);

                    if (monitorsocket!=null&&obputKH!=null){
                    InforMationSet putinfjh=new InforMationSet();

                    dataBase.getUserInfor(account,putinfjh);

                    obputKH.writeObject(putinfjh);
                    obputKH.flush();
                    }
                }

                InforMationSet putinfjh=new InforMationSet();

                dataBase.getUserInfor(hyaccount,putinfjh);

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                dataBase.agreeFriendRequest(account,hyaccount);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Reject_friend_request")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                dataBase.rejectFriendRequest(account,hyaccount);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_invitation")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.groupofInvitation(account,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Agree_invitation")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();

                InforMationSet putinfjh=new InforMationSet();

                dataBase.getGroupinfor(groupofid,putinfjh);

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                dataBase.agreeInvitation(groupofid,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Decline_invitation")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.declineInvitation(groupofid,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Show_friends")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.showFriends(account,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Delete_friends")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                Socket socketchat1=map.get(account+hyaccount+"chat");
                Socket socketinfor1=map.get(account+hyaccount+"infor");
                ObjectOutputStream obputKHchat1=putmap.get(socketchat1);
                ObjectOutputStream obputKHinfor1=putmap.get(socketinfor1);

                Socket hysocket=map.get(hyaccount);
                Socket socketchat2=map.get(hyaccount+account+"chat");
                Socket socketinfor2=map.get(hyaccount+account+"infor");
                ObjectOutputStream obputKHchat2=putmap.get(socketchat2);
                ObjectOutputStream obputKHinfor2=putmap.get(socketinfor2);

                if (hysocket!=null){
                    if (socketinfor2!=null&&obputKHinfor2!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Delete_friends");

                        obputKHinfor2.writeObject(putinfjh);
                        obputKHinfor2.flush();
                    }

                    map.remove(hyaccount+account+"chat",socketchat2);
                    map.remove(hyaccount+account+"infor",socketinfor2);
                    putmap.remove(socketchat2,obputKHchat2);
                    putmap.remove(socketinfor2,obputKHinfor2);
                }

                map.remove(account+hyaccount+"chat",socketchat1);
                map.remove(account+hyaccount+"infor",socketinfor1);
                putmap.remove(socketchat1,obputKHchat1);
                putmap.remove(socketinfor1,obputKHinfor1);

                DataBase dataBase=new DataBase();

                dataBase.deleteFriends(account,hyaccount);

                dataBase.deleteChat(account,hyaccount);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_send_message")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();
                String content=getinfjh.getContent();

                Socket hysocket=map.get(hyaccount);
                if (hysocket!=null){
                    Socket chatsocket=map.get(hyaccount+account+"chat");
                    ObjectOutputStream obputFW=putmap.get(chatsocket);

                    if (chatsocket!=null&&obputFW!=null){
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setNickname(new DataBase().getName(account));
                    putinfjh.setContent(content);
                    putinfjh.setCode("0");

                    obputFW.writeObject(putinfjh);
                    obputFW.flush();
                    }
                }

                DataBase dataBase=new DataBase();
                dataBase.userSendMessage(account,hyaccount,content);

                if (content.length()<=10)
                dataBase.addCommonExpressions(account,content);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_send_pho")){
            try {
                String sender=getinfjh.getAccount();
                String receiver=getinfjh.getPassword();
                String filename=getinfjh.getNickname();
                String number=getID("4");

                String path="E:\\java\\代码\\ResourceFile\\Server_Side\\UserSendPho\\"+number+filename;

                InputStream getfile=KH.getInputStream();
                FileImageOutputStream fileoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                while ((lenth=getfile.read(buff)) !=-1){
                    fileoout.write(buff,0,lenth);
                }
                fileoout.close();
                KH.close();

                DataBase dataBase=new DataBase();
                dataBase.UserSendPho(sender,receiver,path,number);

                BufferedImage imagebuf=ImageIO.read(new File(path));
                Image image=imagebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                ImageIcon icon=new ImageIcon(image);

                Socket hysocket=map.get(receiver);
                if (hysocket!=null){
                    Socket chatsocket=map.get(receiver+sender+"chat");
                    ObjectOutputStream obputFW=putmap.get(chatsocket);

                    if (chatsocket!=null&&obputFW!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setMaxHeadPicture(icon);
                        putinfjh.setCode("0");

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_get_news")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                dataBase.userGetNews(account,hyaccount,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Send_user_file")){
            try {
                String sender=getinfjh.getAccount();
                String receiver=getinfjh.getPassword();
                String filename=getinfjh.getNickname();
                String number=getID("3");

                String path="E:\\java\\代码\\ResourceFile\\Server_Side\\File\\"+number+filename;

                InputStream getfile=KH.getInputStream();
                FileImageOutputStream fileoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                while ((lenth=getfile.read(buff)) !=-1){
                    fileoout.write(buff,0,lenth);
                }
                fileoout.close();
                KH.close();

                DataBase dataBase=new DataBase();
                dataBase.sendUserFile(sender,receiver,filename,path,number);

                Socket hysocket=map.get(receiver);
                if (hysocket!=null){
                    Socket chatsocket=map.get(receiver+sender+"chat");
                    ObjectOutputStream obputFW=putmap.get(chatsocket);

                    if (chatsocket!=null&&obputFW!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setContent("---有一文件待接收---");
                        putinfjh.setCode("1");

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();
                    }

                }
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Get_user_file")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();

                DataBase dataBase=new DataBase();
                dataBase.getUserFile(account,hyaccount,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("receive_user_file")){
            try {
                String number=getinfjh.getAccount();
                String path=getinfjh.getAvatarState();

                OutputStream  putfile=KH.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                byte[] buf = new byte[1024*1024*1024];
                int lenth = 0;

                while( (lenth = bis.read(buf)) != -1 ) {
                    putfile.write(buf,0,lenth);
                    putfile.flush();
                }
                putfile.flush();
                bis.close();
                closeSocket();

                DataBase dataBase=new DataBase();
                dataBase.deletUserFile(number);

                File file=new File(path);
                file.delete();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("delet_user_file")){
            try {
                String number=getinfjh.getAccount();
                String path=getinfjh.getAvatarState();

                DataBase dataBase=new DataBase();
                dataBase.deletUserFile(number);

                File file=new File(path);
                file.delete();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_chat_record")){
            try {
                String account=getinfjh.getAccount();
                String hyaccount=getinfjh.getPassword();
                String kssmg=getinfjh.getTelephone();
                String jssmg=getinfjh.getBirthday();

                DataBase dataBase=new DataBase();
                dataBase.userChatRecord(account,hyaccount,kssmg,jssmg,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Show_group")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.showGroup(account,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Init_group_infor")){
            try {
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.initGroupInfor(groupofid,obputkh);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_send_message")){
            try {
                String account=getinfjh.getAccount();
                String groupofid=getinfjh.getGroupid();
                String content=getinfjh.getContent();

                DataBase dataBase=new DataBase();

                dataBase.forwardGroupMessage(account,groupofid,content,map,putmap);

                dataBase.groupofSendMessage(account,groupofid,content);

                if (content.length()<=10)
                dataBase.addCommonExpressions(account,content);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_send_pho")){
            try {
                String sender=getinfjh.getAccount();
                String qunid=getinfjh.getGroupid();
                String filename=getinfjh.getNickname();
                String number=getID("4");

                String path="E:\\java\\代码\\ResourceFile\\Server_Side\\GroupSendPho\\"+number+filename;

                InputStream getfile=KH.getInputStream();
                FileImageOutputStream fileoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                while ((lenth=getfile.read(buff)) !=-1){
                    fileoout.write(buff,0,lenth);
                }
                fileoout.close();
                KH.close();

                DataBase dataBase=new DataBase();
                dataBase.groupSendPho(sender,qunid,path,number);

                BufferedImage imagebuf=ImageIO.read(new File(path));
                Image image=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                ImageIcon icon=new ImageIcon(image);

                dataBase.groupforwardPho(icon,sender,qunid,map,putmap);
            }catch (Exception e){e.printStackTrace();}
        }

        if (getinfjh.getFunction().equals("Group_get_news")){
            try {
                String groupid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.groupGetNews(groupid,account,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_photo")){
            try {
                String groupofid=getinfjh.getGroupid();

                String path="E:\\java\\代码\\ResourceFile\\Server_Side\\AvatarLibrary\\groupof"+groupofid+".jpg";

                try {
                    File file=new File(path);
                    file.delete();
                }catch (Exception a){a.printStackTrace();}

                InputStream getiamge=KH.getInputStream();
                FileImageOutputStream filephoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                while ((lenth=getiamge.read(buff)) !=-1){
                    filephoout.write(buff,0,lenth);
                }
                filephoout.close();
                KH.close();

                BufferedImage imagebuf=ImageIO.read(new File(path));
                Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);
                ImageIcon imageic=new ImageIcon(image);

                DataBase dataBase=new DataBase();

                dataBase.forwardGroupImage(groupofid,path,imageic,map,putmap);

                dataBase.groupofPhoto(groupofid,path);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Edit_data")){
            try {
                String groupofid=getinfjh.getGroupid();
                String groupofname=getinfjh.getNickname();
                String groupofsig=getinfjh.getSignature();

                DataBase dataBase=new DataBase();

                if (getinfjh.getState().equals("1"))
                dataBase.forwardDate(groupofid,groupofname,map,putmap);

                dataBase.editData(groupofid,groupofname,groupofsig);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Invite_into_group")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                String state=dataBase.inviteIntoGroup(groupofid,account);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Drop_out_group")){
            try {
                String account=getinfjh.getAccount();
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.dropOutGroup(account,groupofid);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Member_number")){
            try {
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                int number=dataBase.memberNumber(groupofid);
                String state=Integer.toString(number);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Dis_band")){
            try {
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.disBand(groupofid);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Member")){
            try {
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.member(groupofid,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Kick_out")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                Socket membersocket=map.get(account);
                Socket chatsocket=map.get(account+groupofid+"chat");
                Socket inforsocket=map.get(account+groupofid+"infor");
                ObjectOutputStream obputKHcaht=putmap.get(chatsocket);
                ObjectOutputStream obputKHinfor=putmap.get(inforsocket);
                if (membersocket!=null){
                    if (inforsocket!=null&&obputKHinfor!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Kick_group");

                        obputKHinfor.writeObject(putinfjh);
                        obputKHinfor.flush();
                    }
                    map.remove(account+groupofid+"chat",chatsocket);
                    map.remove(account+groupofid+"infor",inforsocket);
                    putmap.remove(chatsocket,obputKHcaht);
                    putmap.remove(inforsocket,obputKHinfor);
                }

                DataBase dataBase=new DataBase();
                dataBase.kickOut(groupofid,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_application")){
            try {
                String groupofid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.groupofApplication(groupofid,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Consent_application")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();

                Socket usersocket=map.get(account);
                if (usersocket!=null){
                    Socket monitorsocket=map.get(account+"group");
                    ObjectOutputStream obputKH=putmap.get(monitorsocket);

                    if (monitorsocket!=null&&obputKH!=null){
                    InforMationSet putinfjh=new InforMationSet();

                    dataBase.getGroupinfor(groupofid,putinfjh);

                    obputKH.writeObject(putinfjh);
                    obputkh.flush();
                    }
                }

                dataBase.consentApplication(groupofid,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Refusal_application")){
            try {
                String groupofid=getinfjh.getGroupid();
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.refusalApplication(groupofid,account);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Set_level")){
            try {
                String account=getinfjh.getAccount();
                String level=getinfjh.getMemberlevel();
                String groupofid=getinfjh.getGroupid();

                Socket membersocket=map.get(account);
                Socket inforsocker=map.get(account+groupofid+"infor");
                ObjectOutputStream obputKH=putmap.get(inforsocker);

                if (membersocket!=null){
                    if (inforsocker!=null&&obputKH!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Set_level");
                        putinfjh.setMemberlevel(level);

                        obputKH.writeObject(putinfjh);
                        obputkh.flush();
                    }
                }

                DataBase dataBase=new DataBase();
                dataBase.setLevel(account,level,groupofid);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Send_group_file")){
            try {
                String sender=getinfjh.getAccount();
                String receiver=getinfjh.getGroupid();
                String filename=getinfjh.getNickname();
                String number=getID("3");

                String path="E:\\java\\代码\\ResourceFile\\Server_Side\\File\\"+number+filename;

                InputStream getfile=KH.getInputStream();
                FileImageOutputStream fileoout=new FileImageOutputStream(new File(path));

                byte []buff=new byte[1024*1024*1024];
                int lenth=0;

                while ((lenth=getfile.read(buff)) !=-1){
                    fileoout.write(buff,0,lenth);
                }
                fileoout.close();
                KH.close();

                DataBase dataBase=new DataBase();

                dataBase.sendGroupFile(sender,receiver,filename,path,number);

                dataBase.forwardFilePrompt(sender,receiver,map,putmap);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Get_group_file")){
            try {
                String qunid=getinfjh.getGroupid();

                DataBase dataBase=new DataBase();
                dataBase.getGroupFile(qunid,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Receive_group_file")){
            try {
                String number=getinfjh.getAccount();
                String path=getinfjh.getAvatarState();

                OutputStream  putfile=KH.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
                byte[] buf = new byte[1024*1024*1024];
                int lenth = 0;

                while( (lenth = bis.read(buf)) != -1 ) {
                    putfile.write(buf,0,lenth);
                    putfile.flush();
                }
                putfile.flush();
                bis.close();
                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Delet_group_file")){
            try {
                String number=getinfjh.getAccount();
                String path=getinfjh.getAvatarState();

                DataBase dataBase=new DataBase();
                dataBase.deletGroupFile(number);

                File file=new File(path);
                file.delete();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Group_chat_record")){
            try {
                String account=getinfjh.getAccount();
                String qunid=getinfjh.getGroupid();
                String kssmg=getinfjh.getTelephone();
                String jssmg=getinfjh.getBirthday();

                DataBase dataBase=new DataBase();
                dataBase.groupChatRecord(account,qunid,kssmg,jssmg,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        //;
        if (function.equals("Get_common_expressions")){
            try {
                String account=getinfjh.getAccount();

                DataBase dataBase=new DataBase();
                dataBase.getCommonExpressions(account,obputkh);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

    }
}