package smutil;


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * default
 * @date 2020/11/19 15:38
 * @author yunlongn
 * @version 2.0
 */
public class NumberTool {
    private NumberTool(){}

	/**
	 * 整形转换成网络传输的字节流（字节数组）型数据
	 * 
	 * @param num
	 *            一个整型数据
	 * @return 4个字节的自己数组
	 */
	public static byte[] intToBytes(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (0xff & (num >> 0));
		bytes[1] = (byte) (0xff & (num >> 8));
		bytes[2] = (byte) (0xff & (num >> 16));
		bytes[3] = (byte) (0xff & (num >> 24));
		return bytes;
	}


	/**
	 * 大数字转换字节流（字节数组）型数据
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] byteConvert32Bytes(BigInteger n) {
		byte[] tmpd = (byte[]) null;
		if (n == null) {
			return null;
		}

		if (n.toByteArray().length == 33) {
			tmpd = new byte[32];
			System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
		} else if (n.toByteArray().length == 32) {
			tmpd = n.toByteArray();
		} else {
			tmpd = new byte[32];
			for (int i = 0; i < 32 - n.toByteArray().length; i++) {
				tmpd[i] = 0;
			}
			System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
		}
		return tmpd;
	}

	/**
	 * 换字节流（字节数组）型数据转大数字
	 * 
	 * @param b
	 * @return
	 */
	public static BigInteger byteConvertInteger(byte[] b) {
		if (b[0] < 0) {
			byte[] temp = new byte[b.length + 1];
			temp[0] = 0;
			System.arraycopy(b, 0, temp, 1, b.length);
			return new BigInteger(temp);
		}
		return new BigInteger(b);
	}

