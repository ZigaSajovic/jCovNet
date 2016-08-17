package tests;

import activations.Activation;
import activations.Identity;
import activations.Sigmoid;
import costs.Cost;
import costs.CrossEntropy;
import data.Data;
import data.Image;
import layers.FullyConnected;
import layers.PlaceHolder;
import layers.Softmax;
import net.Manager;
import net.Net;
import net.Trainer;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ziga on 27.7.2016.
 */
public class TrainCheck {

    public static DecimalFormat df = new DecimalFormat("#.####");

    public static void fullyConnectedBatch(int numOfSteps, double learningRate, int batchSize, Activation activation, Cost cost){

        //List<TrainNode> data=GenData.trainClassVecData(0,1,56*56,2,batchSize);
        Net net=new Net();

        net.add(new PlaceHolder());
        net.add(new FullyConnected(new int[]{56*56,400},activation));
        net.add(new FullyConnected(new int[]{400,200},activation));
        net.add(new FullyConnected(new int[]{200,100},activation));
        net.add(new FullyConnected(new int[]{100,100},activation));
        net.add(new FullyConnected(new int[]{100,2},new Identity()));
        net.add(new Softmax());
        net.init();

        Data data=new Data(new String[]{"/home/ziga/Desktop/Test/Class0","/home/ziga/Desktop/Test/Class1"},56*56);

        Trainer.progressRate=10;
        Trainer.train(net,data.trainingSet(),cost,numOfSteps,learningRate);

        System.out.println("Check class Error on training set of size: "+data.trainingSet().size()+"/"+data.size());
        Trainer.checkClassError(net,data.trainingSet());

        System.out.println("\n\nCheck class Error on test set of size: "+data.testSet().size()+"/"+data.size());
        Trainer.checkClassError(net,data.testSet());

        Manager.save(net,"testNet");

    }

    public static void main(String[] args){
        fullyConnectedBatch(100,1,32*2,new Sigmoid(), new CrossEntropy());
    }
}