package wars;

import java.io.*;
import java.util.*;
/**
 * Task 2 - provide command line interface
 * 
 * @author A.A.Marczyk
 * @version 16/02/25
 */
public class GameUI
{
    private BATHS myBattles ;
    private Scanner myIn = new Scanner(System.in);

    public void doMain()
    {
        int choice;
        System.out.println("Enter admiral's name");
        String name = myIn.nextLine();
        myBattles = new SeaBattles(name); // create
        
        choice = 100;
        while (choice != 0 )
        {
            choice = getMenuItem();
            if (choice == 1)
            {
                System.out.println(myBattles.getReserveFleet());
            }
            else if (choice == 2)
            {
                System.out.println(myBattles.getSquadron());
            }
            else if (choice == 3)
            {
                System.out.println("Enter Ship name");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.getShipDetails(ref));
            } 
            else if (choice == 4)
            {
                //write your code here
                System.out.println("Enter Ship name");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                String out = processCommissionResult(myBattles.commissionShip(ref));
                System.out.println(out);

            }
            else if (choice == 5)
            {
       	       //write your code here
                System.out.println("Enter Encounter number");
                int ref = myIn.nextInt();
                String out = processEncounterResult(myBattles.fightEncounter(ref));
                System.out.println(out);
       
                  
            }
            else if (choice ==6)
            {
	        //write your code here
                System.out.println("Enter Ship name to restore");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                String out = processRestoreResult(myBattles.restoreShip(ref));
                System.out.println(out);


            }
            else if (choice == 7)
            {
                //write your code here
                System.out.println("Enter Ship name to decommission");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                String out = processDecommissionResult(myBattles.decommissionShip(ref));
                System.out.println(out);


            }
            else if (choice==8)
            {
                System.out.println(myBattles.toString());
            }
//            else if (choice == 9) // Task 7 only
//            {
//                System.out.println("Write to file");
//                myBattles.saveGame("olenka.dat");
//            }
//            else if (choice == 10) // Task 7 only
//            {
//                System.out.println("Recommission from file");
//                SeaBattles myBattles2=null;
//                myBattles2 = myBattles.loadGame("olenka.dat");
//                System.out.println(myBattles2.toString());               
//            }  
        }
        System.out.println("Thank-you");
    }
    
    private int getMenuItem()
    {   int choice = 100;  
        System.out.println("Main Menu");
        System.out.println("0. Quit");
        System.out.println("1. List ships in the reserve fleet");
        System.out.println("2. List ships in admirals squadron"); 
        System.out.println("3. View a ship");
        System.out.println("4. Commission a ship into admiral's squadron");
        System.out.println("5. Fight an encounter");
        System.out.println("6. Restore a ship");
        System.out.println("7. Decommission a ship");
        System.out.println("8. View admiral's state");
        System.out.println("9. Save this game");
        System.out.println("10. Restore a game");
       
        
        while (choice < 0 || choice  > 10)
        {
            System.out.println("Enter the number of your choice");
            choice =  myIn.nextInt();
        }
        return choice;        
    } 
    
    private String processCommissionResult(int res) {
        String out = "No such result";
        if (res == -1) {
            out = "No such ship";
        }
        if(res == 0) {
            out = "Ship successfully commissioned to squadron";
        }
        if (res == 1) {
            out = "Ship is not in reserve fleet";
        }
        if (res == 2) {
            out = "Not enough funds available";
        }
        return out;
    }
    
    /** Returns details of the encounter result.
     * @param res result of fighting the encounter
     * @return string output of the result of the encounter
     **/
    private String processEncounterResult(int res) {
        String out;
        if (res == 0) {
            out = "Encounter won";
        }
        else if (res == 1) {
            out = "Encounter lost on battle strength";
        }
        else if (res == 2) {
            out = "Encounter lost as no ship available";
        }
        else if (res == 3) {
            out = "Encounter lost with no further resources. You lose the game";
        }
        else if (res == -1) {
            out = "No such encounter";
        }
        else {
            out = "No such result";
        }
        return out;
    }
    
    /** Returns details of the restore result.
     * @param res result of restoring a ship
     * @return string output of the result of the restore
     **/
    private String processRestoreResult(int res) {
        String out = "No such result";
        if (res == -1) {
            out = "No such ship";
        }
        if (res == 0) {
            out = "Ship successfully restored";
        }
        if (res == 1) {
            out = "Ship cannot be restored";
        }
        if (res == 2) {
            out = "Not enough funds to restore ship";
        }
        return out;
    }
    
    /** Returns details of the decommission result.
     * @param res result of decommissioning a ship
     * @return string output of the result of the decommission
     **/
    private String processDecommissionResult(int res) {
        String out = "No such result";
        if (res == -1) {
            out = "No such ship";
        }
        if (res == 0) {
            out = "Ship is decommissioned to reserve fleet";
        }
        if (res == 1) {
            out = "Ship you are trying to decommission is damaged";
        }
        if (res == 2) {
            out = "Ship you are trying to decommission is not in your squadron";
        }
        return out;
    }
    
    public static void main(String[] args)
    {
        GameUI xxx = new GameUI();
        xxx.doMain();
    }
}