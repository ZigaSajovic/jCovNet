package layers;

import activations.Activation;
import tensors.Tensor;
import tensors.Weights;
import tensors.Vector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ziga on 24.7.2016.
 *
 * This parent class ensures that all implemented weighted-layers have
 * weights, biases, an activation function and required methods
 * as contracted by the interface
 */
public abstract class BaseLayer implements Layer {
    // [numOfInputs, numOfOutputs]
    public int shape[];
    //of shape [shape[1], shape[0]]
    public Weights weights;
    //each output has its' own bias
    public Vector bias;
    //activation of neurons' output
    public Activation activation;


    public BaseLayer(Weights weights, Vector bias, Activation activation){
        this.weights=weights;
        this.bias=bias;
        this.activation=activation;
    }

    public BaseLayer(int[] shape, Activation activation){
        this.shape=shape;
        this.activation=activation;
    }

    @Override
    public void initialize (){
        Random rand=new Random(System.nanoTime());
        double[][] weightsTmp=new double[shape[1]][shape[0]];
        double[] biasTmp=new double[shape[1]];
        for(int i=0;i<shape[1];i++){
            for(int j=0;j<shape[0];j++){
                weightsTmp[i][j]=rand.nextGaussian()/Math.sqrt(shape[1]);
            }
            biasTmp[i]=0;
        }
        weights=new Weights(weightsTmp);
        bias=new Vector(biasTmp);
    }

    @Override
    public void update(ArrayList<Tensor[]> gradient, double learningRate){
        for(int i=0;i<this.weights.shape[0];i++){
            for(int j=0;j<this.weights.shape[1];j++){
                for(int k=0;k<gradient.size();k++){
                    this.weights.data[i][j]-=(learningRate/gradient.size())*((Weights)gradient.get(k)[0]).data[i][j];
                }
            }
        }
        for(int i=0;i<this.bias.shape[0];i++){
            for(int k=0;k<gradient.size();k++){
                this.bias.data[i]-=(learningRate/gradient.size())*((Vector)gradient.get(k)[1]).data[i];
            }
        }
    }
}
