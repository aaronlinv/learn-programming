package Demo1;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Utils {
	public static String numWords = "零一二三四五六七八九十";

	/**
	 * 调用方法
	 * @param str
	 * @param map
	 */
	public static void callFunction(String str, Map<String, Integer> map) {
		String[] split = str.trim().split(" ");
		String keyword = split[0];

		if (map.get(keyword) != null) {
			Utils.manipulateNum(str, map);
		} else {

			switch (keyword) {
			case "整数":
				// System.out.println(map.get("钱包"));// 不存在返回null
				int num = Utils.assignInt(split);
				map.put(split[1], num);
				break;
			case "看看":
				Utils.printOut(split, map);
				break;
			case "如果":
				Utils.ternaryOperator(str, map);
				break;
			default:
				throw new IllegalArgumentException("judgeOperator 没有对应的判断符号请输入（整数、看看、如果）: " + keyword);
			}
		}
	}

	/**
	 *  赋值整数变量
	 * @param array
	 * @return
	 */
	public static int assignInt(String[] array) {
		// 先考虑一位情况
		return toNum(array[3]);
	}

	/**
	 * 输出字符串或者变量
	 * @param array
	 * @param map
	 */
	public static void printOut(String[] array, Map<String, Integer> map) {

		String printStr = array[1];
		if (printStr.contains("“") && printStr.contains("”")) {
			System.out.println(printStr.replace("“", "").replace("”", ""));
		} else {
			try {
				System.out.println(toChStr(map.get(printStr)));
			}catch (NullPointerException e) {
				throw new DemoException("变量未定义，请定义变量");
			}
			
		}
	}

	/**
	 * 三目运算
	 * @param str
	 * @param map
	 */
	public static void ternaryOperator(String str, Map<String, Integer> map) {
		// 如果 钱包 大于 十 则 看看 “钱太多了” 否则 看看 “我穷死了”
		// 先不考虑三目嵌套三目的情况
		String statement1 = str.substring(0, str.indexOf("则")).replace("如果", "");
		String statement2 = str.substring(str.indexOf("则"), str.indexOf("否则")).replace("则", "");
		String statement3 = str.substring(str.indexOf("否则")).replace("否则", "");

		boolean judge = judgeOperator(statement1, map);
		// System.out.println(judge);

		if (judge) {
			callFunction(statement2, map);
		} else {
			callFunction(statement3, map);
		}
	}

	/**
	 * 判断语句，传入如果语句 例如：零 等于 零
	 * @param str
	 * @param map
	 * @return 
	 */
	public static boolean judgeOperator(String str, Map<String, Integer> map) {

		String[] strArray = str.trim().split(" ");// 不去除左右空格，空格会被加入到分割数组
		String leftStr = strArray[0];
		String rightStr = strArray[2];
		String middle = strArray[1];
		int leftInt = 0;
		int rightInt = 0;

		if (map.get(leftStr) != null) {
			leftInt = map.get(leftStr);
		} else {
			leftInt = toNum(leftStr);
		}

		if (map.get(rightStr) != null) {
			rightInt = map.get(rightStr);
		} else {
			rightInt = toNum(rightStr);
		}

		switch (middle) {
		case "等于":
			return leftInt == rightInt;
		case "大于":
			return leftInt > rightInt;
		case "小于":
			return leftInt < rightInt;

		default:
			throw new IllegalArgumentException("judgeOperator没有对应的判断符号请输入（大于、等于、小于）: " + middle);
		}
	}

	/**
	 * 汉字单个转化为单个数字
	 * @param str
	 * @return
	 */

	public static int toSingleNum(String str) {
		return numWords.indexOf(str);// -1不存在
	}

	/**
	 * 单个数字转化为单个汉字
	 * @param num
	 * @return
	 */
	public static String toSingleChStr(int num) {

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
		
		
		if (str.contains("百")) {
			var = 100 * toSingleNum(str.substring(0, str.indexOf('百')));// 百位
			var = flag * var;
			str = str.substring(str.indexOf('百') + 1);
			if (str.contains("零")) {
				var += toSingleNum(str.substring(str.indexOf("零")+1));// 几百零几
				return var*flag;
			}
		}

		if (str.length() == 1 && var >=100) { //判断var 不然十 可能会输出100
			var += 10*toSingleNum(str); //几百几：九百九
			return var*flag;
		}
		
		if (str.contains("十")) {
			if (str.indexOf('十') == 0) {
				var +=10; // 十几
			}
			var += 10 * toSingleNum(str.substring(0,str.indexOf('十')));// 十
			str = str.substring(str.indexOf('十')+1);
		}
		
		if (str.length() != 0) {
			var += toSingleNum(str);
		}
		
		return var*flag;
	}

	@Test
	public void test2() {

		System.out.println(toNum("一百一十一"));
	}

	/**
	 * 数字转化为汉字
	 * @param num
	 * @return
	 */
	public static String toChStr(int num) {

		String varStr = "";

		if (num < 0) {
			varStr += "负";
			num = num * -1;
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

	@Test
	public void test() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("钱包", 10);
		System.out.println(toChStr(-909));
	}

	/**
	 * 运算操作
	 * @param str
	 * @param map
	 */
	public static void manipulateNum(String str, Map<String, Integer> map) {
		String[] strArray = str.trim().split(" ");
		String varStr = strArray[0];
		String operator = strArray[1];
		String numStr = strArray[2];

		int num = 0;
		if (map.get(varStr) != null) {
			num = map.get(varStr);
		} else {
			throw new DemoException("变量：" + varStr + " 未定义");
		}

		switch (operator) {
		case "减少":
			num -= toNum(numStr);
			map.put(varStr, num);
			break;
		case "增加":
			num += toNum(numStr);
			map.put(varStr, num);
			break;
		default:
			throw new IllegalArgumentException("manipulateNum没有对应的判断符号请输入（增加、减少）: " + operator);
		}
	}

}
