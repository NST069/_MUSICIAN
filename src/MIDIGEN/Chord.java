package MIDIGEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by NST069 on 14.03.2019.
 */
public class Chord {

    HashMap<Integer, ArrayList<Note>> chord;

    public Chord() {
        chord = new HashMap<>();
    }

    public void Add(Note n) {

        if (!chord.containsKey(n.channel)) {
            ArrayList<Note> l = new ArrayList<>();
            l.add(n);
            chord.put(n.channel, l);
        }
        else{
            chord.get(n.channel).add(n);
        }
    }

    public Set<Integer> getChannels(){
        return chord.keySet();
    }

    public ArrayList<Note> getNotesOfChannel(int channel){
        ArrayList<Note> n=new ArrayList<>();
        if(chord.containsKey(channel)){
            for(Note x : chord.get(channel)){
                n.add(x);
            }
        }
        return n;
    }

    public int Duration(){
        int max=0;
        for(Map.Entry<Integer, ArrayList<Note>> pair : chord.entrySet()){
            for(Note n:pair.getValue()){
                if(n.duration>max) max=n.duration;
            }
        }
        return max;
    }
}

