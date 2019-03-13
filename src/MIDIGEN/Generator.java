package MIDIGEN;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Generator {
    static Random r = new Random();

    public static ArrayList<Note> GenerateSound(){
        ArrayList<Note> l = new ArrayList<>();
        int ClipDuration = Math.abs((r.nextInt()%60000)+1);
        int counter=0;
        while(counter<ClipDuration){
            Note n = makeNote();
            l.add(n);
            counter+=n.duration;
        }

        return l;
    }

    static Note makeNote(){
        ArrayList<Integer> n=new ArrayList<>();
        int chord = Math.abs((r.nextInt()%3)+1);
        if(chord==0) {n.add(-1);}
        else {
            for (int i = 0; i < chord; i++) {
                n.add(Math.abs((r.nextInt() % 60) + 46));
            }
        }
        return new Note(Math.abs(r.nextInt()%1000), Math.abs((r.nextInt()%20)+70), n);
    }
}
