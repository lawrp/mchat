package workshop10.forgui;

public class LengthCheck extends AbstractPasswordCheck {

	public LengthCheck(String ruleChecked, RegistrationChecker checker) {
		super(ruleChecked, checker);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValid(String password) {
		// TODO Auto-generated method stub
		return password.length() >= 6;
	}
}
