// IMPORT ALL FILES
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

import Business.*;
import Utils.*;

public class Main {
    public static void main(String[] args) {
        Utils.clearScreen();
        try {
            // ------------- iNPUT -------------
            CLIio cli = new CLIio();
            cli.cliInput();
            
            
            // ------------- PROSES -------------
            BufferedImage image = cli.getImage();
            ImageTree tree = new ImageTree(
                image, 
                cli.getInputThreshold(), 
                cli.getInputBlockSize(), 
                cli.getInputMethod()
            );

            BufferedImage outputImage = new BufferedImage(
                image.getWidth(), 
                image.getHeight(), 
                BufferedImage.TYPE_INT_RGB
            );
            tree.reconstructImage(outputImage);

            
            // ------------- OUTPUT -------------
            ImageIO.write(outputImage, "png", new File(cli.getOutputPath()));

        } catch (IOException e) {
            System.out.println("ERR:" + e);
            return;
        }

    }
}