package workshop10.forgui;

public class NumericCharacterCheck extends AbstractPasswordCheck {

	public NumericCharacterCheck(String ruleChecked, RegistrationChecker checker) {
		super(ruleChecked, checker);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValid(String password) {
		// TODO Auto-generated method stub
		return password.matches(".*\\d.*");
	}
}
