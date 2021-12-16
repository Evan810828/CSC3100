#include "queue.h"
#include <iostream>
using namespace std;

void helpCommand() {
   cout << "Available commands:" << endl;
   cout << "  enqueue str -- Enqueues the string onto the queue" << endl;
   cout << "  dequeue     -- Dequeues and displays the first value" << endl;
   cout << "  peek        -- Peeks at the first element" << endl;
   cout << "  size        -- Prints the size of the queue" << endl;
   cout << "  isEmpty     -- Prints whether the queue is empty" << endl;
   cout << "  clear       -- Clears the queue" << endl;
   cout << "  list        -- List the elements of the queue" << endl;
   cout << "  help        -- List these commands" << endl;
   cout << "  quit        -- Quits the program" << endl;
}

void Test(string cmd, Queue<string> & queue){
   if (cmd.substr(0,7) == "enqueue") {
      queue.enqueue(cmd.substr(7));
   } else if (cmd.substr(0,7) == "dequeue") {
      if (queue.isEmpty()) {
         cout << "Queue is empty" << endl;
      } else {
         cout << queue.dequeue() << endl;
      }
   } else if (cmd.substr(0,4) == "peek") {
      if (queue.isEmpty()) {
         cout << "Queue is empty" << endl;
      } else {
         cout << queue.peek() << endl;
      }
   } else if (cmd.substr(0,4) == "size") {
      cout << queue.size() << endl;
   } else if (cmd.substr(0,7) == "isEmpty") {
      cout << ((queue.isEmpty()) ? "true" : "false") << endl;
   } else if (cmd.substr(0,5) == "clear") {
      queue.clear();
   } else if (cmd.substr(0,4) == "list") {
      queue.print();
   } else if (cmd.substr(0,4) == "help") {
      helpCommand();
   } else if (cmd.substr(0,4) == "quit") {
      exit(0);
   } else if (cmd != "") {
      cout << "Unrecognized command: " << cmd << endl;
   }
}


int main(){
    Queue<string> queue;
    string cmd;
    while (true){
        cin >> cmd;
        Test(cmd, queue);
    }

    return 0;
}