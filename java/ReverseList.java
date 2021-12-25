
public class ReverseList {
    static class Node{
        int data;
        Node next;
        Node(int data){
            this.data = data;
            this.next = null;
        }
    }
    public static Node Reverse(Node currentNode) {
        Node prev = null;
        Node nextNode = null;
        while(currentNode!=null){
            nextNode = currentNode.next;
            currentNode.next = prev;
            prev = currentNode;
            currentNode = nextNode;
        }
        return prev;
    }
    public static Node RecReverseList(Node node) {
        if(node==null || node.next==null){
            return node;
        }
        Node remaining = RecReverseList(node.next);
        node.next.next = node;
        node.next = null;
        return remaining;
    }
    public static void main(String[] args) {
        Node n1 = new Node(1);        
        Node n2 = new Node(2);            
        Node n3 = new Node(3); 
        Node n4 = new Node(4);        
        Node n5 = new Node(5);        
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        
        Node a = Reverse(n1);
        while(a!=null){
            System.out.print(a.data + "");
            a = a.next;
        }
        System.out.println();
        Node b = RecReverseList(n5);
        while(b!=null){
            System.out.print(b.data + "");
            b = b.next;
        }
    }
}
