package net;

import costs.Cost;
import data.TrainNode;
import layers.Layer;
import tensors.Tensor;
import tensors.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ziga on 24.7.2016.
 */
public class Net implements java.io.Serializable{

    public List<Layer> layers;

    public Net(){
        layers=new ArrayList<Layer>();
    }

    /*
    Adds a layer to the net
     */
    public void add(Layer layer){
        layers.add(layer);
    }

    /*
    Evaluates the net for the current input
     */
    public Vector eval(Tensor input){
        Tensor output=input;
        for(int i=1;i<layers.size();i++){
            output=layers.get(i).eval(output);
        }
        return (Vector)output;
    }

    /*
    Initializes the net by initializing all layers
     */
    public void init(){
        for(int i=1;i<layers.size();i++){
            layers.get(i).initialize();
        }
    }

    /*
    Computes the gradient of a non-batch, meaning one input at a time
     */
    public ArrayList<Tensor[]> gradient(Cost cost, Vector[] data){
        List<Tensor[]> results=new ArrayList<Tensor[]>();
        results.add(new Tensor[]{data[1],data[0]});
        for(int i=1;i<layers.size();i++){
            results.add(layers.get(i).evalForGrad(results.get(i-1)[1]));
        }
        Tensor grad= cost.df((Vector)results.get(results.size()-1)[1],data[1]);
        ArrayList<Tensor[]> grads=new ArrayList<Tensor[]>();
        grads.add(new Tensor[]{((Vector)grad).copy()});
        for(int i=layers.size()-1;i>0;i--){
            grads.add(layers.get(i).grad(results.get(i),results.get(i-1),grad));
        }
        Collections.reverse(grads);
        return grads;
    }

    /*
    Computes the gradient of a batch of inputs, meaning many inputs at once
     */
    public ArrayList<ArrayList<Tensor[]>> gradient(Cost cost, List<TrainNode>data){
        ArrayList<ArrayList<Tensor[]>> batch=new ArrayList<ArrayList<Tensor[]>>();
        int[] index=new int[data.size()];
        for(int i=0;i<data.size();i++){
            ArrayList<Tensor[]> results=new ArrayList<Tensor[]>();
            results.add(new Tensor[]{null,data.get(i).input()});
            int k=1;
            for(int j=1;j<layers.size();j++){
                results.add(layers.get(j).evalForGrad(results.get(j-1)[1]));
                k++;
            }
            batch.add(results);
            index[i]=k;
        }

        ArrayList<ArrayList<Tensor[]>> grads=new ArrayList<ArrayList<Tensor[]>>();
        ArrayList<Tensor> grad=new ArrayList<Tensor>();
        for(int i=0;i<batch.size();i++){
            grad.add(cost.df((Vector)batch.get(i).get(index[i]-1)[1],data.get(i).output()));
        }
        for(int i=layers.size()-1;i>0;i--){
            ArrayList<Tensor[]>gradOne=new ArrayList<Tensor[]>();
            for(int j=0;j<batch.size();j++){
                gradOne.add(layers.get(i).grad(batch.get(j).get(i),batch.get(j).get(i-1),grad.get(j)));
            }
            grads.add(gradOne);
        }
        Collections.reverse(grads);
        return grads;
    }

    /*
    Performs one training step on the entire batch
     */
    public void trainStep(Cost cost, List<TrainNode>data, double learningRate){
        trainStep(cost,data, learningRate,0,data.size());
    }

    /*
    Performs one training step on a subset of the batch, meaning a mini-batch indexed by from-to
     */
    public void trainStep(Cost cost, List<TrainNode>data, double learningRate, int from, int to){
        ArrayList<ArrayList<Tensor[]>> batchResults=new ArrayList<ArrayList<Tensor[]>>();
        int[] index=new int[to-from];
        for(int i=0;i<to-from;i++){
            ArrayList<Tensor[]> results=new ArrayList<Tensor[]>();
            results.add(new Tensor[]{null,data.get(i+from).input()});
            for(int j=1;j<layers.size();j++){
                results.add(layers.get(j).evalForGrad(results.get(j-1)[1]));
            }
            batchResults.add(results);
            index[i]=layers.size();
        }

        ArrayList<Tensor> gradient=new ArrayList<Tensor>();
        for(int i=0;i<batchResults.size();i++){
            gradient.add(cost.df((Vector)batchResults.get(i).get(index[i]-1)[1],data.get(i+from).output()));
        }
        for(int i=layers.size()-1;i>0;i--){
            ArrayList<Tensor[]>grad=new ArrayList<Tensor[]>();
            for(int j=0;j<batchResults.size();j++){
                grad.add(layers.get(i).grad(batchResults.get(j).get(i),batchResults.get(j).get(i-1),gradient.get(j)));
            }
            layers.get(i).update(grad, learningRate);
        }
    }

}
