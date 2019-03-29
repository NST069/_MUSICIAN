package WAVEGEN;

import WAVEGEN.util.Util;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Random;

/**
 * Created by NST069 on 28.03.2019.
 */
public class Oscillator extends SynthControlContainer {
    private Waveform waveform = Waveform.Sine;
    private static final double frequency = 440;
    private int wavepos;
    private final Random r=new Random();

    public Oscillator(Synth synth) {
        super(synth);
        JComboBox<Waveform> cb = new JComboBox<>(new Waveform[]{Waveform.Sine, Waveform.Saw, Waveform.Triangle, Waveform.Square, Waveform.Noise});
        cb.setSelectedItem(Waveform.Sine);
        cb.setBounds(10, 10, 75, 25);
        cb.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                waveform = (Waveform) l.getItem();
            }
        });
        add(cb);

        setSize(280, 100);
        setBorder(Util.WndDesigner.LINE_BORDER);
        setLayout(null);
    }

    private enum Waveform{
        Sine, Square, Triangle, Saw, Noise
    }

    public double nextSample(){
        double tDivP=(wavepos++ / (double)Synth.AudioInfo.SAMPLE_RATE)/(1/frequency);
        switch(waveform){
            case Sine:
                return Math.sin(Util.Math.frequencyToAngularFrequency(frequency)*(wavepos-1)/ Synth.AudioInfo.SAMPLE_RATE);
            case Saw:
                return 2d * (tDivP - Math.floor(0.5+tDivP));
            case Square:
                return Math.signum(Math.sin(Util.Math.frequencyToAngularFrequency(frequency)*(wavepos-1)/ Synth.AudioInfo.SAMPLE_RATE));
            case Triangle:
                return 2d * Math.abs(2d * (tDivP - Math.floor(0.5+tDivP)))-1;
            case Noise:
                return r.nextDouble();
            default:
                throw new RuntimeException();
        }
    }
}
