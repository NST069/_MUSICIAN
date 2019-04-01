package WAVEGEN.util;

import WAVEGEN.RefWrapper;
import WAVEGEN.SynthControlContainer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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

        public static double offsetTone(double baseFrequency, double frequencyMultiplier){
            return baseFrequency * pow(2, frequencyMultiplier);
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
        public static void AddParameterMouseListeners(Component component, SynthControlContainer container,
                                                      int min, int max, int step, RefWrapper<Integer> parameter, Procedure onChangeProcedure){
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
                            new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB), new Point(0,0),"blankCursor");
                    component.setCursor(BLANK_CURSOR);
                    container.setMouseClickLocation(e.getLocationOnScreen());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    component.setCursor(Cursor.getDefaultCursor());
                }
            });
            component.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if(container.getMouseClickLocation().y!=e.getYOnScreen()){
                        boolean mouseMovingUp = container.getMouseClickLocation().y - e.getYOnScreen() >0;
                        if(mouseMovingUp && parameter.val < max){
                            parameter.val+=step;
                        }
                        else if(!mouseMovingUp && parameter.val>min){
                            parameter.val-=step;
                        }
                        if(onChangeProcedure!=null){
                            invokeProcedure(onChangeProcedure, true);
                        }
                        PARAMETER_ROBOT.mouseMove(container.getMouseClickLocation().x, container.getMouseClickLocation().y);
                    }
                }
            });
        }
    }
}
