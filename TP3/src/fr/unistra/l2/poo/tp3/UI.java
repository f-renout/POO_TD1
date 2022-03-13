package fr.unistra.l2.poo.tp3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import static javax.swing.SwingUtilities.*;

public class UI extends JPanel implements PropertyChangeListener, OnGeneratorEndListener {
    private static UI instance;

    @Override
    public void onGeneratorEnd() {
        updateList();
    }

    private void updateList() {
        this.listModel.removeAllElements();
        Iterator<Lapin> iter = ferme.getLapinList(selectedType, !ascending);
        while (iter.hasNext()) {
            this.listModel.addElement(iter.next().toString());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }

    private JList<String> list;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    private static final String sortString = "Trier par";
    private static final String[] sortTypesString = {"AGE", "TAILLE", "POIDS"};
    private static final String generateString = "Générer (entrer nb de lapins)";
    private static final String ascendingString = "Tri croissant";
    private JTextField genAmount;
    private JCheckBox ascendingCheckBox;
    private JProgressBar progressBar;
    private LapinCarac selectedType = LapinCarac.AGE;
    private boolean ascending = true;

    private Ferme ferme = new Ferme();

    public static void main(String[] args) {
        invokeLater(() -> {
            //Create and set up the window.
            JFrame frame = new JFrame("Ferme à Lapin");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            JComponent mainPane = UI.getInstance();
            mainPane.setOpaque(true);
            frame.setContentPane(mainPane);

            //Display the window.
            frame.pack();
            frame.setVisible(true);

        });
    }

    public static UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }

    private UI() {
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
                    LapinProgressBarGenerator gen = new LapinProgressBarGenerator(ferme, Integer.parseInt(genAmount.getText()), false, UI.getInstance());
                    gen.addPropertyChangeListener(UI.getInstance());
                    gen.execute();

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                UI.getInstance().updateList();
                genAmount.setText("");
            }
        });
        genAmount = new JTextField(4);

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        Border titleborder = BorderFactory.createTitledBorder(sortString);
        radioPanel.setBorder(titleborder);
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            JRadioButton radioButton = new JRadioButton(sortTypesString[i]);
            radioButton.setActionCommand(Integer.toString(i));
            if (i == 0)
                radioButton.setSelected(true);
            // Another way to create Listeners (when used multiples times)
            radioButton.addActionListener(new UI.SortTypeListener(LapinCarac.valueOf(sortTypesString[i])));
            group.add(radioButton);
            radioPanel.add(radioButton);
        }

        ascendingCheckBox = new JCheckBox(ascendingString, true);
        ascendingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascending = ascendingCheckBox.isSelected();
                UI.getInstance().updateList();
            }
        });

        progressBar = new JProgressBar();

        //Create a panel for the bottom part (generate button)
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
        bottomPane.add(generateButton);
        bottomPane.add(genAmount);
        bottomPane.add(progressBar);
        bottomPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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

    class SortTypeListener implements ActionListener {
        private LapinCarac type;

        public SortTypeListener(LapinCarac type) {
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedType = type;
            UI.getInstance().updateList();
        }
    }
}
