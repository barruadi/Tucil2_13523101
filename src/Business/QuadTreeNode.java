package Business;

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
    
    static public int total_node = 0;

    //-------------------------CONSTUCTOR-------------------------
    public QuadTreeNode(BufferedImage image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.isLeafNode = false;

        children = new QuadTreeNode[4];
    }


    public void makeQuadTreeNode(float threshold, int errorMethod, int minBlockSize) {
        double error = calculateErrorQuadTreeNode(errorMethod);
        if ( error <= threshold || (width * height) <= minBlockSize) {
            this.isLeafNode = true;
            // set color value
            calculateAvgRGB();
        } else {
            // branch to 4 child
            children[0] = new QuadTreeNode(image, x, y, width / 2, height / 2);
            children[0].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            children[1] = new QuadTreeNode(image, x + width / 2, y, width / 2, height / 2);
            children[1].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            children[2] = new QuadTreeNode(image, x, y + height / 2, width / 2, height / 2);
            children[2].makeQuadTreeNode(threshold, errorMethod, minBlockSize);

            children[3] = new QuadTreeNode(image, x + width / 2, y + height / 2, width / 2, height / 2);
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
}
