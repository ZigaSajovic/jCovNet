package costs;

import tensors.Vector;

import java.io.Serializable;

/**
 * Created by ziga on 23.7.2016.
 */
public interface Cost extends Serializable {

    //returns image of the map
    double f(Vector inputVector, Vector valueVector);
    //returns image of maps derivative
    Vector df(Vector inputVector,Vector valueVector);
}
