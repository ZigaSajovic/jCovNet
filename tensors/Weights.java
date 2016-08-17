package tensors;

/**
 * Created by ziga on 24.7.2016.
 */
public class Weights extends Tensor{

    public double[][] data;

    public Weights(int sizeX, int sizeY){
        this.shape=new int[]{sizeX,sizeY};
        this.data=new double[sizeX][sizeY];
    }

    public Weights(double[][] data){
        this.shape=new int[]{data.length,data[0].length};
        this.data=data;
    }

    @Override
    public void print(){
        for(int i=0;i<this.shape[0];i++){
            for(int j=0;j<this.shape[1];j++){
                System.out.print(this.data[i][j]+" ");
            }
            System.out.println();
        }
    }

    @Override
    public Weights copy() {
        double[][] output=new double[this.shape[0]][this.shape[1]];
        for(int i=0;i<this.shape[0];i++){
            for(int j=0;j<this.shape[1];j++){
                output[i][j]=this.data[i][j];
            }
        }
        return new Weights(output);
    }

    @Override
    public void update() {

    }

}
