package workshop10.forgui;

import java.util.Scanner;

public class RegistraionTest {

	public static void main(String[] args) {
		RegistrationChecker checker = new RegistrationChecker();
		Scanner scanner = new Scanner(System.in);

        System.out.println("enter the password");
        String pwd;
        
        boolean checked = false;
        
        while (!checked) {
        	pwd = scanner.nextLine();
			checker.validatePassword(pwd);
			if (checker.allPassed()) {
				System.out.println("Passed!");
				checked = true;
				continue;
			}
			
			System.out.println(checker.getViolations());
	        System.out.println("Please try again...");
	        checker.reset();
        }

	}

}
