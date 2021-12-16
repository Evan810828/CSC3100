import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecompression {
    public static String getDecompressedText(String compressedText, String dictionary) {
        String decompressedText = "";
        // transform the dictionary from a string into a map
        Map<String, Integer> dic = new HashMap<String, Integer>();
        for(String pair : dictionary.split("\r\n")){
            dic.put(pair.split("\\:")[1], Integer.valueOf(pair.split("\\:")[0]));
        }
        // decode
        String temp = "";
        for(int i=0; i<compressedText.length(); i++){
            temp += compressedText.charAt(i);
            if(dic.containsKey(temp)){
                int ch = dic.get(temp);
                decompressedText += (char) ch;
                temp = "";
            }
        }
        return decompressedText;
    }
    public static void main(String[] args) throws Exception {
        // compressed text
        String compressedText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        // dictionary file
        String dictionary = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[1])), "US-ASCII");
        String decompressedCode = HuffmanDecompression.getDecompressedText(compressedText, dictionary);
        FileWriter fwriter = new FileWriter(args[2], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        if (decompressedCode != null) 
            bwriter.write(decompressedCode);
        bwriter.flush();
        bwriter.close();

        return;
    }
}
