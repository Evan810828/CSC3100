#ifndef QUEUE_H
#define QUEUE_H

#include <iostream>
using namespace std;

// Implemented with linked list

template <typename ValueType>
class Queue
{
private:
    struct Cell
    {
        ValueType value;
        Cell* link;
    };
    
// 如果用线性链表则需要有 head 和 tail， 但是如果用环形链表，就只需要一个 head
    Cell* head;
    Cell* tail;
    int count;
    
public:
    Queue();
    ~Queue();
    int size();
    bool isEmpty();
    void clear();
    void enqueue(ValueType value);
    ValueType dequeue();
    ValueType peek(); // return the first element
    void print();
};

template <typename ValueType>
Queue<ValueType>::Queue()
{
    head = tail = nullptr;
    count = 0;
}

template <typename ValueType>
Queue<ValueType>::~Queue()
{
    clear();
}

template <typename ValueType>
bool Queue<ValueType>::isEmpty()
{
    return (count==0);
}

template <typename ValueType>
int Queue<ValueType>::size()
{
    return count;
}

template <typename ValueType>
void Queue<ValueType>::clear()
{
    while (count > 0) dequeue(); 
}

template <typename ValueType>
void Queue<ValueType>::enqueue(ValueType value)
{
    Cell *cp = new Cell;
    cp->value = value;
    cp->link = NULL;
    if (head == NULL){
        head = cp;
    }else{
        tail->link = cp;
    }
    tail = cp;
    count ++;
}

template <typename ValueType>
ValueType Queue<ValueType>::dequeue()
{
    Cell* cp = head;
    ValueType result = cp->value;
    if (!isEmpty()){
        head = cp->link;
        if (head == NULL) tail = NULL;
        count --;
        delete cp;
    }
    return result;
}

template <typename ValueType>
ValueType Queue<ValueType>::peek()
{
    return head->value;
}

template <typename ValueType>
void Queue<ValueType>::print()
{
    Cell* cp = head;
    for (int index=0; index<count; index++){
        cout << index+1 << " " << cp->value << endl;
        cp = cp->link;
    }
    cout << endl;
}

#endif