package smutil;


/**
 * default
 * @date 2020/11/19 15:38
 * @author yunlongn
 * @version 2.0
 */
public class StringHexTool {

    private static final String AZ_FINAL = "0123456789ABCDEF";
    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str 输入字符
     * @return 16进制字符
     */
    public static String str2HexStr(String str) {
        char[] chars = AZ_FINAL.toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (byte b : bs) {
            bit = (b & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = b & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr 输入字符
     * @return 字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = AZ_FINAL;
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static void main(String[] args) {
        //	01	23	45	67	89	AB	CD	EF	FE	DC	BA	98	76	54	32	10
        String strHex = "0123456789ABCDEFFEDCBA9876543210";
        System.out.println(StringHexTool.hexStr2Str(strHex));
        String str = StringHexTool.hexStr2Str(strHex);
        System.out.println(StringHexTool.str2HexStr(str));

    }
}
