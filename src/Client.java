
public class Client {

	private int suid;
	private int affiliationID;
	private int creditAvailable;
	private boolean pregnancyTestUsed;
	private boolean qualifiesForPregnancyTest;
	
	
	public Client(int suid, int affiliationID, int creditAvailable, boolean pregnancyTestUsed,
					boolean qualifiesForPregnancyTest) {
		this.suid = suid;
		this.affiliationID = affiliationID;
		this.creditAvailable = creditAvailable;
		this.pregnancyTestUsed = pregnancyTestUsed;
		this.qualifiesForPregnancyTest = qualifiesForPregnancyTest;
	}
	
	public int getSUID() {
		return suid;
	}
	
	public int getCredit() {
		return creditAvailable;
	}
	
	public void setCredit(int credit) {
		creditAvailable = credit;
	}
	
	public int getAffiliation() {
		return affiliationID;
	}
	
	public boolean pregnancyTestAvailable() {
		return qualifiesForPregnancyTest && !pregnancyTestUsed;
	}
	

}
