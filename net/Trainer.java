package net;

import costs.Cost;
import data.TrainNode;
import tensors.Vector;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by ziga on 28.7.2016.
 */
public class Trainer {

    public static boolean printProgress=true;
    public static int miniBatchSize =10;
    public static int progressRate=100;
    public static double allowedError=0.01;
    public static DecimalFormat df = new DecimalFormat("#.##");

    public static void train(Net net, List<TrainNode> data, Cost cost, int numOfSteps, double learningRate){
        System.out.println("Beginning training for "+numOfSteps+" steps on "+data.size()+" training samples.");
        Collections.shuffle(data);
        double startTime=System.nanoTime();
        for(int k=0;k<numOfSteps;k++){
            for(int i=0;i<data.size();i+= miniBatchSize){
                net.trainStep(cost,data,learningRate,i,i+ miniBatchSize >data.size()?data.size():i+ miniBatchSize);
            }
            if(printProgress&&k>0&&k%progressRate==0){
                System.out.println("Step "+k+" -> Estimated time remaining: "+df.format((numOfSteps-k)*((System.nanoTime()-startTime)/(1+k))/Math.pow(10,9))+" seconds.");
            }
        }
        System.out.println("Completed "+numOfSteps+" training steps.");
        System.out.println("Duration of training: "+df.format((System.nanoTime()-startTime)/Math.pow(10,9))+" seconds.\n");
    }

    public static void checkError(Net net,List<TrainNode>data){
        int correctCount=0;
        System.out.println("Error computation:");
        for(int i=0;i<data.size();i++){
            Vector eval=net.eval(data.get(i).input());
            System.out.println("\nSample: "+data.get(i).id());
            int tmpCount=0;
            for(int j=0;j<data.get(i).output().size();j++){
                if(Math.abs(data.get(i).output().data[j]-eval.data[j])<allowedError){
                    tmpCount++;
                }
                if(printProgress){
                    System.out.println(j+": Correct: "+df.format(data.get(i).output().data[j])+" | Predicted: "+df.format(eval.data[j])+" | Error: "+df.format(Math.abs(data.get(i).output().data[j]-eval.data[j])));
                }
            }
            if(tmpCount==data.get(i).output().size()){
                correctCount++;
            }
        }
        System.out.println("\nCorrectly predicted "+df.format(((correctCount/(double)data.size()))*100)+"% of samples with allowed error of "+allowedError+".");
    }

    public static void checkClassError(Net net,List<TrainNode>data){
        int correctCount=0;
        System.out.println("Error computation:");
        for(int i=0;i<data.size();i++){
            Vector eval=net.eval(data.get(i).input());
            if(printProgress){
                System.out.println("\nSample: "+data.get(i).id());
            }
            int correct=-1;
            for(int j=0;j<data.get(i).output().size();j++){
                if(data.get(i).output().data[j]==1){
                    correct=j;
                }
            }
            double max=-1;
            int index=-1;
            for(int j=0;j<eval.size();j++){
                if(eval.data[j]>max){
                    max=eval.data[j];
                    index=j;
                }
            }
            if(printProgress){
                System.out.print("Correct class: "+correct+" | Predicted class: "+index+" with probability: "+df.format(max)+" | ");
            }

            if(correct==index){
                correctCount++;
                if(printProgress){
                    System.out.println("CORRECT");
                }
            }
            else if(printProgress){
                System.out.println("WRONG");
            }

        }
        System.out.println("\nCorrectly predicted "+df.format(((correctCount/(double)data.size()))*100)+"% of samples.");
    }
}