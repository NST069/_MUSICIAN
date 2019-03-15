package MIDIGEN;

import javax.sound.midi.MetaMessage;
import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Player {

    Synth s;
    ArrayList<Chord> Chords;
    public boolean isPlaying;

    public Player(){
        isPlaying=false;
        Generate();
    }

    public void Play() {
        s = new Synth();
        isPlaying = true;
        Metronome m = new Metronome();
        m.start(130);
        for (Chord c : Chords) {
            if(isPlaying==true) {
                s.playSound(c);
            }
        }
        m.stop();
        isPlaying = false;
        s.close();
    }
    public void Generate(){
        Chords = Generator.GenerateSound();
    }
}
