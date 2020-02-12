package Demo1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 用于测试程序
 * @author Aaron
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("testFile.txt"));
		String str;
		int i = 1;
		while ((str = in.readLine()) != null) {
			if (str.equals("")) {
				System.out.println();
			} else if (str.substring(0, 1).equals("*")) {
				System.out.println("正确输出---->" + str.substring(1));
			} else if (str.substring(0, 1).equals("-")) {
				System.out.println("####   " + str.substring(1) + "    ####");
			} else {
				System.err.println(i + ":" + str);
				Utils.runMain(str);
				i++;
			}

		}

	}

}
