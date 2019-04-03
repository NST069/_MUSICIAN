package WAVEGEN;

import WAVEGEN.util.Util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;

/**
 * Created by NST069 on 28.03.2019.
 */
public class Oscillator extends SynthControlContainer {

    @Override
    public String toString(){
        String s ="";
        s+=wavetable.toString()+":"+getToneOffset()+" "+volume.val+"%";
        return s;
    }

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

        /*
        TODO: Phase Offset, Detune, Panning, split toneOffset to Coarse pitch & Fine pitch, change Volume to Mix(remove from first?)
        TODO: Invert Osc, Noise, OpenSoundFile, Phase Randomness(?), Osc3 to Amplitude Modulation(?)
        TODO: Knobs
        */
        /*JLabel toneParameter = new JLabel("x0.00");
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
        */
        JKnob jTP = new JKnob();
        jTP.setBounds(170,50,40,40);
        jTP.setStopper(5*Math.PI/6);
        add(jTP);


        JLabel toneText=new JLabel("Tone: ");
        toneText.setBounds(172,40,75,25);
        add(toneText);

        JSlider volumeSlider = new JSlider(JSlider.VERTICAL, 0,100,100);
        volumeSlider.setBounds(250, 5,25,90);
        volumeSlider.setBorder(Util.WndDesigner.LINE_BORDER);
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volume.val = ((JSlider)e.getSource()).getValue();
                synth.updateWaveViewer();
            }
        });
        add(volumeSlider);

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

    public void Update(Oscillator o){
        setKeyFrequency(o.keyFrequency);
        toneOffset = o.toneOffset;
        volume=o.volume;
        wavetable=o.wavetable;
    }
}
