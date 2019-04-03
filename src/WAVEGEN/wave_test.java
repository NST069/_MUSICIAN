package WAVEGEN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by NST069 on 26.03.2019.
 */
public class wave_test extends JFrame{

    ArrayList<Synth> synths = new ArrayList<Synth>();
    JComboBox<Synth> cb_synths;
    JButton open;

    public wave_test(){
        for(int i=0;i<3;i++) synths.add(new Synth());

        cb_synths = new JComboBox<>();
        for(Synth s:synths) cb_synths.addItem(s);
        cb_synths.setBounds(5,5,495, 30);
        cb_synths.setSelectedIndex(0);
        open=new JButton("Open");
        open.setBounds(505,5,100,30);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Synth)cb_synths.getSelectedItem()).show();
                synths.get(2).UpdateParameters(synths.get(0).getOscs());
            }
        });
        setMinimumSize(new Dimension(615, 100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        //JPanel p = new JPanel();
        //p.setLayout(new BorderLayout());
        add(cb_synths);
        add(open);
        //add(p);
    }

    public static void main(String[] args){
        wave_test app = new wave_test();
        app.setVisible(true);
        app.pack();
    }
}
