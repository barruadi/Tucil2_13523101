package Business;

import java.awt.image.BufferedImage;

public class ImageTree {
    public QuadTreeNode root;

    public ImageTree(BufferedImage image, float threshold, int minBlockSize, int errorMethod) {
        QuadTreeNode.resetNodeCounter();
        root = new QuadTreeNode(image, 0, 0, image.getWidth(), image.getHeight());
        root.makeQuadTreeNode(threshold, errorMethod, minBlockSize);
    }

    public void reconstructImage(BufferedImage image) {
        root.reconstructImage(image);
    }

    public int calculateDepth() {
        return root.calculateDepth();
    }

    public int getTotalNode() {
        return root.getTotalNode();
    }
}
