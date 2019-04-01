package WAVEGEN.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.lang.Math.*;

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

        public static double getKeyFrequency(int keyNum){
            return pow(root(2,12), keyNum-49) * 440;
        }
        public static double root(double number, double root){
            return pow(E, log(number)/root);
        }
    }

    public static class ParameterHandling{
        public static final Robot PARAMETER_ROBOT;
        static {
            try{
                PARAMETER_ROBOT = new Robot();
            }catch(AWTException e){
                throw new ExceptionInInitializerError("Cannot Construct Robot Instance");
            }
        }
    }
}
