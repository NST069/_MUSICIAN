package WAVEGEN;

/**
 * Created by NST069 on 26.03.2019.
 */
public class Synth {

    private boolean shouldgenerate;
    private int wavePos;
    private final AudioThread thread = new AudioThread(()->{
        if(!shouldgenerate){
            return null;
        }
        short[] s = new short[AudioThread.BUFFER_SIZE];
        for(int i=0; i<AudioThread.BUFFER_SIZE; ++i){
            s[i] = (short)(Short.MAX_VALUE * Math.sin(2* Math.PI * 440 / AudioInfo.SAMPLE_RATE * wavePos++));
        }
        return s;
    });

    public boolean isThreadRunning(){return thread.isRunning();}
    public void setShouldGenerate(boolean value){shouldgenerate=value;}
    public void CloseAudioThread(){thread.close();}
    public void TriggerPlayback(){ thread.triggerPlayback();}
    public static class AudioInfo{
        public static final int SAMPLE_RATE = 44100;
    }

}
