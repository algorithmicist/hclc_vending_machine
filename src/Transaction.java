import java.util.GregorianCalendar;

/**
 * Represents an event during which a customer bought an item from a vending machine.
 * @author Lane Lawley <lxl5734@rit.edu>
 */
public class Transaction extends ModelBase {
	/** The time the transaction occurred. */
	private GregorianCalendar timestamp;

	/** The machine at which the transaction occurred. */
	private VendingMachine machine;

	/** The customer who purchased the product. */
	private Customer customer;

	/** The product purchased. */
	private FoodItem product;

	/** The row the product was purchased from. */
	private Pair<Integer, Integer> whichRow;

	/**
	 * Transaction constructor.
	 *
	 * @param timestamp		The time the transaction occurred.
	 * @param machine		The machine at which the transaction occurred.
	 * @param customer		The customer who purchased the product.
	 * @param product		The product purchased.
	 * @param whichRow		The row the product was purchased from.
	 * @throws IllegalArgumentException if a <tt>null</tt> is passed in or a coordinate is negative
	 */
	public Transaction(GregorianCalendar timestamp, VendingMachine machine, Customer customer, FoodItem product, Pair<Integer, Integer> whichRow) throws IllegalArgumentException
	{
		if(timestamp==null)
			throw new IllegalArgumentException("Timestamp cannot be null");
		else if(machine==null)
			throw new IllegalArgumentException("Machine cannot be null");
		else if(customer==null)
			throw new IllegalArgumentException("Customer cannot be null");
		else if(whichRow==null)
			throw new IllegalArgumentException("Row specification cannot be null");
		else if(whichRow.first==null || whichRow.second==null)
			throw new IllegalArgumentException("Row specification cannot contain null coordinate");
		else if(whichRow.first<0 || whichRow.second<0)
			throw new IllegalArgumentException("Row specification cannot contain negative coordinate");
		
		this.timestamp = timestamp;
		this.machine = machine;
		this.customer = customer;
		this.product = product;
		this.whichRow = whichRow;
	}

	/**
	 * Shallow copy constructor.
	 *
	 * @param old	Transaction to copy.
	 */
	public Transaction(Transaction old) {
		super(old);
		this.timestamp = old.timestamp;
		this.machine = old.machine;
		this.customer = old.customer;
		this.product = old.product;
		this.whichRow = old.whichRow;
	}

	/** @return	The time the transaction occurred. */
	public GregorianCalendar getTimestamp() {
		return timestamp;
	}

	/** @return	The machine at which the transaction occurred. */
	public VendingMachine getMachine() {
		return machine;
	}

	/** @return The customer who purchased the product.*/
	public Customer getCustomer() {
		return customer;
	}

	/** @return The product purchased. */
	public FoodItem getProduct() {
		return product;
	}

	/** @return The row the product was purchased from. */
	public Pair<Integer, Integer> getRow() {
		return whichRow;
	}

	/**
	 * @param timestamp	The new timestamp.
	 * @throws IllegalArgumentException if a <tt>null</tt> value is provided
	 */
	public void setTimestamp(GregorianCalendar timestamp) throws IllegalArgumentException
	{
		if(timestamp==null)
			throw new IllegalArgumentException("Timestamp must not be null");
		
		this.timestamp = timestamp;
	}

	/**
	 * @param machine	The new machine.
	 * @throws IllegalArgumentException if a <tt>null</tt> value is provided
	 */
	public void setMachine(VendingMachine machine) throws IllegalArgumentException
	{
		if(machine==null)
			throw new IllegalArgumentException("Machine must not be null");
		
		this.machine = machine;
	}

	/**
	 * @param customer	The new customer.
	 * @throws IllegalArgumentException if a <tt>null</tt> value is provided
	 */
	public void setCustomer(Customer customer) throws IllegalArgumentException
	{
		if(customer==null)
			throw new IllegalArgumentException("Customer must not be null");
		
		this.customer = customer;
	}

	/**
	 * @param product	The new product.
	 * @throws IllegalArgumentException if a <tt>null</tt> value is provided
	 */
	public void setProduct(FoodItem product) throws IllegalArgumentException
	{
		if(product==null)
			throw new IllegalArgumentException("Product must not be null");
		
		this.product = product;
	}

	/**
	 * @param whichRow	The new row.
	 * @throws IllegalArgumentException if a <tt>null</tt> or negative value is provided
	 */
	public void setRow(Pair<Integer, Integer> whichRow) throws IllegalArgumentException
	{
		if(whichRow==null)
			throw new IllegalArgumentException("Row specification must not be null");
		else if(whichRow.first==null || whichRow.second==null)
			throw new IllegalArgumentException("Row specification mustn't contain null coordinate");
		else if(whichRow.first<0 || whichRow.second<0)
			throw new IllegalArgumentException("Row specification mustn't contain negative coordinate");
		
		this.whichRow = whichRow;
	}
}
