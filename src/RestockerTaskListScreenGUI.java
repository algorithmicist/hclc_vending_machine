import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Content panel for the restocker task list.
 * @author Piper Chester
 **/
public class RestockerTaskListScreenGUI extends JPanel
{
	/**
	 * Controller instance for this screen.
	 **/
	private RestockerTaskListScreen controller;
	
	/**
	 * Master for this panel
	 */
	private BaseGUI master;

	/**
	 * Contains the check boxes for tasks to be completed.
	 */
	private JPanel taskList;

	/**
	 * Array of tasks to perform.
	 */
	private JCheckBox[] tasks;

	/**
	 * Done button.
	 */
	private ConditionButton doneButton;

	/**
	 * Creates the panel with the given controller instance.
	 * @param controller The controller instance to use.
	 * @param master the master BaseGUI to use
	 **/
	public RestockerTaskListScreenGUI(RestockerTaskListScreen controller, BaseGUI master)
	{
		master.getStatusBar().clearStatus();
		this.controller = controller;
		this.master = master;
		tasks = null;
		taskList = new JPanel();
		doneButton = null;
		addComponents();
		updateTaskList();
	}

	/**
	 * Updates the list of tasks.
	 */
	private void updateTaskList()
	{
		Map<Integer, Pair<String, Boolean>> generatedTasks=controller.getInstructions();
		
		if(generatedTasks.size()>0)
			master.getStatusBar().clearStatus();
		else //no tasks to display
			master.getStatusBar().setStatus("There are no tasks to perform", StatusBar.STATUS_WARN_COLOR);
		
		doneButton.clearConditions();
		tasks=new JCheckBox[generatedTasks.size()];
		for(int index : generatedTasks.keySet())
		{
			Pair<String, Boolean> taskElements=generatedTasks.get(index);
			tasks[index-1]=new JCheckBox(taskElements.first);
			
			if(taskElements.second) //a required one
			{
				JCheckBox inQuestion=tasks[index-1];
				
				doneButton.addCondition(new CheckBoxChecked(inQuestion));
				doneButton.watch(inQuestion);
			}
		}
		doneButton.checkAndSetEnabled();
		
		taskList.removeAll();
		for(JCheckBox boxy : tasks)
			taskList.add(boxy);
		taskList.repaint(); //do some crazy Swing stuff
	}

	/**
	 * Sets up the general panel layout.
	 **/
	private void addComponents()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Check boxes will be vertical
		taskList.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(taskList);
		taskList.setLayout(new BoxLayout(taskList, BoxLayout.Y_AXIS));

		// Align the components to the left side
		taskList.setAlignmentX(LEFT_ALIGNMENT);

		// Panel to hold buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setAlignmentX(LEFT_ALIGNMENT);

		// Do some crazy stuff with buttons
		JButton cancelButton = new JButton("Leave machine");
		doneButton = new ConditionButton("Done");
		
		// Buttons' logic
		doneButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ignored)
			{
				new Thread()
				{
					public void run()
					{
						master.setProcessing(doneButton);
						for(int index=0; index<tasks.length; ++index)
							if(tasks[index].isSelected())
								controller.removeInstruction(index+1);
						
						controller.completeStocking();
						master.getStatusBar().setStatus("Restocking complete!", StatusBar.STATUS_GOOD_COLOR);
						master.popContentPanel();
					}
				}.start();
			}
		});
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ignored)
			{
				master.popContentPanel();
				master.getStatusBar().setStatus("Restocking canceled", StatusBar.STATUS_WARN_COLOR);
			}
		});
		
		// Adds stuff and aligns the button to the right
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(doneButton);

		// Put those buttons on board
		buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int)buttonsPanel.getPreferredSize().getHeight()));
		add(buttonsPanel);
	}

	/**
	 * Checks whether the checkbox is selected.
	 */
	private class CheckBoxChecked implements ConditionButtonCondition
	{
		/** My box. Mine. My precious. */
		private JCheckBox box;

		/**
		 * Provides the check box to check off.
		 * @param box the box to check
		 */
		public CheckBoxChecked(JCheckBox box)
		{
			this.box=box;
		}

		/**
		 * Does the check.
		 * @return whether it is, in fact, currently checked
		 */
		public boolean checkCondition()
		{
			if(box.isSelected())
			{
				master.getStatusBar().clearStatus();
				return true;
			}
			else
			{
				master.getStatusBar().setStatus("You must perform all removal tasks", StatusBar.STATUS_BAD_COLOR);
				return false;
			}
		}
	}
}
