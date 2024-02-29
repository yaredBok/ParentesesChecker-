package org.example;

import java.util.Stack;

public class ParentesesChecker {
    public static void main(String[] args) {
   //     String strCode = "{(){()[}}]";
        String strCode = "{()}([()])";


        Stack <Character> stack = new Stack<>();
        for (int i =0; i <strCode.length(); i++) {
            if (strCode.charAt(i) == '[' || strCode.charAt(i) == '(' || strCode.charAt(i) == '{') {
                stack.push(strCode.charAt(i));
            } else if (!stack.isEmpty() && (
                    (strCode.charAt(i) == ']' && stack.peek() == '[') ||
                    (strCode.charAt(i) == ')' && stack.peek() == '(') ||
                     (strCode.charAt(i)) == '}' && stack.peek() == '{')) {
    stack.pop();

            }else {
                stack.push(strCode.charAt(i));
            }
        }
        if (stack.isEmpty()){
            System.out.println("Balanced");
        }else {
            System.out.println("not balanced");
        }
    }
}
