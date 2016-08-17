package tests;

import activations.Activation;
import activations.Sigmoid;
import costs.Cost;
import costs.CrossEntropy;
import data.TrainNode;
import layers.FullyConnected;
import layers.PlaceHolder;
import data.GenData;
import tensors.Tensor;
import tensors.Vector;
import tensors.Weights;
import net.Net;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziga on 25.7.2016.
 * This class checks the algorithmically computed gradient against
 * numerically computed gradient
 */
public class GradientCheck {

    static double error=0.000001;

    public static void fullyConnected(int netDepth, Activation activation, Cost cost){
        Vector input=new Vector(GenData.vec(netDepth+1,1,netDepth+1));
        Vector output=new Vector(new double[]{Math.random()});
        Net net=new Net();
        net.add(new PlaceHolder());
        for(int i=netDepth;i>0;i--){
            net.add(new FullyConnected(new int[]{i+1,i},activation));
        }
        net.init();
        System.out.println("Beginning algorithmic gradient computation");
        double timeAlgStart=System.nanoTime();
        ArrayList<Tensor[]> grads=net.gradient(cost,new Vector[]{input,output});
        double timeAlgEnd=System.nanoTime();
        System.out.println("Algorithmic gradient computation completed");
        System.out.println("Duration: "+(timeAlgEnd-timeAlgStart)/Math.pow(10,9)+" seconds\n\n");
        System.out.println("Begin numeric gradient computation and check for correctness");
        double timeNumStart=System.nanoTime();
        for(int i=1;i<net.layers.size();i++){
            System.out.println("Layer: "+i);
            FullyConnected layer=(FullyConnected) net.layers.get(i);
            boolean pass=true;
            for(int k=0;k<layer.weights.shape[0];k++){
                for(int l=0;l<layer.weights.shape[1];l++){
                    double h=0.000001;
                    double f1=cost.f(net.eval(input),output);
                    layer.weights.data[k][l]+=h;
                    double f2=cost.f(net.eval(input),output);
                    double g=(f2-f1)/h;
                    if(Math.abs(g-((Weights)grads.get(i-1)[0]).data[k][l])>error){
                        pass=false;
                        System.out.println("Error for weight ("+k+", "+l+")");
                    }
                    layer.weights.data[k][l]-=h;
                }
            }
            for(int k=0;k<layer.bias.size();k++) {
                double h = 0.000001;
                double f1 = cost.f(net.eval(input), output);
                layer.bias.data[k] += h;
                double f2 = cost.f(net.eval(input), output);
                double g = (f2 - f1) / h;
                if (Math.abs(g -((Vector) grads.get(i-1)[1]).data[k])>error) {
                    pass=false;
                    System.out.println("Error for bias ("+k+")");
                }
                layer.bias.data[k]-=h;
            }
            if(pass){
                System.out.println("Gradient Check PASSED");
            }
            else{
            System.out.println("Gradient Check FAILED");
            }
        }
        double timeNumEnd=System.nanoTime();
        System.out.println("Numeric gradient computation completed");
        System.out.println("Duration: "+(timeNumEnd-timeNumStart)/Math.pow(10,9)+" seconds\n\n");
        System.out.println("\nTest completed");
    }

    public static void fullyConnectedBatch(int netDepth, int batchSize, Activation activation, Cost cost){
        List<TrainNode> data=GenData.trainVecData(0,1,netDepth+1,0,1,1,batchSize);
        Net net=new Net();
        net.add(new PlaceHolder());
        for(int i=netDepth;i>0;i--){
            net.add(new FullyConnected(new int[]{i+1,i},activation));
        }
        net.init();
        System.out.println("Beginning algorithmic gradient computation");
        double timeAlgStart=System.nanoTime();
        ArrayList<ArrayList<Tensor[]>> grads=net.gradient(cost,data);
        double timeAlgEnd=System.nanoTime();
        System.out.println("Algorithmic gradient computation completed");
        System.out.println("Duration: "+(timeAlgEnd-timeAlgStart)/Math.pow(10,9)+" seconds\n\n");
        System.out.println("Begin numeric gradient computation and check for correctness");
        double timeNumStart=System.nanoTime();
        for(int b=0;b<data.size();b++){
            System.out.println("\nBatch: "+b);
            for(int i=1;i<net.layers.size();i++){
                if(i==4)continue;
                System.out.println("Layer: "+i);
                FullyConnected layer=(FullyConnected) net.layers.get(i);
                boolean pass=true;
                for(int k=0;k<layer.weights.shape[0];k++){
                    for(int l=0;l<layer.weights.shape[1];l++){
                        double h=0.000001;
                        double f1=cost.f(net.eval(data.get(b).input()),data.get(b).output());
                        layer.weights.data[k][l]+=h;
                        double f2=cost.f(net.eval(data.get(b).input()),data.get(b).output());
                        double g=(f2-f1)/h;
                        if(Math.abs(g-((Weights)grads.get(i-1).get(b)[0]).data[k][l])>error){
                            pass=false;
                            System.out.println("Error for weight ("+k+", "+l+")");
                        }
                        layer.weights.data[k][l]-=h;
                    }
                }
                for(int k=0;k<layer.bias.size();k++) {
                    double h = 0.000001;
                    double f1 = cost.f(net.eval(data.get(b).input()),data.get(b).output());
                    layer.bias.data[k] += h;
                    double f2 = cost.f(net.eval(data.get(b).input()),data.get(b).output());
                    double g = (f2 - f1) / h;
                    if (Math.abs(g -((Vector) grads.get(i-1).get(b)[1]).data[k])>error) {
                        pass=false;
                        System.out.println("Error for bias ("+k+")");
                    }
                    layer.bias.data[k]-=h;
                }
                if(pass){
                    System.out.println("Gradient Check PASSED");
                }
                else{
                    System.out.println("Gradient Check FAILED");
                }
            }
        }
        double timeNumEnd=System.nanoTime();
        System.out.println("\nNumeric gradient computation completed");
        System.out.println("Duration: "+(timeNumEnd-timeNumStart)/Math.pow(10,9)+" seconds\n\n");
        System.out.println("\nTest completed");
    }

    public static void main(String[] args){
        fullyConnectedBatch(10,10,new Sigmoid(),new CrossEntropy());
    }
}
