package WAVEGEN;

import javax.swing.*;

/**
 * Created by NST069 on 26.03.2019.
 */
public class wave_test extends JFrame{

    Synth synth;

    public wave_test(){
        synth=new Synth();
    }

    public static void main(String[] args){
        wave_test app = new wave_test();
        app.setVisible(true);
        app.pack();
    }
}
