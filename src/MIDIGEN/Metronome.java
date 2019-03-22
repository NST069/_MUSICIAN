package MIDIGEN;

import javax.sound.midi.*;

/**
 * Created by NST069 on 15.03.2019.
 */
public class Metronome implements MetaEventListener{

    private Sequencer sequencer;
    private int bpm;

    public void start(int bpm){
        try{
            this.bpm=bpm;
            openSequencer();
            Sequence seq =createSequence();
            startSequence(seq);
        } catch (InvalidMidiDataException | MidiUnavailableException ex){ex.printStackTrace();}
    }

    public void stop(){
        try{
        closeSequencer();
        } catch (MidiUnavailableException ex){ex.printStackTrace();}
    }

    private void openSequencer() throws MidiUnavailableException{
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.addMetaEventListener(this);
    }
    private void closeSequencer() throws MidiUnavailableException{
        sequencer.stop();
        sequencer.close();
    }

    private Sequence createSequence(){
        try{
            Sequence seq=new Sequence(Sequence.PPQ,1);
            Track track=seq.createTrack();

            ShortMessage msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9,1,0);
            MidiEvent evt = new MidiEvent(msg,0);
            track.add(evt);

            addNoteEvent(track, 0);
            addNoteEvent(track, 1);
            addNoteEvent(track, 2);
            addNoteEvent(track, 3);

            msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9,1,0);
            evt = new MidiEvent(msg,0);
            track.add(evt);

            return seq;
        } catch(InvalidMidiDataException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private void addNoteEvent(Track track, long tick) throws InvalidMidiDataException{
        ShortMessage msg = new ShortMessage(ShortMessage.NOTE_ON, 9,35,100);
        MidiEvent evt = new MidiEvent(msg, tick);
        track.add(evt);
    }

    private void startSequence(Sequence seq) throws InvalidMidiDataException{
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(bpm);
        sequencer.start();
    }

    public static void main(String[] args){
        int bpm = 140;
        new Metronome().start(bpm);
    }

    @Override
    public void meta(MetaMessage message){
        if(message.getType()!=47){ //End Of Track
            return;
        }
        doLoop();
    }

    private void doLoop(){
        if(sequencer == null || !sequencer.isOpen()){
            return;
        }
        sequencer.setTickPosition(0);
        sequencer.start();
        sequencer.setTempoInBPM(bpm);
    }
}
