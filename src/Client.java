
public class Client {

	private int suid;
	private int affiliationID;
	private int creditAvailable;
	private boolean pregnancyTestAvailable;
	
	
	public Client(int suid, int creditAvailable, int pregnancyTestAvailable) {
		this.suid = suid;
		this.creditAvailable = creditAvailable;
		this.pregnancyTestAvailable = pregnancyTestAvailable > 0 ? true: false;
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
	
	public boolean isPTAvailable() {
		return pregnancyTestAvailable;
	}
	
	public void setPTAvailability(boolean availability) {
		pregnancyTestAvailable = availability;
	}
	

}
