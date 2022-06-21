package smutil;


/**
 * default
 * @date 2020/11/19 15:38
 * @author yunlongn
 * @version 2.0
 */
public class SM4_Context {
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4_Context() {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
