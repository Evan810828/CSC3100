#ifndef STACK_H
#define STACK_H

#include <iostream>
using namespace std;

// Implemented with array

template <typename ValueType>
class Stack
{
private:
        const int default_capacity = 10;

        ValueType *array;
        int capacity;
        int count;
public:
        // constructor
        Stack();
        Stack(const Stack<ValueType> & src); // copy function
        Stack<ValueType> & operator=(const Stack<ValueType> & src);
        // destructor
        ~Stack();
        // return the current size of the stack
        int size();
        // whether the stack is empty
        bool isEmpty();
        // add elements
        void push(ValueType value);
        // delete elements
        ValueType pop();
        // check the last element
        ValueType peek();
        // change the capacity
        void expandCapacity();
        // deep copy
        void deepCopy(const Stack<ValueType> & src);
        // clear
        void clear();

        // test
        void print(){
            cout << "Current Stack Content: ";
            for (int index=0; index<count; index++){
                cout << index+1 << " " << *(array+index) << " ";
            }
            cout << endl;
        }
};

template <typename T>
Stack<T>::Stack()
{
    capacity = default_capacity;
    array = new T[capacity];
    count = 0;
}

template <typename T>
Stack<T>::Stack(const Stack<T> & src)
{
    deepCopy(src);
}

template <typename T>
Stack<T> & Stack<T>::operator=(const Stack<T> & src)
{
    if (this != src){
        delete[] array;
        deepCopy(src);
    }
    return *this;
}

template <typename T>
Stack<T>::~Stack()
{
    delete[] array;
}

template <typename T>
int Stack<T>::size()
{
    return count;
}

template <typename T>
bool Stack<T>::isEmpty()
{
    return (count==0);
}

template <typename T>
void Stack<T>::push(T value)
{
    if (count == capacity) expandCapacity();
    array[count++] = value;
}

template <typename T>
T Stack<T>::pop()
{
    return *(array+(count--)-1);
}

template <typename T>
T Stack<T>::peek()
{
    return *(array+count-1);
}

template <typename T>
void Stack<T>::expandCapacity()
{
    T *oldArray = array;
    capacity *= 2;
    array = new T[capacity];
    for (int index=0; index<count; index++){
        array[index] = oldArray[index];
    }
    delete[] oldArray;
}

template <typename T>
void Stack<T>::deepCopy(const Stack<T> & src)
{
    capacity = src.capacity + default_capacity;
    array = new T[capacity];
    for (int index=0; index<src.count; index++){
        array[index] = src.array[index];
    }
    count = src.count;
}

template <typename T>
void Stack<T>::clear(){
    count = 0;
}

#endif

