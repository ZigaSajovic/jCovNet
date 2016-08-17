package layers;

import tensors.Tensor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ziga on 23.7.2016.
 */
public interface Layer extends Serializable {
    //return evaluated layer
    Tensor eval(Tensor prevResult);
    /*
    returns intermediate results needed for gradient computation
    output of the layer is to be at index 1 in the array
     */
    Tensor[] evalForGrad(Tensor prevResult);
    /*
    inputs currentResults and prevResults are what the method eval above
    returns for the appropriate layers
    returns gradient
     */
    Tensor[] grad(Tensor[] currentResults, Tensor[] prevResults, Tensor currentGrad);
    //initializes weights
    void initialize();
    //updates the layers parameters
    void update(ArrayList<Tensor[]> gradient, double learningRate);
}
