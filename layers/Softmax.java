package layers;

import activations.Activation;
import activations.Exp;
import tensors.Tensor;
import tensors.Vector;

import java.util.ArrayList;

/**
 * Created by ziga on 27.7.2016.
 */
public class Softmax implements Layer {

    Activation activation;

    public Softmax(){
        this.activation=new Exp();
    }

    @Override
    public Tensor eval(Tensor prevResult) {
        double sum=0;
        for(int i=0;i<((Vector)prevResult).size();i++){
            sum+=activation.f(((Vector)prevResult).data[i]);
        }
        Vector output=((Vector)prevResult).forAllFCopy(activation);
        for(int i=0;i<output.size();i++){
            output.data[i]/=sum;
        }
        return output;
    }

    @Override
    public Tensor[] evalForGrad(Tensor prevResult) {
        double sum=0;
        for(int i=0;i<((Vector)prevResult).size();i++){
            sum+=activation.f(((Vector)prevResult).data[i]);
        }
        Vector output=((Vector)prevResult).forAllFCopy(activation);
        for(int i=0;i<output.size();i++){
            output.data[i]/=sum;
        }
        return new Tensor[]{new Vector(new double[]{sum}),output};
    }

    @Override
    public Tensor[] grad(Tensor[] currentResults, Tensor[] prevResults, Tensor currentGrad) {
        Vector outGrad=new Vector(((Vector)prevResults[1]).size());
        for(int i=0;i<((Vector)prevResults[1]).size();i++){
            outGrad.set(i,0);
            for(int j=0;j<((Vector)prevResults[1]).size();j++){
                if(i==j){
                    outGrad.data[i]+=(((Vector)currentResults[1]).data[i]-Math.pow(((Vector)currentResults[1]).data[i],2))*((Vector)currentGrad).data[j];
                }
                else{
                    outGrad.data[i]-=((Vector)currentResults[1]).data[i]*((Vector)currentResults[1]).data[j]*((Vector)currentGrad).data[j];
                }
            }
        }
        ((Vector)currentGrad).data=outGrad.data;
        ((Vector)currentGrad).shape=outGrad.shape;
        return new Tensor[]{outGrad.copy()};
    }

    @Override
    /*
    Requires no initialization as there are no weights in a layer of this type
     */
    public void initialize() {
        return;
    }

    @Override
    /*
    Requires no update as there are no weights in a layer of this type
     */
    public void update(ArrayList<Tensor[]> gradient, double learningRate) {
        return;
    }

}
