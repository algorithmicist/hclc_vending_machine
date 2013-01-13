import java.util.*;

/**
 *
 * @author Kyle Savarese
 *
 */

public class ManagerMachineManagementScreen {

	/** the database */
	private static DatabaseLayer db = DatabaseLayer.getInstance();

	/** the machines */
	private Collection<VendingMachine> storefronts;

	/**
	 * base constructor
	 * @param vms the machines
	 */
	public ManagerMachineManagementScreen( Collection<VendingMachine> vms ) {
		storefronts = vms;
	}

	/**
	 * lists all machines
	 * @return a collection of all machines
	 */
	public Collection<VendingMachine> listMachinessAll() {
		return storefronts;
	}

	/**
	 * lists machines by location
	 * @param loc the location to find machines from
	 * @return lists the machines
	 */
	public Collection<VendingMachine> listMachinesByLocation( Location loc ) {
		ArrayList<VendingMachine> vms = new ArrayList<VendingMachine>();
		for ( VendingMachine vm : storefronts ) {
			if ( vm.getLocation().equals( loc ) ) 
				vms.add( vm );
		}
		return vms;
	}

	/**
	 * adds a new machine
	 * @param location the loc of the machine
	 * @param interval the stocking interval
	 * @param layout the initial layout to use
	 * @return the id of the machine
	 */
	public int addMachine( Location location, int interval, VMLayout layout ) {
		VendingMachine machine = new VendingMachine( location, interval, layout );
		try {
			VendingMachine machine = new VendingMachine( location, interval, layout );
			db.updateOrCreateVendingMachine( machine );
			storefronts.add( machine );
			return machine.getId();
		} catch ( Exception databaseProblem ) {
			System.err.println("ERROR: Database problem encountered!");
			System.err.println("     : Dump details ... " + databaseProblem);
			return -1;
		}
	}

	/**
	 * deactivate a machine
	 * @param id the id of the machine
	 */
	public void deactivateMachine( int id ) {
		try {
			VendingMachine vm = db.getVendingMachineById( id );
			vm.makeActive( false );
			db.updateOrCreateVendingMachine( vm );
			storefronts = db.getVendingMachinesAll();
		} catch ( Exception databaseProblem ) {
			ControllerExceptionHandler.registerConcern(ControllerExceptionHandler.Verbosity.ERROR, databaseProblem);
		}
	}

	/**
	 * reactivates a machine
	 * @param id the id of the machine
	 */
	public void reactivateMachine( int id ) {
		try {
			VendingMachine vm = db.getVendingMachineById( id );
			vm.makeActive( true );
			db.updateOrCreateVendingMachine( vm );
			storefronts = db.getVendingMachinesAll();
		} catch ( Exception databaseProblem ) {
			ControllerExceptionHandler.registerConcern(ControllerExceptionHandler.Verbosity.ERROR, databaseProblem);
		}
	}
	
	/**
	 * changes a machines location
	 * @param id the machines is
	 * @param location the new location
	 * @return whether it succeeded
	 */
	public boolean changeMachineLocation( int id, Location location ) {
		try {
			VendingMachine vm = db.getVendingMachineById( id );
			vm.setLocation( location );
			db.updateOrCreateVendingMachine( vm );
			storefronts = db.getVendingMachinesAll();
		} catch ( Exception databaseProblem ) {
			ControllerExceptionHandler.registerConcern(ControllerExceptionHandler.Verbosity.WARN, databaseProblem);
			System.err.println("ERROR: Database problem encountered!");
			System.err.println("     : Dump details ... " + databaseProblem);
			return false;
		}
		return true;
	}

	/**
	 * exits the screen
	 */
	public void exit() {

	}
}
