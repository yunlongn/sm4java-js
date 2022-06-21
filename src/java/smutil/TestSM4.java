package smutil;

/**
 * TestSM4
 * @author yunlong.li
 */
public class TestSM4 {
    public static void main(String[] args) {
        SM4Utils sm4 = new SM4Utils("mLGFIwf7fnYckSqL", "mLGFIwf7fnYckSqL");

        String data = sm4.encryptDataEcb("123456");
        System.out.println(sm4.decryptDataEcb(data).trim());
        String cbcData = sm4.encryptDataCbc("123456-99");
        System.out.println(sm4.decryptDataCBC(cbcData));


    }
}
