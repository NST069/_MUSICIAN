package MIDIGEN;

import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Note {

    int duration;
    int volume;
    ArrayList<Integer> notes;

    public Note(int d, int v, ArrayList<Integer> notes){
        this.duration=d;
        this.volume=v;
        this.notes=notes;
    }
}
