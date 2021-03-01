package builder;

import java.util.Deque;
import java.util.HashSet;

public class SyntaxAnalyzer {
    private static boolean checkTree(HashSet<CurrentState> currentTreeLevel){
        for(CurrentState cs:currentTreeLevel){
            if(cs.getS().equals("")){
                return true;
            }
        }

        return currentTreeLevel.isEmpty();
    }

    private static HashSet<CurrentState> step(Deque<Path> nfa, HashSet<CurrentState> currentTreeLevel){
        HashSet<CurrentState> nextTreeLevel = new HashSet<>();

        for(CurrentState cs: currentTreeLevel){
            if(cs.getS().length() == 0){
                continue;
            }
            char currentChar = cs.getS().charAt(0);

            for(Path p:nfa){
                if(p.getFrom() == cs.getFrom()){
                    if(p.getW() == currentChar){
                        CurrentState nextState;
                        if(cs.getS().length() > 1){
                            nextState = new CurrentState(p.getTo(), cs.getS().substring(1));
                        }
                        else{
                            nextState = new CurrentState(p.getTo(), "");
                        }
                        nextTreeLevel.add(nextState);
                    }
                    else if(p.getW() == '\0'){
                        CurrentState nextCondition = new CurrentState(p.getTo(), cs.getS());
                        nextTreeLevel.add(nextCondition);
                    }
                }
            }
        }

        return nextTreeLevel;
    }

    public static boolean analyze(NFABuilder nfa, String str){
        HashSet<CurrentState> treeLevel = new HashSet<>();
        CurrentState state_0 = new CurrentState(0, str);
        treeLevel.add(state_0);
        while (!checkTree(treeLevel)){
            treeLevel = step(nfa.getNfa(), treeLevel);
        }

        return !treeLevel.isEmpty();
    }
}
