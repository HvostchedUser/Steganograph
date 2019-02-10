import java.math.BigInteger;
import java.nio.ByteBuffer;

public class check {

    public static void main(String[] args) {

        String a="typeyourtexthere";
        byte[] b=a.getBytes();
        System.out.println(new String(toByteArray(toBitString(b))));
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
