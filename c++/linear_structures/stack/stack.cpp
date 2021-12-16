#include "stack.h"
#include "iostream"
using namespace std;

void helpCommand() {
   cout << "Available commands:" << endl;
   cout << "  push str  -- Pushes the string onto the stack" << endl;
   cout << "  pop       -- Pops the stack and displays the value" << endl;
   cout << "  peek      -- Peeks at the top element" << endl;
   cout << "  size      -- Prints the size of the stack" << endl;
   cout << "  isEmpty   -- Prints whether the stack is empty" << endl;
   cout << "  clear     -- Clears the stack" << endl;
   cout << "  list      -- List the elements of the stack" << endl;
   cout << "  help      -- List these commands" << endl;
   cout << "  quit      -- Quits the program" << endl;
}

void Test(string command, Stack<string> & stack){
   if (command.substr(0,4) == "push") {
      stack.push(command.substr(4));
   } else if (command.substr(0,3) == "pop") {
      if (stack.isEmpty()) {
         cout << "Stack is empty" << endl;
      } else {
         cout << stack.pop() << endl;
      }
   } else if (command.substr(0,4) == "peek") {
      if (stack.isEmpty()) {
         cout << "Stack is empty" << endl;
      } else {
         cout << stack.peek() << endl;
      }
   } else if (command.substr(0,4) == "size") {
      cout << stack.size() << endl;
   } else if (command.substr(0,7) == "isEmpty") {
      cout << ((stack.isEmpty()) ? "true" : "false") << endl;
   } else if (command.substr(0,4) == "clear") {
      stack.clear();
   } else if (command.substr(0,4) == "list") {
      stack.print();
   } else if (command.substr(0,4) == "help") {
      helpCommand();
   } else if (command.substr(0,4) == "quit") {
      exit(0);
   } else if (command != "") {
      cout << "Unrecognized command: " << command << endl;
   }
}


int main(){
    Stack<string> stack;
    string command;

    while (true){
        cin >> command;
        Test(command, stack);
    }

    return 0;
}