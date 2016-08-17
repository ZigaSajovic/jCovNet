package activations;

/**
 * Created by ziga on 23.7.2016.
 */
public class Identity implements Activation {
    @Override
    public double f(double inputDouble) {
        return inputDouble;
    }

    @Override
    public double df(double inputDouble) {
        return 1;
    }
}
