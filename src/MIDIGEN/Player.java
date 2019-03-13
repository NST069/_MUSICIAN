package MIDIGEN;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Player {

    Synth s;
    ArrayList<Note> notes;
    public boolean isPlaying;

    public Player(){
        isPlaying=false;
        Generate();
    }

    public void Play() {
        s = new Synth();
        isPlaying = true;
            for (Note note : notes) {
                if(isPlaying==true) {
                    if (note.notes.get(0) != -1) {
                        s.playSound(0, note);
                    } else {
                        try {
                            Thread.sleep(note.duration);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            isPlaying = false;
        s.close();
    }
    public void Generate(){
        notes = Generator.GenerateSound();
    }
}
