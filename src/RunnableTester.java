
public class RunnableTester {

	private RuntimeDatabase rDB;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			rDB = new RuntimeDatabase();
		}
		catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

	}

}
