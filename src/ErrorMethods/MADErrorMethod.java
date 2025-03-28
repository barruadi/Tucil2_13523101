package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MADErrorMethod implements _ErrorMethod{
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        int total_pixels = width * height;
        long sum_R = 0;  double mad_R = 0; 
        long sum_G = 0;  double mad_G = 0; 
        long sum_B = 0;  double mad_B = 0;

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
                mad_R = Math.abs(color.getRed() - mean_R);
                mad_G = Math.abs(color.getGreen() - mean_G);
                mad_B = Math.abs(color.getBlue() - mean_B);
            }
        }

        mad_R /= total_pixels;
        mad_G /= total_pixels;
        mad_B /= total_pixels;

        //-----------------Variansi total-----------------
        double totalvariance = (mad_R + mad_G + mad_B) / 3.0;

        return totalvariance;
    }
}
