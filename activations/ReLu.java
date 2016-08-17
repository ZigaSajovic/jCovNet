package activations;

/**
 * Created by ziga on 23.7.2016.
 */
public class ReLu implements Activation {
    @Override
    public double f(double inputDouble) {
        if(inputDouble>0){
            return inputDouble;
        }
        else return 0;
    }

    @Override
    public double df(double inputDouble) {
        if(inputDouble>0){
            return 1;
        }
        else {
            return 0;
        }
    }
}
