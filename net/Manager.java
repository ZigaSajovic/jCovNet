package net;

import java.io.*;

/**
 * Created by ziga on 29.7.2016.
 */
public class Manager {

    /*
    Saves the net to the appointed location
     */
    public static void save(Net net, String savePath){
        ObjectOutputStream out;

        try{
            File file = new File(savePath+".nNet");
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(net);


            out.flush();
            out.close();
            System.out.println("Net saved SUCCESSFULLY");
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error with specified file") ;
            ex.printStackTrace();
        }
        catch (IOException ex) {
            System.out.println("Error with I/O processes") ;
            ex.printStackTrace();
        }
    }

    /*
    Loads the net from the appointed location
     */
    public static Net load(String loadPath){
        Net out = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(loadPath+".nNet");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            out = (Net) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Net loaded SUCCESSFULLY");
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return out;
    }
}
