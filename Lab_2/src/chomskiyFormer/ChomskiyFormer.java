package chomskiyFormer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

public class ChomskiyFormer {
    private HashSet<Character> N ;
    private HashSet<Character> SIGMA;
    private Multimap<String,Character> P;
    private Character S;

    private String newN = "";
    private String newSIGMA = "";
    private String newP_rules = "";
    private String newS = "";

    private HashMap<String, String> newNeterminals = new HashMap();

    private int globalIndexNewN = 1;

    public String getNewN() {
        return newN;
    }

    public String getNewSIGMA() {
        return newSIGMA;
    }

    public String getNewP_rules() {
        return newP_rules;
    }

    public String getNewS() {
        return newS;
    }

    public ChomskiyFormer(HashSet<Character> n, HashSet<Character> SIGMA, Multimap<String, Character> p, Character s) {
        N = n;
        this.SIGMA = SIGMA;
        P = p;
        S = s;
    }

    public String setXi(char Xi){
        if(N.contains(Xi)){
            return String.valueOf(Xi);
        }
        else if(SIGMA.contains(Xi)){
            globalIndexNewN++;
            newNeterminals.put("N_" + (globalIndexNewN-1), String.valueOf(Xi));
            return "N_" + (globalIndexNewN-1);
        }
        return null;
    }

    public void toChomskiy(){
        Multimap<String,String> newP = ArrayListMultimap.create();//P'

        //Добавление в P' правил 2ого вида A->a, a принадлежит Σ
        for(String alpha : P.keySet()){
            if(alpha.length() == 1 && SIGMA.contains(alpha.toCharArray()[0])){
                List<Character> A = (List<Character>) P.get(alpha);
                newP.put(alpha, A.get(0).toString());
            }
        }
//        System.out.println("1 step:" + newP.toString());

        //Добавление в P' правил 1-ого вида A->BC, A,B,C принадлежат N
        for(String alpha : P.keySet()){
            if(alpha.length() == 2 && N.contains(alpha.toCharArray()[0]) && N.contains(alpha.toCharArray()[1])){
                List<Character> A = (List<Character>) P.get(alpha);
                newP.put(alpha, A.get(0).toString());
            }
        }
//        System.out.println("2 step:" + newP.toString());

        //Добавление в P' правила S->e, если оно есть
        for(String alpha : P.keySet()){
            if(alpha.equals("e")){
                List<Character> A = (List<Character>) P.get(alpha);
                if(!P.containsValue(A.get(0))){
                    newP.put(alpha, A.get(0).toString());
                }
            }
        }
//        System.out.println("3 step:" + newP.toString());

        //Добавление правил из 4ого пункта алгоритма
        for(String alpha : P.keySet()){
            if(alpha.length() > 2){
                List<Character> A = (List<Character>) P.get(alpha);
                String curA = A.get(0).toString();
                for(int i = 1; i < alpha.length(); i++){
                    //curA->Xi<newN>
                    String Xi = setXi(alpha.toCharArray()[i-1]);
                    String newN;
                    if(i != alpha.length()-1){
                        newN = "N_"+ globalIndexNewN;
                        newNeterminals.put(newN, alpha.substring(i));
                        globalIndexNewN++;
                    }
                    else{
                        newN = alpha.substring(i);
                    }
//                    System.out.println("New rule:" + curA + "->" + Xi + newN);
                    newP.put(Xi + newN, curA);
                    curA = newN;
                }
            }
        }
//        System.out.println("4 step:" + newP.toString());

        //Добавление правил из 5-ого пункта
        for(String alpha : P.keySet()){
            if(alpha.length() == 2 && (SIGMA.contains(alpha.toCharArray()[0]) || SIGMA.contains(alpha.toCharArray()[1]))){
                List<Character> A = (List<Character>) P.get(alpha);
                String X1 = setXi(alpha.toCharArray()[0]);
                String X2 = setXi(alpha.toCharArray()[1]);
                newP.put(X1+X2, A.get(0).toString());
            }
        }
//        System.out.println("5 step:" + newP.toString());

        //Добавление правил из 6-ого пункта
//        System.out.print("New Neterminals:[");
//        for(Map.Entry<String, String> entry : newNeterminals.entrySet()){
//            System.out.print("{" + entry.getKey() + "<->" + entry.getValue() + "}");
//        }
//        System.out.println("]");

        for(Map.Entry<String,String> entry : newNeterminals.entrySet()){
            char[] tmp = entry.getValue().toCharArray();
            boolean inSigma = true;
            for(char alpha : tmp){
                if (!SIGMA.contains(alpha)) {
                    inSigma = false;
                    break;
                }
            }

            if(inSigma){
                newP.put(entry.getValue(), entry.getKey());
            }
        }
//        System.out.println("6 step:" + newP.toString());

        //Задание новых параметров

        for(char n : N){
            newN = newN.concat(n + ",");
        }
        for(Map.Entry<String,String> entry : newNeterminals.entrySet()){
            newN = newN.concat(entry.getKey() + ",");
        }
        newN = newN.substring(0, newN.length()-1);
//        System.out.println(newN);

        for(char term : SIGMA){
            newSIGMA = newSIGMA.concat(term + ",");
        }
        newSIGMA = newSIGMA.substring(0, newSIGMA.length()-1);
//        System.out.println(newSIGMA);

        HashMap<String, String> tmp = new HashMap<>();
        for (String firstName : newP.keySet()) {
            List<String> lastNames = (List<String>) newP.get(firstName);
            for (String lastName : lastNames) {
                if(tmp.containsKey(lastName)){
//                    System.out.println("Already contain:" + lastName + "with :[" + tmp.get(lastName.toString()) + "]");
                    String oldStr = tmp.get(lastName);
                    tmp.replace(lastName, tmp.get(lastName), oldStr + "|" + firstName);
                }
                else {
                    tmp.put(lastName, firstName);
                }
            }
        }
        for(Map.Entry<String, String> entry : tmp.entrySet()){
            newP_rules = newP_rules.concat(entry.getKey() + "->" + entry.getValue() + ",");
        }
        newP_rules = newP_rules.substring(0, newP_rules.length()-1);
//        System.out.println(newP_rules);

        newS = S.toString();
//        System.out.println(newS);
    }

    public boolean checkForCycleRules() throws Exception{
        for(String alpha : P.keySet()){
            if(alpha.equals(P.get(alpha).toString())){
                throw new Exception("Грамматика содержит циклы в правилах! Их нельзя использовать согласно определению приведенной КСГ!!!");
            }
        }
        return true;
    }

    public boolean checkForChainRules() throws Exception {
        for(String alpha : P.keySet()){
            List<Character> listA = (List<Character>) P.get(alpha);

            if(alpha.length() == 1){
                if(N.contains(alpha.toCharArray()[0]) && N.containsAll(listA)){
                    throw new Exception("Грамматика содержит цепные правила! Их нельзя использовать согласно определению приведенной КСГ!!!");
                }
            }
        }
        return true;
    }
}
