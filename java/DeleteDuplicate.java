import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DeleteDuplicate {
    public static Integer DelDul(List<Integer> list) {
        int new_length = 0;
        if(list.isEmpty()) return new_length;
        for(int i=0;i<=list.size()-1;i++)
            while(list.get(i)==list.get(i+1)){
                list.remove(i+1);
                if(i==list.size()-1) break;
            }
        return list.size();                
    }
    public static void main(String[] args) {
        int size = 500;
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            list.add(new Random().nextInt(20));
        }
        list.sort(Comparator.naturalOrder());
        DelDul(list);
        return;
    } 
}
