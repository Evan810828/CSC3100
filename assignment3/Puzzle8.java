import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Puzzle8 {
    public static class state{ // board state class
        int move_count;
        int[] positions = new int[9]; // index:=number 0~8, value:=number's position 0~8
        state previous_state;
        public state(int move_count, int[] positions, state previous_state) {
            this.move_count = move_count;
            for(int i=0;i<9;i++){
                this.positions[i] = positions[i];
            }
            this.previous_state = previous_state;
        }
    }
    public static ArrayList<state> getNeighbors(state current){
        ArrayList<state> neighbors = new ArrayList<state>(); // a state have 2~4 neighbors
        int[] move = {-3, 3, -1, 1};
        for(int i=0;i<4;i++){
            state neighbor = new state(current.move_count+1, current.positions, current);
            if(neighbor.positions[0]%3==0 && move[i]==-1) continue;
            if(neighbor.positions[0]%3==2 && move[i]==1) continue;
            if(neighbor.positions[0]<0 || neighbor.positions[0]>8) continue;
            neighbor.positions[0] += move[i];
            for(int j=1;j<=8;j++){
                if(neighbor.positions[j]==neighbor.positions[0]){
                    neighbor.positions[j] -= move[i];
                    neighbors.add(neighbor);;
                    break;
                }
            }
        }
        return neighbors;
    }
    public static int ManhattanPriority(state s){ // get the Manhattan priority of a state
        int distance=0;
        for(int i=1;i<=8;i++){
            int row_dis = Math.abs((s.positions[i]/3) - (i-1)/3);
            int col_dis = Math.abs((s.positions[i]%3) - (i-1)%3);
            distance += row_dis+col_dis;
        }
        int prio = distance+s.move_count;
        return prio;
    }
    public static boolean check_success(state current) { // check whether the current state is the goal state
        for(int i=1;i<=8;i++){
            if(current.positions[i]!=(i-1)) return false;
        }
        return true;
    }
    static Comparator<state> cState = new Comparator<state>() { // define a comparator for the priority queue
        public int compare(state s1, state s2){
            return ManhattanPriority(s1) - ManhattanPriority(s2);
        }
    };
    public static state findSolution(state initial) {
        state current = new state(initial.move_count, initial.positions, initial.previous_state);
        PriorityQueue<state> q = new PriorityQueue<state>(cState);
        while(!check_success(current)){ // not the goal state
            // System.out.print(showState(current));
            for(state neighbor : getNeighbors(current)){
                q.add(neighbor);
            }
            state next = q.poll();
            current = new state(next.move_count, next.positions, next.previous_state);
        }
        return current;
    }
    public static String showState(state s){
        int[] out = new int[9];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<9;i++){
            out[s.positions[i]] = i;
        }
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                sb.append(out[row*3+col]);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n");
        }
        sb.append("\n");
        String str = sb.toString();
        return str;
    }
    public static void main(String[] args) throws Exception{
        long startTime=System.currentTimeMillis();
        // read initial
        BufferedReader br = new BufferedReader(new FileReader("initial.txt"));
        StringBuilder sb_r = new StringBuilder();
        String str="";
        while((str=br.readLine()) != null){
            sb_r.append(str+"\n");
        }
        br.close();
        String initial_string = sb_r.toString();
        // construct initial state
        int[] initial_positions = new int[9];
        int index=0, count=0;
        while(index<initial_string.length()){
            if(initial_string.charAt(index)>=48 && initial_string.charAt(index)<=56){ // 0~8
                initial_positions[Character.getNumericValue(initial_string.charAt(index))] = count;
                count++;
            }
            index++;
        }
        state initial = new state(0, initial_positions, null);
        
        state goal = findSolution(initial);

        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
        StringBuilder sb_w = new StringBuilder();
        state s = new state(goal.move_count, goal.positions, goal.previous_state);
        while(s.previous_state!=null){
            sb_w.insert(0, showState(s));
            s = s.previous_state;
        }
        sb_w.insert(0, showState(s));
        for(int i=0;i<2;i++)
            sb_w.deleteCharAt(sb_w.length()-1);
        str = sb_w.toString();
        bw.write(str);
        bw.close();
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return;
    }
}