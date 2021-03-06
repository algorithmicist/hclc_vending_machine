import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Collection;
import java.util.LinkedList;

public class ManagerHomeScreenTest
{
	/**
	 * Holds fake data used in tests
	 **/
	private TestUtilities testUtil;

	/**
	 * Database driver
	 **/
	private DatabaseLayer dbl;

	/**
	 * Manager to use in tests. Taken from testUtil.
	 **/
	private Manager manny;

	/**
	 * Runs before each of the following tests. Does the following:
	 * 1. Gets the instance of the DatabaseLayer
	 * 2. Nukes the database
	 * 3. Creates a new instance of the TestUtilities class that gets the data
	 * and adds it to the database.
	 * 4. Sets manny to the first manager in the set of managers generated by
	 * testUtil.
	 **/
	@Before
	public void ManagerHomeScreenTest() throws SQLException, BadStateException, BadArgumentException
	{
		dbl = DatabaseLayer.getInstance();
		dbl.nuke();
		testUtil = new TestUtilities(true);
		manny = testUtil.managers.get(0);
	}

	/**
	 * Tests the default constructor. The only thing that can go wrong is
	 * an exception can be thrown.
	 **/
	@Test
	public void constructorTest()
	{
		ManagerHomeScreen test = new ManagerHomeScreen(manny);
	}

	/**
	 * Tests the alterLayout method with good input
	 **/
	@Test
	public void goodAlterLayout()
	{
		ManagerHomeScreen test = new ManagerHomeScreen(manny);
		ManagerAlterLayoutScreen test1 = test.alterLayout();
		assertTrue(test1 != null);
	}

	/**
	 * Tests the manageMachines method with good input
	 **/
	@Test
	public void goodManageMachines()
	{
		ManagerHomeScreen test = new ManagerHomeScreen(manny);
		ManagerMachineManagementScreen test1 = test.manageMachines();
		assertTrue(test1 != null);
	}

	/**
	 * Tests the manageUser method with good input
	 **/
	@Test
	public void goodManageUsers()
	{
		ManagerHomeScreen test = new ManagerHomeScreen(manny);
		ManagerUserAccountsScreen test1 = test.manageUsers();
		assertTrue(test1 != null);
	}

	@Test
	public void testName() throws IllegalArgumentException,
		SQLException, BadArgumentException, BadStateException
	{
		ManagerHomeScreen screen=new ManagerHomeScreen(manny);
		assertEquals(screen.getUserName(), manny.getName());
	}
}
