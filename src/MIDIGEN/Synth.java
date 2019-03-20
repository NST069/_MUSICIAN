package MIDIGEN;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Synth {

    private Sequencer sequencer;
    private Synthesizer synth;
    private MidiChannel[] channels;
    private Track track;

    private boolean paused;

    public Synth(int bpm, int duration) {
        try {
            paused=true;
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

    public void close(){
        if(sequencer.isOpen()) sequencer.close();
        if(synth.isOpen()) synth.close();
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

    public void Start(boolean restart) {

        try {
            paused=false;
            if(restart) {
                sequencer.setTickPosition(0);
            }
            synth = MidiSystem.getSynthesizer();
            Transmitter seqTrans = sequencer.getTransmitter();
            Receiver synthRcvr = synth.getReceiver();
            seqTrans.setReceiver(synthRcvr);
            synth.open();
            sequencer.start();


            while (true) {
                System.out.println(sequencer.getMicrosecondPosition());
                if (!sequencer.isRunning() || paused) {
                    System.out.println("close");
                    sequencer.stop();
                    seqTrans.close();
                    synthRcvr.close();
                    synth.close();
                    return;
                }
            }

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    public long getPosition(){
        return sequencer.getTickPosition();
    }
    public void setInitialPosition(){ sequencer.setTickPosition(0);}
    public long getLength(){
        return sequencer.getTickLength();
    }
    public boolean getPausedStatus(){return paused;}
    public void Pause(){
        System.out.println("paused");
        paused=true;
    }

    public void Record(String to) throws IOException{
        File f = new File(System.getProperty("user.home")+"\\Desktop\\"+to);
        System.out.println(f.getAbsolutePath());
        MidiSystem.write(sequencer.getSequence(),MidiSystem.getMidiFileTypes(sequencer.getSequence())[0], f);
        return;
    }
}
