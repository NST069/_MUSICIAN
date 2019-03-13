package MIDIGEN;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Synth {

    private MidiChannel[] channels;
    private Synthesizer synth;

    public Synth(){
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels=synth.getChannels();
        }catch(MidiUnavailableException ex){ex.printStackTrace();}
    }
    public void close(){synth.close();}

    public void playSound(int channel, int duration, int volume, int... notes){
        for(int note:notes){
            channels[channel].noteOn(note, volume);
        }
        try{
            Thread.sleep(duration);
        }catch(InterruptedException ex){ex.printStackTrace();}
        for(int note:notes){
            channels[channel].noteOff(note);
        }
    }
}
