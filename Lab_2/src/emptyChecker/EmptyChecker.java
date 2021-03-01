package emptyChecker;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.List;

public class EmptyChecker {
    private HashSet<Character> N ;
    private HashSet<Character> SIGMA;
    private Multimap<String,Character> P;
    private Character S;
    private HashSet<Character> Ni;
    private boolean result;


    public EmptyChecker(HashSet<Character> N, HashSet<Character> SIGMA, Multimap<String,Character> P, Character S) {
        this.N = N;
        this.SIGMA = SIGMA;
        this.P = P;
        this.S = S;
        result = false;
        Ni = new HashSet<>();
    }

    public boolean isResult() {
        return result;
    }

    public void checkContextFreeGrammar(){
        HashSet<Character> newNi = new HashSet<>();
        Multimap<String,Character> newP = ArrayListMultimap.create(P);

        while (!newP.isEmpty()){

            for (String alpha : P.keySet()) {

                if(!newP.containsKey(alpha)){
                    continue;
                }

                boolean flag = true;

                for (char c:alpha.toCharArray()) {
                    if (!Ni.contains(c) && !SIGMA.contains(c)) {
                        flag = false;
                        break;
                    }
                }

                if(flag || alpha.equals("e")){
                    List<Character> listA = (List<Character>) P.get(alpha);
                    newP.removeAll(alpha);
                    newNi.addAll(listA);
                }
            }

            if(!Ni.containsAll(newNi)){
                Ni.addAll(newNi);
                if(Ni.contains(S)){
                    result = Ni.contains(S);
                    return;
                }
            }
        }
    }

}
