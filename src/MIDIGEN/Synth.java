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

    public void playSound(Chord c){
        for(int m : c.getChannels()) {
            for (Note n : c.getNotesOfChannel(m)) {
                if (n.note != -1) {
                    channels[m].noteOn(n.note, n.volume);
                }
            }

            for (Note n : c.getNotesOfChannel(m)) {
                try {
                    Thread.sleep(n.duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            for (Note n : c.getNotesOfChannel(m)) {
                if (n.note != -1) {
                    channels[m].noteOff(n.note);
                }
            }
        }
    }
}
