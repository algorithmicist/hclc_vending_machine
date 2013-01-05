import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Runs tests on the DatabaseLayer
 * @author Matthew Koontz
 **/
public class TestDatabaseLayer
{
	/**
	 * DatabaseLayer instance
	 **/
	private DatabaseLayer dbl;

	/**
	 * Set of items to use in tests
	 **/
	private ArrayList<FoodItem> items;

	/**
	 * Clears the database and initializes test objects for each test
	 **/
	@Before
	public void setUp() throws SQLException
	{
		dbl = DatabaseLayer.getInstance();
		dbl.nuke();
		initFoodItems();
	}

	/**
	 * Creates a set of FoodItems for use in tests
	 **/
	private void initFoodItems()
	{
		items = new ArrayList<FoodItem>();
		items.add(new FoodItem("Twix", 175, 1000));
		items.add(new FoodItem("Snickers", 175, 1000));
		items.add(new FoodItem("Chips", 150, 500));
		items.add(new FoodItem("Fish Sandwich", 200, 100000));
	}

	/**
	 * Checks if two FoodItems are the same
	 * @param item1 The first item
	 * @param item2 The second item
	 **/
	private void foodItemEquals(FoodItem item1, FoodItem item2)
	{
		assertTrue(item1.getId() == item2.getId());
		assertTrue(item1.getName().equals(item2.getName()));
		assertTrue(item1.getPrice() == item2.getPrice());
		assertTrue(item1.getFreshLength() == item2.getFreshLength());
	}

	/**
	 * Tests adding FoodItems to the database.
	 * Note: This only tests if the id was changed and SQL exceptions. getFoodItem() check if
	 * FoodItems can be added correctly.
	 **/
	@Test
	public void addFoodItem() throws SQLException
	{
		for (FoodItem item : items)
		{
			dbl.updateOrCreateFoodItem(item);
			assertTrue(!item.isTempId());
		}
	}

	/**
	 * Tests getting items from the database
	 **/
	@Test
	public void getFoodItem() throws SQLException
	{
		for (FoodItem item : items)
			dbl.updateOrCreateFoodItem(item);
		for (FoodItem item : items)
		{
			int id = item.getId();
			FoodItem test = dbl.getFoodItemById(id);
			foodItemEquals(test, item);
		}
		for (FoodItem test : dbl.getFoodItemsAll())
		{
			FoodItem item = null;
			for (FoodItem check : items)
			{
				if (check.getId() == test.getId())
				{
					item = check;
					break;
				}
			}
			String failure = String.format("Item I tried to find:\n %5d %10s\nI found:\n", test.getId(), test.getName());
			for (FoodItem check : items)
			{
				failure += String.format("%5d %10s\n", check.getId(), check.getName());
			}
			assertTrue(failure, item != null);
			foodItemEquals(test, item);
		}
	}

	/**
	 * Tests updating items in the database
	 **/
	@Test
	public void changeFoodItem() throws SQLException
	{
		for (FoodItem item : items)
			dbl.updateOrCreateFoodItem(item);
		FoodItem change = items.get(0);
		change.setName("Name change");
		dbl.updateOrCreateFoodItem(change);
		FoodItem test = dbl.getFoodItemById(change.getId());
		foodItemEquals(test, change);

		change = items.get(1);
		change.setPrice(300);
		dbl.updateOrCreateFoodItem(change);
		test = dbl.getFoodItemById(change.getId());
		foodItemEquals(test, change);

		change = items.get(2);
		change.setFreshLength(12345);
		dbl.updateOrCreateFoodItem(change);
		test = dbl.getFoodItemById(change.getId());
		foodItemEquals(test,change);
	}
}
