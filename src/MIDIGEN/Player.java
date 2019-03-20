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
    Thread PlayingThread;

    public Player(){
        //Generate();
    }
    public void Close(){
        s.close();
    }

    public void Play() throws InterruptedException{
        PlayingThread = new Thread(()-> s.Start(true));
        PlayingThread.run();
    }
    public void Pause() throws InterruptedException {
        PlayingThread.interrupt();
        s.Pause();
        PlayingThread.join();
    }
    public void Resume() throws InterruptedException{
        if(s.getPosition()<s.getLength()) {
            PlayingThread = new Thread(()-> s.Start(false));
        }
        else {
            PlayingThread = new Thread(()-> s.Start(true));
        }
        PlayingThread.run();
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

    public void Save(){
        try{

            s.Record("1.mid");
            System.out.println("saved");
        }catch(IOException ex){ex.printStackTrace();}

    }
}
