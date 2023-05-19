package utils;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;

public class InforMationSet implements Serializable {
    private String function;

    private String state;

    private String account;

    private String groupid;

    private String password;

    private String mailbox;

    private String telephone;

    private String nickname;

    private String signature;

    private String gender;

    private String birthday;

    private String avatarstate;

    private ImageIcon headpicture;

    private ImageIcon maxheadpicture;

    private String memberlevel;

    private String code;

    private String content;

    private String chatobject;

    private String offline;

    private HashMap<Integer,ImageIcon> phomap;

    public void setFunction(String function){
        this.function=function;
    }
    public String getFunction(){
        return function;
    }

    public void setState(String state){
        this.state=state;
    }
    public String getState(){
        return state;
    }

    public void setAccount(String account){
        this.account=account;
    }
    public String getAccount(){
        return account;
    }

    public void setGroupid(String groupid){
        this.groupid=groupid;
    }
    public String getGroupid(){
        return groupid;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }

    public void setMailbox(String mailbox){
        this.mailbox=mailbox;
    }
    public String getMailbox(){
        return mailbox;
    }

    public void setTelephone(String telephone){
        this.telephone=telephone;
    }
    public String getTelephone(){
        return telephone;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    public String getNickname(){
        return nickname;
    }

    public void setGender(String gender){
        this.gender=gender;
    }
    public String getGender(){
        return gender;
    }

    public void setBirthday(String birthday){
        this.birthday=birthday;
    }
    public String getBirthday(){
        return birthday;
    }

    public void setSignature(String signature){
        this.gender=gender;
    }
    public String getSignature(){
        return signature;
    }

    public void setAvatarState(String avatarstate){
        this.avatarstate=avatarstate;
    }
    public String getAvatarState(){
        return avatarstate;
    }

    public void setHeadPicture(ImageIcon headpicture){
        this.headpicture=headpicture;
    }
    public ImageIcon getHeadPicture(){
        return headpicture;
    }

    public void setMaxHeadPicture(ImageIcon maxheadpicture){
        this.maxheadpicture=maxheadpicture;
    }
    public ImageIcon getMaxHeadPicture(){
        return maxheadpicture;
    }

    public void setMemberlevel(String memberlevel){
        this.memberlevel=memberlevel;
    }
    public String getMemberlevel(){
        return memberlevel;
    }

    public void setCode(String code){
        this.code=code;
    }
    public String getCode(){
        return code;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }

    public void setChatobject(String chatobject){
        this.chatobject=chatobject;
    }
    public String getChatobject(){
        return chatobject;
    }

    public void setOffline(String offline){
        this.offline=offline;
    }
    public String getOffline(){
        return offline;
    }

    public void setPhomap(HashMap<Integer,ImageIcon> phomap){
        this.phomap=phomap;
    }
    public HashMap<Integer,ImageIcon> getPhomap(){
        return phomap;
    }

    @Override
    public String toString(){
        return "";
    }
}