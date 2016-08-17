package layers;

import activations.Activation;
import tensors.Tensor;
import tensors.Volume;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ziga on 30.7.2016.
 */
public class Convolutional implements Layer {

    //[numOfInputs(depthOfInputVolume),widthOfMasks, heightOfMasks, numOfOutputs(numOfMasks->depthOfOutputVolume)]
    public int[] shape;
    //[widthStride, heightStride]
    public int[] stride;
    /*each of shape [shape[1], shape[2], shape[0]]
      or explicitly [width, height, depthOfInputVolume]
    */
    public Volume[] mask;
    //each mask has its own bias (one for each mask)
    public double[] bias;
    //activation of neurons' output
    Activation activation;

    public Convolutional(int[] shape, int[] stride, Activation activation){
        this.shape=shape;
        this.stride=stride;
        this.activation=activation;
    }

    @Override
    public Tensor eval(Tensor prevResult) {
        return null;
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
        Random rand=new Random(System.nanoTime());
        mask=new Volume[this.shape[3]];
        bias=new double[this.shape[3]];
        for(int l=0;l<mask.length;l++){
            double[][][] volume=new double[shape[1]][shape[2]][shape[0]];
            for(int i=0;i<volume.length;i++){
                for(int j=0;j<volume[i].length;j++){
                    for(int k=0;k<volume[i][j].length;k++){
                        volume[i][j][k]=rand.nextGaussian()/Math.sqrt(shape[3]);
                    }
                }
            }
            mask[l]=new Volume(volume);
            bias[l]=0;
        }
    }

    @Override
    public void update(ArrayList<Tensor[]> gradient, double learningRate) {

    }
}
