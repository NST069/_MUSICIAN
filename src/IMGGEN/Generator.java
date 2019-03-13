package IMGGEN;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by NST069 on 13.03.2019.
 */
public class Generator {

    public static BufferedImage Generate(int dims) {
        Random rand = new Random();
        BufferedImage img = new BufferedImage(dims, dims, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < dims; y++)
            for (int x = 0; x < dims; x++) {
                int a = Math.abs(rand.nextInt())% 256;
                int r = Math.abs(rand.nextInt())% 256;
                int g = Math.abs(rand.nextInt())% 256;
                int b = Math.abs(rand.nextInt())% 256;

                int p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        return img;
    }
}
