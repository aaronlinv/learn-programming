package Demo1;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Utils {
	private static String numWords = "零一二三四五六七八九十";
	private static Map<String, Integer> map = new HashMap<String, Integer>();

	/**
	 * 调用方法
	 * @param str
	 */
	public static void callFunction(String str) {
		String[] split = str.trim().split("\\s+");
		String keyword = split[0];
		if (keyword.equals("无")) {
			return;
		}
		if (isManipulate(str) || isVar(keyword)) {
			Utils.manipulateNum(str);
		} else {
			// isManipulate 已经判断了len == 1情况 不会越界
			switch (keyword) {
			case "整数":
				// System.out.println(map.get("钱包"));// 不存在返回null
				Utils.assignInt(str);
				break;
			case "看看":
				Utils.printOut(str);
				break;
			case "如果":
				Utils.ternaryOperator(str);
				break;
			default:
				throw new IllegalArgumentException("没有关键字：" + keyword + " 请使用关键字：整数、看看、如果");
			}
		}
	}

	/**
	 *  赋值整数变量
	 * @param array
	 * @return
	 */
	public static void assignInt(String str) {
		String[] strArr = str.trim().split("\\s+");
		// 短路 所以不会越界
		if (strArr.length != 4 || !strArr[2].equals("等于")) {
			throw new DemoException("语法有错，请检查语法");
		}

		setVar(strArr[1], toNum(strArr[3]));
	}

	/**
	 * 输出字符串或者变量
	 * @param array
	 */
	public static void printOut(String str) {
		String[] strArr = str.trim().split("\\s+");
		if (strArr.length != 2) {
			throw new DemoException("语法有错，请检查语法");
		}
		String printStr = strArr[1];
		if (printStr.contains("“") && printStr.contains("”")) {
			System.out.println(printStr.replace("“", "").replace("”", "")); // 看看 “字符串”
		} else {
			System.out.println(toChStr(getVar(printStr)));
		}
	}

	/**
	 * 三目运算
	 * @param str
	 */
	public static void ternaryOperator(String str) {
		// 如果 钱包 大于 十 则 看看 “钱太多了” 否则 看看 “我穷死了”
		// 先不考虑三目嵌套三目的情况
		String statement1 = str.substring(0, str.indexOf("则")).replace("如果", "");
		String statement2 = str.substring(str.indexOf("则"), str.indexOf("否则")).replace("则", "");
		String statement3 = str.substring(str.indexOf("否则")).replace("否则", "");

		boolean judge = judgeOperator(statement1);
		// System.out.println(judge);

		if (judge) {
			callFunction(statement2);
		} else {
			callFunction(statement3);
		}
	}

	/**
	 * 判断语句，传入如果语句 例如：零 等于 零
	 * @param str
	 * @return 
	 */
	public static boolean judgeOperator(String str) {
		String[] strArr = str.trim().split("\\s+");// 不去除左右空格，空格会被加入到分割数组
		if (strArr.length != 3) {
			throw new DemoException("语法有错，请检查语法");
		}
		String leftStr = strArr[0];
		String rightStr = strArr[2];
		String middle = strArr[1];
		int leftInt = 0;
		int rightInt = 0;

		// 如果都不是变量，那么toNum来异常处理
		if (isVar(leftStr)) {
			leftInt = getVar(leftStr);
		} else {
			try {
				leftInt = toNum(leftStr);
			} catch (Exception e) {
				throw new DemoException("变量：" + leftStr + " 未定义，请定义变量");
			}
		}

		if (isVar(rightStr)) {
			rightInt = getVar(rightStr);
		} else {
			try {
				rightInt = toNum(rightStr);
			} catch (Exception e) {
				throw new DemoException("变量：" + rightStr + " 未定义，请定义变量");
			}

		}

		switch (middle) {
		case "等于":
			return leftInt == rightInt;
		case "大于":
			return leftInt > rightInt;
		case "小于":
			return leftInt < rightInt;

		default:
			throw new IllegalArgumentException("没有关键字：" + middle + " 请使用关键字：大于、等于、小于");
		}
	}

	/**
	 * 汉字单个转化为单个数字
	 * @param str
	 * @return
	 */

	public static int toSingleNum(String str) {
		int num = numWords.indexOf(str);
		if (num == -1) {
			throw new DemoException("语法有错，请检查语法，字符转化错误");
		}
		return num;// -1不存在
	}

	/**
	 * 单个数字转化为单个汉字
	 * @param num
	 * @return
	 */
	public static String toSingleChStr(int num) {
		if (num < 0 || num > 10) {
			throw new DemoException("语法有错，请检查语法，字符转化错误");
		}
		return numWords.substring(num, num + 1);
	}

	/**
	 * 汉字转化为数字
	 * @return
	 */
	public static int toNum(String str) {
		int flag = 1;// 负数标记
		int var = 0;
		char[] arr = str.toCharArray();
		if (arr[0] == '负') {
			str = str.replace("负", "");

			flag = -1;
		}
		// 数字机械式的转化
		if (str.length() > 1 && !str.contains("百") && !str.contains("十")) {
			char[] chArr = str.toCharArray();
			int temp = 1;
			for (int i = chArr.length - 1; i >= 0; i--) {
				String s = "" + chArr[i];
				var += temp * toSingleNum(s);
				temp *= 10;
			}
			return var * flag;
		}

		if (str.contains("百")) {
			var = 100 * toSingleNum(str.substring(0, str.indexOf('百')));// 百位
			str = str.substring(str.indexOf('百') + 1);
			if (str.contains("零")) {
				var += toSingleNum(str.substring(str.indexOf("零") + 1));// 几百零几
				return var * flag;
			}
		}

		if (str.length() == 1 && var >= 100) { // 判断var 不然十 可能会输出100
			var += 10 * toSingleNum(str); // 几百几：九百九
			return var * flag;
		}

		if (str.contains("十")) {
			if (str.indexOf('十') == 0) {
				var += 10; // 十几
			}
			var += 10 * toSingleNum(str.substring(0, str.indexOf('十')));// 十
			str = str.substring(str.indexOf('十') + 1);
		}

		if (str.length() != 0) {
			var += toSingleNum(str);
		}

		return var * flag;
	}

	@Test
	public void test2() {
		// Java int Max ：2147483647 二一四七四八三六四七 Min：-2147483648
		// System.out.println(toNum("负二一四七四八三六四八"));
		System.out.println(toNum("负九九九"));
	}

	/**
	 * 数字转化为汉字
	 * @param num
	 * @return
	 */
	public static String toChStr(int num) {

		if (num == -2147483648) {
			throw new DemoException("超出支持的输入输出范围(-2147483647到2147483647)");
		}
		String varStr = "";

		if (num < 0) {
			varStr += "负";
			num = num * -1; // 转化为正数
		}

		// 数字机械式的转化
		if (num > 999) {
			// 1234
			char[] CharArr = Integer.toString(num).toCharArray();
			for (char c : CharArr) {
				String s = "" + c;
				varStr += toSingleChStr(Integer.parseInt(s));
			}
			return varStr;
		}
		if (num == 0) { // 零
			return "零";
		}

		if (num / 10 == 1) { // 用十几 改变一十几的写法
			varStr += "十";
			if (num % 10 != 0) {
				varStr += toSingleChStr(num % 10);
			}
			return varStr;
		}

		if (num / 100 > 0) {
			varStr += toSingleChStr(num / 100) + "百";
			if (num % 100 == 0) {// 几百
				return varStr;
			} else if (num % 100 < 10) { // 几百零几
				return varStr += "零" + toSingleChStr(num % 100);
			}
		}

		num %= 100;
		if (num / 10 != 0) {
			varStr += toSingleChStr(num / 10) + "十";// 十
		}

		if (num % 10 != 0) {
			varStr += toSingleChStr(num % 10); // 几
		}

		return varStr;
	}

	/**
	 * 运算操作
	 * @param str
	 */
	public static void manipulateNum(String str) {
		String[] strArray = str.trim().split("\\s+");
		if (strArray.length != 3) {
			throw new DemoException("语法有错，请检查语法");
		}

		String operator = strArray[1];
		String varStr = strArray[0];
		String numStr = strArray[2];

		int var = getVar(varStr);
		int num = toNum(numStr);

		switch (operator) {
		case "减少":
			var -= num;
			break;
		case "增加":
			var += num;
			break;
		case "乘以":
			var *= num;
			break;
		case "除以":
			if (num == 0) {
				throw new DemoException("除数不能等于零哦");
			} else {
				var /= num;
			}
			break;
		case "模除":
			var %= num;
			break;
		default:
			throw new IllegalArgumentException("没有关键字：" + operator + " 请使用关键字：增加、减少、乘以、除以、模除 ");
		}

		setVar(varStr, var);
	}

	/**
	 * 判断是否为运算操作语句
	 * @param str
	 * @return
	 */
	public static boolean isManipulate(String str) {
		String[] array = str.trim().split("\\s+");

		String[] keywords = { "增加", "减少", "乘以", "除以", "模除" };

		// 下面会用到array[1]
		if (array.length == 1) {
			throw new DemoException("语法有错，请检查语法");
		}
		String symbol = array[1];

		for (String s : keywords) {
			if (symbol.equals(s)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testSymbol() {
		System.out.println(isManipulate("钱包 模除  十"));
	}

	/**
	 * 从Map中获取变量，不存在抛出异常
	 * @param str 需要获取数据的变量名
	 * @return
	 */
	private static int getVar(String varStr) {
		if (isVar(varStr)) {
			return map.get(varStr);
		} else {
			throw new DemoException("变量：" + varStr + " 未定义，请定义变量");
		}
	}

	/**
	 * 存入变量到map中
	 * @param varStr 键
	 * @param var 值
	 */
	private static void setVar(String varStr, int var) {
		map.put(varStr, var);
	}

	/**
	 * 判断是否为已存在的变量
	 * @param varStr 键
	 * @return
	 */
	private static boolean isVar(String varStr) {
		return map.get(varStr) != null ? true : false;
	}
}
