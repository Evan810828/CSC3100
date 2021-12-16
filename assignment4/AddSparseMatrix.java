import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class AddSparseMatrix {
    /* use linked list to contain the matrices */
    static class Node{
        int value, index;
        Node next;
        // each node denotes a valueber in a row
        Node(int index, int value){
            this.index = index;
            this.value = value;
        }
        // shallow copy
        Node(Node n){
            this.index = n.index;
            this.value = n.value;
        }
    }
    static class LinkedList{
        int row;
        Node head;
        LinkedList next_row;
        // each list denotes a row
        LinkedList(int row, Node head){
            this.row = row;
            this.head = head;
        }
        // shallow copy
        LinkedList(LinkedList l){
            this.row = l.row;
            this.head = l.head;
        }
    }
    static class Matrix{
        LinkedList head;
        int row_cnt, col_cnt;
        Matrix(int row_cnt, int col_cnt){
            this.row_cnt = row_cnt;
            this.col_cnt = col_cnt;
        }
    }
    /* functions*/
    // read integer sub-strings
    public static int ReadNum(String op, String str) {
        String array[] = str.split(":");
        if(op.equals("front")){
            return Integer.parseInt(array[0]);
        }else if(op.equals("back")){
            return Integer.parseInt(array[1]);
        }
        return 0;
    }
    public static Matrix ReadInput(BufferedReader br) throws Exception {
        String str="";
        // size
        int row_count, col_count;
        str=br.readLine();
        row_count = Integer.parseInt(str.substring(0, str.indexOf(",")));
        col_count = Integer.parseInt(str.substring(str.indexOf(" ")+1, str.length()));
        // matrix
        Matrix m = new Matrix(row_count, col_count);
        LinkedList first_row=null, current_list=null;
        int flag=1;
        while((str=br.readLine()) != null){
            String array[] = str.split(" ");
            if(array[1].equals(":")) continue; // empty row
            Node current_node = new Node(ReadNum("front", array[1]), ReadNum("back", array[1])); // head
            LinkedList new_list = new LinkedList(ReadNum("front", array[0]), current_node);
            for(int i=2;i<array.length;i++){
                Node new_node = new Node(ReadNum("front", array[i]), ReadNum("back", array[i]));
                current_node.next = new_node;
                current_node = new_node;
            }
            if(flag==1){
                first_row = new_list;
                current_list = first_row;
                flag=0;
            }else{
                current_list.next_row = new_list;
                current_list = new_list;
            }
        }
        br.close();
        m.head = first_row;
        return m;
    }
    public static LinkedList AddRow(LinkedList l1, LinkedList l2) {
        LinkedList res = null;
        Node n1=l1.head,n2=l2.head;
        Node res_current=null;
        int flag=1;
        while(n1!=null && n2!=null){
            Node new_node=null;
            if(n1.index==n2.index){
                new_node = new Node(n1.index, n1.value+n2.value);
                n1 = n1.next;
                n2 = n2.next;
            }
            else if(n1.index>n2.index){
                new_node = new Node(n2);
                n2 = n2.next;
            }
            else if(n1.index<n2.index){
                new_node = new Node(n1);
                n1 = n1.next;
            }
            if(flag==1){
                res_current = new_node;
                res = new LinkedList(l1.row, res_current);
                flag=0;
            }else{
                res_current.next = new_node;
                res_current = new_node;
            }
        }
        while(n1!=null){
            Node new_node = new Node(n1);
            n1 = n1.next;
            if(flag==1){
                res_current = new_node;
                res = new LinkedList(l1.row, res_current);
                flag=0;
            }
            else{
                res_current.next = new_node;
                res_current = new_node;
            }
        }
        while(n2!=null){
            Node new_node = new Node(n2);
            n2 = n2.next;
            if(flag==1){
                res_current = new_node;
                res = new LinkedList(l1.row, res_current);
                flag=0;
            }
            else{
                res_current.next = new_node;
                res_current = new_node;
            }
        }
        return res;
    }
    public static Matrix AddMatrix(Matrix m1, Matrix m2) {
        Matrix res = new Matrix(m1.row_cnt, m1.col_cnt);
        LinkedList current1=m1.head, current2=m2.head;
        LinkedList res_current=null;
        int flag=1;
        while(current1!=null && current2!=null){
            LinkedList new_list=null;
            if(current1.row==current2.row){
                new_list = AddRow(current1, current2);
                current1 = current1.next_row;
                current2 = current2.next_row;
            }else if(current1.row>current2.row){
                new_list = new LinkedList(current2);
                current2 = current2.next_row;
            }else if(current1.row<current2.row){
                new_list = new LinkedList(current1);
                current1 = current1.next_row;
            }
            if(flag==1){
                res_current = new_list;
                res.head = res_current;
                flag=0;
            }
            else{
                res_current.next_row = new_list;
                res_current = new_list;
            }
        }
        while(current1!=null){
            LinkedList new_list = new LinkedList(current1);
            current1 = current1.next_row;
            if(flag==1){
                res_current = new_list;
                res.head = res_current;
                flag=0;
            }
            else{
                res_current.next_row = new_list;
                res_current = new_list;
            }
        }
        while(current2!=null){
            LinkedList new_list = new LinkedList(current2);
            current2 = current2.next_row;
            if(flag==1){
                res_current = new_list;
                res.head = res_current;
                flag=0;
            }
            else{
                res_current.next_row = new_list;
                res_current = new_list;
            }
        }
        return res;
    }
    public static void WriteMatrix(Matrix m, BufferedWriter bw) throws Exception {
        LinkedList list = m.head;
        bw.write(String.format("%d, %d", m.row_cnt, m.col_cnt));
        bw.newLine();
        for(int r=1;r<=m.row_cnt;r++){
            bw.write(String.format("%d ", r));
            if(list.row==r){
                Node node = list.head;
                while(node!=null){
                    if(node.value!=0){
                        bw.write(String.format("%d:%d ", node.index, node.value));
                    }
                    node = node.next;
                }
                if(list.next_row!=null) list = list.next_row;
                // bw.write("\n");
                bw.newLine();
            }else{ // empty row
                bw.write(":");
                bw.newLine();
            }
        }
        bw.close();
    }
    public static void main(String[] args) throws Exception {
        long startTime=System.currentTimeMillis();
        
        BufferedReader br_1 = new BufferedReader(new FileReader("inputA.txt"));
        BufferedReader br_2 = new BufferedReader(new FileReader("inputB.txt"));
        Matrix m1 = ReadInput(br_1);
        Matrix m2 = ReadInput(br_2);
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
        WriteMatrix(AddMatrix(m1, m2), bw);
        
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        return;
    }
}