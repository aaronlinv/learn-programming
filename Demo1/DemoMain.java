package Demo1;

import java.util.Scanner;

public class DemoMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {

			Utils.callFunction(sc.nextLine());
			try {
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
