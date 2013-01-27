


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientTest {
	
	private Client client;
	
	@Before
	public void testSetup() {
		client = new Client(5573646, true, -200);
	}

	@Test
	public void testGetSuid() {
		assertEquals("getSUID", 5573646, client.getSuid());
	}


	@Test
	public void testIsPregnancyTestUsed() {
		assertEquals("isPregnancyTestUsed", true, client.isPregnancyTestUsed());
	}

	@Test
	public void testSetPregnancyTestUsed() {
		client.setPregnancyTestUsed(false);
		assertEquals("setPregnancyTest", false, client.isPregnancyTestUsed());
	}

	@Test
	public void testGetCredit() {
		assertEquals("getCredit", -200, client.getCredit());
	}

	@Test
	public void testSetCredit() {
		client.setCredit(-100);
		assertEquals("setCredit", -100, client.getCredit());
	}
	
	@Test
	public void testEquals() {
		assertFalse(client.equals(new Client(5573645, true, -200)));
	}

}
