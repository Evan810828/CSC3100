#include <iostream>
#include <vector>
#include <map>
#include <time.h>
#include <windows.h>
#include <math.h>

using namespace std;

vector<int> GenerateVector(){
    vector<int> vec;
    for (int i=0; i<15000; i++){  // adjust the vector size
        vec.push_back(rand());
    }
    return vec;
}

vector<int> SelectionSort(vector<int> vec){
    for (int step=vec.size(); step>1; step--){
        int largetst = 0;
        for (int index=1; index<step; index++){
            if (vec[largetst] < vec[index]){
                largetst = index;
            }
        }
        int temp = vec[step-1];
        vec[step-1] = vec[largetst];
        vec[largetst] = temp;
    }

    return vec;
}

vector<int> BubbleSort(vector<int> vec){
    if (vec.size() <= 1)
        return vec;

    for (int step = vec.size(); step > 0; step--)
    {
        for (int index = 0; index < step - 1; index++)
        {
            // move the larger one to the right side
            if (vec[index] > vec[index + 1])
            {
                int temp = vec[index + 1];
                vec[index + 1] = vec[index];
                vec[index] = temp;
            }
        }
    }

    return vec;
}

vector<int> InsertionSort(vector<int> vec){
    if (vec.size() <= 1)
        return vec;

    for (int step=1; step<vec.size(); step++){
        int temp = vec[step];
        int index = step-1;
        while (index >= 0 && vec[index] > temp){
            vec[index+1] = vec[index];
            index --;
        }
        vec[index+1] = temp;
    }

    return vec;
}

vector<int> Merge(vector<int> left, vector<int> right){
    vector<int> result={};
    for (int index=0; index<left.size() && index<right.size(); index++){
        if (left[index] <= right[index]){
            result.push_back(left[index]);
            result.push_back(right[index]);
        }else{
            result.push_back(right[index]);
            result.push_back(left[index]);
        }
    }
    if (left.size()<right.size()){
        result.push_back(*right.end()-1);
    }
    if (left.size()>right.size()){
        result.push_back(*left.end()-1);
    }

    return result;
}
vector<int> MergeSort(vector<int> vec){
    if (vec.size() <= 1)
        return vec;

    int mid = floor(vec.size()/2);
    vector<int> left(vec.begin(), vec.begin()+mid);
    vector<int> right(vec.begin()+mid, vec.end());

    return Merge(MergeSort(left), MergeSort(right));
}

int GetIndex(vector<int> & vec, int left, int right){
    int temp = vec[left];
    while (left < right){
        while (temp <= vec[right] && left<right){
            right--;
        }
        vec[left] = vec[right];
        while (temp >= vec[left] && left<right){
            left++;
        }
        vec[right] = vec[left];
    }
    vec[left] = temp;
    return left;
}
vector<int> QuikSort(vector<int> & vec, int left, int right){
    if (left < right){
        int index = GetIndex(vec, left, right);
        QuikSort(vec, left, index-1);
        QuikSort(vec, index+1, right);
    }

    return vec;
}

int main()
{
    vector<int> vec = GenerateVector();

    DWORD start_4 = GetTickCount();
    vector<int> selection_sort = SelectionSort(vec);
    DWORD end_4 = GetTickCount();
    cout << "Selection Sorting Duration: " << end_4-start_4 << "ms" << endl;

    DWORD start_1 = GetTickCount();
    vector<int> bubble_sort = BubbleSort(vec);
    DWORD end_1 = GetTickCount();
    cout << "Bubble Sorting Duration: " << end_1-start_1 << "ms" << endl;

    DWORD start_2 = GetTickCount();
    vector<int> insertion_sort = InsertionSort(vec);
    DWORD end_2 = GetTickCount();
    cout << "Insertion Sorting Duration: " << end_2-start_2 << "ms" << endl;

    DWORD start_3 = GetTickCount();
    vector<int> merge_sort = MergeSort(vec);
    DWORD end_3 = GetTickCount();
    cout << "Merge Sorting Duration: " << end_3-start_3 << "ms" << endl;

    DWORD start_5 = GetTickCount();
    vector<int> quick_sort = QuikSort(vec, 0, vec.size()-1);
    DWORD end_5 = GetTickCount();
    cout << "Quick Sorting Duration: " << end_5-start_5 << "ms" << endl;

    return 0;
}