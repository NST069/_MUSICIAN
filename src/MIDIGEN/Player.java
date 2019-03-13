package MIDIGEN;

import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Player {

    Synth s;

    public Player(){

    }

    public void Play(){
        s = new Synth();
        //int notes[][] = {{1000,69},{1000,72},{1000,76}};
        ArrayList<Note> notes = Generator.GenerateSound();
        for(Note note:notes){
            if(note.notes.get(0)!=-1){
                s.playSound(0, note);
            }
            else{
                try{
                    Thread.sleep(note.duration);
                }catch(InterruptedException ex){ex.printStackTrace();}
            }
        }
        s.close();
    }
}
