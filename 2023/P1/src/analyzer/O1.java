package analyzer;

public class O1 implements Algorithm {
    long n;
    @Override
    public String getName() {
        return "O1";
    }

    @Override
    public void init(long n) {
        this.n = n;
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
        return "O1";
    }

    @Override
    public long getMaxSize() {
        return 0;
    }

    @Override
    public void run() {
        int n = 2 * 2;
    }
}
