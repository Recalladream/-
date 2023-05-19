package dao;

import com.mysql.jdbc.Driver;
import utils.InforMationSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DataBase {
    String smg;
    Connection con;
    Statement scz;
    PreparedStatement pcz;
    ResultSet find;
    //;
    public DataBase(){
       initlj();
    }

    private void initlj(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/qq","root","478788223");
        }catch (Exception e){e.printStackTrace();}
    }

    private Boolean recordDateJudg(String chatsmg,String kssmg,String jssmg){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date chatda=sdf.parse(chatsmg);

            Calendar ca=Calendar.getInstance();
            ca.setTime(chatda);

            chatsmg=ca.get(Calendar.YEAR)+"/";
            chatsmg+=ca.get(Calendar.MONTH)+1+"/";
            chatsmg+=ca.get(Calendar.DAY_OF_MONTH);

            if (chatsmg.compareTo(kssmg)<0)return false;
            if (chatsmg.compareTo(jssmg)>0)return false;
        }catch (Exception e){e.printStackTrace();}
        return true;
    }

    private ImageIcon getpho(String account){
        ImageIcon icon=null;
        try {
            String smg="select * from user where account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            ResultSet find=pcz.executeQuery();
            while (find.next()){
                String path=find.getString("head_picture");
                if (path!=null){
                    if (!path.equals("")){
                        BufferedImage iamgebuf=ImageIO.read(new File(path));
                        Image image=iamgebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);
                        ImageIcon imageic=new ImageIcon(image);
                        icon=imageic;
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return icon;
    }

    public void forwardOffline(String account,String state,HashMap<String,Socket> map,HashMap<Socket,ObjectOutputStream> putmap){
        try {
            smg="select * from friend where user1 = ? or user2 = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String friend_user1=find.getString("user1");
                String friend_user2=find.getString("user2");
                String hyaccount="";

                if (friend_user1.equals(account))hyaccount=friend_user2;
                if (friend_user2.equals(account))hyaccount=friend_user1;

                Socket hysocket=map.get(hyaccount);
                if (hysocket!=null){
                    Socket infsocket=map.get(hyaccount+account+"infor");
                    ObjectOutputStream obputKH=putmap.get(infsocket);
                    if (infsocket!=null&&obputKH!=null){
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("off-line");
                    putinfjh.setState(state);

                    obputKH.writeObject(putinfjh);
                    obputKH.flush();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void online(String account){
        try {
            smg="update user set off_line = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setInt(1,1);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void offLine(String account){
        try {
            smg="update user set off_line = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setInt(1,0);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public String loginCheck(String account,String pass){
        try {
            smg="select * from user where account = ? and mode = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,"account_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_nick_name=find.getString("nick_name");
                String user_account=find.getString("account");
                String user_pass=find.getString("pass_word");
                boolean user_off_line=find.getBoolean("off_line");

                if (user_account.equals(account))
                    if (user_pass.equals(pass)){
                        if (user_off_line)
                            return "4";

                        return user_nick_name;
                    }
                else{
                    return "2";
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return "3";
    }

    public String emailLogin(String account){
        try {
            smg="select * from user where account = ? and mode = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,"mail_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");
                boolean user_off_line=find.getBoolean("off_line");

                if (user_account.equals(account)){
                    if (user_off_line)
                        return "3";

                    return "1";
                }
            }

        }catch (Exception e){e.printStackTrace();}
        return "2";
    }

    public String emailRegister(String account,String mail){
        try {
            smg="insert into user(account,nick_name,mail_box,mode) values(?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,"(昵称)");
            pcz.setString(3,mail);
            pcz.setString(4,"mail_register");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        return "1";
    }

    public String snowflakeJudg(String ID,String function){
        try {
            if (function.equals("1"))
            smg="select * from user where account = ?";
            else
            if (function.equals("2"))
            smg="select * from groupof where qun_account = ?";
            else
            if (function.equals("3"))
            smg="select * from file where number = ?";
            else
            if (function.equals("4"))
            smg="select * from user_chat where Image_number = ?";

            pcz=con.prepareStatement(smg);
            pcz.setString(1,ID);
            find=pcz.executeQuery();

            while (find.next()){
                if (function.equals("1")){
                    String user_account=find.getString("account");
                    if (user_account.equals(ID)){
                        return "1";
                    }
                }
                else if (function.equals("2")){
                    String groupof_qun_account=find.getString("qun_account");
                    if (groupof_qun_account.equals(ID)){
                        return "1";
                    }
                }
                else if (function.equals("3")){
                    return "1";
                }
                else if (function.equals("4")){
                    return "1";
                }
            }

        }catch (Exception e){e.printStackTrace();}

        return "2";
    }

    private void joinUser(String account,String nickname,String password,String telephone,String mail){
        try {
            smg="insert into user(account,pass_word,nick_name,mail_box,tele_phone,mode) values(?,?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,password);
            pcz.setString(3,nickname);
            pcz.setString(4,mail);
            pcz.setString(5,telephone);
            pcz.setString(6,"account_register");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public String userRegister(String account,String nickname,String password,String telephone,String mail){
        try {
            smg="select * from user where (tele_phone = ? or mail_box = ?) and mode =?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,telephone);
            pcz.setString(2,mail);
            pcz.setString(3,"account_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_tele_phone=find.getString("tele_phone");
                String user_mail_box=find.getString("mail_box");

                if (user_tele_phone.equals(telephone))
                    return "1";
                if (user_mail_box.equals(mail))
                    return "2";
            }

        }catch (Exception e){e.printStackTrace();}
        joinUser(account,nickname,password,telephone,mail);
        return "3";
    }

    private String accountRetrieveJudge(String account,String xpass){
        try {
            smg="select * from user where account = ? and mode = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,"account_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");
                String user_pass_word=find.getString("pass_word");

                if (user_account.equals(account))
                    if (!user_pass_word.equals(xpass))
                        return "1";
                else
                    return "2";
            }
        }catch (Exception e){e.printStackTrace();}
        return "3";
    }
    public String accountRetrieval(String account,String xpass){
        String judejg="";
        try {
            judejg=accountRetrieveJudge(account,xpass);
            if (judejg.equals("1")){
                smg="update user set pass_word = ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,xpass);
                pcz.setString(2,account);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
        return judejg;
    }

    private String emailRetrievalJudge(String mail,String xpass){
        try {
            smg="select * from user where mail_box = ? and mode = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,mail);
            pcz.setString(2,"account_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_mail_box=find.getString("mail_box");
                String user_pass_word=find.getString("pass_word");
                String user_account=find.getString("account");

                if (user_mail_box.equals(mail))
                    if (!user_pass_word.equals(xpass))
                        return user_account;
                    else
                        return "2";
            }
        }catch (Exception e){e.printStackTrace();}
        return "3";
    }
    public String emailRetrieval(String mail,String xpass){
        String judejg="";
        try {
            judejg=emailRetrievalJudge(mail,xpass);
            if (!judejg.equals("2")&&!judejg.equals("3")){
                smg="update user set pass_word = ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,xpass);
                pcz.setString(2,judejg);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
        return judejg;
    }

    private String changePasswordJudge(String account,String ypass,String xpass){
        try {
            smg="select * from user where account = ? and mode = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,"account_register");
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");
                String user_pass_word=find.getString("pass_word");


                if (user_account.equals(account))
                    if (user_pass_word.equals(ypass)){
                        if (ypass.equals(xpass))
                            return "3";

                        return "4";
                    }
                else
                    return "1";
            }
        }catch (Exception e){}
        return "2";
    }
    public String changePassword(String account,String ypass,String xpass){
        String judejg="";
        try {
            judejg=changePasswordJudge(account,ypass,xpass);
            if (judejg.equals("4")){
                smg="update user set pass_word = ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,xpass);
                pcz.setString(2,account);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
        return judejg;
    }

    public String avatarPath(String account){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");
                String user_head_picture=find.getString("head_picture");

                if (user_account.equals(account)){
                    if (user_head_picture !=null)
                        return user_head_picture;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return "2";
    }

    public void initUserInfor(String account,InforMationSet putinfjh){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");

                if (user_account.equals(account)){
                    String user_nick_name=find.getString("nick_name");
                    String user_signature=find.getString("signature");
                    String user_gender=find.getString("gender");
                    String user_birth_day=find.getString("birth_day");
                    String user_head_picture=find.getString("head_picture");

                    putinfjh.setNickname(user_nick_name);
                    putinfjh.setSignature(user_signature);
                    putinfjh.setGender(user_gender);
                    putinfjh.setBirthday(user_birth_day);

                    if (user_head_picture!=null){
                        BufferedImage imagebuf=ImageIO.read(new File(user_head_picture));
                        Image image=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                        ImageIcon imageic=new ImageIcon(image);
                        putinfjh.setMaxHeadPicture(imageic);
                    }
                    else{
                        putinfjh.setMaxHeadPicture(null);
                    }

                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardUserImage(String account,String path,ImageIcon imageic,HashMap<String,Socket>map,HashMap<Socket,ObjectOutputStream>putmap){
        try {
            smg="select * from friend where user1 = ? or user2 = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String friend_user1=find.getString("user1");
                String friend_user2=find.getString("user2");
                String hyaccount="";

                if (friend_user1.equals(account))hyaccount=friend_user2;
                if (friend_user2.equals(account))hyaccount=friend_user1;

                Socket hysocket=map.get(hyaccount);
                if (hysocket!=null){
                    Socket infsocket=map.get(hyaccount+account+"infor");
                    ObjectOutputStream obputKH=putmap.get(infsocket);

                    if (infsocket!=null&&obputKH!=null){
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("image");
                    putinfjh.setHeadPicture(imageic);

                    obputKH.writeObject(putinfjh);
                    obputKH.flush();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void userPhoto(String account,String path){
        try {
            smg="update user set head_picture = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,path);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardInfor(String account,String nickname,HashMap<String,Socket> map,HashMap<Socket,ObjectOutputStream> putmap){
        try {
            smg="select * from friend where user1 = ? or user2 = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String friend_user1=find.getString("user1");
                String friend_user2=find.getString("user2");
                String hyaccount="";

                if (friend_user1.equals(account))hyaccount=friend_user2;
                if (friend_user2.equals(account))hyaccount=friend_user1;

                Socket hysocket=map.get(hyaccount);
                if (hysocket!=null){
                    Socket infsocket=map.get(hyaccount+account+"infor");
                    ObjectOutputStream obputKH=putmap.get(infsocket);
                    if (infsocket!=null&&obputKH!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("name");
                        putinfjh.setNickname(nickname);

                        obputKH.writeObject(putinfjh);
                        obputKH.flush();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void editInfor(String account,String nickname,String gender,String birthday,String signature){
        try {
            smg="update user set nick_name = ? , gender = ? , birth_day = ? , signature = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,nickname);
            pcz.setString(2,gender);
            pcz.setString(3,birthday);
            pcz.setString(4,signature);
            pcz.setString(5,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private String findAccount(String hyaccount){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,hyaccount);
            find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");

                if (user_account.equals(hyaccount))
                    return "1";
            }
        }catch (Exception e){}
        return "2";
    }
    public String addFriend(String account,String hyaccount){
        try {
             String state=findAccount(hyaccount);
             if (state.equals("1")){
                 smg="select * from friend where (user1 = ? and user2 = ?) or (user1 = ? and user2 = ?)";
                 pcz=con.prepareStatement(smg);
                 pcz.setString(1,account);
                 pcz.setString(2,hyaccount);
                 pcz.setString(3,hyaccount);
                 pcz.setString(4,account);
                 find=pcz.executeQuery();

                 while (find.next()){
                     String friend_user1=find.getString("user1");
                     String friend_user2=find.getString("user2");

                     if ((friend_user1.equals(account)&&friend_user2.equals(hyaccount))||(friend_user1.equals(hyaccount)&&friend_user2.equals(account))){
                         return "3";
                     }
                 }

                 smg="select * from request where (applicant = ? and recipient = ?) or (applicant = ? and recipient = ?)";
                 pcz=con.prepareStatement(smg);
                 pcz.setString(1,account);
                 pcz.setString(2,hyaccount);
                 pcz.setString(3,hyaccount);
                 pcz.setString(4,account);
                 find=pcz.executeQuery();

                 while (find.next()){
                     String request_applicant=find.getString("applicant");
                     String request_recipient=find.getString("recipient");

                     if (request_applicant.equals(account)&&request_recipient.equals(hyaccount)){
                         return "4";
                     }

                     if (request_applicant.equals(hyaccount)&&request_recipient.equals(account)){
                         return "6";
                     }
                 }

                 smg="insert into request(applicant,recipient,state) values (?,?,?) ";
                 pcz=con.prepareStatement(smg);
                 pcz.setString(1,account);
                 pcz.setString(2,hyaccount);
                 pcz.setString(3,"未处理");
                 pcz.executeUpdate();
                 return "5";
             }
        }catch (Exception e){e.printStackTrace();}
        return "2";
    }

    public void creatGroup(String groupofId,String groupofname,String account){
        try {
            smg="insert into groupof(qun_account,qun_name) values (?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofId);
            pcz.setString(2,groupofname);
            pcz.executeUpdate();

            smg="insert into member(groupof,user,level) values (?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofId);
            pcz.setString(2,account);
            pcz.setString(3,"1");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public String addGroup(String account,String groupofid){
        try {
            int k=0;
            smg="select * from groupof where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String groupof_qun_account=find.getString("qun_account");
                if (groupof_qun_account.equals(groupofid)){
                    k=1;
                    break;
                }
            }
            if (k!=1)return "1";

            k=0;
            smg="select * from member where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String member_groupof=find.getString("groupof");
                String member_user=find.getString("user");
                if (member_groupof.equals(groupofid)&&member_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "2";

            k=0;
            smg="select * from application where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String application_groupof=find.getString("groupof");
                String application_user=find.getString("user");
                if (application_groupof.equals(groupofid)&&application_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "3";

            k=0;
            smg="select * from invitation where user = ? and groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String invitation_user=find.getString("user");
                String invitation_groupof=find.getString("groupof");
                if (invitation_groupof.equals(groupofid)&&invitation_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "4";

            smg="insert into application(groupof,user,state) values (?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.setString(3,"未处理");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        return "5";
    }

    public String getName(String request_applicant){
        try {
            String smg="select * from user where account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,request_applicant);
            ResultSet find=pcz.executeQuery();

            while (find.next()){
                String user_account=find.getString("account");
                String user_nick_name=find.getString("nick_name");

                if (user_account.equals(request_applicant)){
                    return user_nick_name;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return "";
    }
    public void friendRequest(String account,ObjectOutputStream obputKH){
        try {
            smg="select * from request where recipient = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();

            while (find.next()){
                String request_applicant=find.getString("applicant");
                String applicantname=getName(request_applicant);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setAccount(request_applicant);
                putinfjh.setNickname(applicantname);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void getUserInfor(String account,InforMationSet putinfjh){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            while (find.next()){
                String user_nick_name=find.getString("nick_name");
                String user_head_picture=find.getString("head_picture");
                String user_off_line=find.getString("off_line");

                putinfjh.setAccount(account);
                putinfjh.setNickname(user_nick_name);
                putinfjh.setOffline(user_off_line);

                if (user_head_picture !=null){
                    BufferedImage imagebuf= ImageIO.read(new File(user_head_picture));
                    Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);

                    ImageIcon imageic=new ImageIcon(image);
                    putinfjh.setHeadPicture(imageic);
                }else {
                    putinfjh.setHeadPicture(null);
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void agreeFriendRequest(String account,String hyaccount){
        try {
            smg="insert into friend(user1,user2) values (?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.executeUpdate();

            smg="delete from request where applicant = ? and recipient = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,hyaccount);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void rejectFriendRequest(String account,String hyaccount){
        try {
            smg="delete from request where applicant = ? and recipient = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,hyaccount);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private String getGroupofname(String invitation_groupof){
        try {
            String smg="select * from groupof where qun_account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,invitation_groupof);
            ResultSet find=pcz.executeQuery();

            while (find.next()){
                String groupof_qun_account=find.getString("qun_account");
                String groupof_qun_name=find.getString("qun_name");

                if (groupof_qun_account.equals(invitation_groupof)){
                    return groupof_qun_name;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return "";
    }
    public void groupofInvitation(String account,ObjectOutputStream obputKH){
        try {
            smg="select * from invitation where user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            while (find.next()){
                String invitation_groupof=find.getString("groupof");
                String qun_name=getGroupofname(invitation_groupof);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setGroupid(invitation_groupof);
                putinfjh.setNickname(qun_name);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void agreeInvitation(String groupofid,String account){
        try {
            smg="insert into member(groupof,user,level) values(?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.setString(3,"3");
            pcz.executeUpdate();

            smg="delete from invitation where user = ? and groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,groupofid);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void declineInvitation(String groupofid,String account){
        try {
            smg="delete from invitation where user = ? and groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,groupofid);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private void getFriendsInfor(String hyzh,ObjectOutputStream obputKH){
        try {
            String smg="select * from user where account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,hyzh);
            ResultSet find=pcz.executeQuery();
            while (find.next()){
                String user_nick_name=find.getString("nick_name");
                String user_head_picture=find.getString("head_picture");
                String user_off_line=find.getString("off_line");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setAccount(hyzh);
                putinfjh.setNickname(user_nick_name);
                putinfjh.setOffline(user_off_line);

                if (user_head_picture !=null){
                    BufferedImage imagebuf= ImageIO.read(new File(user_head_picture));
                    Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);

                    ImageIcon imageic=new ImageIcon(image);
                    putinfjh.setHeadPicture(imageic);
                }else {
                    putinfjh.setHeadPicture(null);
                }

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    public void showFriends(String account,ObjectOutputStream obputKH){
        try {
            smg="select * from friend where user1 = ? or user2 = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String friend_user1=find.getString("user1");
                String friend_user2=find.getString("user2");
                String hyzh="";

                if (friend_user1.equals(account))hyzh=friend_user2;
                if (friend_user2.equals(account))hyzh=friend_user1;

                getFriendsInfor(hyzh,obputKH);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void deleteFriends(String account,String hyaccount){
        try {
            smg="delete from friend where (user1 = ? and user2 = ?) or (user1 = ? and user2 = ?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,hyaccount);
            pcz.setString(4,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void deleteChat(String account,String hyaccount){
        try {
            smg="delete from user_chat where (sender = ? and recipient = ?) or (sender = ? and recipient = ?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,hyaccount);
            pcz.setString(4,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void userSendMessage(String account,String hyaccount,String content){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into user_chat(sender,recipient,content,chat_type,date) values(?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,content);
            pcz.setString(4,"文本");
            pcz.setString(5,time);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void UserSendPho(String account,String hyaccount,String payh,String phoid){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into user_chat(sender,recipient,content,Image_number,chat_type,date) values (?,?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,payh);
            pcz.setString(4,phoid);
            pcz.setString(5,"图片");
            pcz.setString(6,time);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void userGetNews(String account,String hyaccount,ObjectOutputStream obputKH){
        String jlname=getName(account);
        String hyname=getName(hyaccount);
        try {
            smg="select * from user_chat where (sender = ? and recipient = ?) or (sender = ? and recipient = ?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,hyaccount);
            pcz.setString(4,account);
            find=pcz.executeQuery();
            while (find.next()){
                String user_chat_sender=find.getString("sender");
                String user_chat_recipient=find.getString("recipient");
                String user_chat_content=find.getString("content");
                String type=find.getString("chat_type");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");

                if (user_chat_sender.equals(account)&&user_chat_recipient.equals(hyaccount)){
                    putinfjh.setNickname(jlname);
                    putinfjh.setCode("2");
                    if (type.equals("文本"))
                        putinfjh.setContent(user_chat_content);
                    else{
                        BufferedImage imagebuf=ImageIO.read(new File(user_chat_content));
                        Image image=imagebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                        ImageIcon icon=new ImageIcon(image);
                        putinfjh.setMaxHeadPicture(icon);
                    }
                }
                if (user_chat_sender.equals(hyaccount)&&user_chat_recipient.equals(account)){
                    putinfjh.setNickname(hyname);
                    putinfjh.setCode("0");
                    if (type.equals("文本"))
                        putinfjh.setContent(user_chat_content);
                    else{
                        BufferedImage imagebuf=ImageIO.read(new File(user_chat_content));
                        Image image=imagebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                        ImageIcon icon=new ImageIcon(image);
                        putinfjh.setMaxHeadPicture(icon);
                    }
                }

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void sendUserFile(String sender,String receiver,String filename,String path,String number){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into file(sender,receiver,file_name,path,number,time,identity) values (?,?,?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,sender);
            pcz.setString(2,receiver);
            pcz.setString(3,filename);
            pcz.setString(4,path);
            pcz.setString(5,number);
            pcz.setString(6,time);
            pcz.setString(7,"user");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void getUserFile(String account,String hyaccount,ObjectOutputStream obputKH){
        try {
            smg="select * from file where sender = ? and receiver = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,hyaccount);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String filename=find.getString("file_name");
                String number=find.getString("number");
                String path=find.getString("path");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setNickname(filename);
                putinfjh.setAccount(number);
                putinfjh.setFunction(path);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void deletUserFile(String number){
        try {
            smg="delete from file where number = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,number);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void userChatRecord(String account,String hyaccount,String kssmg,String jssmg,ObjectOutputStream obputkh){
        String zjname=getName(account);
        String hyname=getName(hyaccount);
        try {
            smg="select * from user_chat where (sender = ? and recipient = ?) or (sender = ? and recipient = ?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,hyaccount);
            pcz.setString(3,hyaccount);
            pcz.setString(4,account);
            find=pcz.executeQuery();
            while (find.next()){
                String sender=find.getString("sender");
                String recipient=find.getString("recipient");
                String chatdate=find.getString("date");
                String type=find.getString("chat_type");

                if (recordDateJudg(chatdate,kssmg,jssmg)){
                    String content=find.getString("content");
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setState("1");

                    if (sender.equals(account)){
                        putinfjh.setCode("2");
                        putinfjh.setNickname(zjname);
                    }
                    if (sender.equals(hyaccount)){
                        putinfjh.setCode("0");
                        putinfjh.setNickname(hyname);
                    }

                    if (type.equals("文本"))
                        putinfjh.setContent(content);
                    else {
                        BufferedImage imagebuf=ImageIO.read(new File(content));
                        Image image=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                        ImageIcon icon=new ImageIcon(image);
                        putinfjh.setMaxHeadPicture(icon);
                    }

                    obputkh.writeObject(putinfjh);
                    obputkh.flush();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void getGroupInfor(String member_groupof,ObjectOutputStream obputKH,String member_level){
        try {
            String smg="select * from groupof where qun_account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,member_groupof);
            ResultSet find=pcz.executeQuery();
            while (find.next()){
                String groupof_groupof_name=find.getString("qun_name");
                String groupof_head_picture=find.getString("head_picture");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setGroupid(member_groupof);
                putinfjh.setNickname(groupof_groupof_name);
                putinfjh.setMemberlevel(member_level);

                if (groupof_head_picture!=null&&!groupof_head_picture.equals("")&&!groupof_head_picture.equals(null)){
                    BufferedImage imagebuf= ImageIO.read(new File(groupof_head_picture));
                    Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);

                    ImageIcon imageic=new ImageIcon(image);

                    putinfjh.setHeadPicture(imageic);
                }else {
                    putinfjh.setHeadPicture(null);
                }

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    public void showGroup(String account,ObjectOutputStream obputKH){
        try {
            smg="select * from member where user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            while (find.next()){
                String member_groupof=find.getString("groupof");
                String member_level=find.getString("level");

                getGroupInfor(member_groupof,obputKH,member_level);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void initGroupInfor(String groupofid,ObjectOutputStream obputKH){
        try {
            smg="select * from groupof where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String groupof_qun_name=find.getString("qun_name");
                String groupof_qun_signature=find.getString("qun_signature");
                String groupof_head_picture=find.getString("head_picture");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setNickname(groupof_qun_name);
                putinfjh.setSignature(groupof_qun_signature);

                if (groupof_head_picture!=null&&!groupof_head_picture.equals(null)&&!groupof_head_picture.equals("")){
                    BufferedImage imagebuf=ImageIO.read(new File(groupof_head_picture));
                    Image image=imagebuf.getScaledInstance(200,200,Image.SCALE_DEFAULT);
                    ImageIcon imageic=new ImageIcon(image);

                    putinfjh.setMaxHeadPicture(imageic);
                }else {
                    putinfjh.setMaxHeadPicture(null);
                }

                obputKH.writeObject(putinfjh);
                obputKH.flush();

                break;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardGroupMessage(String account, String groupofid, String content, HashMap<String,Socket> map, HashMap<Socket,ObjectOutputStream>putmap){
        String jlum=getName(account);
        ImageIcon icon=getpho(account);
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");

                if (!member_user.equals(account)){
                    Socket membersocket=map.get(member_user);
                    if (membersocket!=null){
                        Socket chatsocket=map.get(member_user+groupofid+"chat");
                        ObjectOutputStream obputFW=putmap.get(chatsocket);

                        if (chatsocket!=null&&obputFW!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setNickname(jlum);
                        putinfjh.setContent(content);
                        putinfjh.setHeadPicture(icon);
                        putinfjh.setCode("0");

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();
                        }
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupofSendMessage(String account,String groupofid,String content){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into groupof_chat(groupof,sender,content,chat_type,date) values(?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.setString(3,content);
            pcz.setString(4,"文本");
            pcz.setString(5,time);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupforwardPho(ImageIcon iconpho,String account,String groupofid, HashMap<String,Socket> map, HashMap<Socket,ObjectOutputStream>putmap){
        String jlum=getName(account);
        ImageIcon icon=getpho(account);
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");

                if (!member_user.equals(account)){
                    Socket membersocket=map.get(member_user);
                    if (membersocket!=null){
                        Socket chatsocket=map.get(member_user+groupofid+"chat");
                        ObjectOutputStream obputFW=putmap.get(chatsocket);

                        if (chatsocket!=null&&obputFW!=null){
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setNickname(jlum);
                            putinfjh.setMaxHeadPicture(iconpho);
                            putinfjh.setHeadPicture(icon);
                            putinfjh.setCode("0");

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();
                        }
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupSendPho(String sender,String qunid,String path,String number){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into groupof_chat(groupof,sender,content,Image_number,chat_type,date) values(?,?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,qunid);
            pcz.setString(2,sender);
            pcz.setString(3,path);
            pcz.setString(4,number);
            pcz.setString(5,"图片");
            pcz.setString(6,time);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupGetNews(String groupid,String account,ObjectOutputStream obputkh){
        try {
            smg="select * from groupof_chat where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupid);
            find=pcz.executeQuery();
            while (find.next()){
                String groupof_chat_content=find.getString("content");
                String groupof_chat_sender=find.getString("sender");
                String sender_nick_name=getName(groupof_chat_sender);
                String type=find.getString("chat_type");

                InforMationSet putinfjh=new InforMationSet();
                ImageIcon icon=getpho(groupof_chat_sender);
                putinfjh.setState("1");
                putinfjh.setNickname(sender_nick_name);
                putinfjh.setHeadPicture(icon);

                if (type.equals("文本"))
                    putinfjh.setContent(groupof_chat_content);
                else {
                    BufferedImage imagebuf=ImageIO.read(new File(groupof_chat_content));
                    Image image=imagebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                    ImageIcon imageIcon=new ImageIcon(image);
                    putinfjh.setMaxHeadPicture(imageIcon);
                }

                if (!groupof_chat_sender.equals(account))
                putinfjh.setCode("0");
                else
                putinfjh.setCode("2");

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardGroupImage(String groupofid,String path,ImageIcon imageic,HashMap<String,Socket> map,HashMap<Socket,ObjectOutputStream> putmap){
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");

                Socket membersocket=map.get(member_user);
                if (membersocket!=null){
                    Socket inforsocket=map.get(member_user+groupofid+"infor");
                    ObjectOutputStream obputKH=putmap.get(inforsocket);

                    if (inforsocket!=null&&obputKH!=null){
                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setFunction("image");
                    putinfjh.setHeadPicture(imageic);

                    obputKH.writeObject(putinfjh);
                    obputKH.flush();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupofPhoto(String groupofid,String path){
        try {
            smg="update groupof set head_picture = ? where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,path);
            pcz.setString(2,groupofid);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardDate(String groupofid,String groupofname,HashMap<String,Socket> map,HashMap<Socket,ObjectOutputStream> putmap){
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");

                Socket membersocket=map.get(member_user);
                if (membersocket!=null){
                    Socket inforsocket=map.get(member_user+groupofid+"infor");
                    ObjectOutputStream obputKH=putmap.get(inforsocket);

                    if (inforsocket!=null&&obputKH!=null){
                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("name");
                        putinfjh.setNickname(groupofname);

                        obputKH.writeObject(putinfjh);
                        obputKH.flush();
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void editData(String groupofid,String groupofname,String groupofsig){
        try {
            smg="update groupof set qun_name = ? , qun_signature = ? where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofname);
            pcz.setString(2,groupofsig);
            pcz.setString(3,groupofid);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public String inviteIntoGroup(String groupofid,String account){
        int k=0;
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            while (find.next()){
                String user_account=find.getString("account");
                if (user_account.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k!=1)return "1";

            k=0;
            smg="select * from member where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String member_groupof=find.getString("groupof");
                String member_user=find.getString("user");

                if (member_groupof.equals(groupofid)&&member_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "2";

            k=0;
            smg="select * from application where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            find=pcz.executeQuery();
            while (find.next()){
                String application_groupof=find.getString("groupof");
                String application_user=find.getString("user");

                if (application_groupof.equals(groupofid)&&application_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "3";

            k=0;
            smg="select * from invitation where user = ? and groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String invitation_user=find.getString("user");
                String invitation_groupof=find.getString("groupof");

                if (invitation_groupof.equals(groupofid)&&invitation_user.equals(account)){
                    k=1;
                    break;
                }
            }
            if (k==1)return "4";

            smg="insert into invitation(user,groupof,processing_state) values(?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,groupofid);
            pcz.setString(3,"未处理");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        return "5";
    }

    public void dropOutGroup(String account,String groupofid){
        try {
            smg="delete from member where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public int memberNumber(String groupofid){
        int number=0;
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                number++;
            }
        }catch (Exception e){e.printStackTrace();}
        return number;
    }

    public void disBand(String groupofid){
        try {
            smg="delete from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.executeUpdate();

            String path=null;
            smg="select * from groupof where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                path=find.getString("head_picture");
            }
            if (path !=null){
                File file=new File(path);
                file.delete();
            }

            smg="delete from groupof where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private void getMemberInf(String member_user,String member_level,ObjectOutputStream obputKH){
        try {
            String smg="select * from user where account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,member_user);
            ResultSet find=pcz.executeQuery();
            while (find.next()){
                String user_nick_name=find.getString("nick_name");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setAccount(member_user);
                putinfjh.setNickname(user_nick_name);
                putinfjh.setMemberlevel(member_level);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    public void member(String groupofid,ObjectOutputStream obputKH){
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");
                String member_level=find.getString("level");

                getMemberInf(member_user,member_level,obputKH);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void kickOut(String groupofid,String account){
        try {
            smg="delete from member where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private void getApplicationInf(String application_user,ObjectOutputStream obputkh){
        try {
            String smg="select * from user where account = ?";
            PreparedStatement pcz=con.prepareStatement(smg);
            pcz.setString(1,application_user);
            ResultSet find=pcz.executeQuery();
            while (find.next()){
                String user_nick_name=find.getString("nick_name");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setAccount(application_user);
                putinfjh.setNickname(user_nick_name);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }
    public void groupofApplication(String groupofid,ObjectOutputStream obputkh){
        try {
            smg="select * from application where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String application_user=find.getString("user");

                getApplicationInf(application_user,obputkh);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void getGroupinfor(String groupofid,InforMationSet putinfjh){
        try {
            smg="select * from groupof where qun_account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String groupof_groupof_name=find.getString("qun_name");
                String groupof_head_picture=find.getString("head_picture");

                putinfjh.setGroupid(groupofid);
                putinfjh.setNickname(groupof_groupof_name);
                putinfjh.setMemberlevel("3");

                if (groupof_head_picture !=null){
                    BufferedImage imagebuf= ImageIO.read(new File(groupof_head_picture));
                    Image image=imagebuf.getScaledInstance(60,60,Image.SCALE_DEFAULT);

                    ImageIcon imageic=new ImageIcon(image);

                    putinfjh.setHeadPicture(imageic);
                }else {
                    putinfjh.setHeadPicture(null);
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void consentApplication(String groupofid,String account){
        try {
            smg="insert into member(groupof,user,level) values(?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.setString(3,"3");
            pcz.executeUpdate();

            smg="delete from application where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void refusalApplication(String groupofid,String account){
        try {
            smg="delete from application where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void setLevel(String  account,String level,String groupid){
        try {
            if (level.equals("1")){
                smg="update member set level = ? where groupof = ? and level = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,"3");
                pcz.setString(2,groupid);
                pcz.setString(3,"1");
                pcz.executeUpdate();
            }

            smg="update member set level = ? where groupof = ? and user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,level);
            pcz.setString(2,groupid);
            pcz.setString(3,account);
            pcz.executeUpdate();

        }catch (Exception e){e.printStackTrace();}
    }

    public void sendGroupFile(String sender,String receiver,String filename,String path,String number){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into file(sender,receiver,file_name,path,number,time,identity) values (?,?,?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,sender);
            pcz.setString(2,receiver);
            pcz.setString(3,filename);
            pcz.setString(4,path);
            pcz.setString(5,number);
            pcz.setString(6,time);
            pcz.setString(7,"groupof");
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void forwardFilePrompt(String account, String groupofid,HashMap<String,Socket> map, HashMap<Socket,ObjectOutputStream>putmap){
        String jlum=getName(account);
        try {
            smg="select * from member where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,groupofid);
            find=pcz.executeQuery();
            while (find.next()){
                String member_user=find.getString("user");

                if (!member_user.equals(account)){
                    Socket membersocket=map.get(member_user);
                    if (membersocket!=null){
                        Socket chatsocket=map.get(member_user+groupofid+"chat");
                        ObjectOutputStream obputFW=putmap.get(chatsocket);

                        if (chatsocket!=null&&obputFW!=null){
                            InforMationSet putinfjh=new InforMationSet();
                            putinfjh.setContent(jlum+"-发送了群文件-");
                            putinfjh.setCode("1");

                            obputFW.writeObject(putinfjh);
                            obputFW.flush();
                        }
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void getGroupFile(String qunid,ObjectOutputStream obputKH){
        try {
            smg="select * from file where receiver = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,qunid);
            find=pcz.executeQuery();
            while (find.next()){
                String filename=find.getString("file_name");
                String number=find.getString("number");
                String path=find.getString("path");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setNickname(filename);
                putinfjh.setAccount(number);
                putinfjh.setFunction(path);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void deletGroupFile(String number){
        try {
            smg="delete from file where number = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,number);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void groupChatRecord(String account,String qunid,String kssmg,String jssmg,ObjectOutputStream obputKH){
        try {
            smg="select * from groupof_chat where groupof = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,qunid);
            find=pcz.executeQuery();
            while (find.next()){
                String memberzh=find.getString("sender");
                String chatdate=find.getString("date");
                String type=find.getString("chat_type");

                if (recordDateJudg(chatdate,kssmg,jssmg)){
                    ImageIcon icon=getpho(memberzh);
                    String nickname=getName(memberzh);
                    String content=find.getString("content");

                    InforMationSet putinfjh=new InforMationSet();
                    putinfjh.setState("1");
                    putinfjh.setNickname(nickname);
                    putinfjh.setHeadPicture(icon);

                    if (type.equals("文本"))
                        putinfjh.setContent(content);
                    else {
                        BufferedImage imagebuf=ImageIO.read(new File(content));
                        Image image=imagebuf.getScaledInstance(300,300,Image.SCALE_DEFAULT);
                        ImageIcon imageIcon=new ImageIcon(image);
                        putinfjh.setMaxHeadPicture(imageIcon);
                    }

                    if (!memberzh.equals(account))
                        putinfjh.setCode("0");
                    else
                        putinfjh.setCode("2");

                    obputKH.writeObject(putinfjh);
                    obputKH.flush();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    //;

    public void addCommonExpressions(String account,String content){
        try {
            int k=0,x=0;
            smg="select * from common_expression where content = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,content);
            find=pcz.executeQuery();
            while (find.next()){
                k=1;
            }
            if (k==1)return;

            String []jl=new String[20];
            smg="select * from common_expression where user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,content);
            find=pcz.executeQuery();
            while (find.next()){
                String conten=find.getString("content");
                jl[x++]=conten;
            }
            if (x!=9){
                smg="insert into common_expression(user,content) values (?,?)";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,account);
                pcz.setString(2,content);
                pcz.executeUpdate();
            }else {
                smg="delete from common_expression where user = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,account);
                pcz.executeUpdate();

                smg="insert into common_expression(user,content) values (?,?)";
                pcz=con.prepareStatement(smg);
                for (int i=1;i<x;i++){
                    pcz.setString(1,account);
                    pcz.setString(2,jl[i]);
                    pcz.executeUpdate();
                }
                pcz.setString(1,account);
                pcz.setString(2,content);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void getCommonExpressions(String account,ObjectOutputStream obputKH){
        try {
            smg="select * from common_expression where user = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            while (find.next()){
                String content=find.getString("content");

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setState("1");
                putinfjh.setContent(content);

                obputKH.writeObject(putinfjh);
                obputKH.flush();
            }
        }catch (Exception e){e.printStackTrace();}
    }

}