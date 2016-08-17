package data;

import tensors.Vector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ziga on 29.7.2016.
 *
 * Class carries out partitioning of data into training, validation and test sets.
 * It randomizes the order, so the initial order does not matter.
 */
public class Data {


    List<TrainNode> trainingSet;
    List<TrainNode> testSet;

    public Data(String[] path, int inputLength){
        loadClassVecData(path,inputLength);
    }

    public int size(){
        return trainingSet.size()+testSet.size();
    }

    public List<TrainNode> trainingSet(){
        return trainingSet;
    }

    public List<TrainNode> testSet(){
        return testSet;
    }

    public void loadClassVecData(String[] path, int inputLenght){
        List<TrainNode> tmp=new ArrayList<TrainNode>();
        for(int i=0;i<path.length;i++){
            final File dir = new File(path[i]);
            if (dir.isDirectory()) for (final File f : dir.listFiles(Image.IMAGE_FILTER)) {
                BufferedImage img = Image.loadAsGrayScale(f);
                tmp.add(new TrainNode(f.getName(),Image.imgToVec(img), binaryVecClassifier(i,path.length)));
            }
        }

        int i=(int)(tmp.size()*0.7);
        filter(tmp,inputLenght);
        Collections.shuffle(tmp);;
        trainingSet =tmp.subList(0,i);
        testSet =tmp.subList(i,tmp.size());
    }

    public Vector binaryVecClassifier(int classNumber, int vectorLength){
        Vector out=new Vector(vectorLength);
        out.set(classNumber,1);
        return out;
    }

    public void filter(List<TrainNode>data, int inputLength){
        int n=data.size();
        for(int i=0;i<n;i++){
            if(data.get(i).input.size()!=inputLength){
                data.remove(i);
                n--;
                i--;
            }
        }
    }

}
