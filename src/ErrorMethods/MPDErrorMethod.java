package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MPDErrorMethod implements _ErrorMethod {
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        //---------------Initialisasi------------------
        int max_R = 0; int min_R = 256;
        int max_G = 0; int min_G = 256;
        int max_B = 0; int min_B = 256;


        //---------------Milai Max dan Min------------------
        for (int i = x; i <= x + width; i++) {
            for (int j = y; j <= y + height; j++){
                if (i < 0 || i >= image.getWidth() || j < 0 || j >= image.getHeight()) {
                    continue;
                }
                Color color = new Color(image.getRGB(i, j));

                // cari max
                max_R = Math.max(max_R, color.getRed());
                max_G = Math.max(max_G, color.getGreen());
                max_B = Math.max(max_B, color.getBlue());

                // cari min
                min_R = Math.min(min_R, color.getRed());
                min_G = Math.min(min_G, color.getGreen());
                min_B = Math.min(min_B, color.getBlue());
            }
        }


        // -----------------Hitung max diff--------------------
        int delta_R = max_R - min_R;
        int delta_G = max_G - min_G;
        int delta_B = max_B - min_B;

        double delta_total = ((double) (delta_R + delta_G + delta_B)) / 3.0;

        return delta_total;
    }
}
