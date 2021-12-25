import java.util.LinkedList;
import java.util.Queue;

public class ReverseQueue {
    public static void StackPush(Queue<Integer> q, int val) {
        q.add(val);
        for(int i=1;i<q.size();i++){
            q.add(q.poll());
        }
    }
    public static void main(String[] args) {
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        for(int i=1;i<6;i++){
            q1.add(i);
        }
        while(!q1.isEmpty()){
            StackPush(q2, q1.poll());
        }
        System.out.println(q2.toString());
    }
}
