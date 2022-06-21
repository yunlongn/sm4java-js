package smutil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * default
 * @date 2020/11/19 15:38
 * @author yunlongn
 * @version 2.0
 */
public class SM4Utils {
    private final Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
    private final String sKy;
    private final String sIv;

    private boolean hexString = false;

    public SM4Utils(String sKy, String sIv) {
        this.sKy = sKy;
        this.sIv = sIv;
    }

    public String encryptDataEcb(String plainText) {
        try{
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            keyBytes = sKy.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes(StandardCharsets.UTF_8));
            String cText = new BASE64Encoder().encode(encrypted);
            if (cText != null && cText.trim().length() > 0)
            {
                Matcher m = pattern.matcher(cText);
                cText = m.replaceAll("");
            }
            return cText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataEcb(String cText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = false;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            keyBytes = sKy.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cText));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }

    public String decryptDataEcbOpenPadding(String cText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            keyBytes = sKy.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cText));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }

    public String encryptDataCbc(String plainText){
        try{
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;
            byte[] keyBytes;
            byte[] ivBytes;
            keyBytes = sKy.getBytes();
            ivBytes = sIv.getBytes();
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes(StandardCharsets.UTF_8));
            String cText = new BASE64Encoder().encode(encrypted);
            if (cText != null && cText.trim().length() > 0) {
                Matcher m = pattern.matcher(cText);
                cText = m.replaceAll("");
            }
            return cText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptDataCBC(String cText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = Util.hexStringToBytes(sKy);
                ivBytes = Util.hexStringToBytes(sIv);
            } else {
                keyBytes = sKy.getBytes();
                ivBytes = sIv.getBytes();
            }
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cText));
            return new String(decrypted, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
    }
    public static void main(String[] args){

    }
}
