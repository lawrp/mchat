package workshop10.forgui;

public abstract class AbstractPasswordCheck implements IPasswordCheck {
	private RegistrationChecker checker;
	IPasswordCheck nextCheck;
	private String ruleChecked;
	
	public AbstractPasswordCheck(String ruleChecked, RegistrationChecker checker) {
		this.ruleChecked = ruleChecked;
		this.checker = checker;
	}
	
	public void validate(String password) {
		if(isValid(password))        
        {
            if(nextCheck!=null)
            	nextCheck.validate(password);
        }

		else {
            System.out.println(ruleChecked);
            checker.add(ruleChecked);
		}
	}

	protected abstract boolean isValid(String password);
}
