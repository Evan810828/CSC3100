import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class createInput {
    public static void main(String[] args) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("in.txt"));
        StringBuilder sb_w = new StringBuilder();
        for(int i=1;i<=10000;i++){
            sb_w.append(String.format("%d", i));
            int count = new Random().nextInt(150);
            for(int j=1;j<=count;j++){   
                int value = new Random().nextInt(5000)-2500;
                sb_w.append(String.format(" %d:%d", j*20, value));
            }
            if(count==0) sb_w.append(" :\n");
            else sb_w.append(" \n");
        }
        String s=sb_w.toString();
        bw.write(s);
        bw.close();
    }
}
