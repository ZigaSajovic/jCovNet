package data;

import tensors.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ziga on 25.7.2016.
 */
public class GenData {

    public static List<TrainNode> trainVecData(double inE, double inStd, int inputSize, double outE, double outStd, int outputSize, int batchSize){
        List<TrainNode> output=new ArrayList<TrainNode>();
        for(int i=0;i<batchSize;i++){
            output.add(new TrainNode(Integer.toString(i),new Vector(vec(inE,inStd,inputSize)),new Vector(vec(outE,outStd,outputSize))));
        }
        return output;
    }

    public static List<TrainNode> trainClassVecData(double inE, double inStd, int inputSize, int outputSize, int batchSize){
        List<TrainNode> output=new ArrayList<TrainNode>();
        for(int i=0;i<batchSize;i++){
            output.add(new TrainNode(Integer.toString(i),new Vector(vec(inE,inStd,inputSize)),new Vector(vecClass(outputSize))));
        }
        return output;
    }

    public static double[] vec(double E,double std, int size){
        Random rand=new Random(System.nanoTime());
        double[] output=new double[size];
        for(int i=0;i<size;i++){
            output[i]= rand.nextGaussian()*std+E;
        }
        return output;
    }

    public static double[] vecClass(int size){
        Random rand=new Random(System.nanoTime());
        double[] output=new double[size];
        output[rand.nextInt(size)]=1;
        return output;
    }

    public static Vector[] randomVecBatch(double E, double std, int batchSize, int size){
        Vector[] output=new Vector[batchSize];
        for(int i=0;i<batchSize;i++){
            output[i]=new Vector(vec(E,std,size));
        }
        return output;
    }

    public static Vector[] randomClassVecBatch(int batchSize, int size){
        Vector[] output=new Vector[batchSize];
        for(int i=0;i<batchSize;i++){
            output[i]=new Vector(vecClass(size));
        }
        return output;
    }
}
