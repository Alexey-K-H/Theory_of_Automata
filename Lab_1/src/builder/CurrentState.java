package builder;

public class CurrentState {
    private final int from;
    private final String s;

    public CurrentState(int from, String s){
        this.from = from;
        this.s = s;
    }

    public int getFrom() {
        return from;
    }

    public String getS() {
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof CurrentState)){
            return false;
        }
        CurrentState other = (CurrentState) obj;
        return (other.getFrom() == this.getFrom()) && ((other.getS()).equals(this.getS()));
    }
}
