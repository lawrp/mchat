package workshop10.forgui;

import java.util.ArrayList;
import java.util.List;

public class RegistrationChecker {
	protected List<String> rulesViolated;
	private IPasswordCheck nextCheck;
	
	public RegistrationChecker() {
		rulesViolated = new ArrayList<>();
		buildChecker();
	}

	void buildChecker() {
		LengthCheck lenChk = new LengthCheck("Password length should be 6 or greater", this);
		UpperLetterCheck upChk = new UpperLetterCheck("Password should contain at least one uppcase letter", this);
		lenChk.nextCheck = upChk;
		SpecialCharacterCheck spcChk = new SpecialCharacterCheck("Password should contain at least one special charater", this);
		upChk.nextCheck = spcChk;
		NumericCharacterCheck numChk = new NumericCharacterCheck("Password should contain at least one numeric charater", this);
		spcChk.nextCheck = numChk;
		this.nextCheck = lenChk;
	}
	
    public void validatePassword(String password) {
         if(nextCheck!=null) {
            nextCheck.validate(password);
         }
    }
		
	public void add(String ruleChecked) {
		rulesViolated.add(ruleChecked);
	}

	public void reset() {
		rulesViolated.clear();
	}
	
	public boolean allPassed() {
		return rulesViolated.size() == 0;
	}

	public List<String> getViolations() {
		return rulesViolated;
	}
}
