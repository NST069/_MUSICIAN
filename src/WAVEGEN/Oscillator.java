package WAVEGEN;

import WAVEGEN.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by NST069 on 28.03.2019.
 */
public class Oscillator extends SynthControlContainer {
    private static final int TONE_OFFSET_LIMIT=2000;

    private double keyFrequency;
    public void setKeyFrequency(double value){
        keyFrequency = value;
        ApplyToneOffset();
    }
    private RefWrapper<Integer> toneOffset = new RefWrapper<>(0);
    private double getToneOffset(){
        return toneOffset.val/1000d;
    }
    private RefWrapper<Integer> volume = new RefWrapper<>(100);
    private double getVolumeMultiplier(){
        return volume.val/100d;
    }

    private Wavetable wavetable = Wavetable.Sine;
    private int wavetableStepSize;
    private int wavetableIndex;

    public Oscillator(Synth synth) {
        super(synth);
        JComboBox<Wavetable> cb = new JComboBox<>(Wavetable.values());
        cb.setSelectedItem(Wavetable.Sine);
        cb.setBounds(10, 10, 75, 25);
        cb.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                wavetable = (Wavetable) l.getItem();
            }
            synth.updateWaveViewer();
        });
        add(cb);

        JLabel toneParameter = new JLabel("x0.00");
        toneParameter.setBounds(165,65,50,25);
        toneParameter.setBorder(Util.WndDesigner.LINE_BORDER);
        Util.ParameterHandling.AddParameterMouseListeners(toneParameter, this,
                -TONE_OFFSET_LIMIT, TONE_OFFSET_LIMIT, 1, toneOffset,
                ()->{
                    ApplyToneOffset();
                    toneParameter.setText("x"+String.format("%.3f", getToneOffset()));
                    synth.updateWaveViewer();
                });
        add(toneParameter);

        JLabel toneText=new JLabel("Tone: ");
        toneText.setBounds(172,40,75,25);
        add(toneText);

        JLabel volumeParameter = new JLabel(" 100%");
        volumeParameter.setBounds(222,65,50,25);
        volumeParameter.setBorder(Util.WndDesigner.LINE_BORDER);
        Util.ParameterHandling.AddParameterMouseListeners(volumeParameter, this, 0, 100, 1,
                volume, ()->{
                    volumeParameter.setText(" "+volume.val+"%");
                    synth.updateWaveViewer();
                });
        add(volumeParameter);

        JLabel volumeText=new JLabel("Volume: ");
        volumeText.setBounds( 225,40, 75,25);
        add(volumeText);

        setSize(280, 100);
        setBorder(Util.WndDesigner.LINE_BORDER);
        setLayout(null);
    }

    public double nextSample(){
        double sample = wavetable.getSamples()[wavetableIndex]*getVolumeMultiplier();
        wavetableIndex = (wavetableIndex + wavetableStepSize) % Wavetable.SIZE;
        return sample;
    }

    private void ApplyToneOffset(){
        wavetableStepSize = (int)(Wavetable.SIZE * (Util.Math.offsetTone(keyFrequency, getToneOffset()))/Synth.AudioInfo.SAMPLE_RATE);
    }

    public double[] getSampleWaveform(int numSamples){
        double[] samples = new double[numSamples];

        double frequency = 1.0 / (numSamples/(double)Synth.AudioInfo.SAMPLE_RATE)*9.0;
        int index=0;
        int stepSize = (int)(Wavetable.SIZE * Util.Math.offsetTone(frequency, getToneOffset())/Synth.AudioInfo.SAMPLE_RATE);

        for(int i=0; i<numSamples; ++i){
            samples[i]=wavetable.getSamples()[index]*getVolumeMultiplier();
            index = (index+stepSize) % Wavetable.SIZE;
        }

        return samples;
    }
}
