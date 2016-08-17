package tensors;

import activations.Activation;

/**
 * Created by ziga on 28.7.2016.
 */
public class Volume extends Tensor{

    double[][][] volume;

    public Volume(double[][][] volume){
        this.shape=new int[]{volume.length,volume[0].length,volume[0][0].length};
        this.volume=volume;
    }

    @Override
    public void print() {
        for(int i=0;i<volume.length;i++){
            System.out.println("Depth: "+i);
            for(int j=0;j<volume[i].length;j++){
                for(int k=0;k<volume[i][j].length;k++){
                    System.out.print(volume[i][j][k]);
                    if(k!=volume[i][j].length-1){
                        System.out.print(" , ");
                    }
                    else{
                        System.out.println();
                    }
                }
            }
        }
    }

    @Override
    public Tensor copy() {
        return null;
    }

    @Override
    public void update() {

    }

    public void forAllF(Activation activation){
        for(int i=0;i<volume.length;i++){
            for(int j=0;j<volume[i].length;j++){
                for(int k=0;k<volume[i][j].length;k++){
                    volume[i][j][k]=activation.f(volume[i][j][k]);
                }
            }
        }
    }

    public void forAllDf(Activation activation){
        for(int i=0;i<volume.length;i++){
            for(int j=0;j<volume[i].length;j++){
                for(int k=0;k<volume[i][j].length;k++){
                    volume[i][j][k]=activation.df(volume[i][j][k]);
                }
            }
        }
    }
}
