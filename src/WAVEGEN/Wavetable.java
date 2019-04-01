package WAVEGEN;

import WAVEGEN.util.Util;

/**
 * Created by NST069 on 01.04.2019.
 */
enum Wavetable {
    Sine, Square, Saw, Triangle;

    static final int SIZE=8192;
    private final float[] samples = new float[SIZE];

    static{
        final double FUNDAMENTAL_FREQ=1d/(SIZE/(double)Synth.AudioInfo.SAMPLE_RATE);
        for(int i=0; i<SIZE; ++i){
            double t = i/(double)Synth.AudioInfo.SAMPLE_RATE;
            double tDivP= t/(1d/FUNDAMENTAL_FREQ);
            Sine.samples[i] = (float)Math.sin(Util.Math.frequencyToAngularFrequency(FUNDAMENTAL_FREQ)*t);
            Square.samples[i] = Math.signum(Sine.samples[i]);
            Saw.samples[i] = (float)(2d*tDivP - Math.floor(.5+tDivP));
            Triangle.samples[i] = (float)(2d* Math.abs(Saw.samples[i])-1d);
        }
    }

    float[] getSamples(){
        return samples;
    }
}
