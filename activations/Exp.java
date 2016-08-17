package activations;

/**
 * Created by ziga on 27.7.2016.
 */
public class Exp implements Activation{
    @Override
    public double f(double inputDouble) {
        return Math.exp(inputDouble);
    }

    @Override
    public double df(double inputDouble) {
        return Math.exp(inputDouble);
    }
}
