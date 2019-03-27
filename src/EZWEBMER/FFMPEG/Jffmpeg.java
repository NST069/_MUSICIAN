package EZWEBMER.FFMPEG;


import java.io.*;
import java.net.URL;

/**
 * Created by NST069 on 27.03.2019.
 */
public class Jffmpeg {

    URL ffmpeg;

    public Jffmpeg(){
        ffmpeg = getClass().getResource("ffmpeg.exe");
    }

    public void MakeMp4(File img, File music, String name){
        try {

            /*Process process = new ProcessBuilder(ffmpeg.getPath(),
                    "-loop","-1", "-r", "1",
                    "-i", img.getAbsolutePath(),
                    "-i", music.getAbsolutePath(),
                    "-b:v", "0", "-crf" ,"2", "-b:a", "160k", "-shortest", "-g", "9999",
                    "-pix_fmt", "yuv420p", "-speed", "0", "-deadline", "0", "-threads", "4",
                    music.getParent()+name + ".mp4").start();
              */

            Process process = Runtime.getRuntime().exec("cmd /c \"start cmd /k " +
                    ".\\out\\production\\_MUSICIAN\\EZWEBMER\\FFMPEG\\ffmpeg -loop 1 -r 1 "+
                    " -i "+ img.getAbsolutePath()+
                    " -i "+music.getParent()+"\\1.wav "+
                    //" -i "+ music.getAbsolutePath()+
                    " -b:v 0 -crf 2 -b:a 160k -shortest -g 9999 -pix_fmt yuv420p -speed 0 -deadline 0 -threads 4 "+
                    music.getParent()+"\\"+name + ".mp4\"");
            System.out.println("Started Baking MP4");
            //Process process = Runtime.getRuntime().exec("cmd /K Start");
            process.waitFor();
            System.out.println("Baked");

            //Process process = Runtime.getRuntime().exec("cmd /K Start");
        }catch (IOException | InterruptedException ex){ex.printStackTrace();}
    }
}
