package parser;

import java.util.HashSet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ParserArguments {
    private final HashSet<Character> N = new HashSet<>();
    private final HashSet<Character> SIGMA = new HashSet<>();
    private final Multimap<Character, char[]> P = ArrayListMultimap.create();
    private Character S;
    private int K;

    public ParserArguments(String N, String SIGMA, String P, String S, String K) throws Exception{
        parseN(N);
        parseSigma(SIGMA);
        parseS(S);
        parseP(P);
        parseK(K);
    }

    public void parseN(String strN) throws Exception {
        String checkErr = strN.replaceAll("[^->e'\"]", "");
        if(checkErr.length() > 0 ){
            throw new Exception("Использован неверный символ!");
        }
        else {
            String[] N_arr = strN.split(",");
            for(String value : N_arr){
                char[] arr = value.toCharArray();
                if(arr.length > 1){
                    N.clear();
                    throw new Exception("Нельзя использовать связку символов за один!");
                }
                else{
                    N.add(arr[0]);
                }
            }
        }
    }


    public void parseSigma(String strSigma) throws Exception {
        String newStr = strSigma.replaceAll("[^->e'\"]", "");
        if ( 0 < newStr.length()) {
            throw new Exception("Использован неверный символ, попробуйте еще раз");
        }
        else{
            for (Character c:strSigma.toCharArray()) {
                if(N.contains(c)){
                    throw new Exception("Использован символ "+c+" из множества N! ∑ не может содержать символы из данного множества");
                }
            }

            String[] Terminal_arr = strSigma.split(",");
            for (String value : Terminal_arr) {
                char[] arr = value.toCharArray();
                if(arr.length>1){
                    SIGMA.clear();
                    throw new Exception("Нельзя использовать связку символов за один, попробуйте еще раз");
                }
                else {
                    SIGMA.add(arr[0]);
                }
            }
        }
    }

    public void parseP(String strP) throws Exception {

            if(!strP.matches("[^'\"]*")){
                throw new Exception("Использован неверный символ, попробуйте еще раз");
            }

            //[ a->b , c->d ]
            String[] P_arr = strP.split(",");

            for (String s:P_arr) {//a->bcd

                String[] P_current = s.split("->");//[ a , bcd ]
                if(P_current.length == 2){
                    char[] A = P_current[0].toCharArray(); // [ a ]
                    if(A.length == 1 && N.contains(A[0])){
                        char[] a = P_current[1].toCharArray();// [ b, c, d ]
                        for (char c:a) {
                            if ((!N.contains(c) && !SIGMA.contains(c)) && c != 'e' && c != '|') {
                                P.clear();
                                throw new Exception("Использовался символ не из множеств N, ∑!");
                            }
                        }
                        String a_str = new String(a);
                        if(a_str.contains("|")){
                            String[] tmp = a_str.split("\\|");
                            for(String r: tmp){
                                P.put(A[0], r.toCharArray());
                            }
                        }
                        else {
                            P.put(A[0], a);
                        }
                    }
                    else {
                        throw new Exception("Нетерминал не принадлежит множеству N, попробуйте еще раз");
                    }
                }
                else {
                    throw new Exception("Правила должны иметь вид А->a, попробуйте еще раз");
                }
            }
    }

    public void parseS(String strS) throws Exception {
            char[] chars = strS.toCharArray();
            if(chars.length !=1){
                throw new Exception("Нужен лишь один нетерминал, попробуйте еще раз");
            }
            else if(!N.contains(chars[0])){
                throw new Exception("Нетерминал не содержится в множестве N, попробуйте еще раз");
            }
            else {
                S=chars[0];
            }
    }

    public void parseK(String strK) throws Exception {
        K = Integer.parseInt(strK);
        if(K <= 0){
            throw new Exception("Значение K не может быть меньше 1!");
        }
    }

    public HashSet<Character> getN() {
        return N;
    }

    public HashSet<Character> getSIGMA() {
        return SIGMA;
    }

    public Multimap<Character, char[]> getP() {
        return P;
    }

    public Character getS() {
        return S;
    }

    public int getK() {
        return K;
    }
}
