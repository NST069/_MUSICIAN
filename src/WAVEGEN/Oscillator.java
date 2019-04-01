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
    private int toneOffset;
    private double getToneOffset(){
        return toneOffset/1000d;
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
        });
        add(cb);

        JLabel toneParameter = new JLabel("x0.00");
        toneParameter.setBounds(165,65,50,25);
        toneParameter.setBorder(Util.WndDesigner.LINE_BORDER);
        toneParameter.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
                        new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB), new Point(0,0),"blankCursor");
                setCursor(BLANK_CURSOR);
                mouseClickLocation = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        toneParameter.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(mouseClickLocation.y!=e.getYOnScreen()){
                    boolean mouseMovingUp = mouseClickLocation.y - e.getYOnScreen() > 0;
                    if(mouseMovingUp && toneOffset<TONE_OFFSET_LIMIT){
                        ++toneOffset;
                    }
                    else if(!mouseMovingUp && toneOffset>-TONE_OFFSET_LIMIT){
                        --toneOffset;
                    }
                    ApplyToneOffset();
                    toneParameter.setText("x" + String.format("%.3f",getToneOffset()));
                }
                Util.ParameterHandling.PARAMETER_ROBOT.mouseMove(mouseClickLocation.x,mouseClickLocation.y);
            }
        });
        add(toneParameter);

        JLabel toneText=new JLabel("Tone: ");
        toneText.setBounds(172,40,75,25);
        add(toneText);

        setSize(280, 100);
        setBorder(Util.WndDesigner.LINE_BORDER);
        setLayout(null);
    }

    public double nextSample(){
        double sample = wavetable.getSamples()[wavetableIndex];
        wavetableIndex = (wavetableIndex + wavetableStepSize) % Wavetable.SIZE;
        return sample;
    }

    private void ApplyToneOffset(){
        wavetableStepSize = (int)(Wavetable.SIZE * (keyFrequency*Math.pow(2, getToneOffset())))/Synth.AudioInfo.SAMPLE_RATE;
    }
}
