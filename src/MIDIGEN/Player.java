package MIDIGEN;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Player {

    Synth s;

    public Player(){

    }

    public void Play(){
        s = new Synth();
        int notes[][] = {{1000,69},{1000,72},{1000,76}};
        for(int[] note:notes){
            if(note[1]!=-1){
                s.playSound(0, note[0], 80, note[1]);
            }
            else{
                try{
                    Thread.sleep(note[0]);
                }catch(InterruptedException ex){ex.printStackTrace();}
            }
        }
        s.close();
    }
}
