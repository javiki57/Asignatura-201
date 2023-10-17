package analyzer;

public class O2N implements Algorithm {

    long n;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void init(long n) {
        this.n=n;
    }

    @Override
    public void reset() {

    }

    @Override
    public String getSolution() {
        return null;
    }

    @Override
    public String getComplexity() {
        return "O2N";
    }

    @Override
    public long getMaxSize() {
        return 0;
    }

    //hanoi
    @Override
    public void run() {
        O2n(n, "", "", "");
    }

    private void O2n(long n, String fromPeg, String toPeg, String sparePeg) {
        if (n<1) {
            return;
        }
        if (n>1) {
            O2n(n-1, fromPeg, sparePeg, toPeg);
        }
        if (n>1) {
            O2n(n-1, sparePeg, toPeg, fromPeg);
        }
    }

}
