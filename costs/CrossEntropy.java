package costs;

import tensors.Vector;

/**
 * Created by ziga on 28.7.2016.
 */
public class CrossEntropy implements Cost{
    @Override
    public double f(Vector inputVector, Vector valueVector) {
        double output=0;
        for(int i=0;i<inputVector.size();i++){
            output-=valueVector.data[i]*Math.log(inputVector.data[i])/inputVector.size();
        }
        return output;
    }

    @Override
    public Vector df(Vector inputVector, Vector valueVector) {
        Vector output=new Vector(inputVector.size());
        for(int i=0;i<inputVector.size();i++){
            output.data[i]=-valueVector.data[i]/(inputVector.data[i]*inputVector.size());
        }
        return output;
    }
}
