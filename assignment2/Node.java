public class Node {
    public char ch;
    public int frequency, validFlag = 1;
    public Node leftNode, rightNode;
    public Node(char ch, int frequency) {
        this.ch = ch;
        this.frequency = frequency;
        leftNode = null;
        rightNode = null;
    }
    public Node(int frequency){
        this.validFlag = 0; // a node with no character
        this.frequency = frequency;
        leftNode = null;
        rightNode = null;
    }
}
