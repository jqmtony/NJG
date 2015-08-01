/*
 * @(#)FdcStringUtil.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 房地产 字符串工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since 1.4
 */
public class FdcStringUtil {

	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";

	/**
	 * 索引——未找到
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * 填充_最大个数
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !FdcStringUtil.isEmpty(str);
	}

	/**
	 * 是否是空白
	 * <p>
	 * 包括:<br>
	 * 1、null<br>
	 * 2、""<br>
	 * 3、其它 Java 空白字符，参照Character.isWhitespace： <br>
	 * <li>它是 Unicode 空格字符（SPACE_SEPARATOR、LINE_SEPARATOR 或 PARAGRAPH_SEPARATOR)， 但不是非中断空格（'\u00A0'、'\u2007'、'\u202F'）
	 * <br>
	 * <li>它是 '\u0009'，HORIZONTAL TABULATION <br>
	 * <li>它是 ' '，LINE FEED <br>
	 * <li>它是 '\u000B'，VERTICAL TABULATION <br>
	 * <li>它是 '\u000C'，FORM FEED <br>
	 * <li>它是 ' '，CARRIAGE RETURN <br>
	 * <li>它是 '\u001C'，FILE SEPARATOR <br>
	 * <li>它是 '\u001D'，GROUP SEPARATOR <br>
	 * <li>它是 '\u001E'，RECORD SEPARATOR <br>
	 * <li>它是 '\u001F'，UNIT SEPARATOR <br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是非空白
	 * <p>
	 * 不包括:<br>
	 * 1、null<br>
	 * 2、""<br>
	 * 3、其它 Java 空白字符，参照Character.isWhitespace： <br>
	 * <li>它是 Unicode 空格字符（SPACE_SEPARATOR、LINE_SEPARATOR 或 PARAGRAPH_SEPARATOR)， 但不是非中断空格（'\u00A0'、'\u2007'、'\u202F'） <br>
	 * <li>它是 '\u0009'，HORIZONTAL TABULATION <br>
	 * <li>它是 ' '，LINE FEED <br>
	 * <li>它是 '\u000B'，VERTICAL TABULATION <br>
	 * <li>它是 '\u000C'，FORM FEED <br>
	 * <li>它是 ' '，CARRIAGE RETURN <br>
	 * <li>它是 '\u001C'，FILE SEPARATOR <br>
	 * <li>它是 '\u001D'，GROUP SEPARATOR <br>
	 * <li>它是 '\u001E'，RECORD SEPARATOR <br>
	 * <li>它是 '\u001F'，UNIT SEPARATOR <br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !FdcStringUtil.isBlank(str);
	}

	/**
	 * 左填充" "
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.leftPad(null, *) = null<br>
	 * FdcStringUtil.leftPad("", 3) = "   "<br>
	 * FdcStringUtil.leftPad("bat", 3) = "bat"<br>
	 * FdcStringUtil.leftPad("bat", 5) = "  bat"<br>
	 * FdcStringUtil.leftPad("bat", 1) = "bat"<br>
	 * FdcStringUtil.leftPad("bat", -1) = "bat"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * 左填充
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.leftPad(null, *, *) = null<br>
	 * FdcStringUtil.leftPad("", 3, 'z') = "zzz"<br>
	 * FdcStringUtil.leftPad("bat", 3, 'z') = "bat"<br>
	 * FdcStringUtil.leftPad("bat", 5, 'z') = "zzbat"<br>
	 * FdcStringUtil.leftPad("bat", 1, 'z') = "bat"<br>
	 * FdcStringUtil.leftPad("bat", -1, 'z') = "bat"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}

		return padding(pads, padChar).concat(str);
	}

	/**
	 * 左填充
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.leftPad(null, *, *) = null<br>
	 * FdcStringUtil.leftPad("", 3, "z") = "zzz"<br>
	 * FdcStringUtil.leftPad("bat", 3, "yz") = "bat"<br>
	 * FdcStringUtil.leftPad("bat", 5, "yz") = "yzbat"<br>
	 * FdcStringUtil.leftPad("bat", 8, "yz") = "yzyzybat"<br>
	 * FdcStringUtil.leftPad("bat", 1, "yz") = "bat"<br>
	 * FdcStringUtil.leftPad("bat", -1, "yz") = "bat"<br>
	 * FdcStringUtil.leftPad("bat", 5, null) = "  bat"<br>
	 * FdcStringUtil.leftPad("bat", 5, "") = "  bat"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @param padStr
	 * @return
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}

			return new String(padding).concat(str);
		}
	}

	/**
	 * 右填充
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.leftPad(null, *) = null<br>
	 * FdcStringUtil.leftPad("", 3) = "   "<br>
	 * FdcStringUtil.leftPad("bat", 3) = "bat"<br>
	 * FdcStringUtil.leftPad("bat", 5) = "  bat"<br>
	 * FdcStringUtil.leftPad("bat", 1) = "bat"<br>
	 * FdcStringUtil.leftPad("bat", -1) = "bat"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * 右填充
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.rightPad(null, *, *) = null<br>
	 * FdcStringUtil.rightPad("", 3, 'z') = "zzz"<br>
	 * FdcStringUtil.rightPad("bat", 3, 'z') = "bat"<br>
	 * FdcStringUtil.rightPad("bat", 5, 'z') = "batzz"<br>
	 * FdcStringUtil.rightPad("bat", 1, 'z') = "bat"<br>
	 * FdcStringUtil.rightPad("bat", -1, 'z') = "bat"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}

		return str.concat(padding(pads, padChar));
	}

	/**
	 * 右填充
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.rightPad(null, *, *) = null<br>
	 * FdcStringUtil.rightPad("", 3, "z") = "zzz"<br>
	 * FdcStringUtil.rightPad("bat", 3, "yz") = "bat"<br>
	 * FdcStringUtil.rightPad("bat", 5, "yz") = "batyz" <br>
	 * FdcStringUtil.rightPad("bat", 8, "yz") = "batyzyzy"<br>
	 * FdcStringUtil.rightPad("bat", 1, "yz") = "bat"<br>
	 * FdcStringUtil.rightPad("bat", -1, "yz") = "bat"<br>
	 * FdcStringUtil.rightPad("bat", 5, null) = "bat  "<br>
	 * FdcStringUtil.rightPad("bat", 5, "") = "bat  "<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @param size
	 * @param padStr
	 * @return
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}

			return str.concat(new String(padding));
		}
	}

	/**
	 * 填充
	 * 
	 * @param repeat
	 * @param padChar
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}

		return new String(buf);
	}

	/**
	 * 首字母大写
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.capitalize(null) = null<br>
	 * FdcStringUtil.capitalize("") = ""<br>
	 * FdcStringUtil.capitalize("cat") = "Cat"<br>
	 * FdcStringUtil.capitalize("cAt") = "CAt"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		String strRs = str;

		if (FdcStringUtil.isNotEmpty(str)) {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(Character.toTitleCase(str.charAt(0))).append(str.substring(1));

			strRs = stringBuffer.toString();
		}

		return strRs;
	}

	/**
	 * 首字母小写
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.uncapitalize(null) = null<br>
	 * FdcStringUtil.uncapitalize("") = ""<br>
	 * FdcStringUtil.uncapitalize("Cat") = "cat"<br>
	 * FdcStringUtil.uncapitalize("CAT") = "cAT"<br>
	 * 
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		String strRs = str;

		if (FdcStringUtil.isNotEmpty(str)) {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(Character.toLowerCase(str.charAt(0))).append(str.substring(1));

			strRs = stringBuffer.toString();
		}

		return strRs;
	}

	/**
	 * 根据字符类型、驼峰状态拆分字符串
	 * 
	 * <pre>
	 * 
	 * FdcStringUtil.splitByCharacterTypeCamelCase(null) = null FdcStringUtil.splitByCharacterTypeCamelCase("") = []
	 * FdcStringUtil.splitByCharacterTypeCamelCase("ab de fg") = ["ab", " ", "de", " ", "fg"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("ab:cd:ef") = ["ab", ":", "cd", ":", "ef"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("number5") = ["number", "5"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("fooBar") = ["foo", "Bar"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("foo200Bar") = ["foo", "200", "Bar"]
	 * FdcStringUtil.splitByCharacterTypeCamelCase("ASFRules") = ["ASF", "Rules"]
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] splitByCharacterTypeCamelCase(String str) {
		return splitByCharacterType(str, true);
	}

	/**
	 * 根据字符类型拆分字符串
	 * 
	 * @param str
	 *            字符串
	 * @param camelCase
	 *            是否根据驼峰状态拆分字符串
	 * @return
	 */
	private static String[] splitByCharacterType(String str, boolean camelCase) {
		String[] strArr = null;

		if (null != str) {
			if (str.length() == 0) {
				strArr = FdcArrayUtil.EMPTY_STRING_ARRAY;
			} else {
				char[] c = str.toCharArray();
				List list = new ArrayList();
				int tokenStart = 0;
				// 取得常规类别
				int currentType = Character.getType(c[tokenStart]);
				for (int pos = tokenStart + 1; pos < c.length; pos++) {
					int type = Character.getType(c[pos]);
					if (type == currentType) {
						continue;
					}
					// 大写，然后小写
					if (camelCase && type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
						int newTokenStart = pos - 1;
						if (newTokenStart != tokenStart) {
							list.add(new String(c, tokenStart, newTokenStart - tokenStart));
							tokenStart = newTokenStart;
						}
					} else {
						list.add(new String(c, tokenStart, pos - tokenStart));
						tokenStart = pos;
					}
					currentType = type;
				}
				list.add(new String(c, tokenStart, c.length - tokenStart));

				strArr = (String[]) list.toArray(new String[list.size()]);
			}
		}

		return strArr;
	}

	/**
	 * 过滤掉字符串中的特殊字符
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
