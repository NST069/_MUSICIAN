package MIDIGEN;

import java.io.File;
import java.util.Scanner;

/**
 * Created by NST069 on 13.03.2019.
 */
public class main_test {

    public static void main(String[] args){
        Player p=new Player();
        p.Open();
        p.Play();

        System.out.println("Save? [Y/N] ");
        Scanner s = new Scanner(System.in);
        if(s.nextLine().equals("Y")){
            p.save();
        }
        p.close();
    }
}
