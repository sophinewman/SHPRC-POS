
public class Client {

	private int suid;
	private boolean pregnancyTestUsed;
	private int credit;
	
	public Client(int suid, boolean pregnancyTestUsed, int credit) {
		this.suid = suid;
		this.pregnancyTestUsed = pregnancyTestUsed;
		this.credit= credit;
	}

	
	public boolean equals(Client client) {
		return (client.getSuid() == suid);
	}
	
	/**
	 * @return the suid
	 */
	public int getSuid() {
		return suid;
	}



	/**
	 * @return the pregnancyTest
	 */
	public boolean isPregnancyTestUsed() {
		return pregnancyTestUsed;
	}

	/**
	 * @param pregnancyTest the pregnancyTest to set
	 */
	public void setPregnancyTestUsed(boolean pregnancyTestUsed) {
		this.pregnancyTestUsed = pregnancyTestUsed;
	}

	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

}
