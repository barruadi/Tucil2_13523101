package Business;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ErrorMethods.*;

public class QuadTreeNode {
    private int             x,                    // counted from top left corner 
                            y;
    private int             width,                // image dimension
                            height;
    private int             val_R,                // value RGB
                            val_G, 
                            val_B;   
    private boolean         isLeafNode;           // deafult as non-leaf
    private BufferedImage   image;
    
    QuadTreeNode[] children;
    
    static public  int total_node  = 0;
    static private int frame_count = 0;
    static private int level       = 0;
    public static List<BufferedImage> gifFrames = new ArrayList<>();

    //-------------------------CONSTUCTOR-------------------------
    public QuadTreeNode(BufferedImage image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isLeafNode = false;

        children = new QuadTreeNode[4];
        total_node++;
    }

    public static void resetNodeCounter() {
        total_node = 0;
    }

    public int getTotalNode() {
        return total_node;
    }

    public void makeQuadTreeNode(float threshold, int errorMethod, int minBlockSize) {
        double error = calculateErrorQuadTreeNode(errorMethod);
        calculateAvgRGB();
        if ( error <= threshold || (width * height) <= minBlockSize) {
            this.isLeafNode = true;
            
        } else {
            // branch to 4 child
            int first_height, second_height;
            if (height % 2 == 1) {
                first_height = (height / 2) + 1;
                second_height = height - first_height;
            } else {
                first_height = height / 2;
                second_height = height / 2;
            }

            int first_width, second_width;
            if (width % 2 == 1) {
                first_width = (width / 2) + 1;    
                second_width = width - first_width;
            } else {
                first_width = width / 2;
                second_width = width / 2;
            }

            // upper left
            children[0] = new QuadTreeNode(image, x, y, first_width, first_height);
            children[0].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            // upper right
            children[1] = new QuadTreeNode(image, x + first_width, y, second_width, first_height);
            children[1].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            // down left
            children[2] = new QuadTreeNode(image, x, y + first_height, first_width, second_height);
            children[2].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            // down right
            children[3] = new QuadTreeNode(image, x + first_width, y + first_height, second_width, second_height);
            children[3].makeQuadTreeNode(threshold, errorMethod, minBlockSize);
        }
    }
    

    //------------------ HELPER ------------------
    private double calculateErrorQuadTreeNode(int errorMethod) {
        _ErrorMethod method;
        double error;

        switch (errorMethod) {
            case 1:
                method = new VarianceErrorMethod();
                error = method.calculateErr(
                    this.image, this.x, this.y, this.width, this.height
                );
                return error;

            case 2:
                method = new MADErrorMethod();
                error = method.calculateErr(
                    this.image, this.x, this.y, this.width, this.height
                );
                return error;
                
            case 3:
                method = new MPDErrorMethod();
                error = method.calculateErr(
                    this.image, this.x, this.y, this.width, this.height
                );
                return error;

            case 4:
                method = new EntropyErrorMethod();
                error = method.calculateErr(
                    this.image, this.x, this.y, this.width, this.height
                );
                return error;

            case 5:
                method = new SSIMErrorMathod();
                error = method.calculateErr(
                    this.image, this.x, this.y, this.width, this.height
                );
                return error;
        
            default:               // error
                return 0.0;
        }
    }

    private void calculateAvgRGB() {
        int total_pixels = width * height;
        long sum_R = 0;  
        long sum_G = 0;  
        long sum_B = 0; 

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Color color = new Color(image.getRGB(i, j));
                sum_R += color.getRed();
                sum_G += color.getGreen();
                sum_B += color.getBlue();
            }
        }

        this.val_R = (int) (sum_R / total_pixels);
        this.val_G = (int) (sum_G / total_pixels);
        this.val_B = (int) (sum_B / total_pixels);
    }

    public int get24BitRGB() {
        return (val_R << 16) | (val_G << 8) | (val_B);
    }
    //--------------------------------------------


    public void reconstructImage(BufferedImage outputImage) {
        if (isLeafNode) {
            for (int i = x; i < x + width; i++) {
                for (int j = y; j < y + height; j++) {
                    outputImage.setRGB(i, j, get24BitRGB());
                }
            }
        } else {
            // reconstruct children
            this.children[0].reconstructImage(outputImage);
            this.children[1].reconstructImage(outputImage);
            this.children[2].reconstructImage(outputImage);
            this.children[3].reconstructImage(outputImage);
        }
    }

    public int calculateDepth() {
        if (isLeafNode) {
            return 0;
        } else {
            int maxDepth = 0;
            for (QuadTreeNode child : children) {
                maxDepth = Math.max(maxDepth, child.calculateDepth());
            }
            return maxDepth + 1;
        }
    }

    //--------------------GIF MAKE------------------------
    public void drawFrame(BufferedImage outputImage) {
        BufferedImage frame = new BufferedImage(outputImage.getWidth(), outputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < outputImage.getWidth(); i++) {
            for (int j = 0; j < outputImage.getHeight(); j++) {
                frame.setRGB(i, j, outputImage.getRGB(i, j));
            }
        }
        gifFrames.add(frame);
    }
}
