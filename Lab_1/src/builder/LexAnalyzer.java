package builder;

import expression.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LexAnalyzer {
    public LexAnalyzer(){}

    public static List<Word> parse(String str, StringBuilder err_msg){
        List<Word> internalVar = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        boolean flagForeach = true;

        for(char c:str.toCharArray()){
            if(c == ' ') continue;

            if(c == '(' || c == ')' || c == '&' || c == '|' || c == '*'){
                switch (c){
                    case '(':{
                        stack.push('(');
                        break;
                    }
                    case '&':{
                        if(stack.peek() != null && stack.peek() == '('){
                            stack.push('&');
                        }
                        else {
                            err_msg.append("Ошибка в записи опреации: &");
                            flagForeach = false;
                        }
                        break;
                    }
                    case '|':{
                        if(stack.peek() != null && stack.peek() == '('){
                            stack.push('|');
                        }
                        else {
                            err_msg.append("Ошибка в записи опреации: |");
                            flagForeach = false;
                        }
                        break;
                    }
                    case '*':{
                        if(stack.peek() != null && stack.peek() == ')'){
                            stack.pop();
                            stack.pop();
                        }
                        else {
                            err_msg.append("Ошибка в записи выражения: *");
                            flagForeach = false;
                            break;
                        }
                        Word w = internalVar.remove(internalVar.size() - 1);
                        internalVar.add(new KleeneStar(w));
                        break;
                    }
                    case ')':{
                        if(stack.peek() != null && stack.peek() == '('){
                            stack.push(')');
                        }
                        else if(stack.peek() != null && stack.peek() == '&'){
                            Word w1 = internalVar.remove(internalVar.size() - 1);
                            Word w2 = internalVar.remove(internalVar.size() - 1);
                            internalVar.add(new Concatenation(w2, w1));
                            stack.pop();
                            stack.pop();
                        }
                        else if(stack.peek() != null && stack.peek() == '|'){
                            Word w1 = internalVar.remove(internalVar.size() - 1);
                            Word w2 = internalVar.remove(internalVar.size() - 1);
                            internalVar.add(new Or(w2, w1));
                            stack.pop();
                            stack.pop();
                        }
                        else {
                            err_msg.append("Неверное выражение: )");
                            flagForeach = false;
                        }
                        break;
                    }
                    default:{
                        err_msg.append("Неверное выражение");
                        flagForeach = false;
                    }
                }
            }
            else {
                internalVar.add(new Symbol(c));
            }

            if(!flagForeach){
                break;
            }
        }

        if(!stack.isEmpty() || !flagForeach){
            err_msg.append("Неверное выражение");
            return null;
        }

        return internalVar;
    }
}
