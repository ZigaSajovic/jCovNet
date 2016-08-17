package costs;

import tensors.Vector;

/**
 * Created by ziga on 23.7.2016.
 */
public class MeanSquare implements Cost {

    @Override
    public double f(Vector inputVector, Vector valueVector) {
        double output=0;
        for(int i=0;i<inputVector.size();i++){
            output+=Math.pow(inputVector.get(i)-valueVector.get(i),2)/inputVector.size();
        }
        return output;
    }

    @Override
    public Vector df(Vector inputVector, Vector valueVector) {
        Vector output=new Vector(inputVector.size());
        for(int i=0;i<inputVector.size();i++){
            output.set(i,2*(inputVector.get(i)-valueVector.get(i))/inputVector.size());
        }
        return output;
    }
}
