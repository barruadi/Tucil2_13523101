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
            // ------------- INITILIZE -------------
            ImageTree       tree;
            BufferedImage   outputImage;
            int             treeDepth;
            int             totalNode;

            // ------------- iNPUT -------------
            CLIio cli = new CLIio();
            cli.cliInput();
            boolean targetCompresiStatus = cli.getTargetKompresiStatus();
            float minThreshold = 0;
            float maxThreshold = cli.getMaxThreshold();
            float midThrehold = (minThreshold + maxThreshold) / 2;
            
            long startTime = System.currentTimeMillis();            // Start Timer
            
            // ------------- PROSES -------------
            BufferedImage image = cli.getImage();

            if (targetCompresiStatus) {
                while(true) {
                    tree = new ImageTree(
                        image, 
                        midThrehold, 
                        cli.getInputBlockSize(), 
                        cli.getInputMethod()
                    );
                    outputImage = new BufferedImage(
                        image.getWidth(), 
                        image.getHeight(), 
                        BufferedImage.TYPE_INT_RGB
                    );
                    tree.reconstructImage(outputImage);
                    String fileExtension = cli.getFileExtension();
                    ImageIO.write(outputImage, fileExtension, new File(cli.getOutputPath()));
                    double output_file_size_before = Utils.getFileSizeKb(new File(cli.getInputPath()));
                    double output_file_size_after = Utils.getFileSizeKb(new File(cli.getOutputPath()));
                    double output_compression_percentage = (1 - (double) output_file_size_after / output_file_size_before) * 100;

                    if (Math.abs(output_compression_percentage - cli.getInputTargetCompression()) < 0.01) {
                        treeDepth = tree.calculateDepth();
                        totalNode = tree.getTotalNode();
                        break;
                    } else if (Math.abs(maxThreshold - minThreshold) < 0.01) {
                        System.out.println("ERR: Target compression not achieved");
                        treeDepth = tree.calculateDepth();
                        totalNode = tree.getTotalNode();
                        break;
                    }

                    if (output_compression_percentage < cli.getInputTargetCompression()) {
                        maxThreshold = midThrehold;
                        midThrehold = (minThreshold + maxThreshold) / 2;
                    } else if (output_compression_percentage > cli.getInputTargetCompression()) {
                        minThreshold = midThrehold;
                        midThrehold = (minThreshold + maxThreshold) / 2;
                    }

                    File outputFile = new File(cli.getOutputPath());
                    if (outputFile.exists()) {
                        outputFile.delete();
                    } 
                }
            } else {
                tree = new ImageTree(
                    image, 
                    cli.getInputThreshold(), 
                    cli.getInputBlockSize(), 
                    cli.getInputMethod()
                );
    
                outputImage = new BufferedImage(
                    image.getWidth(), 
                    image.getHeight(), 
                    BufferedImage.TYPE_INT_RGB
                );
                tree.reconstructImage(outputImage);
                treeDepth = tree.calculateDepth();
                totalNode = tree.getTotalNode();

                String fileExtension = cli.getFileExtension();
                ImageIO.write(outputImage, fileExtension, new File(cli.getOutputPath()));
            }

            
            long endTime = System.currentTimeMillis();              // End Timer

            
            // ------------- OUTPUT -------------
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