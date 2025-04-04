package wars;
import java.io.*;
import java.util.*;

/**
 * Task 2 - provide command line interface
 * 
 * @author TEAM85
 * @version 1.3
 */
public class GameUI {
    private BATHS myBattles;
    private Scanner myIn = new Scanner(System.in);

    public void doMain() {
        int choice;
        System.out.println("Enter admiral's name");
        String name = myIn.nextLine();
        myBattles = new SeaBattles(name); // create
        choice = 100;
        while (choice != 0) {
            choice = getMenuItem();
            if (choice == 1) {
                System.out.println(myBattles.getReserveFleet());
            } else if (choice == 2) {
                System.out.println(myBattles.getSquadron());
            } else if (choice == 3) {
                System.out.println("Enter Ship name");
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.getShipDetails(ref));
            } else if (choice == 4) {
                System.out.println("Enter Ship name to commission");
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.commissionShip(ref));
            } else if (choice == 5) {
                System.out.println("Enter Encounter number");
                int ref = myIn.nextInt();
                System.out.println(myBattles.fightEncounter(ref));
            } else if (choice == 6) {
                System.out.println("Enter Ship name to restore");
                String ref = (myIn.nextLine()).trim();
                myBattles.restoreShip(ref);
            } else if (choice == 7) {
                System.out.println("Enter Ship name to decommission");
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.decommissionShip(ref));
            } else if (choice == 8) {
                System.out.println(myBattles.toString());
            } else if (choice == 9) {
                System.out.println(myBattles.getAllEncounters());
            } else if (choice == 10) {
                System.out.println("War Chest: " + myBattles.getWarChest());
            } else if (choice == 11) {
                System.out.println("Enter filename to save:");
                String fname = myIn.nextLine().trim();
                try {
                    myBattles.saveGame(fname);
                    System.out.println("Game saved successfully.");
                } catch (Exception ex) {
                    System.out.println("Error saving game: " + ex.getMessage());
                }
            } else if (choice == 12) {
                System.out.println("Enter filename to load:");
                String fname = myIn.nextLine().trim();
                try {
                    BATHS loadedGame = myBattles.loadGame(fname);
                    if (loadedGame != null) {
                        myBattles = loadedGame;
                        System.out.println("Game loaded successfully.");
                    } else {
                        System.out.println("Failed to load game.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error loading game: " + ex.getMessage());
                }
            }
        }
        System.out.println("Thank-you");
    }

    private int getMenuItem() {
        int choice = 100;
        System.out.println("Main Menu");
        System.out.println("0. Quit");
        System.out.println("1. List ships in the reserve fleet");
        System.out.println("2. List ships in admiral's squadron");
        System.out.println("3. View a ship");
        System.out.println("4. Commission a ship into admiral's squadron");
        System.out.println("5. Fight an encounter");
        System.out.println("6. Restore a ship");
        System.out.println("7. Decommission a ship");
        System.out.println("8. View admiral's state");
        System.out.println("9. List all encounters");
        System.out.println("10. View war chest");
        System.out.println("11. Save game");
        System.out.println("12. Load game");
        while (choice < 0 || choice > 12) {
            System.out.println("Enter the number of your choice");
            choice = myIn.nextInt();
            myIn.nextLine(); // consume newline
        }
        return choice;
    }

    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
        gameUI.doMain();
    }
}
