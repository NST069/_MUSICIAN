package WAVEGEN;

import WAVEGEN.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/**
 * Created by NST069 on 26.03.2019.
 */
public class Synth {

    @Override
    public String toString(){
        String s ="";
        for(Oscillator o: oscs){
            if(o!=null){
                s+=o.toString()+", ";
            }
        }
        return s;
    }

    private static final HashMap<Character, Double> KEY_FREQS = new HashMap<>();

    private final Oscillator[] oscs = new Oscillator[3];
    private final WaveViewer waveViewer= new WaveViewer(oscs);

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
        frame.setMinimumSize(new Dimension(923, 345));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);


        int y = 0;
        for (int i = 0; i < oscs.length; i++) {
            oscs[i] = new Oscillator(this);
            oscs[i].setLocation(5, y);
            frame.add(oscs[i]);
            y += 105;
        }
        waveViewer.setBounds(290,0,620,310);
        frame.add(waveViewer);

        frame.addKeyListener(keyAdapter);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thread.close();
            }
        });
        frame.repaint();
    }

    public void show(){
        frame.setVisible(true);
    }

    static{
        final int STARTING_KEY = 16;
        final int KEYFREQ_INCREMENT=2;
        final char[] KEYS = "zxcvbnm,./asdfghjkl;'qwertyuiop[]".toCharArray();
        for(int i = STARTING_KEY, key=0;i<KEYS.length * KEYFREQ_INCREMENT + STARTING_KEY; i+=KEYFREQ_INCREMENT, ++key){
            KEY_FREQS.put(KEYS[key], Util.Math.getKeyFrequency(i));
        }
    }

    private final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(!KEY_FREQS.containsKey(e.getKeyChar())){return;}
            if (!thread.isRunning()) {
                for(Oscillator o : oscs){
                    o.setKeyFrequency(KEY_FREQS.get(e.getKeyChar()));
                }
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

    public void updateWaveViewer(){
        waveViewer.repaint();
    }
}
