package WAVEGEN.util;

/**
 * Created by NST069 on 26.03.2019.
 */
public class Util {
    public static void invokeProcedure(Procedure p, boolean print){
        try{
            p.invoke();
        }catch(Exception ex){ if(print) ex.printStackTrace();}
    }
}
