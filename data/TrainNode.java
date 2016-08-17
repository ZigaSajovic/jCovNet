package data;

import tensors.Vector;

/**
 * Created by ziga on 30.7.2016.
 */
public class TrainNode {

    String id;
    Vector input;
    Vector output;

    public TrainNode(String id, Vector input, Vector output){
        this.id=id;
        this.input=input;
        this.output=output;
    }

    public Vector input(){
        return this.input;
    }

    public Vector output(){
        return this.output;
    }

    public String id(){
        return this.id;
    }
}
