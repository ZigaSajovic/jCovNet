package tensors;

/**
 * Created by ziga on 25.7.2016.
 */
public abstract class Tensor implements java.io.Serializable{
    public int[] shape;

    public abstract void print();
    public abstract Tensor copy();
    public abstract void update();
}
