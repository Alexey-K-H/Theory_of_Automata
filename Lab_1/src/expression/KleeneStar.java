package expression;

public class KleeneStar implements Word{

    private final Word s;

    public KleeneStar(Word s){
        this.s = s;
    }

    public Word getS(){
        return s;
    }

    @Override
    public String toString() {
        return "kleene(" + s.toString() + ")";
    }
}
