package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Random;

public class decode {
    public static void main(String[] args) throws IOException {
        Random r=new Random(Long.parseLong(args[0]));


        BufferedImage bi;


        if(args[1].contains("http://")||args[1].contains("https://")) {
            bi = ImageIO.read(new URL(args[1]));
        }else{
            bi = ImageIO.read(new File(args[1]));
        }

        BufferedImage in;

        if(args[2].contains("http://")||args[2].contains("https://")) {
            in = ImageIO.read(new URL(args[2]));
        }else{
            in = ImageIO.read(new File(args[2]));
        }
        String bits="";
        int col=0;
        //System.out.println(bi.getWidth()*bi.getHeight());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                if(r.nextBoolean()) {
                    Color x = new Color(bi.getRGB(i, j));
                    Color y = new Color(in.getRGB(i, j));
                    if((Math.abs(x.getRed() - y.getRed()) > 0)&&(Math.abs(x.getGreen() - y.getGreen()) > 0)&&(Math.abs(x.getBlue() - y.getBlue()) > 0)){

                        System.out.println(new String(toByteArray(bits)));
                        return;
                    }
                    if (col == 0) {
                        if (Math.abs(x.getRed() - y.getRed()) > 0) {
                            bits = "1" + bits;
                        } else {
                            bits = "0" + bits;
                        }
                    }
                    if (col == 1) {
                        if (Math.abs(x.getGreen() - y.getGreen()) > 0) {
                            bits = "1" + bits;
                        } else {
                            bits = "0" + bits;
                        }
                    }
                    if (col == 2) {
                        if (Math.abs(x.getBlue() - y.getBlue()) > 0) {
                            bits = "1" + bits;
                        } else {
                            bits = "0" + bits;
                        }
                    }
                    //System.out.println(new String(toByteArray(bits)));
                    col=r.nextInt(3);
                    col = col % 3;
                }
            }
        }
    }
    public static String toBitString(final byte[] b) {
        final char[] bits = new char[8 * b.length];
        for(int i = 0; i < b.length; i++) {
            final byte byteval = b[i];
            int bytei = i << 3;
            int mask = 0x1;
            for(int j = 7; j >= 0; j--) {
                final int bitval = byteval & mask;
                if(bitval == 0) {
                    bits[bytei + j] = '0';
                } else {
                    bits[bytei + j] = '1';
                }
                mask <<= 1;
            }
        }
        return String.valueOf(bits);
    }
    public static byte[] toByteArray(String b){
        byte[] bval = new BigInteger(b, 2).toByteArray();
        return bval;
    }
}
