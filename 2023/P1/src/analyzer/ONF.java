package analyzer;

public class ONF implements Algorithm {
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
        return null;
    }

    @Override
    public long getMaxSize() {
        return 0;
    }

    @Override
    public void run() {
        OnF(n);
    }

    private void OnF(long n) {
        for (int i = 0; i < n; i++) {
            int x = 2 * 3;
            OnF(n-1);
        }
    }
}
