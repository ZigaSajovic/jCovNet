package data;

import tensors.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


/**
 * Created by ziga on 29.7.2016.
 */
public class Image {

    static final String[] EXTENSIONS = new String[]{"jpg","gif", "png", "bmp"};
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };

    public static BufferedImage loadAsGrayScale(String path){
        return loadAsGrayScale(new File(path));
    }

    public static BufferedImage loadAsGrayScale(File f){
        BufferedImage img = null;

        try {
            img = ImageIO.read(f);

            BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = image.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            return image;
        } catch (final IOException e) {
        }
        return null;
    }

    public static Vector imgToVec(BufferedImage img){
        double[] out=new double[img.getWidth()*img.getHeight()];
        for(int i=0;i<img.getHeight();i++){
            for(int j=0;j<img.getWidth();j++){
                out[i*img.getWidth()+j]=(double)img.getRGB(j,i);
            }
        }
        return new Vector(out);
    }

    public static void save(BufferedImage img, String savePath, String format){
        File outputfile = new File(savePath);
        try {
            ImageIO.write(img, format, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void display(BufferedImage img){
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        BufferedImage img= loadAsGrayScale(new File("/home/ziga/Desktop/photos/Others/1.jpg"));
        display(img);
    }
}
