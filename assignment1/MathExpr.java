import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class MathExpr {
	// Check whether the character before str.charAt(index) other than ' ' is '('
	public static boolean lastCharIsBracket(String str, int index) {
		index -= 1;
		while(index>=0) {
			if(str.charAt(index)=='(') {
				return true;
			}else if(str.charAt(index)==' ') {
				index -= 1;
			}else {
				return false;
			}
		}
		return false;
	}
	
	// First convert the string into the infix expression in list
		public static List<String> convertToInfixList(String str) {
			List<String> list = new ArrayList<>();
			int index = 0; // to traverse the string
			
			while (index < str.length()) {
				char ch = str.charAt(index);
				
				// if it is a space
				if (ch==' ') {
					index++;
				}
				
				// if the token is an +-*/ operator
				else if (ch=='+' || ch=='-' || ch=='*' || ch=='/') {
					// if it express positive or negative number which only happens as "-a+b" or "b+(-a)"
					if(index==0 || lastCharIsBracket(str, index)) {
						list.add("0"); // insert a 0 before the current operator
					}
					list.add(ch+"");
					index++;
				}
			
				// else if the token is a parenthesis
				else if (ch=='(' || ch==')') {
					list.add(ch+"");
					index++;
				}
				
				// else if the token is a number
				else if (48<=ch && ch<=57) {
					// the first digit must be a number
					String number = "";
					while (index<str.length() && ((48<=str.charAt(index) && str.charAt(index)<=57) || str.charAt(index)=='.')) {
						// the subsequent digit must be a number or '.'
						number += str.charAt(index);
						index++;
					}
					if(number.indexOf(".") != number.lastIndexOf(".")) {
						throw new RuntimeException("invalid number");
					}else {
						list.add(number);
					}
				}
				
				// else if the token is trigonometric function among "cos", "sin", "tan"
				else if(str.length()>=index+3) {
					if (str.substring(index, index+3).equals("cos") || str.substring(index, index+3).equals("sin") || str.substring(index, index+3).equals("tan")) {
						list.add(str.substring(index, index+3));
						index+=3;
					}

					// else if the token is "sqrt"
					else if(str.length()>=index+4) {
						if (str.substring(index, index+4).equals("sqrt")) {
							list.add(str.substring(index, index+4));
							index+=4;
						}
					}
				}
				
				// else -> invalid character
				else {
					throw new RuntimeException("Invalid character!");
				}
			}
			
			return list;
		}
		
		// Second convert the infix expression into suffix expression
		public static List<String> convertToSuffixList(List<String> infix_list) {
			List<String> list = new ArrayList<>(); // the suffix list
			Stack<String> operation_stack = new Stack<>(); // used to contain operators
			
			for (String item : infix_list) {
				// if it is a number
				if(judgeType(item).equals("number")) {
					list.add(item);
				}
				
				// else if it is parentheses
				else if(judgeType(item).equals("parentheses")) {
					if("(".equals(item)) { // directly push "(" into the stack
						operation_stack.push(item);
					}else if (")".equals(item)) {
						while (!operation_stack.isEmpty()) { // pop out until "(" appears
							if("(".equals(operation_stack.peek())) {
								operation_stack.pop();
								// if the next token in the stack is a function
								if(!operation_stack.isEmpty()) {
									if(judgeType(operation_stack.peek()).equals("function")) {
										list.add(operation_stack.pop());
									}
								}
								break;
							}else {
								list.add(operation_stack.pop());
							}
						}
					}
				}
				
				// else if it is an operator among + - / *
				else if(judgeType(item).equals("operator")) {
					// when the stack is empty 
					// or the current priority is bigger than that of the top of the stack
					// or the top element is "("
					if (operation_stack.isEmpty() || getPriority(item)>getPriority(operation_stack.peek()) || operation_stack.peek().equals("(")) {
						operation_stack.push(item);
					}else {
						// first pop out the element if the stack is not empty and the top one is not "("
						while(!operation_stack.isEmpty() && !operation_stack.peek().equals("(") && getPriority(item)<=getPriority(operation_stack.peek())) {
							list.add(operation_stack.pop());
						}
						// then push the item into the stack
						operation_stack.push(item);
					}
				}
				
				// else if it is among "sin cos tan sqrt"
				else if(judgeType(item).equals("function")) {
					operation_stack.push(item);
				}
			}
			
			while(!operation_stack.isEmpty()) {
				list.add(operation_stack.pop());
			}
			
			return list;
		}
		
		// Finally compute the result of the suffix expression
		public static Double computeResult(List<String> suffix_list) {
			Double result = 0.0;
			Stack<Double> stack = new Stack<>();
			
			for (String item : suffix_list) {
				// if it is a number
				if(judgeType(item).equals("number")) { // directly push it into the stack
					stack.push(Double.parseDouble(item));
				}
				
				// else if it is an operator
				else if(judgeType(item).equals("operator")) {
					Double num2 = stack.pop();
					Double num1 = stack.pop();
					switch(item) {
						case "+":
							stack.push(num1+num2);
							break;
						case "-":
							stack.push(num1-num2);
							break;
						case "*":
							stack.push(num1*num2);
							break;
						case "/":
							stack.push(num1/num2);
							break;
					}
				}
				
				// else if it is a function
				else if(judgeType(item).equals("function")) {
					Double num = stack.pop();
					switch(item) {
						case "sin":
							stack.push(Math.sin(num));
							break;
						case "cos":
							stack.push(Math.cos(num));
							break;
						case "tan":
							stack.push(Math.tan(num));
							break;
						case "sqrt":
							stack.push(Math.sqrt(num));
							break;
					}
				}
				
				else { // such as ( )
					throw new RuntimeException("Invalid computing element!");
				}
//				System.out.print(stack+"\n");
			}
			
			result = stack.pop();
			if(stack.isEmpty()) {
				return result;
			}else {
				throw new RuntimeException("Multiple items in result stack!");
			}
		}
		
		// judge whether a character is a number or an operator
		public static String judgeType(String str) {
			if(str.matches("\\d+") || str.matches("^[0-9]+(.[0-9]+)?$")) {
				return "number";
			}else if("+ - / *".contains(str)) {
				return "operator";
			}else if("sin cos tan sqrt".contains(str)) {
				return "function";
			}else if("( )".contains(str)) {
				return "parentheses";
			}
			
			else {
				throw new RuntimeException("Invalid string element!");
			}
		}
		// set the priority of each operator among + - / *
		public static int getPriority(String operator) {
			int priority = -1;
			
			switch (operator) {
				case "+" :
					priority = 0;
					break;
				case "-" :
					priority = 0;
					break;
				case "/" :
					priority = 1;
					break;
				case "*" :
					priority = 1;
					break;
			}
			
			return priority;
		}
	
    public static double parse(String str) {
        // implement the code here
        // evaluate a math expression, and return the value
        // if the expression is invalid, return NaN.
        
    	try {
			return (computeResult(convertToSuffixList(convertToInfixList(str))));
		} catch (Exception e) {
			return Double.NaN;
		}
    }
}
