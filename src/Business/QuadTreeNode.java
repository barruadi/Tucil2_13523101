package Business;

public class QuadTreeNode {
    private int x, y;           // counted from top left corner 
    private int width, height;

    QuadTreeNode[] children;

    // CONSTRUCTOR
    public QuadTreeNode(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        children = new QuadTreeNode[4];
    }
}
