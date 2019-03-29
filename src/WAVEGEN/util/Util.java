package WAVEGEN.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.lang.Math.PI;

/**
 * Created by NST069 on 26.03.2019.
 */
public class Util {
    public static void invokeProcedure(Procedure p, boolean print){
        try{
            p.invoke();
        }catch(Exception ex){ if(print) ex.printStackTrace();}
    }

    public static class WndDesigner{
        public static final Border LINE_BORDER = BorderFactory.createLineBorder(Color.BLACK);
    }

    public static class Math{
        public static double frequencyToAngularFrequency(double freq) {
            return 2 * PI * freq;
        }
    }
}
