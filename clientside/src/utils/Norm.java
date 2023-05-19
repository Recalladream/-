package utils;

import javax.swing.*;
import java.awt.*;

public class Norm {

    public Norm(){

    }
    public static boolean normAtiveJudgment(String wb,int gn){
        if (gn==1){
            char []a=wb.toCharArray();

            int len=wb.length();
            if (len<9||a[0]=='0'||len>11)return false;

            for (int i=0;i<len;i++){
                if (a[i]<'0'||a[i]>'9')
                    return false;
            }
        }

        if(gn==2){
            char []a=wb.toCharArray();

            int len=wb.length();
            if (len<9||a[0]=='0'||len>11)return false;

            for (int i=0;i<len;i++){
                if (a[i]<'0'||a[i]>'9')
                    if (a[i]<'a'||a[i]>'z')
                        if (a[i]<'A'||a[i]>'Z')
                            return false;
            }
        }

        if (gn==3){
            char []a=wb.toCharArray();
            int len=wb.length();
            if(len!=11||a[0]=='0')return false;

            for (int i=0;i<len;i++){
                if(a[i]<'0'||a[i]>'9')
                    return false;
            }
        }

        return true;
    }

    public static void prompt(int gn){
        JFrame gfjm=new JFrame("注意");
        gfjm.setBounds(800,600,420,200);
        gfjm.setResizable(false);

        JPanel pan=new JPanel();
        gfjm.add(pan);

        Box box=Box.createVerticalBox();
        pan.add(box);

        Box tsbox=Box.createHorizontalBox();
        JLabel tsLa=new JLabel();
        tsLa.setFont(new Font("宋体",Font.BOLD,22));
        tsbox.add(tsLa);

        Box jgbox=Box.createHorizontalBox();
        JLabel jgLa=new JLabel();
        jgLa.setFont(new Font("宋体",Font.BOLD,20));
        jgbox.add(jgLa);

        box.add(Box.createVerticalStrut(20));
        box.add(tsbox);
        box.add(Box.createVerticalStrut(20));
        box.add(jgbox);

        if (gn==1){
            tsLa.setText("账号错误");
            jgLa.setText("9-11位数字,0不能开头");
        }
        if (gn==2){
            tsLa.setText("密码错误");
            jgLa.setText("9-11位数字、字母,无特殊字符,0不能开头");
        }
        if (gn==3){
            tsLa.setText("群ID错误");
            jgLa.setText("9-11位数字,0不能开头");
        }

        gfjm.setVisible(true);
    }
}
