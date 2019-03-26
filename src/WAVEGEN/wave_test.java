package WAVEGEN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by NST069 on 26.03.2019.
 */
public class wave_test extends JFrame{

    Synth synth = new Synth();

    public wave_test(){
        setMinimumSize(new Dimension(500, 540));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        initListeners(this);
    }

    private void initListeners(JFrame f) {
        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!synth.isThreadRunning()){
                    synth.setShouldGenerate(true);
                    synth.TriggerPlayback();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                synth.setShouldGenerate(false);
            }
        });
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synth.CloseAudioThread();
            }
        });
        //
    }

    public static void main(String[] args){
        wave_test app = new wave_test();
        app.setVisible(true);
        app.pack();
    }
}
