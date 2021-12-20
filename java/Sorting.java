import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Sorting {
    public static ArrayList<Integer> BubbleSort(ArrayList<Integer> array) {
        ArrayList<Integer> array_copy = new ArrayList<Integer>(array);
        for(int i=array_copy.size()-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(array_copy.get(j)>array_copy.get(j+1)){
                    int temp = array_copy.get(j);
                    array_copy.set(j, array_copy.get(j+1));
                    array_copy.set(j+1, temp);
                }
            }
        }
        return array_copy;
    }
    public static ArrayList<Integer> SelectionSort(ArrayList<Integer> array) {
        ArrayList<Integer> array_copy = new ArrayList<Integer>(array);
        for(int i=0;i<array_copy.size()-1;i++){
            int min = i;
            for(int j=i+1;j<array_copy.size();j++){
                if(array_copy.get(j)<array_copy.get(min))
                    min = j;
            }
            int temp = array_copy.get(min);
            array_copy.set(min, array_copy.get(i));
            array_copy.set(i, temp);
        }
        return array_copy;
    }
    public static ArrayList<Integer> InsertionSort(ArrayList<Integer> array) {
        ArrayList<Integer> array_copy = new ArrayList<Integer>(array);
        for(int i=1;i<array_copy.size();i++){
            int temp = array_copy.get(i);
            int j=i;
            while(j>0 && array_copy.get(j-1)>temp){
                array_copy.set(j, array_copy.get(j-1));
                j--;
            }
            array_copy.set(j, temp);
        }
        return array_copy;
    }
    public static ArrayList<Integer> MergeSort(ArrayList<Integer> array) {
        ArrayList<Integer> array_copy = new ArrayList<Integer>(array);
        RecMergeSort(array_copy, 0, array_copy.size()-1);
        return array_copy;
    }
    public static void RecMergeSort(ArrayList<Integer> array, int low, int high) {
        if(low==high) return;
        int mid = (low + high) / 2;
        RecMergeSort(array, low, mid);
        RecMergeSort(array, mid+1, high);
        Merge(array, low, high);
        return;
    }
    public static void Merge(ArrayList<Integer> array, int low, int high) {
        ArrayList<Integer> merged_array = new ArrayList<>(high-low+1);
        int mid = (high+low)/2, ptr1 = low, ptr2 = mid + 1;
        while(ptr1<=mid && ptr2<=high){
            if(array.get(ptr1)<array.get(ptr2))
                merged_array.add(array.get(ptr1++));
            else
                merged_array.add(array.get(ptr2++));
        }
        while(ptr1<=mid)
            merged_array.add(array.get(ptr1++));
        while(ptr2<=high)
            merged_array.add(array.get(ptr2++));
        for(int i=low;i<=high;i++){
            array.set(i, merged_array.get(i-low));
        }
        return;
    }
    public static ArrayList<Integer> QuickSort(ArrayList<Integer> array) {
        ArrayList<Integer> array_copy = new ArrayList<Integer>(array);
        RecQuickSort(array_copy, 0, array_copy.size()-1);
        return array_copy;
    }
    public static void RecQuickSort(ArrayList<Integer> array, int low, int high) {
        if(low==high) return;
        int partion = PartionIt(array, low, high);
        RecQuickSort(array, low, partion-1);
        RecQuickSort(array, partion+1, high);
    }
    public static int PartionIt(ArrayList<Integer> array, int low, int high) {
        int pivot = array.get(low), ptr1 = low+1, ptr2 = high;
        while(true){
            while(array.get(ptr1++)<=pivot);
            while(array.get(ptr2--)>=pivot);
            if(ptr1==ptr2) break;
            else{
                int temp = array.get(ptr1);
                array.set(ptr1, array.get(ptr2));
                array.set(ptr2, temp);
            }
        }
        int temp = array.get(low);
        array.set(low, array.get(ptr1));
        array.set(ptr1, temp);
        return ptr1;
    }

    public static void main(String[] args) {
        // create test case
        int size = 40000;
        ArrayList<Integer> array = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            array.add(new Random().nextInt(500)-250);
        }
        ArrayList<Integer> ans = new ArrayList<>(array);
        ans.sort(Comparator.naturalOrder());

        // bubble sort
        long startTime=System.currentTimeMillis();
        ArrayList<Integer> bs_array = BubbleSort(array);
        long endTime=System.currentTimeMillis();
        if(bs_array.equals(ans)) System.out.println(String.format("Bubble sort: %d",(endTime-startTime)));
        else System.out.println("wrong answer!");

        // selection sort
        startTime=System.currentTimeMillis();
        ArrayList<Integer> ss_array = SelectionSort(array);
        endTime=System.currentTimeMillis();
        if(ss_array.equals(ans)) System.out.println(String.format("Selection sort: %d",(endTime-startTime)));
        else System.out.println("wrong answer!");

        // insertion sort
        startTime=System.currentTimeMillis();
        ArrayList<Integer> is_array = InsertionSort(array);
        endTime=System.currentTimeMillis();
        if(is_array.equals(ans)) System.out.println(String.format("Insertion sort: %d",(endTime-startTime)));
        else System.out.println("wrong answer!");

        // merge sort
        startTime=System.currentTimeMillis();
        ArrayList<Integer> ms_array = MergeSort(array);
        endTime=System.currentTimeMillis();
        if(ms_array.equals(ans)) System.out.println(String.format("Merge sort: %d",(endTime-startTime)));
        else System.out.println("wrong answer!");

        // quick sort
        startTime=System.currentTimeMillis();
        ArrayList<Integer> qs_array = MergeSort(array);
        endTime=System.currentTimeMillis();
        if(qs_array.equals(ans)) System.out.println(String.format("Quick sort: %d",(endTime-startTime)));
        else System.out.println("wrong answer!");


        return;
    }
}