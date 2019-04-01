package WAVEGEN;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NST069 on 29.03.2019.
 */
public class SynthControlContainer extends JPanel {
    private Synth synth;
    protected boolean on;
    public boolean isOn(){return on;}
    public void setOn(boolean value){on=value;}
    private Point mouseClickLocation;

    public Point getMouseClickLocation(){return mouseClickLocation;}
    public void setMouseClickLocation(Point value){mouseClickLocation=value;}

    public SynthControlContainer(Synth s){
        this.synth = s;
    }

    @Override
    public Component add(Component component){
        component.addKeyListener(synth.getKeyAdapter());
        return super.add(component);
    }
}
