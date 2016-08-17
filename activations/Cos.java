package activations;

/**
 * Created by ziga on 27.7.2016.
 */
public class Cos implements Activation {
    @Override
    public double f(double inputDouble) {
        return Math.cos(inputDouble);
    }

    @Override
    public double df(double inputDouble) {
        return -Math.sin(inputDouble);
    }
}
