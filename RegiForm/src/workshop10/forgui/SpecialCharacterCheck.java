package workshop10.forgui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharacterCheck extends AbstractPasswordCheck {

	public SpecialCharacterCheck(String ruleChecked, RegistrationChecker checker) {
		super(ruleChecked, checker);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValid(String password) {
		//Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
		Pattern pattern = Pattern.compile("[-$&+,:;=?@#|'<>.^*()%!]");
		Matcher matcher = pattern.matcher(password);
		return matcher.find();
	}
}
