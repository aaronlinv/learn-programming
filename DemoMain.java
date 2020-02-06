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


}
