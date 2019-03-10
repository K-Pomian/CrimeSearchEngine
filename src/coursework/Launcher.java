/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

/**
 *
 * @author kpomian
 */
public class Launcher {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIHandler searchMenu = new GUIHandler();
                searchMenu.createGUI();
                searchMenu.pack();
                String message = "Found " + DataQualityCheck.findRecordsWithNoID() + " records with no ID\n";
                message += "Found " + DataQualityCheck.findDuplicatedIDRecords() + " records with duplicated ID\n";
                message += "For more information open text files inside folder named 'txt'";
                searchMenu.popUpMessage(message);
            }
        });
    }
}
