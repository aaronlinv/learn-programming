package Demo1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DemoMain {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				Utils.callFunction(sc.nextLine().trim(), map);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}


}
