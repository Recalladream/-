package user.chat;

import utils.InforMationSet;
import utils.OutputControl;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserChatRecord extends Thread{
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    //;
    String jlzh;
    String hyzh;
    JTextPane chatar;
    String kssmg;
    String jssmg;
    JButton hypho;
    JLabel headpicture;

    public UserChatRecord(String jlzh,String hyzh,JTextPane chatar,String kssmg,String jssmg,JButton hypho,JLabel headpicture){
        this.jlzh=jlzh;
        this.hyzh=hyzh;
        this.chatar=chatar;
        this.kssmg=kssmg;
        this.jssmg=jssmg;
        this.hypho=hypho;
        this.headpicture=headpicture;

        initSocket();
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

    public void run(){
        try {
            InforMationSet putinfjh=new InforMationSet();
            putinfjh.setFunction("User_chat_record");
            putinfjh.setAccount(jlzh);
            putinfjh.setPassword(hyzh);
            putinfjh.setTelephone(kssmg);
            putinfjh.setBirthday(jssmg);

            obputFW.writeObject(putinfjh);
            obputFW.flush();

            while (true){
                InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                String state=getinfjh.getState();
                if (state.equals("1")){
                    String fqr=getinfjh.getNickname();
                    String content=getinfjh.getContent();
                    ImageIcon icon=getinfjh.getMaxHeadPicture();
                    int dqfs=Integer.parseInt(getinfjh.getCode());

                    if (dqfs==0)
                        if (icon!=null){
                            OutputControl.writePho(fqr,icon,dqfs,chatar,hypho.getIcon());
                        }else
                            OutputControl.write(fqr,content,dqfs,chatar,hypho.getIcon());
                    else
                    if (icon!=null){
                        OutputControl.writePho(fqr,icon,dqfs,chatar,headpicture.getIcon());
                    }else
                        OutputControl.write(fqr,content,dqfs,chatar,headpicture.getIcon());

                }else
                if (state.equals("2")){
                    closeSocket();
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

}
