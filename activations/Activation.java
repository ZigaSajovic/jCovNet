package activations;

import java.io.Serializable;

/**
 * Created by ziga on 23.7.2016.
 */

public interface Activation extends Serializable {
    //returns image of the map
    double f(double inputDouble);
    //returns image of maps derivative
    double df(double inputDouble);
}
