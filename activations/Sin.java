package activations;

/**
 * Created by ziga on 27.7.2016.
 */
public class Sin implements Activation {
    @Override
    public double f(double inputDouble) {
        return Math.sin(inputDouble);
    }

    @Override
    public double df(double inputDouble) {
        return Math.cos(inputDouble);
    }
}
