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

	@Test
	public void testCall() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		callFunction("整数 气温 等于 十", map);
		callFunction("气温 减少 三", map);
		callFunction("气温 增加 二", map);
		callFunction("看看 气温", map);
		callFunction("如果 气温 大于 八 则 看看 “你好，世界” 否则 看看 “冻死我了”", map);
		//
		callFunction("整数 钱包 等于 零", map);
		callFunction("钱包 增加 四", map);
		callFunction("钱包 减少 一", map);
		callFunction("看看 钱包 ", map);
		callFunction("看看 “字符串”", map);
		callFunction("如果 钱包 大于 二 则 看看 “钱太多了” 否则 看看 “我穷死了”", map);
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
			System.out.println(toChStr(map.get(printStr)));
		}
	}
	@Test
	public void testPrintOut() {
		

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("钱包", 10);
		Utils.printOut("看看 钱包 ".split(" "), map);
		Utils.printOut("看看 “字符串” ".split(" "), null);

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
		
		if( judge) {
			callFunction(statement2, map);
		}else {
			callFunction(statement3, map);
		}
	}

	@Test
	public void testTernary() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("钱包", 10);
		ternaryOperator("如果 钱包 等于 十 则 看看 “钱太多了” 否则 看看 “我穷死了”", map);

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

	@Test
	public void testJudge() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("钱包", 10);
		System.out.println(judgeOperator("零 等于 零", map));
	}

	/**
	 * 汉字转化为数字
	 * @return
	 */
	public static int toNum(String str) {
		return numWords.indexOf(str);// -1不存在
	}
	
	/**
	 * 数字转化为汉字
	 * @param num
	 * @return
	 */
	public static String toChStr(int num) {
		
		return numWords.substring(num,num+1);
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
	

	@Test
	public void testManipulate() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("钱包", 10);
		manipulateNum("钱包 减少 四", map);
		System.out.println(map.get("钱包"));
	}
}
