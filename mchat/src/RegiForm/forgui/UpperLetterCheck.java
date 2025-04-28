package src.RegiForm.forgui;

public class UpperLetterCheck extends AbstractPasswordCheck {

	public UpperLetterCheck(String ruleChecked, RegistrationChecker checker) {
		super(ruleChecked, checker);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isValid(String password) {
		// TODO Auto-generated method stub
		return password.matches(".*[A-Z].*");
	}
}
