package WAVEGEN;

import WAVEGEN.util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by NST069 on 01.04.2019.
 */
public class WaveViewer extends JPanel {
    private Oscillator[] oscs;

    public WaveViewer(Oscillator[] oscs){
        this.oscs=oscs;
        setBorder(Util.WndDesigner.LINE_BORDER);
    }

    @Override
    public void paintComponent(Graphics g){
        final int PAD=25;

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        int numSamples = getWidth() - PAD*2;
        double[] mixedSamples = new double[numSamples];
        for(Oscillator o : oscs){
            double[]samples = o.getSampleWaveform(numSamples);
            for(int i=0;i<samples.length;++i){
                mixedSamples[i]+=samples[i]/oscs.length;
            }
        }

        int midY = getHeight()/2;
        Function<Double, Integer> sampletoYCoord = sample->(int)(midY+sample*(midY-PAD));

        g2d.drawLine(PAD, midY, getWidth()-PAD, midY);
        g2d.drawLine(PAD, PAD, PAD, getHeight()-PAD);

        for(int i=0;i<numSamples;++i){
            int nextY = (i==numSamples-1)?(sampletoYCoord.apply(mixedSamples[i])):(sampletoYCoord.apply(mixedSamples[i+1]));
            g2d.drawLine(PAD+i, sampletoYCoord.apply(mixedSamples[i]), PAD+i+1, nextY);
        }
    }
}
