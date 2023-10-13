package projetsi.models;

public class Pair<F, S> {

    private F first;
    private S second;

    /**
     * @param f The first element
     * @param s The second element
     */
    public Pair(F f, S s) {
        first = f;
        second = s;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F f) {
        first = f;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S s) {
        second = s;
    }
}
