package MIDIGEN;

import javax.sound.midi.MetaMessage;
import java.io.IOException;
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
        //Generate();
    }
    public void close(){
        s.close();
    }

    public void Play() {/*
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
        */
        s.Start();
    }

    public void Open(){
        s=new Synth(140, 300);
        //Chords = Generator.GenerateSound();
    }

    public void save(){
        try{

            s.Record("1.mid");
            System.out.println("saved");
        }catch(IOException ex){ex.printStackTrace();}

    }
}
