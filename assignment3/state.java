public class state{
    // board state class
    int move_count;
    int[] positions = new int[9]; // index:=number 0~8, value:=number's position 0~8
    state previous_state;
    public state(int move_count, int[] positions, state previous_state) {
        this.move_count = move_count;
        this.positions = positions;
        this.previous_state = previous_state;
    }
    public static state[] getNeighbors(state current){
        state[] neighbors = new state[4]; // a state have 2~4 neighbors
        int[] move = {-3, 3, -1, 1};
        for(int i=0;i<4;i++){
            state neighbor = new state(current.move_count, current.positions, current.previous_state);
            neighbor.positions[0] += move[i];
            if(neighbor.positions[0]<0 || neighbor.positions[0]>8) continue;
            for(int j=1;j<=8;j++){
                if(neighbor.positions[j]==neighbor.positions[0]){
                    neighbor.positions[j] -= move[i];
                    neighbors[i] = neighbor;
                }
            }
        }
        return neighbors;
    }
}
