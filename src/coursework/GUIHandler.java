/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Class used to draw main GUI frame and the components
 *
 * @author kpomian
 */
public class GUIHandler extends JFrame implements ActionListener {

    JLabel logo = null, sortingLabel = null;
    JComboBox criteria = null;
    JTextField searchField = null;
    JButton searchButton = null;
    JPanel searchingPanel = null, results = null, sortingPanel = null;
    JTable table = null;
    static JRadioButton defaultMode = null, month = null, latitude = null, longitude = null, LSOAname = null;
    static ButtonGroup sortingButtons = null;
    static final String[] columnNames = {"CrimeID", "Month", "Reported by", "Falls within",
        "Logitude", "Latitude", "Location", "LSOA code",
        "LSOA name", "Crime type", "Last outcome category", "Context"};

    public GUIHandler() {
        super("CrimeDatabase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new GridLayout(3, 3));
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    /**
     * Resets the constraints to their default values
     *
     * @param c The GridBagConstraints object handling the constraints
     */
    private static void resetConstraints(GridBagConstraints c) {
        c = new GridBagConstraints();
    }

    /**
     * Sets constraints for a component
     *
     * @param c The GridBagConstraints object handling the constraints
     * @param gridX Index of column
     * @param gridY Index of row
     */
    private static void determineConstraints(GridBagConstraints c, int gridX, int gridY) {
        c.gridx = gridX;
        c.gridy = gridY;
    }

    /**
     * Creates components and adds them to the frame
     */
    public void createGUI() {
        //Specifying general constraints of the layout
        GridBagConstraints constraints = new GridBagConstraints();
        
        //Picture got from https://www.crimestoppersoforegon.com/ and modified by me
        ImageIcon icon = new ImageIcon("assets/main.png");
        logo = new JLabel(icon);
        logo.setAlignmentY(Component.CENTER_ALIGNMENT);
        getContentPane().add(logo);

        searchingPanel = new JPanel(new GridBagLayout());
        searchingPanel.setBackground(Color.white);
        getContentPane().add(searchingPanel);

        String[] options = {"Choose searching criteria...", "Longitude", "Latitude", "LSOA name", "Crime type"};
        criteria = new JComboBox(options);
        criteria.setSelectedIndex(0);
        resetConstraints(constraints);
        determineConstraints(constraints, 1, 0);
        searchingPanel.add(criteria, constraints);

        searchField = new JTextField(30);
        resetConstraints(constraints);
        determineConstraints(constraints, 1, 1);
        searchingPanel.add(searchField, constraints);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        resetConstraints(constraints);
        determineConstraints(constraints, 2, 1);
        searchingPanel.add(searchButton, constraints);

        sortingPanel = new JPanel();
        sortingPanel.setBackground(Color.white);
        resetConstraints(constraints);
        constraints.gridwidth = 2;
        determineConstraints(constraints, 1, 2);
        searchingPanel.add(sortingPanel, constraints);

        sortingLabel = new JLabel("Sort by:");
        sortingPanel.add(sortingLabel);

        sortingButtons = new ButtonGroup();

        defaultMode = new JRadioButton("Default");
        defaultMode.addActionListener(this);
        defaultMode.setBackground(Color.white);
        sortingPanel.add(defaultMode);

        month = new JRadioButton("Month");
        month.addActionListener(this);
        month.setBackground(Color.white);
        sortingPanel.add(month);

        longitude = new JRadioButton("Longitude");
        longitude.addActionListener(this);
        longitude.setBackground(Color.white);
        sortingPanel.add(longitude);

        latitude = new JRadioButton("Latitude");
        latitude.addActionListener(this);
        latitude.setBackground(Color.white);
        sortingPanel.add(latitude);

        LSOAname = new JRadioButton("LSOA name");
        LSOAname.addActionListener(this);
        LSOAname.setBackground(Color.white);
        sortingPanel.add(LSOAname);

        sortingButtons.add(defaultMode);
        sortingButtons.add(month);
        sortingButtons.add(longitude);
        sortingButtons.add(latitude);
        sortingButtons.add(LSOAname);
        sortingButtons.setSelected(defaultMode.getModel(), true);

    }

    /**
     * Creates and pops up message
     * 
     * @param message Message to be shown
     */
    public void popUpMessage(String message) {
        JFrame messageFrame = new JFrame("PopUp");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        messageFrame.setLocation(screen.width / 2 - this.getSize().width / 2, screen.height / 2 - this.getSize().height / 2);
        JOptionPane.showMessageDialog(messageFrame, message);
        repaint();
    }

    /**
     * Creates a table for searching results
     *
     * @param data Information used to fill the table
     */
    private void createTable(Object[][] data) {
        if (data == null) {
            popUpMessage("No results found");
        } else {
            results = new JPanel(new BorderLayout());
            results.setBackground(Color.white);
            getContentPane().add(results);

            table = new JTable(data, columnNames);
            table.setEnabled(false);
            results.add(table.getTableHeader(), BorderLayout.PAGE_START);
            results.add(table, BorderLayout.CENTER);
            revalidate();
        }
    }

    /**
     * Updates the table if there is one
     *
     * @param data Information used to update the table
     */
    private void updateTable(Object[][] data) {
        if (data == null) {
            table.getTableHeader().setVisible(false);
            table.setVisible(false);
            popUpMessage("No results found");
        } else {
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);
            if (data.length == 0) {
                table.getTableHeader().setVisible(false);
                table.setVisible(false);
            } else {
                table.getTableHeader().setVisible(true);
                table.setVisible(true);
            }
        }

    }

    /**
     * Pass information about an event, which occurred
     *
     * @param e ActionEvent object used to get information about performed
     * action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            if (criteria.getSelectedIndex() != 0) {
                try {
                    if (table == null) {
                        createTable(GUIImplementation.getResults(searchField.getText(), criteria.getSelectedItem().toString()));
                    } else {
                        updateTable(GUIImplementation.getResults(searchField.getText(), criteria.getSelectedItem().toString()));
                    }
                } catch (InvalidCharacterException ice) {
                    System.out.println(ice);
                    if (table != null) {
                        table.setVisible(false);
                        table.getTableHeader().setVisible(false);
                    }
                    popUpMessage("Longitude and Latitude have to be numbers");
                }

            }
        }
    }
}
