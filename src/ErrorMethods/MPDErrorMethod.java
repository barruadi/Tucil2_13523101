package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MPDErrorMethod implements _ErrorMethod {
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        //---------------Initialisasi------------------
        long total_pixels = width * height;
        int[] probability_R = new int[256]; double entropy_R = 0;
        int[] probability_G = new int[256]; double entropy_G = 0;
        int[] probability_B = new int[256]; double entropy_B = 0;

        //---------------Hitung probabilitas------------------
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color color = new Color(image.getRGB(i, j));
                probability_R[color.getRed()]++;
                probability_G[color.getGreen()]++;
                probability_B[color.getBlue()]++;
            }
        }

        
        //---------------Hitung Entropi--------------------
        for (int i = 0; i < 256; i++) {
            if (probability_R[i] > 0) {
                double p = (double) probability_R[i] / total_pixels;
                entropy_R -= p * Math.log(p) / Math.log(2);
            }
            if (probability_G[i] > 0) {
                double p = (double) probability_G[i] / total_pixels;
                entropy_G -= p * Math.log(p) / Math.log(2);
            }
            if (probability_B[i] > 0) {
                double p = (double) probability_B[i] / total_pixels;
                entropy_B -= p * Math.log(p) / Math.log(2);
            }
        }

        
        //---------------Hitung Entropi Total--------------------
        double total_entropi = (entropy_R + entropy_G + entropy_B) / 3.0;
        
        return total_entropi;
    }
}
