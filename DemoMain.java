package Demo1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class DemoMain {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		Scanner sc = new Scanner(System.in);
		while (true) {

			try {
				Utils.callFunction(removeSpaces(sc.nextLine()), map);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 处理掉语句中间多余的空格
	 * @param str
	 * @return
	 */
	public static String removeSpaces(String str) {
		String newStr = "";
		str = str.trim();
		String []strArr = str.split(" ");
		for (String s : strArr) {
			if (s.trim().equals("")) {
				continue;
			}
			newStr+=s+" ";
		}
		return newStr.trim();
	}
	@Test
	public  void testRemove() {
		String str = "     钱包     模除     二      ";
		System.out.println(removeSpaces(str));
	}

}
