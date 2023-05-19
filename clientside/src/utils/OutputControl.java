package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class OutputControl {

    public static void write(String nickname,String text, int textAlign,JTextPane chatar,Icon headpicture) throws BadLocationException {
        if (textAlign==0){
            StyledDocument doc = chatar.getStyledDocument();
            if (headpicture!=null){
                chatar.setCaretPosition(doc.getLength());
                chatar.insertIcon(headpicture);
            }

            doc=chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

            doc.insertString(doc.getLength(),nickname+" :"+"\n",set);

            doc.insertString(doc.getLength(),text+"\n",set);
        }
        if (textAlign==1){
            StyledDocument doc = chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

            doc.insertString(doc.getLength(),text+"\n",set);
        }
        if (textAlign==2){
            StyledDocument doc = chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

            doc.insertString(doc.getLength(),": "+nickname,set);

            if (headpicture!=null){
                chatar.setCaretPosition(doc.getLength());
                chatar.insertIcon(headpicture);
            }

            doc.insertString(doc.getLength(),"\n"+text+"\n",set);
        }
    }

    public static void writeCommonExpression(String text, int textAlign,JTextPane chatar) throws BadLocationException {
        if (textAlign==0){
            StyledDocument doc = chatar.getStyledDocument();
            doc=chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);
            doc.insertString(doc.getLength(),text,set);
        }
    }

    public static void writeTextPho(String nickname,String text, int textAlign,JTextPane chatar,Icon headpicture,HashMap<Integer,ImageIcon> phomap)throws BadLocationException{
        StyledDocument doc=chatar.getStyledDocument();
        doc=chatar.getStyledDocument();
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, textAlign);
        doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

        doc.insertString(doc.getLength(),": "+nickname,set);

        if (headpicture!=null){
            chatar.setCaretPosition(doc.getLength());
            chatar.insertIcon(headpicture);
        }

        doc.insertString(doc.getLength(),"\n",set);


        char []jl=text.toCharArray();
        for(int i=0;i<text.length();i++){
            if (phomap.get(i)!=null){
                chatar.setCaretPosition(doc.getLength());
                chatar.insertIcon(phomap.get(i));
            }
            else {
                doc.insertString(doc.getLength(),String.valueOf(jl[i]),set);
            }
        }

        doc.insertString(doc.getLength(),"\n",set);
    }

    public static void writePho(String nickname,Icon pho, int textAlign,JTextPane chatar,Icon headpicture) throws BadLocationException{
        if (textAlign==0){
            StyledDocument doc = chatar.getStyledDocument();
            if (headpicture!=null){
                chatar.setCaretPosition(doc.getLength());
                chatar.insertIcon(headpicture);
            }

            doc=chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

            doc.insertString(doc.getLength(),nickname+" :"+"\n",set);

            chatar.setCaretPosition(doc.getLength());
            chatar.insertIcon(pho);

            doc.insertString(doc.getLength(),"\n",set);
        }
        if (textAlign==2){
            StyledDocument doc = chatar.getStyledDocument();
            SimpleAttributeSet set = new SimpleAttributeSet();
            StyleConstants.setAlignment(set, textAlign);
            doc.setParagraphAttributes(chatar.getText().length(), doc.getLength() - chatar.getText().length(), set, false);

            doc.insertString(doc.getLength(),": "+nickname,set);

            if (headpicture!=null){
                chatar.setCaretPosition(doc.getLength());
                chatar.insertIcon(headpicture);
            }

            doc.insertString(doc.getLength(),"\n",set);

            chatar.setCaretPosition(doc.getLength());
            chatar.insertIcon(pho);

            doc.insertString(doc.getLength(),"\n",set);
        }
    }

    public static void jcphomap(String content,HashMap<Integer,ImageIcon> phomap){
        try {
            char []jl=content.toCharArray();
            for(int i=0;i<content.length();i++){
                if (phomap.get(i)!=null){
                    if (jl[i]!=' '){
                        ImageIcon icon=phomap.get(i);
                        phomap.remove(i,icon);
                    }
                }
            }

        }catch (Exception e){e.printStackTrace();}
    }

    public static int changePosition(int textlen,int inport,HashMap<Integer,ImageIcon> phomap,int func,int maxwz){
        int k=0;
        if (func==0){
            for (int i=maxwz;i>=inport;i--){
                if (phomap.get(i)!=null){
                    ImageIcon icon=phomap.get(i);
                    phomap.remove(i,icon);
                    phomap.put(i+1,icon);
                    if (k==0){
                        maxwz+=1;
                        k=1;
                    }
                }
            }
        }

        if (func==2){
            for (int i=maxwz;i>inport;i--){
                if (phomap.get(i)!=null){
                    ImageIcon icon=phomap.get(i);
                    phomap.remove(i,icon);
                    phomap.put(i+1,icon);
                    if (k==0){
                        maxwz+=1;
                        k=1;
                    }
                }
            }
        }

        if (func==1){
            for (int i=inport;i<=maxwz;i++){
                if (phomap.get(i)!=null){
                    ImageIcon icon=phomap.get(i);
                    phomap.remove(i,icon);
                    phomap.put(i-1,icon);
                    if (k==0){
                        maxwz-=1;
                        k=1;
                    }
                }
            }
        }
        return maxwz;
    }

    public static String lineFeedControl(String content){
        StringBuilder sb=new StringBuilder(content);
        int len=content.length();

        for(int i=25;i<len;i+=25){
            sb.insert(i,"\n");
        }

        return sb.toString();
    }

}