package test.vanswitcher.Secret;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Ignore this class
public class IlIlIlIllIlIlIlIlIllIl {


    public static byte[] IllIlIlIllIl(String st, int n) {
        byte[] IllIlIlIllIl = st.getBytes();
        for (int IllllIlIlIlI = 0; IllllIlIlIlI < st.length(); IllllIlIlIlI++) {
            IllIlIlIllIl[IllllIlIlIlI] ^= n;
        }
        return IllIlIlIllIl;
    }

    public static String IllllIlIlIlI(String IllllIlIlIlI) throws NoSuchAlgorithmException {
        return IlIlIIllIllI(IllllIlIlIlI.getBytes());
    }

    public static String IlIlIIllIllI(byte[] IllllIlIlIlI) throws NoSuchAlgorithmException {
        MessageDigest IllIllIlIlIl = MessageDigest.getInstance("SHA-256");
        byte[] IllIlIlIllIl = IllIllIlIlIl.digest(IllllIlIlIlI);
        String IlllIlIIIlIl = String.format("%032x", new BigInteger(1, IllIlIlIllIl));
        return IlllIlIIIlIl;
    }
}
