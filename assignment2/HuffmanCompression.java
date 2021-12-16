import java.io.*;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCompression {
    public static String getCompressedCode(String inputText, String[] huffmanCodes) {
        String compressedCode = "";
        for(int i=0; i<inputText.length(); i++){
            compressedCode += huffmanCodes[Integer.valueOf(inputText.charAt(i))];
        }
        return compressedCode;
    }
    static Comparator<Node> cNode = new Comparator<Node>() { // define a comparator for the priority queue
        public int compare(Node n1, Node n2){
            return n1.frequency - n2.frequency;
        }
    };
    public static PriorityQueue<Node> getFrequencyTable(String inputText) {
        // assign a "frequency" for each ascii code that appears
        PriorityQueue<Node> frequencyTable = new PriorityQueue<Node>(cNode);
        int count, flag;
        for(int i=0; i<inputText.length(); i++){
            flag = 0;
            count = 0;
            for(int j=0; j<inputText.length(); j++)
                if(inputText.charAt(j) == inputText.charAt(i)) 
                    count++;
            for(int k=0; k<i; k++)
                if(inputText.charAt(k) == inputText.charAt(i)) // the character has been counted
                    flag = 1;
            if(flag!=1){
                Node n = new Node(inputText.charAt(i), count);
                frequencyTable.add(n);
            }
        }
        return frequencyTable;
    }
    public static BinaryTree getHuffmanTree(PriorityQueue<Node> frequencyTable) {
        BinaryTree huffmanTree = new BinaryTree();
        while(frequencyTable.size()>1){
            Node leftNode = frequencyTable.poll();
            Node rightNode = frequencyTable.poll();
            Node newNode = new Node(leftNode.frequency+rightNode.frequency);
            newNode.leftNode = leftNode;
            newNode.rightNode = rightNode;
            frequencyTable.add(newNode);
        }
        huffmanTree.root = frequencyTable.poll();
        return huffmanTree;
    }
    public static Map<Integer, String> encode(BinaryTree huffmanTree, String encodeString) {
        Map<Integer, String> dic = new HashMap<Integer, String>(); // < ascii code,  huffman code >
        Node current = huffmanTree.root;
        if(current.leftNode==null && current.rightNode==null){
            dic.put(Integer.valueOf(current.ch), encodeString);
        }else{
            if(current.leftNode!=null){
                dic.putAll(encode(new BinaryTree(current.leftNode), encodeString+'1')); // recursion
            }
            if(current.rightNode!=null){
                dic.putAll(encode(new BinaryTree(current.rightNode), encodeString+'0')); // recursion
            }
        }
        return dic;
    }
    public static String[] getHuffmanCode(String inputText) {
        String[] huffmanCodes = new String[128];
        // get the frequency table for inputText
        PriorityQueue<Node> frequencyTable = getFrequencyTable(inputText);
        // generate Huffman Tree
        BinaryTree huffmanTree = getHuffmanTree(frequencyTable);
        // encode using the Huffman tree
        String initialCode = "";
        Map<Integer, String> dic = encode(huffmanTree, initialCode);
        for(Integer key : dic.keySet()){
            huffmanCodes[key] = dic.get(key);
        }
        return huffmanCodes;
    }
    public static void main(String[] args) throws Exception {
        // obtain input text from a text file encoded with ASCII code
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        // get Huffman codes for each character and write them to a dictionary file
        String[] huffmanCodes = HuffmanCompression.getHuffmanCode(inputText);
        FileWriter fwriter1 = new FileWriter(args[1], false);
        BufferedWriter bwriter1 = new BufferedWriter(fwriter1);
        for (int i = 0; i < huffmanCodes.length; i++) 
            if (huffmanCodes[i] != null) {
                bwriter1.write(Integer.toString(i) + ":" + huffmanCodes[i]);
                bwriter1.newLine();
            }
        bwriter1.flush();
        bwriter1.close();
        // get compressed code for input text based on huffman codes of each character
        String compressedCode = HuffmanCompression.getCompressedCode(inputText, huffmanCodes);
        FileWriter fwriter2 = new FileWriter(args[2], false);
        BufferedWriter bwriter2 = new BufferedWriter(fwriter2);
        if (compressedCode != null) 
            bwriter2.write(compressedCode);
        bwriter2.flush();
        bwriter2.close();
    }
}
