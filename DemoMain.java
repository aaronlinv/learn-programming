package Demo1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DemoMain {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		Scanner sc = new Scanner(System.in);
		while (true) {
			Utils.callFunction(sc.nextLine().trim(), map);
		}
	}

	public void line() {

		String lines2 = "整数 钱包 等于 零\r\n" + "钱包 增加 四\r\n" + "钱包 减少 四\r\n" + "看看 钱包 or 看看 “字符串”\r\n"
				+ "如果 钱包 大于 十 则 看看 “钱太多了” 否则 看看 “我穷死了”";
		String lines3 = "整数 气温 等于 十\r\n" + "气温 减少 三\r\n" + "气温 增加 二\r\n" + "看看 气温\r\n"
				+ "如果 气温 大于 八 则 看看 “你好，世界” 否则 看看 “冻死我了”";
		/*
		String lines = sc.nextLine();
		String[] strArray = lines.split("\n");

		for (String str : strArray) {
			// System.out.println(str.trim());
			// Utils.callFunction(str.trim(), map);
		}
		*/
	}

}
