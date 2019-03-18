package IMGGEN;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by NST069 on 13.03.2019.
 */
public class img_show extends JFrame {

    public img_show() {
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        img_show app = new img_show();

        BufferedImage img = Generator.Generate(500, "test");
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage((Image) img, 0, 0, this);
            }
        };

        contentPane.revalidate();
        app.add(contentPane);
        app.setContentPane(contentPane);


        app.setVisible(true);
        app.pack();
    }
}
