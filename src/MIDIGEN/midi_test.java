package MIDIGEN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by NST069 on 21.03.2019.
 */
public class midi_test extends JFrame{

    private JPanel wrapper;
    private JButton play_pause;
    private JButton stop;
    private JButton save;
    private JButton generate;

    private Player p;

    public midi_test() {
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComps();
        initListeners(this);

        p=new Player();
        p.Open(130, 300);

        wrapper = new JPanel(new GridLayout(2,2));
        wrapper.add(play_pause);
        wrapper.add(stop);
        wrapper.add(save);
        wrapper.add(generate);
        add(wrapper);
    }

    public static void main(String[] args) {
        midi_test app = new midi_test();
        app.setVisible(true);
        app.pack();



    }

    private void initComps(){
        play_pause=new JButton("Play");
        stop=new JButton("Stop");
        save=new JButton("Save");
        generate=new JButton("Generate");
    }
    private void initListeners(JFrame f){
        play_pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(p.isPaused()){
                        play_pause.setText("Pause");
                        p.Resume();
                    }
                    else{
                        play_pause.setText("Play");
                        p.Pause();
                    }

                }catch(InterruptedException ex){ex.printStackTrace();}
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    p.Stop();
                }catch(InterruptedException ex){ex.printStackTrace();}
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Save to
                p.Save();
            }
        });
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.Close();
                p.Open(140, 300);
            }
        });
    }
}
