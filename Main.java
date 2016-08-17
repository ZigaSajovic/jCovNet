/**
 * Created by ziga on 24.7.2016.
 *
 */
public class Main {


    public static void main(String[] args){


    }


    /*public static void main(String[] args){
        Vector a=new Vector(new double[]{2,4});
        Weights b=new Weights(new double[][]{{1,2},{3,4}});

        FullyConnected l=new FullyConnected(b,a,new Sigmoid());

        Vector aa=new Vector(new double[]{2});
        Weights ba=new Weights(new double[][]{{1,2}});

        FullyConnected ll=new FullyConnected(ba,aa,new Identity());

        Vector i=new Vector(new double[]{1,1});
        Vector o=new Vector(new double[]{3});

        Net n=new Net();
        n.add(new PlaceHolder());
        n.add(l);
        n.add(ll);

        n.eval(i).print();

        //n.gradient(new MeanSquare(),new Vector[]{i,o});
    }*/
}
