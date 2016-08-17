package layers;

import tensors.Tensor;
import tensors.Vector;

import java.util.ArrayList;

/**
 * Created by ziga on 25.7.2016.
 */
public class PlaceHolder implements Layer {

    @Override
    public Vector eval(Tensor prevResult) {
        return (Vector)prevResult;
    }

    @Override
    public Tensor[] evalForGrad(Tensor prevResult) {
        return new Tensor[0];
    }

    @Override
    public Tensor[] grad(Tensor[] currentResults, Tensor[] prevResults, Tensor currentGrad) {
        return new Tensor[0];
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(ArrayList<Tensor[]> gradient, double learningRate) {

    }
}
