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
            initChannels();
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

    public void initChannels(){
        channels[0].programChange(1);       //Acoustic Piano
        channels[1].programChange(14);      //Xylophone
        channels[2].programChange(20);      //Church Organ
        channels[3].programChange(26);      //Steel Guitar
        channels[4].programChange(33);      //Acoustic Bass
        channels[5].programChange(41);      //Violin
        channels[6].programChange(81);      //Square Wave
        channels[7].programChange(92);      //Space Pad
        channels[8].programChange(118);     //Melodic Drum
        //channels[9] drums
    }
}
