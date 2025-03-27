package Business;

public class ImageBlock {
    int[][] redPixel;
    int[][] greenPixel;
    int[][] bluePixel;

    int blockWidth, blockHeight;

    // CONSTRUCTOR
    public ImageBlock(int blockWidth, int blockHeight) {
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;

        redPixel = new int[blockHeight][blockWidth];
        greenPixel = new int[blockHeight][blockWidth];
        bluePixel = new int[blockHeight][blockWidth];
    }
}
