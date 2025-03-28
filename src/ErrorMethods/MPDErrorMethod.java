package ErrorMethods;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MPDErrorMethod implements _ErrorMethod {
    @Override
    public double calculateErr(BufferedImage image, int x, int y, int width, int height) {
        return 0;
    }
}
