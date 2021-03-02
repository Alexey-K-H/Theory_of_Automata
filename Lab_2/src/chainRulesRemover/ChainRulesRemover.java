package chainRulesRemover;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;

public class ChainRulesRemover {
    private HashSet<Character> N ;
    private HashSet<Character> SIGMA;
    private Multimap<String,Character> P;
    private Character S;

    private String newN;
    private String newSIGMA;
    private String newP_rules;
    private String newS;

    public String getNewN() {
        return newN;
    }

    public void setNewN(String newN) {
        this.newN = newN;
    }

    public String getNewSIGMA() {
        return newSIGMA;
    }

    public void setNewSIGMA(String newSIGMA) {
        this.newSIGMA = newSIGMA;
    }

    public String getNewP() {
        return newP_rules;
    }

    public void setNewP(String newP) {
        this.newP_rules = newP;
    }

    public String getNewS() {
        return newS;
    }

    public void setNewS(String newS) {
        this.newS = newS;
    }

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
        Multimap<String,Character> newP = ArrayListMultimap.create();//P'

        for(char A : N){
            HashSet<Character> Na = new HashSet<>();
            Na.add(A);//Na_{0} = {A}

            for(String alpha : P.keySet()){
                List<Character> listA = (List<Character>) P.get(alpha);
                char[] arr = alpha.toCharArray();
                if(Na.containsAll(listA) && (arr.length == 1 && N.contains(arr[0]))){
                    Na.add(arr[0]);
                }

                if(N.containsAll(listA) && ((arr.length == 1 && !N.contains(arr[0])) || (arr.length > 1)) ){
                    newP.put(alpha, A);
                }
            }

//            System.out.print("N_{" + A + "}=[");
//            for(char element : Na){
//                System.out.print(element + ",");
//            }
//            System.out.println("]");
        }

//        for (String firstName : newP.keySet()) {
//            List<Character> lastNames = (List<Character>) newP.get(firstName);
//            for (Character lastName : lastNames) {
//                System.out.print(firstName + " -> { ");
//                System.out.println(lastName + "}");
//            }
//        }

        //Fill new params
        String nValue = "";
        for(char n : N){
             nValue = nValue.concat(n + ",");
        }
        nValue = nValue.substring(0, nValue.length() - 1);
        setNewN(nValue);

        String sigmaValue = "";
        for(char n : SIGMA){
            sigmaValue = sigmaValue.concat(n + ",");
        }
        sigmaValue = sigmaValue.substring(0, sigmaValue.length()-1);
        setNewSIGMA(sigmaValue);

        String pValue = "";
        HashMap<String, String> tmp = new HashMap<>();
        for (String firstName : newP.keySet()) {
            List<Character> lastNames = (List<Character>) newP.get(firstName);
            for (Character lastName : lastNames) {
                if(tmp.containsKey(lastName.toString())){
//                    System.out.println("Already contain:" + lastName + "with :[" + tmp.get(lastName.toString()) + "]");
                    String oldStr = tmp.get(lastName.toString());
                    tmp.replace(lastName.toString(), tmp.get(lastName.toString()), oldStr + "|" + firstName);
                }
                else {
                    tmp.put(lastName.toString(), firstName);
                }
            }
        }

        for(Map.Entry<String, String> entry : tmp.entrySet()){
            pValue = pValue.concat(entry.getKey() + "->" + entry.getValue() + ",");
        }

        pValue = pValue.substring(0, pValue.length()-1);
        setNewP(pValue);

        setNewS(S.toString());

//        System.out.println(getNewN());
//        System.out.println(getNewSIGMA());
//        System.out.println(getNewP());
//        System.out.println(getNewS());
    }
}
