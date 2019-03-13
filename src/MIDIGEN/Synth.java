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

    public void playSound(int channel, Note note){
        for(int n:note.notes){
            channels[channel].noteOn(n, note.volume);
        }
        try{
            Thread.sleep(note.duration);
        }catch(InterruptedException ex){ex.printStackTrace();}
        for(int n:note.notes){
            channels[channel].noteOff(n);
        }
    }
}
