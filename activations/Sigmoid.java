package activations;

/**
 * Created by ziga on 23.7.2016.
 */
public class Sigmoid implements Activation {
    @Override
    public double f(double inputDouble) {
        return 1/(1+Math.exp(-inputDouble));
    }

    @Override
    public double df(double inputDouble) {
        return (1/(1+Math.exp(-inputDouble)))*(1-1/(1+Math.exp(-inputDouble)));
    }
}
