package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author TEAM85
 * @version 1.6
 */

public class SeaBattles implements BATHS 
{
    // may have one HashMap and select on stat

    private String admiral;
    private double warChest;

    private ArrayList<Encounter> encountersList = new ArrayList<>();
    private HashMap<String, Ship> shipsHashMap = new HashMap<>();


//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm) {
        this.admiral = adm;
        this.warChest = 1000;
        setupShips();
        setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
        public SeaBattles(String admir, String filename)  //Task 3
    {
        admiral = admir;
        setupEncounters();
        readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString() {
        String s = "\nAdmiral: " + admiral +
                   "\nWar Chest: " + warChest +
                   "\nStatus: " + (isDefeated() ? "Defeated" : "Is OK") +
                   "\nSquadron: " + getSquadron() +
                   "\nReserve Fleet: " + getReserveFleet() +
                   "\nSunk Ships: " + getSunkShips();
        return s;
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated() 
    {
        boolean hasActiveShips = shipsHashMap.values().stream().anyMatch(ship -> ship.getState() == ShipState.ACTIVE);
        return warChest <= 0 && !hasActiveShips;
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest() 
    {
        return warChest;
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet()
    {   //assumes reserves is a Hashmap
        StringBuilder s = new StringBuilder("************ Reserve Fleet *************");
        shipsHashMap.values().stream()
            .filter(ship -> ship.getState() == ShipState.RESERVE)
            .forEach(ship -> s.append("\n").append(ship));
        return s.length() == 0 ? "No ships" : s.toString();
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron() 
    {
        StringBuilder s = new StringBuilder("************ Squadron ************");
        shipsHashMap.values().stream()
            .filter(ship -> ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING)
            .forEach(ship -> s.append("\n").append(ship));
        return s.length() == 0 ? "\nNo ships commissioned" : s.toString();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips() 
    {
        StringBuilder s = new StringBuilder("************ Sunk Ships *************");
        shipsHashMap.values().stream()
            .filter(ship -> ship.getState() == ShipState.SUNK)
            .forEach(ship -> s.append("\n").append(ship));
        return s.length() == 0 ? "\nNo ships sunk yet" : s.toString();
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips() 
    {
        StringBuilder s = new StringBuilder("************ All Ships *************");
        shipsHashMap.values().forEach(ship -> s.append("\n").append(ship));
        return s.toString();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String name) 
    {
        Ship ship = shipsHashMap.get(name.toLowerCase());
        return ship != null ? ship.toString() : "\nNo such ship";
    }    
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/        
    public String commissionShip(String name) 
    {
        Ship ship = shipsHashMap.get(name.toLowerCase());
        if (ship == null) {
            return "Not found";
        }
        if (ship.getState() != ShipState.RESERVE) {
            return "Not available";
        }
        int commissionFee = ship.getCommissionFee();
        if (commissionFee > warChest) {
            return "Not enough money";
        }
        warChest -= commissionFee;
        ship.setState(ShipState.ACTIVE);
        return "Ship commissioned";
    }
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String name) 
    {
        Ship ship = shipsHashMap.get(name.toLowerCase());
        return ship != null && (ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING);
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String name) 
    {
        Ship ship = shipsHashMap.get(name.toLowerCase());
        if (ship == null || ship.getState() == ShipState.SUNK || ship.getState() != ShipState.ACTIVE) {
            return false;
        }
        ship.setState(ShipState.RESERVE);
        warChest += ship.getCommissionFee() / 2;
        return true;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String name) 
    {
        Ship ship = shipsHashMap.get(name.toLowerCase());
        if (ship != null && ship.getState() == ShipState.RESTING) {
            ship.setState(ShipState.ACTIVE);
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
    public boolean isEncounter(int num) 
    {
        return num >= 1 && num <= encountersList.size();
    }
     
     
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
    public String fightEncounter(int encNo) 
    {
        if (!isEncounter(encNo)) {
            return "No such encounter";
        }
        Encounter encounter = encountersList.get(encNo - 1);
        Ship shipForEncounter = getShipForEncounter(encounter);
        if (shipForEncounter == null) {
            warChest -= encounter.getPrizeMoney();
            return isDefeated() ? "Encounter lost and you lose your job" : "Encounter lost as no suitable ship available";
        }
        if (encounter.getSkillLevel() > shipForEncounter.getSkillLevel()) {
            warChest -= encounter.getPrizeMoney();
            shipForEncounter.setState(ShipState.SUNK);
            return isDefeated() ? "Encounter lost and you lose your job" : "Encounter lost on skill level";
        }
        warChest += encounter.getPrizeMoney();
        shipForEncounter.setState(ShipState.RESTING);
        return "Encounter won by " + shipForEncounter.getName() + " - War Chest: " + warChest;
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num) 
    {
        return isEncounter(num) ? encountersList.get(num - 1).toString() : "\nNo such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters() 
    {
        StringBuilder s = new StringBuilder("\n************ All Encounters ************\n");
        encountersList.forEach(encounter -> s.append(encounter).append("\n"));
        return s.length() == 0 ? "No encounters available" : s.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
    private void setupShips() 
    {
        Ship victory = new ManOWar("Victory", "Alan Aikin", 500, 3, 3, 30);
        Ship sophie = new Frigate("Sophie", "Ben Baggins", 160, 8, 16, true);
        Ship endeavour = new ManOWar("Endeavour", "Col Cannon", 300, 4, 2, 20);
        Ship arrow = new Sloop("Arrow", "Dan Dare", 150, 5, true);
        Ship belerophon = new ManOWar("Belerophon", "Ed Evans", 500, 8, 3, 50);
        Ship surprise = new Frigate("Surprise", "Fred Fox", 100, 6, 10, false);
        Ship jupiter = new Frigate("Jupiter", "Gil Gamage", 200, 7, 20, false);
        Ship paris = new Sloop("Paris", "Hal Henry", 200, 5, true);
        Ship beast = new Sloop("Beast", "Ian Idle", 400, 5, false);
        Ship athena = new Sloop("Athena", "John Jones", 100, 5, true);
        shipsHashMap.put("victory", victory);
        shipsHashMap.put("sophie", sophie);
        shipsHashMap.put("endeavour", endeavour);
        shipsHashMap.put("arrow", arrow);
        shipsHashMap.put("belerophon", belerophon);
        shipsHashMap.put("surprise", surprise);
        shipsHashMap.put("jupiter", jupiter);
        shipsHashMap.put("paris", paris);
        shipsHashMap.put("beast", beast);
        shipsHashMap.put("athena", athena);
    }
     
    private void setupEncounters() 
    {
        Encounter encounter1 = new Encounter(1, EncounterType.BATTLE, "Trafalgar", 3, 300);
        Encounter encounter2 = new Encounter(2, EncounterType.SKIRMISH, "Belle Isle", 3, 120);
        Encounter encounter3 = new Encounter(3, EncounterType.BLOCKADE, "Brest", 3, 150);
        Encounter encounter4 = new Encounter(4, EncounterType.BATTLE, "St Malo", 9, 200);
        Encounter encounter5 = new Encounter(5, EncounterType.BLOCKADE, "Dieppe", 7, 90);
        Encounter encounter6 = new Encounter(6, EncounterType.SKIRMISH, "Jersey", 8, 45);
        Encounter encounter7 = new Encounter(7, EncounterType.BLOCKADE, "Nantes", 6, 130);
        Encounter encounter8 = new Encounter(8, EncounterType.BATTLE, "Finisterre", 4, 100);
        Encounter encounter9 = new Encounter(9, EncounterType.SKIRMISH, "Biscay", 5, 200);
        Encounter encounter10 = new Encounter(10, EncounterType.BATTLE, "Cadiz", 1, 250);
        encountersList.add(encounter1);
        encountersList.add(encounter2);
        encountersList.add(encounter3);
        encountersList.add(encounter4);
        encountersList.add(encounter5);
        encountersList.add(encounter6);
        encountersList.add(encounter7);
        encountersList.add(encounter8);
        encountersList.add(encounter9);
        encountersList.add(encounter10);
    }
        
    // Useful private methods to "get" objects from collections/maps
        
    // Get a ship for an encounter
    private Ship getShipForEncounter(Encounter enc) 
    {
        for (Ship ship : shipsHashMap.values()) {
            if (ship.getState() == ShipState.ACTIVE && ship.canFight(enc.getType())) {
                return ship;
            }
        }
        return null;
    }

    //*******************************************************************************
    //*******************************************************************************
  
    /************************ Task 3 ************************************************/

    
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename)
    { 
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                readEncounter(line);
                line = reader.readLine();
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname) 
    {
        try (FileOutputStream fos = new FileOutputStream(fname);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void readEncounter(String line) {
        StringTokenizer st = new StringTokenizer (line, ","); // comma-separated data
        int encNo = Integer.parseInt(st.nextToken());
        EncounterType encType = EncounterType.valueOf(st.nextToken().toUpperCase());
        String encName = st.nextToken();
        int encLevel = Integer.parseInt(st.nextToken());
        int encPrize = Integer.parseInt(st.nextToken());
        encountersList.add(new Encounter(encNo, encType, encName, encLevel, encPrize));
    }

    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname) 
    {
        try (FileInputStream fis = new FileInputStream(fname);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SeaBattles) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
 
}



