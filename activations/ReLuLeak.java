package activations;

/**
 * Created by ziga on 23.7.2016.
 */
public class ReLuLeak implements Activation {
    @Override
    public double f(double inputDouble) {
        if(inputDouble>0){
            return inputDouble;
        }
        else {
            return 0.0001;
        }
    }

    @Override
    public double df(double inputDouble) {
        if(inputDouble>0){
            return 1;
        }
        else {
            return 0.0001;
        }
    }
}
