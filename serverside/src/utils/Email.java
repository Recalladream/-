package utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class Email {

    public static String getCode(){
        String code;
        char []smg=new char[6];
        Random rands1=new Random();
        Random rands2=new Random();

        for(int i=0;i<6;i++){
            int s1=rands1.nextInt(26);
            int s2=rands2.nextInt(26);

            if(s1<=13){
                smg[i]=(char)('A'+s2);
            }
            if(s1>13){
                smg[i]=(char)('a'+s2);
            }
        }
        code=new String(smg);
        return code;
    }

    public static String sendEmail(String mail,String code,String function){
        try {
            // 收件人电子邮箱
            String to =mail; //可以多个

            // 发件人电子邮箱
            String from = "2831582161@qq.com";

            // 指定发送邮件的主机为 smtp.qq.com
            String host = "smtp.qq.com";  //QQ 邮件服务器

            // 获取系统属性
            Properties properties = System.getProperties();

            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);

            properties.put("mail.smtp.auth", "true");
            // 获取默认session对象

            Session session = Session.getDefaultInstance(properties, new Authenticator(){
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("2831582161@qq.com", "epkyhslvwmcadddc"); //发件人邮件用户名、授权码
                }
            });

            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.setRecipients(MimeMessage.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: 头部头字段
            message.setSubject("qq聊天室");

            // 设置消息体
            message.setText("尊敬的用户：\n"+"你获得的"+function+"验证码为："+code+"\n(二分钟之内有效请尽快使用)");

            // 发送消息
            Transport.send(message);
        }catch (Exception e) {return "0";}
        return "1";
    }
}