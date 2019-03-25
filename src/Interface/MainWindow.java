package Interface;

import MIDIGEN.Player;
import jdk.nashorn.internal.runtime.Debug;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by NST069 on 13.03.2019.
 */
public class MainWindow extends JFrame {

    /***
     * TODO: Make auto-generated interface
     */
    private JPanel content;
    private JPanel pnl;
    private JPanel _cover;
    private JPanel right;
    private JButton play;
    private JButton save;
    private JLabel _title;
    private JButton remake;

    private MIDIGEN.Player player;
    private BufferedImage image;

    public MainWindow(){
        super("_MUSICIAN");
        setPreferredSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        player=new MIDIGEN.Player();
        image=IMGGEN.Generator.Generate(500,"");
        player.Open(140, 300);

        initComps();

        content = new JPanel(new BorderLayout());

        pnl = new JPanel(new GridLayout(1,2));

        pnl.add(_cover, 0);
        right = new JPanel(new GridLayout(4,1));
        right.add(_title, 0);
        right.add(play, 1);
        right.add(save, 2);
        right.add(remake, 3);
        pnl.add(right, 1);
        content.add(pnl);

        add(content);
        initListeners(this);
    }

    private void initComps(){
        _cover = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(image, _cover.getX(), _cover.getY(), this);
            }
        };
        play=new JButton("Play");
        save=new JButton("Save");
        _title=new JLabel("");
        remake=new JButton("Remake");
    }

    public static void main(String[] args){
        MainWindow app = new MainWindow();
        app.setVisible(true);
        app.pack();

    }

    public void initListeners(JFrame f){
        play.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isPaused()) {
                    play.setText("Pause");
                    player.Resume();
                } else {
                    play.setText("Play");
                    player.Pause();
                }

            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new File(System.getProperty("user.home")+"\\Desktop\\mus\\").mkdir();
                File p = new File(System.getProperty("user.home")+"\\Desktop\\mus\\"+"1.png");
                File f = new File(System.getProperty("user.home")+"\\Desktop\\mus\\"+"1.mid");
                player.Save(f);
                try {
                    ImageIO.write(image,"png",p);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        remake.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(player.isOpened()) player.Close();
                player.Open(140, 300);
                image=IMGGEN.Generator.Generate(500,"");
                _cover.repaint();
            }
        });
    }
}
