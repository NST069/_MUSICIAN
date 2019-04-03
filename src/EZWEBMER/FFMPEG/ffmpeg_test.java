package EZWEBMER.FFMPEG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by NST069 on 03.04.2019.
 */
public class ffmpeg_test extends JFrame {

    JButton OpenImg;
    File fImg;
    JButton OpenAud;
    File fAud;
    JFileChooser jfc = new JFileChooser(new File(System.getProperty("user.home")+"\\Desktop\\"));
    JTextField filename;
    JButton Convert;

    Jffmpeg jffmpeg;

    public ffmpeg_test(){
        setMinimumSize(new Dimension(400, 150));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        jffmpeg = new Jffmpeg();



        OpenImg = new JButton("Open Image");
        OpenImg.setBounds(5,5,185,25);
        OpenImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openfileTo(true);
            }
        });
        add(OpenImg);
        OpenAud = new JButton("Open Audio");
        OpenAud.setBounds(195,5,185,25);
        OpenAud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openfileTo(false);
            }
        });
        add(OpenAud);
        filename = new JTextField();
        filename.setBounds(5,35,375,25);
        add(filename);
        Convert = new JButton("Convert");
        Convert.setBounds(5,65,375,25);
        Convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\n\n"+fImg.getAbsolutePath()+"\n"+fAud.getAbsolutePath()+"\n"+filename.getText());
                jffmpeg.MakeMp4(fImg, fAud, filename.getText());
            }
        });
        add(Convert);
    }


    public static void main(String[] args) {
        ffmpeg_test app = new ffmpeg_test();
        app.setVisible(true);
        app.pack();



    }

    private void openfileTo(boolean isPic){
        int ret = jfc.showOpenDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION) {
            if(isPic) {
                fImg = jfc.getSelectedFile();
                System.out.println(fImg.getAbsolutePath());
            }
            else{
                fAud = jfc.getSelectedFile();
                System.out.println(fAud.getAbsolutePath());
            }
        }
        else System.out.println("err");
    }
}
