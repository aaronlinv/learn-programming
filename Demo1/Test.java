package Demo1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于测试程序
 * @author Aaron
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		Map<String, Integer> map = new HashMap<String, Integer>();

		BufferedReader in = new BufferedReader(new FileReader("testFile.txt"));
		String str;
		int i = 1;
		while ((str = in.readLine()) != null) {
			if (str.equals("")) {
				System.out.println();
			}else if(str.substring(0,1).equals("-")) {
				System.out.println("---->"+str.substring(1));
			}else {
				System.err.println(i + ":" + str);
				try {
					Utils.callFunction(DemoMain.removeSpaces(str), map);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				i++;
			}

		}

	}

}
