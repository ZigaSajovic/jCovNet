package costs;

import tensors.Vector;

/**
 * Created by ziga on 23.7.2016.
 */
public class CrossEntropyBinary implements Cost {
    @Override
    public double f(Vector inputVector, Vector valueVector) {
        double output=0;
        for(int i=0;i<inputVector.size();i++){
            output+=(valueVector.get(i)*Math.log(inputVector.get(i))+(1-valueVector.get(i))*Math.log(1-inputVector.get(i)))/inputVector.size();
        }
        return output;
    }

    @Override
    public Vector df(Vector inputVector, Vector valueVector) {
        Vector output=new Vector(inputVector.size());
        for(int i=0;i<inputVector.size();i++){
            output.set(i,(valueVector.get(i)/inputVector.get(i)-(1-valueVector.get(i))/(1-inputVector.get(i)))/inputVector.size());
        }
        return output;
    }
}
