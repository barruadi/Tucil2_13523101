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
            
            long startTime = System.currentTimeMillis();            // Start Timer
            
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
            int treeDepth = tree.calculateDepth();
            int totalNode = tree.getTotalNode();
            
            long endTime = System.currentTimeMillis();              // End Timer

            
            // ------------- OUTPUT -------------
            String fileExtension = cli.getFileExtension();
            ImageIO.write(outputImage, fileExtension, new File(cli.getOutputPath()));

            cli.cliOutput(
                endTime - startTime,
                treeDepth, 
                totalNode
            );

        } catch (IOException e) {
            System.out.println("ERR: " + e);
            return;
        }
    }
}