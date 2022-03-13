package lapin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.Border;

public final class Main extends JPanel implements PropertyChangeListener,
							OnGeneratorEndListener{
	private static Main instance;
    private JList<String> list;
    private DefaultListModel<String> listModel;
 
    private static final String sortString = "Sort type";
    private static final String[] sortTypesString = {"Age", "Taille", "Poids"};
    private static final String generateString = "Generate";
    private static final String ascendingString = "Ascending order";
    private JTextField genAmount;
    private JCheckBox ascendingCheckBox;
    private JProgressBar progressBar;
    private int selectedType = Lapin.AGE;
    private boolean ascending = true;

    private FermeALapin ferme = new FermeALapin();

    /**
     * Create a singleton to make it easily accessible in Listeners classes
     * @return
     */
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }
 
    private Main() {
        super(new BorderLayout());

        listModel = new DefaultListModel<String>();
 
        //Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        // create all elements displayed in the panel
        JButton generateButton = new JButton(generateString);
        // One way to create Listeners with direct implementation (when used once)
        generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ferme.generateLapinList(Integer.parseInt(genAmount.getText()), 
							Main.getInstance(), Main.getInstance()); 
				} catch(Exception exception) {
					System.out.println(exception.getMessage());
				}
				Main.getInstance().updateList();
				genAmount.setText("");
			}
		});
        genAmount = new JTextField(4);
        
        JPanel radioPanel = new JPanel(new GridLayout(0,1));
        Border titleborder = BorderFactory.createTitledBorder(sortString);
        radioPanel.setBorder(titleborder);
        ButtonGroup group = new ButtonGroup();
        for(int i=0 ; i < 3 ; i++) {
        	JRadioButton radioButton = new JRadioButton(sortTypesString[i]);
        	radioButton.setActionCommand(Integer.toString(i));
        	if (i == 0)
        		radioButton.setSelected(true);
        	// Another way to create Listeners (when used multiples times)
        	radioButton.addActionListener(new sortTypeListener());
        	group.add(radioButton);
        	radioPanel.add(radioButton);
        }
        
        ascendingCheckBox = new JCheckBox(ascendingString, true);
        ascendingCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ascending = ascendingCheckBox.isSelected();
				Main.getInstance().updateList();
			}
		});
        
        progressBar = new JProgressBar();
 
        //Create a panel for the bottom part (generate button)
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
        bottomPane.add(generateButton);
        bottomPane.add(genAmount);
        bottomPane.add(progressBar);
        bottomPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Create a panel for the right part (sort buttons)
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(radioPanel);
        eastPanel.add(ascendingCheckBox);
        
        // add everything in the Main Panel
        add(listScrollPane, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.PAGE_END);
        add(eastPanel, BorderLayout.EAST);
    }
    
    private void updateList() {
    	this.listModel.removeAllElements();
    	try {
    		Iterator<Lapin> iter = null;
    		switch (selectedType) {
			case Lapin.AGE:
				iter = this.ferme.getLapinList(Lapin.AGE, ascending);
				break;
			case Lapin.TAILLE:
				iter = this.ferme.getLapinList(Lapin.TAILLE, ascending);
				break;
			case Lapin.POIDS:
				iter = this.ferme.getLapinList(Lapin.POIDS, ascending);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + selectedType);
			}
    		
			while(iter.hasNext()) {
				this.listModel.addElement(iter.next().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    // A Third way to use Listerners (when only one Listerner of this type is required)
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }

	@Override
	public void onGeneratorEnd() {
		this.updateList();		
	}
 
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent mainPane = Main.getInstance(); // We use the singleton !
        mainPane.setOpaque(true); 
        frame.setContentPane(mainPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
    	/** For thread safety,
         * this method should be invoked from the
         * event-dispatching thread. 
        **/
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    class sortTypeListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		selectedType = Integer.parseInt(e.getActionCommand());
    		Main.getInstance().updateList();
    	}
    }
}
