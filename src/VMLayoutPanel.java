import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;

/**
 * Panel for displaying a vending machine's layout. Has modes for customer
 * and manager.
 * @author Matthew Koontz
 **/
public class VMLayoutPanel extends JPanel implements ActionListener
{
	/**
	 * Grid of toggle buttons that display the items.
	 **/
	private JToggleButton[][] grid;

	/**
	 * The selected row. Defaults to null.
	 **/
	private Pair<Integer, Integer> selectedRow;

	/**
	 * Creates a VMLayoutPanel using the given array of items.
	 * @param items The items to display in this panel.
	 * @param managerMode If true it will allow the user to select empty rows
	 * and rows with deactivated items.
	 **/
	public VMLayoutPanel(FoodItem[][] items, boolean managerMode)
	{
		grid = new JToggleButton[items.length][items[0].length];
		selectedRow = null;
		addComponents(items, managerMode);
	}

	/**
	 * Adds the components to the VMLayout
	 * @param items The items to display in this panel.
	 * @param managerMode If true it will allow the user to select empty rows
	 * and rows with deactivated items.
	 **/
	private void addComponents(FoodItem[][] items, boolean managerMode)
	{
		// Allows the panel to expand
		setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		// height and width
		int height = items[0].length;
		int width = items.length;

		// Sets the layout to a grid layout
		setLayout(new GridLayout(height, width, 5, 5));

		// Add buttons
		for (int i=0;i<height;++i)
		{
			for (int j=0;j<width;++j)
			{
				// Item for this location
				FoodItem item = items[j][i];

				// Set name to item's name or EMPTY if item is null
				String name = "EMPTY";
				if (item != null) {
					name = item.getName();
				}

				// Set price to item's price or "" (the empty string) if item
				// is null
				String price = "";
				if (item != null)
					price = CLIUtilities.formatMoney(item.getPrice());


				// Create toggle button with name and price
				grid[j][i] = new JToggleButton(String.format("<html>%s<br />%s</html>", name, price));
				// Disable the button if it is (null or inactive) and the
				// manager mode is not set
				if ((item == null || !item.isActive()) && !managerMode)
					grid[j][i].setEnabled(false);

				// This class will handle the button being clicked
				grid[j][i].addActionListener(this);

				// Allow the button to expand
				grid[j][i].setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
				
				//Add the button to the panel
				add(grid[j][i]);
			}
		}
	}
	

	/**
	 * @return The row currently selected, or null if no row is selected.
	 **/
	public Pair<Integer, Integer> getSelectedRow()
	{
		return selectedRow;
	}

	/**
	 * Handles a toggle button getting clicked. Ensures the toggle button clicked
	 * remains selected and all the other toggle buttons are not selected. Also
	 * sets selectedRow to the appropriate value.
	 * @param event Contains information regarding the action that was performed.
	 **/
	@Override
	public void actionPerformed(ActionEvent event)
	{
		JToggleButton source = (JToggleButton)event.getSource();
		source.setSelected(true);
		int height = grid[0].length;
		int width = grid.length;
		for (int i=0;i<height;++i)
		{
			for (int j=0;j<width;++j)
			{
				JToggleButton button = grid[j][i];
				if (button == source)
					selectedRow = new Pair<Integer, Integer>(j, i);
				else
					button.setSelected(false);
			}
		}
	}
}