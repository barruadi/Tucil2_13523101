package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SSIMErrorMathod implements _ErrorMethod {
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        if (width < 2 || height < 2) return 1.0;

        double meanR = 0, meanG = 0, meanB = 0;
        int totalPixels = width * height;

        // First pass: compute means
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color c = new Color(image.getRGB(i, j));
                meanR += c.getRed();
                meanG += c.getGreen();
                meanB += c.getBlue();
            }
        }
        meanR /= totalPixels;
        meanG /= totalPixels;
        meanB /= totalPixels;

        double varR = 0, varG = 0, varB = 0;

        // Second pass: compute variances
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color c = new Color(image.getRGB(i, j));
                varR += Math.pow(c.getRed() - meanR, 2);
                varG += Math.pow(c.getGreen() - meanG, 2);
                varB += Math.pow(c.getBlue() - meanB, 2);
            }
        }

        varR /= totalPixels;
        varG /= totalPixels;
        varB /= totalPixels;

        double C1 = 6.5025;

        double ssimR = (2 * meanR * meanR + C1) / (meanR * meanR + varR + C1);
        double ssimG = (2 * meanG * meanG + C1) / (meanG * meanG + varG + C1);
        double ssimB = (2 * meanB * meanB + C1) / (meanB * meanB + varB + C1);

        // average SSIM
        double avgSSIM = (ssimR + ssimG + ssimB) / 3.0;

        return 1.0 - avgSSIM;  // lower error is better
    }
}
