import java.util.HashSet;

public class ParserArguments {
    private static final HashSet<Character> N = new HashSet<>();
    private static final HashSet<Character> SIGMA = new HashSet<>();
    private static Character S;

    public void parseN(String strN){
        while (true){
            boolean flag = false;
            String checkErr = strN.replaceAll("[^->e'\"]", "");
            if(checkErr.length() > 0 ){
                System.out.println("Использован неверный символ!");
            }
            else {

            }
        }
    }

    public void parseSigma(){

    }

    public void parseP(){

    }

    public void parseS(){

    }
}
