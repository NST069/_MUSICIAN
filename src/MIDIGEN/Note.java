package MIDIGEN;


/**
 * Created by NST069 on 13.03.2019.
 */
public class Note {

    int channel;
    int duration;
    int volume;
    int note;

    public Note(int c, int d, int v, int n){
        this.channel=c;
        this.duration=d;
        this.volume=v;
        this.note=n;
    }
}
