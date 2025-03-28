package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class VarianceErrorMethod implements _ErrorMethod {
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        int total_pixels = width * height;
        long sum_R = 0;  double var_R = 0; 
        long sum_G = 0;  double var_G = 0; 
        long sum_B = 0;  double var_B = 0;

        //---------------jumlah pixel dalmm satu blok------------------
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color color = new Color(image.getRGB(i, j));
                sum_R += color.getRed();
                sum_G += color.getGreen();
                sum_B += color.getBlue();
            }
        }

        //---------------Rata" Pixel dalam satu block--------------------
        double mean_R = sum_R / (double) total_pixels;
        double mean_G = sum_G / (double) total_pixels;
        double mean_B = sum_B / (double) total_pixels;
        

        // -----------------Variansi setiap kanal warna-----------------
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color color = new Color(image.getRGB(i, j));
                var_R += Math.pow(color.getRed() - mean_R, 2);
                var_G += Math.pow(color.getGreen() - mean_G, 2);
                var_B += Math.pow(color.getBlue() - mean_B, 2);
            }
        }

        var_R /= total_pixels;
        var_G /= total_pixels;
        var_B /= total_pixels;

        //-----------------Variansi total-----------------
        double totalvariance = (var_R + var_G + var_B) / 3.0;

        return totalvariance;
    }
}
