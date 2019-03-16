package MIDIGEN;


/**
 * Created by NST069 on 13.03.2019.
 */
public class Note {

    int channel;
    int duration;
    int velocity;
    int key;

    public Note(int channel, int duration, int velocity, int key){
        this.channel=channel;
        this.duration=duration;
        this.velocity=velocity;
        this.key=key;
    }
}
