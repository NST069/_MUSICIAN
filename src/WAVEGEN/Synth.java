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
public class Synth {

    private final Oscillator[] oscs = new Oscillator[3];

    private boolean shouldgenerate;
    private final AudioThread thread = new AudioThread(() -> {
        if (!shouldgenerate) {
            return null;
        }
        short[] s = new short[AudioThread.BUFFER_SIZE];
        for (int i = 0; i < AudioThread.BUFFER_SIZE; ++i) {
            double d = 0;
            for (Oscillator o : oscs) {
                d += o.nextSample() / oscs.length;
            }
            s[i] = (short) (Short.MAX_VALUE * d);
        }
        return s;
    });

    private final JFrame frame = new JFrame("WAVEGEN");

    //
//    public boolean isThreadRunning(){return thread.isRunning();}
//    public void setShouldGenerate(boolean value){shouldgenerate=value;}
//    public void CloseAudioThread(){thread.close();}
//    public void TriggerPlayback(){ thread.triggerPlayback();}
    public static class AudioInfo {
        public static final int SAMPLE_RATE = 44100;
    }

    Synth() {
        frame.setMinimumSize(new Dimension(500, 540));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        int y = 0;
        for (int i = 0; i < oscs.length; i++) {
            oscs[i] = new Oscillator(this);
            oscs[i].setLocation(5, y);
            frame.add(oscs[i]);
            y += 105;
        }
        initListeners(frame);
        frame.repaint();
    }

    private void initListeners(JFrame f) {
        f.addKeyListener(keyAdapter);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thread.close();
            }
        });
        //
    }

    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!thread.isRunning()) {
                shouldgenerate = true;
                thread.triggerPlayback();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            shouldgenerate = false;
        }
    };

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}
