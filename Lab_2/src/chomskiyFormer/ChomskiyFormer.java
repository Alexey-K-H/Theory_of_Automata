package chomskiyFormer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashSet;
import java.util.List;

public class ChomskiyFormer {
    private HashSet<Character> N ;
    private HashSet<Character> SIGMA;
    private Multimap<String,Character> P;
    private Character S;

    private String newN;
    private String newSIGMA;
    private String newP_rules;
    private String newS;

    public ChomskiyFormer(HashSet<Character> n, HashSet<Character> SIGMA, Multimap<String, Character> p, Character s) {
        N = n;
        this.SIGMA = SIGMA;
        P = p;
        S = s;
    }

    public void toChomskiy(){
        Multimap<String,Character> newP = ArrayListMultimap.create();//P'

        //Добавление в P' правил 2ого вида A->a, a принадлежит Σ
        for(String alpha : P.keySet()){
            if(alpha.length() == 1 && SIGMA.contains(alpha.toCharArray()[0])){
                List<Character> A = (List<Character>) P.get(alpha);
                newP.put(alpha, A.get(0));
            }
        }
        System.out.println("1 step:" + newP.toString());

        //Добавление в P' правил 1-ого вида A->BC, A,B,C принадлежат N
        for(String alpha : P.keySet()){
            if(alpha.length() == 2 && N.contains(alpha.toCharArray()[0]) && N.contains(alpha.toCharArray()[1])){
                List<Character> A = (List<Character>) P.get(alpha);
                newP.put(alpha, A.get(0));
            }
        }
        System.out.println("2 step:" + newP.toString());

        //Добавление в P' правила S->e, если оно есть
        for(String alpha : P.keySet()){
            if(alpha.equals("e")){
                List<Character> A = (List<Character>) P.get(alpha);
                if(!P.containsValue(A.get(0))){
                    newP.put(alpha, A.get(0));
                }
            }
        }
        System.out.println("3 step:" + newP.toString());

        for(String alpha : P.keySet()){
            if(alpha.length() > 2){
                List<Character> A = (List<Character>) P.get(alpha);

            }
        }
    }
}
