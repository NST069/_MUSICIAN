package MIDIGEN;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Generator {
    static Random r = new Random();

    public static ArrayList<Chord> GenerateSound(int ClipDuration){
        ArrayList<Chord> l = new ArrayList<>();
        //int ClipDuration = Math.abs((r.nextInt()%60000)+1);
        int counter=0;
        while(counter<ClipDuration){
            Chord n = makeChord();
            l.add(n);
            counter+=n.Duration();
        }

        return l;
    }

    static Chord makeChord(){
        Chord chord = new Chord();

        int notes = Math.abs(r.nextInt(5));
        for(int i=0;i<notes;i++){
            chord.Add(makeNote());
        }

        return chord;
    }

    static Note makeNote() {
        int channel = Math.abs(r.nextInt(8));               //Math.abs(r.nextInt()%16);
        int duration = Math.abs(r.nextInt(100));
        int volume = Math.abs((r.nextInt() % 20) + 70);
        int note = Math.abs((r.nextInt() % 60) + 46);
        return new Note(channel, duration, volume, note);
    }
}
