import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

public class MaxProfit {
    public static Integer CalcMaxProfit(List<Integer> list) {
        int maxProfit = 0;
        Formatter f = new Formatter();
        for(int i=0;i<list.size()-1;i++){
            if(list.get(i)<list.get(i+1)){
                String s = f.format("buy at %d:%d, sell at %d:%d => %d", i,list.get(i),i+1,list.get(i+1),-list.get(i)+list.get(i+1)).toString();
                System.out.println(s);
                maxProfit += -list.get(i)+list.get(i+1);
            }
        }
        f.close();
        return maxProfit;
    }
    public static void main(String[] args) {
        int size = 8;
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            list.add(new Random().nextInt(10));
        }
        System.out.println(CalcMaxProfit(list));
        return;
    }    
}