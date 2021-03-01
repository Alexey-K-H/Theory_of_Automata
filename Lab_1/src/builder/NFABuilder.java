package builder;

import expression.*;

import java.util.Deque;
import java.util.HashSet;

public class NFABuilder {
    private Deque<Path> nfa;
    private final HashSet<Character> alphabet;

    public NFABuilder(){
        alphabet = new HashSet<>();
    }

    public Deque<Path> getNfa() {
        return nfa;
    }

    public HashSet<Character> getAlphabet() {
        return alphabet;
    }

    public Deque<Path> maker(Word w){
        switch (w.getClass().getCanonicalName()){
            case "expression.Symbol":{
                alphabet.add(((Symbol)w).getS());
                return Path.sym((Symbol) w);
            }
            case "expression.Concatenation":{
                Word w1 = ((Concatenation)w).getS1();
                Word w2 = ((Concatenation)w).getS2();
                Deque<Path> pathDeque1 = maker(w1);
                Deque<Path> pathDeque2 = maker(w2);
                return Path.concat(pathDeque1, pathDeque2);
            }
            case "expression.KleeneStar":{
                Word w1 = ((KleeneStar)w).getS();
                Deque<Path> pathDeque1 = maker(w1);
                return Path.kleene(pathDeque1);
            }
            case "expression.Or":{
                Word w1 = ((Or)w).getS1();
                Word w2 = ((Or)w).getS2();
                Deque<Path> pathDeque1 = maker(w1);
                Deque<Path> pathDeque2 = maker(w2);
                return Path.orOp(pathDeque1, pathDeque2);
            }
            default:{
                System.out.println(w.getClass().getCanonicalName());
                return null;
            }
        }
    }

    public void makeNFA(Word w){
        this.nfa = maker(w);
    }
}
