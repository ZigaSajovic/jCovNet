package layers;

import activations.Activation;
import tensors.Tensor;
import tensors.Vector;
import tensors.Weights;

/**
 * Created by ziga on 24.7.2016.
 */
public class FullyConnected extends BaseLayer {

    public FullyConnected(Weights weights, Vector bias, Activation activation) {
        super(weights,bias,activation);
    }

    public FullyConnected(int[] shape,Activation activation){
        super(shape,activation);
    }

    @Override
    public Vector eval(Tensor prevResult) {
        Vector out=matMulLayer((Vector)prevResult);
        out.forAllF(activation);
        return out;
    }

    @Override
    public Vector[] evalForGrad(Tensor prevResult) {
        Vector matMulLayer=matMulLayer((Vector)prevResult);
        Vector out=matMulLayer.forAllFCopy(activation);
        return new Vector[]{matMulLayer,out};
    }

    @Override
    /*
    Derivation of gradients:
    Let W^i_j denote the weight matrix and L(n) the n-th (this) layer with x(n)^j inputs
    L(n)=W^i_j*x(n)^j
    =>
    dC/dx(n)^j=dC/dx(n-1)^i*dL(n)/dx(n)^j
    =>
    dC/dx(n)^j=dL(n-1)/dx(n-1)^i*W^i_j
    -----------------------------------
    dC/dW^i_j=dL(n-1)/dx(n-1)^i*dL(n)/dx(n)^j
    =>
    dC/dW^i_j=dL(n-1)/dx(n-1)^i*x(n)^j
    -----------------------------------
    dC/dBias(n)^i=dL(n-1)/dx(n-1)^i
     */
    public Tensor[] grad(Tensor[] currentResults, Tensor[] prevResults, Tensor currentGrad) {
        ((Vector[])currentResults)[0].forAllDf(activation);
        ((Vector)currentGrad).octaveProd(((Vector[])currentResults)[0]);
        Vector dBias=((Vector)currentGrad).copy();
        Weights dW=dLdW(((Vector)(prevResults)[1]),((Vector)currentGrad));
        matMulTrans(((Vector)currentGrad));
        return new Tensor[]{dW,dBias};
    }

    /*
    Performs layers operations:
    out=W^i_j*x^j+b^j
     */

    private Vector matMulLayer(Vector prevResult){
        Vector out=new Vector(this.shape[1]);
        for(int i=0;i<this.shape[1];i++){
            double sum=0;
            for(int j=0;j<this.shape[0];j++){
                sum+=this.weights.data[i][j]*prevResult.data[j];
            }
            sum+=this.bias.data[i];
            out.set(i,sum);
        }
        return out;
    }

    /*
    Performs multiplication of a row vector with a matrix
    (note reversed index in x)
    y=W^i_jx_i
     */

    private void matMulTrans(Vector currentGrad){
        double[] tmpGrad=new double[this.shape[0]];
        for(int i=0;i<this.shape[0];i++){
            double sum=0;
            for(int j=0;j<this.shape[1];j++){
                sum+=currentGrad.data[j]*weights.data[j][i];
            }
            tmpGrad[i]=sum;
        }
        currentGrad.shape=new int[]{this.shape[0]};
        currentGrad.data=tmpGrad;
    }

    /*
    Computes derivative of the current layer, with respect to the weights
    It formats the output, so the corresponding indices are summed
    at the update stage
     */

    private Weights dLdW(Vector prevOutput, Vector currentGrad){
        double[][] output=new double[weights.shape[0]][weights.shape[1]];
        for(int i=0;i<weights.shape[0];i++){
            for(int j=0;j<weights.shape[1];j++){
                output[i][j]=prevOutput.get(j)*currentGrad.get(i);
            }
        }
        return new Weights(output);
    }
}
