package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Random r=new Random(Long.parseLong(args[0]));

        BufferedImage bi;//
        if(args[1].contains("http://")||args[1].contains("https://")) {//for example http://www.chetekwinterfest.org/webcam/b.jpg
            bi = ImageIO.read(new URL(args[1]));
        }else{
            bi = ImageIO.read(new File(args[1]));
        }
        String a=args[2];
        byte[] b=a.getBytes();
        String bits=toBitString(b);
        int loc=bits.length()-1;
        int col=0;
        //System.out.println(bi.getWidth()*bi.getHeight());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                if(r.nextBoolean()) {
                    Color temp = new Color(bi.getRGB(i, j));
                    if (loc < 0) {
                            if(temp.getRed()<255){
                                temp = new Color(temp.getRed() + 1, temp.getGreen(), temp.getBlue());
                            }else{
                                temp = new Color(temp.getRed() - 1, temp.getGreen(), temp.getBlue());
                            }
                            if(temp.getGreen()<255) {
                                temp = new Color(temp.getRed(), temp.getGreen() + 1, temp.getBlue());
                            }else{
                                temp = new Color(temp.getRed(), temp.getGreen() - 1, temp.getBlue());
                            }
                            if(temp.getBlue()<255) {
                                temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() + 1);
                            }else{
                                temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() - 1);
                            }
                        bi.setRGB(i, j, temp.getRGB());
                        ImageIO.write(bi, "png", new File(args[3]));
                        return;
                    }
                    if (col == 0) {
                        if(temp.getRed()<255){
                            temp = new Color(temp.getRed() + 1 * Integer.parseInt(bits.charAt(loc) + ""), temp.getGreen(), temp.getBlue());
                        }else{
                            temp = new Color(temp.getRed() - 1 * Integer.parseInt(bits.charAt(loc) + ""), temp.getGreen(), temp.getBlue());
                        }
                    }
                    if (col == 1) {
                        if(temp.getGreen()<255) {
                            temp = new Color(temp.getRed(), temp.getGreen() + 1 * Integer.parseInt(bits.charAt(loc) + ""), temp.getBlue());
                        }else{
                            temp = new Color(temp.getRed(), temp.getGreen() - 1 * Integer.parseInt(bits.charAt(loc) + ""), temp.getBlue());
                        }
                    }
                    if (col == 2) {
                        if(temp.getBlue()<255) {
                            temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() + 1 * Integer.parseInt(bits.charAt(loc) + ""));
                        }else{
                            temp = new Color(temp.getRed(), temp.getGreen(), temp.getBlue() - 1 * Integer.parseInt(bits.charAt(loc) + ""));
                        }
                    }
                    bi.setRGB(i, j, temp.getRGB());
                    loc--;
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