	/**
	 * 根据字节数组获得值(十六进制数字)
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getHexString(byte[] bytes) {
		return getHexString(bytes, true);
	}

	/**
	 * 根据字节数组获得值(十六进制数字)
	 * 
	 * @param bytes
	 * @param upperCase
	 * @return
	 */
	public static String getHexString(byte[] bytes, boolean upperCase) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			ret.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return upperCase ? ret.toString().toUpperCase() : ret.toString();
	}

	/**
	 * 打印十六进制字符串
	 * 
	 * @param bytes
	 */
	public static void printHexString(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print("0x" + hex.toUpperCase() + ",");
		}
		System.out.println("");
	}

	/**
	 * 16进制字符串转byte
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || "".equals(hexString)) {
			return null;
		}

		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 用于建立十六进制字符的输出的小写字符数组
	 */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 用于建立十六进制字符的输出的大写字符数组
	 */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	public static final String[] BIN_String = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
			"1001", "1010", "1011", "1100", "1101", "1110", "1111" };

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data
	 *            byte[]
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制char[]
	 */
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符数组
	 *
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            用于控制输出的char[]
	 * @return 十六进制char[]
	 */
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		return encodeHexString(data, toDigits).toCharArray();
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data
	 *            byte[]
	 * @return 十六进制String
	 */
	public static String encodeHexString(byte[] data) {
		return encodeHexString(data, true);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data
	 *            byte[]
	 * @param toLowerCase
	 *            <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	 * @return 十六进制String
	 */
	public static String encodeHexString(byte[] data, boolean toLowerCase) {
		return encodeHexString(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param data
	 *            byte[]
	 * @param toDigits
	 *            用于控制输出的char[]
	 * @return 十六进制String
	 */
	protected static String encodeHexString(byte[] data, char[] toDigits) {
		int l = data.length;
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < l; i++) {
			out.append(toDigits[(0xF0 & data[i]) >>> 4]);
			out.append(toDigits[0x0F & data[i]]);
		}
		return out.toString();
	}

	/**
	 * 将十六进制字符数组转换为字节数组
	 *
	 * @param data
	 *            十六进制char[]
	 * @return byte[]
	 * @throws RuntimeException
	 *             如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
	 */
	public static byte[] decodeHex(char[] data) {
		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
            for (int i = 0; i < len; i += 2) {
                out.write(((toDigit(data[i], i) << 4) | toDigit(data[i + 1], i + 1)) & 0xff);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
		return out.toByteArray();
	}

	/**
	 * 将十六进制字符转换成一个整数
	 *
	 * @param ch
	 *            十六进制char
	 * @param index
	 *            十六进制字符在字符数组中的位置
	 * @return 一个整数
	 * @throws RuntimeException
	 *             当ch不是一个合法的十六进制字符时，抛出运行时异常
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	/**
	 * 数字字符串转ASCII码字符串
	 * 
	 * @param String
	 *            字符串
	 * @return ASCII字符串
	 */
	public static String StringToAsciiString(String content) {
		StringBuilder result = new StringBuilder();
		for (char c : content.toCharArray()) {
			result.append(Integer.toHexString(c));
		}
		return result.toString();
	}

	/**
	 * 十六进制转字符串
	 * 
	 * @param hexString
	 *            十六进制字符串
	 * @param encodeType
	 *            编码类型4：Unicode，2：普通编码
	 * @return 字符串
	 */
	public static String hexStringToString(String hexString, int encodeType) {
		StringBuilder result = new StringBuilder();
		int max = hexString.length() / encodeType;
		for (int i = 0; i < max; i++) {
			char c = (char) hexStringToAlgorism(hexString.substring(i * encodeType, (i + 1) * encodeType));
			result.append(c);
		}
		return result.toString();
	}

	/**
	 * 十六进制字符串装十进制
	 * 
	 * @param hex
	 *            十六进制字符串
	 * @return 十进制数值
	 */
	public static int hexStringToAlgorism(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * 十六转二进制
	 * 
	 * @param hex
	 *            十六进制字符串
	 * @return 二进制字符串
	 */
	public static String hexStringToBinary(String hex) {
		hex = hex.toUpperCase();
		StringBuffer sb = new StringBuffer();
		for (char c : hex.toCharArray()) {
			sb.append(BIN_String[charToByte(c)]);
		}
		return sb.toString();
	}

	/**
	 * ASCII码字符串转数字字符串
	 * 
	 * @param String
	 *            ASCII字符串
	 * @return 字符串
	 */
	public static String AsciiStringToString(String content) {
		StringBuffer result = new StringBuffer();
		int length = content.length() / 2;
		for (int i = 0; i < length; i+=2) {
			String c = content.substring(i, i + 2);
			int a = hexStringToAlgorism(c);
			char b = (char) a;
			String d = String.valueOf(b);
			result.append(d);
		}
		return result.toString();
	}


	/**
	 * 字节数组转为普通字符串（ASCII对应的字符）
	 * 
	 * @param bytearray
	 *            byte[]
	 * @return String
	 */
	public static String byteToString(byte[] bytearray) {
		StringBuilder resultBuffer = new StringBuilder();
		for (byte b : bytearray) {
			resultBuffer.append(b);
		}
		return resultBuffer.toString();
	}

	/**
	 * 二进制字符串转十进制
	 * 
	 * @param binary
	 *            二进制字符串
	 * @return 十进制数值
	 */
	public static int binaryToAlgorism(String binary) {
		return Integer.parseInt(binary, 2);
	}


	/**
	 * HEX字符串前补0，主要用于长度位数不足。
	 * 
	 * @param str
	 *            String 需要补充长度的十六进制字符串
	 * @param maxLength
	 *            int 补充后十六进制字符串的长度
	 * @return 补充结果
	 */
	static public String patchHexString(String str, int maxLength) {
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < maxLength - str.length(); i++) {
			sb.append("0");
		}
		str = (sb.toString() + str).substring(0, maxLength);
		return str;
	}

	/**
	 * 将一个字符串转换为int
	 * 
	 * @param s
	 *            String 要转换的字符串
	 * @param defaultInt
	 *            int 如果出现异常,默认返回的数字
	 * @param radix
	 *            int 要转换的字符串是什么进制的,如16 8 10.
	 * @return int 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt, int radix) {
		int i = 0;
		try {
			i = Integer.parseInt(s, radix);
		} catch (NumberFormatException ex) {
			i = defaultInt;
		}
		return i;
	}

	/**
	 * 将一个十进制形式的数字字符串转换为int
	 * 
	 * @param s
	 *            String 要转换的字符串
	 * @param defaultInt
	 *            int 如果出现异常,默认返回的数字
	 * @return int 转换后的数字
	 */
	public static int parseToInt(String s, int defaultInt) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			i = defaultInt;
		}
		return i;
	}


	public static byte[] subByte(byte[] input, int startIndex, int length) {
		return Arrays.copyOfRange(input, startIndex, startIndex + length);
	}
}
