package analyzer;

import java.util.ArrayList;
import java.util.List;

public class OLOGN implements Algorithm {

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
        for (double i = 0; i < Math.log(n); i++) {
            int x = 2*3;
        }
    }

}
