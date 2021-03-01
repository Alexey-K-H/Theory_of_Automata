package chainRulesRemover;

import com.google.common.collect.Multimap;

import java.util.HashSet;

public class ChainRulesRemover {
    private HashSet<Character> N ;
    private HashSet<Character> SIGMA;
    private Multimap<String,Character> P;
    private Character S;
    private HashSet<Character> Na;


    public ChainRulesRemover(HashSet<Character> n, HashSet<Character> SIGMA, Multimap<String, Character> p, Character s) {
        N = n;
        this.SIGMA = SIGMA;
        P = p;
        S = s;
    }

    public boolean checkForERules() throws Exception {
        for(String alpha : P.keySet()){
            if(alpha.equals("e")){
                throw new Exception("Обнаружено e-правило в грамматике! Их нельзя использовать при удалении цепных правил!");
            }
        }
        return true;
    }

    public void removeChainRules(){
        for(char A : N){

        }
    }
}
