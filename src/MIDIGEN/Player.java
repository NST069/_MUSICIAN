package MIDIGEN;

import javax.sound.midi.MetaMessage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Player{

    Synth s;
    ArrayList<Chord> Chords;

    public Player(){
        //Generate();
    }
    public void Close(){
        s.close();
    }

    public void Play(){
        s.Start(true);
    }
    public void Pause(){
        s.Pause();
    }
    public void Resume(){
        if(s.getPosition()<s.getLength()) {
            s.Start(false);
        }
        else {
            s.Start(true);
        }
    }
    public void Stop() throws InterruptedException{
        Pause();
        s.setInitialPosition();
    }

    public void Open(int bpm, int duration){
        s=new Synth(bpm, duration);
        //Chords = Generator.GenerateSound();
    }

    public boolean isPaused(){
        return s.getPausedStatus();
    }

    public void Save(File f){
        try{

            s.Record(f);
            System.out.println("saved");
        }catch(IOException ex){ex.printStackTrace();}

    }
    public void Generate(){
        notes = Generator.GenerateSound();
    }
}
