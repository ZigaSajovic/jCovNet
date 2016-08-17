package tensors;

import activations.Activation;

/**
 * Created by ziga on 23.7.2016.
 */
public class Vector extends Tensor{

    public double[] data;

    public Vector(int size){
        this.shape=new int[]{size};
        this.data=new double[size];
    }

    public Vector(double[] data){
        this.shape=new int[]{data.length};
        this.data=data;
    }

    public int size(){
        return shape[0];
    }

    public double get(int index){
        if(index<shape[0]){
            return data[index];
        }
        else{
            return -999.9999;
        }
    }

    public void set(int setAt, double toSet){
        if(setAt<shape[0]){
            this.data[setAt]=toSet;
        }
        else{

        }
    }

    public void set(Vector vec){
        this.shape=vec.shape;
        this.data=vec.data;
    }

    public void forAllF(Activation activation){
        for(int i=0;i<this.shape[0];i++){
            data[i]=activation.f(data[i]);
        }
    }

    public Vector forAllFCopy(Activation activation){
        Vector out=new Vector(this.shape[0]);
        for(int i=0;i<this.shape[0];i++){
            out.set(i,activation.f(data[i]));
        }
        return out;
    }

    public void forAllDf(Activation activation){
        for(int i=0;i<this.shape[0];i++){
            data[i]=activation.df(data[i]);
        }
    }

    public Vector forAllDfCopy(Activation activation){
        Vector out=new Vector(this.shape[0]);
        for(int i=0;i<this.shape[0];i++){
            out.set(i,activation.df(data[i]));
        }
        return out;
    }

    @Override
    public void print(){
        for(int i=0;i<this.shape[0];i++){
            System.out.println(i+": "+data[i]);
        }
    }

    @Override
    public Vector copy(){
        Vector out=new Vector(this.shape[0]);
        for(int i=0;i<this.shape[0];i++){
            out.set(i,data[i]);
        }
        return out;
    }

    @Override
    public void update() {

    }

    public void octaveProd(Vector vec){
        for(int i=0;i<this.shape[0];i++){
            this.data[i]=this.data[i]*vec.data[i];
        }
    }

    public Volume toVolume(){
        double[][][] out=new double[1][1][data.length];
        out[0][0]=data;
        return new Volume(out);
    }
}
