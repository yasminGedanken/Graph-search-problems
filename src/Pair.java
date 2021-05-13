public class Pair<T, E> {
    T first;
    E second;


    public Pair(T fir, E sec){
        first = fir;
        second = sec;
    }


    // Getter
    public T getFirst() {
        return first;
    }

    // Setter
    public void setFirst(T fir) {
        this.first= fir;
    }
    // Getter
    public E getSecond() {
        return second;
    }

    // Setter
    public void setSecond(E sec) {
        this.second= sec;
    }


}
