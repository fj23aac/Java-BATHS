package wars;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Provide a GUI interface for the game
 * 
 * @author TEAM85
 * @version 1.5
 */
public class GameGUI 
{
    private BATHS gp = new SeaBattles("Fred");
    private JFrame myFrame = new JFrame("Game GUI");
    private JTextArea listing = new JTextArea();
    private JLabel codeLabel = new JLabel();
    private JButton fightBtn = new JButton("Fight Encounter");
    private JButton viewBtn = new JButton("View State");
    private JButton clearBtn = new JButton("Clear");
    private JButton quitBtn = new JButton("Quit");
    private JButton warChestBtn = new JButton("View War Chest");
    private JButton commissionShipBtn = new JButton("Commission Ship");
    private JButton saveGameBtn = new JButton("Save Game");
    private JButton loadGameBtn = new JButton("Load Game");
    private JPanel eastPanel = new JPanel();

    public GameGUI() {
        makeFrame();
        makeMenuBar(myFrame);
    }

    private void makeFrame() {
        myFrame.setLayout(new BorderLayout());
        myFrame.add(listing, BorderLayout.CENTER);
        listing.setVisible(false);
        myFrame.add(eastPanel, BorderLayout.EAST);
        eastPanel.setLayout(new GridLayout(8, 1)); // Adjusted to fit the new buttons
        eastPanel.add(viewBtn);
        eastPanel.add(fightBtn);
        eastPanel.add(clearBtn);
        eastPanel.add(warChestBtn);
        eastPanel.add(commissionShipBtn); // Added commission ship button
        eastPanel.add(saveGameBtn); // Added save game button
        eastPanel.add(loadGameBtn); // Added load game button
        eastPanel.add(quitBtn);
        clearBtn.addActionListener(new ClearBtnHandler());
        fightBtn.addActionListener(new FightBtnHandler());
        quitBtn.addActionListener(new QuitBtnHandler());
        viewBtn.addActionListener(new ViewStateHandler());
        warChestBtn.addActionListener(new WarChestBtnHandler());
        commissionShipBtn.addActionListener(new CommissionShipHandler()); // Added action listener
        saveGameBtn.addActionListener(new SaveGameHandler()); // Added action listener
        loadGameBtn.addActionListener(new LoadGameHandler()); // Added action listener
        fightBtn.setVisible(true);
        clearBtn.setVisible(true);
        quitBtn.setVisible(true);
        viewBtn.setVisible(true);
        warChestBtn.setVisible(true);
        commissionShipBtn.setVisible(true); // Set visibility
        saveGameBtn.setVisible(true); // Set visibility
        loadGameBtn.setVisible(true); // Set visibility
        JScrollPane scrollPane = new JScrollPane(listing);
        myFrame.getContentPane().add(scrollPane);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    private void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu shipMenu = new JMenu("Ships");
        menubar.add(shipMenu);
        JMenu encounterMenu = new JMenu("Encounters");
        menubar.add(encounterMenu);
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);

        JMenuItem listShipItem = new JMenuItem("List reserve Ships");
        JMenuItem listSquadronItem = new JMenuItem("List Squadron");
        JMenuItem viewShipItem = new JMenuItem("View Ship");
        JMenuItem commissionShipItem = new JMenuItem("Commission Ship");
        JMenuItem listEncountersItem = new JMenuItem("List All Encounters");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");

        commissionShipItem.addActionListener(new CommissionShipHandler());
        viewShipItem.addActionListener(new ViewShipHandler());
        listSquadronItem.addActionListener(new ListSquadronHandler());
        listShipItem.addActionListener(new ListReserveHandler());
        listEncountersItem.addActionListener(new ListAllEncountersHandler());
        saveGameItem.addActionListener(new SaveGameHandler());
        loadGameItem.addActionListener(new LoadGameHandler());

        shipMenu.add(commissionShipItem);
        shipMenu.add(viewShipItem);
        shipMenu.add(listSquadronItem);
        shipMenu.add(listShipItem);
        encounterMenu.add(listEncountersItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(loadGameItem);
    }

    private class ListReserveHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String xx = gp.getReserveFleet();
            listing.setText(xx);
        }
    }

    private class ListAllEncountersHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String xx = gp.getAllEncounters();
            listing.setText(xx);
        }
    }

    private class ViewStateHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String xx = gp.toString();
            listing.setText(xx);
        }
    }

    private class ListSquadronHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setVisible(true);
            String xx = gp.getSquadron();
            listing.setText(xx);
        }
    }

    private class ClearBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText("");
        }
    }

    private class ViewShipHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String shipName = JOptionPane.showInputDialog("Ship name: ");
            String result = gp.getShipDetails(shipName);
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class FightBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputValue = JOptionPane.showInputDialog("Encounter number ?: ");
            int num = Integer.parseInt(inputValue);
            String result = gp.fightEncounter(num);
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class CommissionShipHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputValue = JOptionPane.showInputDialog("Ship name ?: ");
            String result = gp.commissionShip(inputValue);
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class WarChestBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = "War Chest: " + gp.getWarChest();
            JOptionPane.showMessageDialog(myFrame, result);
        }
    }

    private class QuitBtnHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(myFrame, "Are you sure you want to quit?", "Finish", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private class SaveGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fname = JOptionPane.showInputDialog("Enter filename to save:");
            if (fname != null && !fname.trim().isEmpty()) {
                try {
                    gp.saveGame(fname.trim());
                    JOptionPane.showMessageDialog(myFrame, "Game saved successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(myFrame, "Error saving game: " + ex.getMessage());
                }
            }
        }
    }

    private class LoadGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fname = JOptionPane.showInputDialog("Enter filename to load:");
            if (fname != null && !fname.trim().isEmpty()) {
                try {
                    BATHS loadedGame = gp.loadGame(fname.trim());
                    if (loadedGame != null) {
                        gp = loadedGame;
                        JOptionPane.showMessageDialog(myFrame, "Game loaded successfully.");
                    } else {
                        JOptionPane.showMessageDialog(myFrame, "Failed to load game.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(myFrame, "Error loading game: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}
