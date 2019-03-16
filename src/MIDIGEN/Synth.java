package MIDIGEN;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Synth {
/*
    private MidiChannel[] channels;
    private Synthesizer synth;

    public Synth(){
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels=synth.getChannels();
            initChannels();
        }catch(MidiUnavailableException ex){ex.printStackTrace();}
    }
    public void close(){synth.close();}

    public void playSound(Chord c){
        for(int m : c.getChannels()) {
            for (Note n : c.getNotesOfChannel(m)) {
                if (n.note != -1) {
                    channels[m].noteOn(n.note, n.volume);
                }
            }

            for (Note n : c.getNotesOfChannel(m)) {
                try {
                    Thread.sleep(n.duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            for (Note n : c.getNotesOfChannel(m)) {
                if (n.note != -1) {
                    channels[m].noteOff(n.note);
                }
            }
        }
    }

    public void initChannels(){
        channels[0].programChange(1);       //Acoustic Piano
        channels[1].programChange(14);      //Xylophone
        channels[2].programChange(20);      //Church Organ
        channels[3].programChange(26);      //Steel Guitar
        channels[4].programChange(33);      //Acoustic Bass
        channels[5].programChange(41);      //Violin
        channels[6].programChange(81);      //Square Wave
        channels[7].programChange(92);      //Space Pad
        channels[8].programChange(118);     //Melodic Drum
        //channels[9] drums
    }
    */
    /***
     * MidiEvents:
     * 192 - change Instrument
     * 144 - Note On
     * 128 - Note Off
     */
    private Sequencer sequencer;
    private MidiChannel[] channels;
    private Track track;

    public Synth(int bpm, int duration) {
        try {
            sequencer = MidiSystem.getSequencer();
            channels = MidiSystem.getSynthesizer().getChannels();
            sequencer.open();
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            track = seq.createTrack();
            initChannels(track);

            AnalyzeGenerated(Generator.GenerateSound(duration));

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(bpm);

        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }

    private MidiEvent makeEvent(int cmd, int channel, int note, int velocity, int tick) {
        MidiEvent ev = null;

        try {
            ShortMessage sm = new ShortMessage();
            sm.setMessage(cmd, channel, note, velocity);
            ev = new MidiEvent(sm, tick);
        } catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
        }

        return ev;
    }

    private void initChannels(Track track) {
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 0, 1, 0, 1));//Acoustic Piano
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 1, 14, 0, 1));//Xylophone
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 2, 20, 0, 1));//Church Organ
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 3, 26, 0, 1));//Steel Guitar
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 4, 33, 0, 1));//Acoustic Bass
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 5, 41, 0, 1));//Violin
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 6, 81, 0, 1));//Square Wave
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 7, 92, 0, 1));//Space Pad
        track.add(makeEvent(ShortMessage.PROGRAM_CHANGE, 8, 118, 0, 1));//Melodic Drum
        //channels[9] drums
    }

    private void AnalyzeGenerated(ArrayList<Chord> data) {
        try {
            int t = 0;
            for (Chord x : data) {
                for (int m : x.getChannels()) {
                    int max=0;
                    for (Note n : x.getNotesOfChannel(m)) {
                        addNote(track, n, t);
                        max=(max<n.duration)?n.duration:max;
                    }
                    t+=max;
                }
            }
        }catch(InvalidMidiDataException | InterruptedException ex){ex.printStackTrace();}
    }

    private void addNote(Track track, Note n,int start) throws InvalidMidiDataException, InterruptedException{
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, n.channel,n.key,n.velocity);
        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, n.channel,n.key,n.velocity);

        track.add(new MidiEvent(on, start));
        track.add(new MidiEvent(off, start+n.duration));
    }

    public void Start() {
        sequencer.start();
        while(true){
            if(!sequencer.isRunning()){
                sequencer.close();
            }
        }
    }
    public void Record(String to) throws IOException{
        File f = new File(System.getProperty("user.home")+"\\Desktop\\"+to);
        System.out.println(f.getAbsolutePath().toString());
        MidiSystem.write(sequencer.getSequence(),MidiSystem.getMidiFileTypes(sequencer.getSequence())[0], f);
    }
}
