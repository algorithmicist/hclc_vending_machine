import java.util.Collection;
import java.util.Scanner;

/**
 * CLI for the Restocker's perspective.
 * Provides an entry point for the Restocker.
 * @author Piper Chester <pwc1203@rit.edu>
 */
public class RestockerCLI {

	/** Instantiating a new RestockerMachinePickerScreen*/
	private static RestockerMachinePickerScreen restockerMachinePickerScreen = new RestockerMachinePickerScreen();


	/** The entry point to the program.
	 * 
	 * @param args Arguments required to create String arrays in Java
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Restocker interface!\n---\n");
		pickMachine(restockerMachinePickerScreen);
	}

	/** Allows the Restocker to view the list of tasks that needs to be performed.
	 *
	 * @param restockerTaskListScreen The state of the RestockerTaskListScreen
	 */
	private static void listTasks(RestockerTaskListScreen restockerTaskListScreen){
		String[] tasks;

		CLIUtilities.printTitle("List Tasks Screen");
	
		tasks = restockerTaskListScreen.assembleStockingList();

		for (String task : tasks)
			System.out.println(task);

		System.out.println("========\nApplication closed.");
	}
	
	/**
	 * Allows the Restocker to select a machine.
	 *
	 * @param restockerMachinePickerScreen The state of the RestockerMachinePickerScreen
	 */
	private static void pickMachine(RestockerMachinePickerScreen restockerMachinePickerScreen) {
		Collection<VendingMachine> vms = RestockerMachinePickerScreen.listActiveMachines();

		int idNumber;
	
		CLIUtilities.printTitle("Pick Machine Screen");
		
		CLIUtilities.printCollection(vms);

			
		RestockerTaskListScreen restockerTaskListScreen = null;

		while (restockerTaskListScreen == null){
			idNumber = CLIUtilities.promptInt("Please give ID number");

			restockerTaskListScreen = restockerMachinePickerScreen.tryMachine(idNumber);
		}
		

		listTasks(restockerTaskListScreen);		
	}
}
