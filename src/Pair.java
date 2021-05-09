public class Pair {
    int first;
    int second;

    public Pair(int fir, int sec){
        first = fir;
        second = sec;
    }
    public Pair(){
        first = -1;
        second = -1;
    }

    // Getter
    public int getFirst() {
        return first;
    }

    // Setter
    public void setFirst(int fir) {
        this.first= fir;
    }
    // Getter
    public int getSecond() {
        return second;
    }

    // Setter
    public void setSecond(int sec) {
        this.second= sec;
    }


}
