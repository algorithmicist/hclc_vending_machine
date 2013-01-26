import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.BorderLayout;

/**
 * Content panel for the customer login screen.
 * @author Matthew Koontz
 **/
public class CustomerLoginScreenGUI extends JPanel
{
	/**
	 * Controller instance for this screen.
	 **/
	private CustomerLoginScreen controller;

	/**
	 * Login by customer id button.
	 **/
	private JButton loginButton;

	/**
	 * Login as cash customer button.
	 **/
	private JButton cashButton;

	/**
	 * Creates the panel with the given controller instance.
	 * @param controller The controller instance to use.
	 **/
	public CustomerLoginScreenGUI(CustomerLoginScreen controller)
	{
		this.controller = controller;
		addComponents();
	}

	/**
	 * Lays out the components on the panel.
	 **/
	public void addComponents()
	{
		// Components will be laid out vertically in the main panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Align the components to the left side
		this.setAlignmentX(LEFT_ALIGNMENT);

		// Panel to hold id text box and login button
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.setAlignmentX(LEFT_ALIGNMENT);

		// Panel to hold id label and text box
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));

		// Label for customer id text box
		JLabel idLabel = new JLabel("Enter ID");
		idPanel.add(idLabel);

		// Gap between label and text box
		idPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		// Text field for customer id
		JTextField idTextField = new JTextField(20);
		idTextField.setMaximumSize(idTextField.getPreferredSize());
		idPanel.add(idTextField);

		// Add box id label and text box to login panel
		idPanel.setMaximumSize(idPanel.getPreferredSize());
		loginPanel.add(idPanel);

		// Gap between customer id text box and login button
		loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		// Panel to align the login button to the right
		JPanel loginButtonPanel = new JPanel();
		loginButtonPanel.setLayout(new BoxLayout(loginButtonPanel, BoxLayout.X_AXIS));

		// Aligns the button to the right
		loginButtonPanel.add(Box.createGlue());

		// Add the login button
		loginButton = new JButton("Login");
		loginButton.setEnabled(false);
		loginButtonPanel.add(loginButton);
		loginPanel.add(loginButtonPanel);

		// Add the login panel to main panel
		loginPanel.setMaximumSize(loginPanel.getPreferredSize());
		this.add(loginPanel);

		// Gap between above and pay with cash button
		this.add(Box.createRigidArea(new Dimension(0,20)));

		// Pay with cash button
		cashButton = new JButton("Pay with cash");
		this.add(cashButton);
	}

	/**
	 * Main method, displays the GUI for the vending machine specified on the
	 * command line.
	 * @param args Command line arguments:
	 * <ol>
	 * <li>ID of the vending machine</li>
	 * </ol>
	 **/
	public static void main(String[] args) throws Exception
	{
		GUIUtilities.setNativeLookAndFeel();
		CustomerLoginScreen controller = CustomerLoginScreen.buildInstance(Integer.parseInt(args[0]));
		CustomerLoginScreenGUI gui = new CustomerLoginScreenGUI(controller);
		BaseGUI base = new BaseGUI("Customer Login Screen");
		base.setContentPanel(gui);
		base.displayGUI();
	}
}