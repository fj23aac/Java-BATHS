package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS 
{
    // may have one HashMap and select on stat

    private String admiral;
    private double warChest;
    private HashMap<String, Ship> reserveFleet = new HashMap<>();
    private HashMap<String, Ship> squadron = new HashMap<>();
    private HashMap<Integer, Encounter> encounters = new HashMap<>();
    private List<String> sunkShips = new ArrayList<>();


//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
       this.admiral = adm;
       this.warChest = 1000.0;
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
       this.admiral = admir;
       this.warChest = 1000.0;        
       setupShips();
       readEncounters(filename);
       // setupEncounters();
       // uncomment for testing Task 
       // readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString()
    {
        String s = "";
        s = s + "   State of the game";
        s = s + "   Admiral: " + admiral + "\n";
        s = s + "   Warchest: " + getWarChest() + "\n";
        s = s + "   Defeated: " + isDefeated()+ "\n";
        s = s + "   Ships in squadron: " + getSquadron() + "\n";
        s = s + "   ships in the reserve fleet: " + getReserveFleet() + "\n";
        return s;
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated()
    {
        if (warChest <= 0 && squadron.isEmpty())
        {
            return true;
        }
        return false;
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
        if (!reserveFleet.isEmpty()) // Inverted condition
        {
        return reserveFleet.keySet().toString(); // Return if fleet is not empty
        }
        return "No ships";
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron()
    {
        if (!squadron.isEmpty()) // Inverted condition
        {
        return squadron.keySet().toString(); // Return if squadron is not empty
        }
        return "No ships";
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips()
    {
       if (!sunkShips.isEmpty()) // Inverted condition
        {
        return String.join(", ", sunkShips); // Return if sunkShips is not empty
        }
        return "No ships";
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
        if (!(reserveFleet.isEmpty() && squadron.isEmpty() && sunkShips.isEmpty())) // Inverted condition
        {
        return "Reserve Fleet: " + getReserveFleet() + ", Squadron: " + getSquadron() + ", Sunk Ships: " + getSunkShips(); // Return if any fleet has ships
        }
        return "No ships";
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String nme)
    {
 
        if (squadron.containsKey(nme))
        {
            return squadron.get(nme).toString();
        }
        else if (reserveFleet.containsKey(nme))
        {
            return reserveFleet.get(nme).toString();
        }
        return "\nNo such ship";
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
    public String commissionShip(String nme)
    {
        if (reserveFleet.containsKey(nme)) // Inverted condition
        {
        Ship ship = reserveFleet.get(nme);
        double cost = ship.getCommissionFee();
            if (warChest < cost)
            {
                return "- Not enough money"; // Original return preserved
            }
            reserveFleet.remove(nme);
            squadron.put(nme, ship);
            warChest -= cost;
            ship.setState("active");
            return "Ship commissioned"; // Return if ship is commissioned
        }
        return "- Ship not found";
    }
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String nme)
    {
        return squadron.containsKey(nme); // checks if the squadron map has an entry with the key matching nme
     
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme)
    {
        if (squadron.containsKey(nme))
        {
            Ship ship = squadron.remove(nme);
            ship.setState("reserve");
            reserveFleet.put(nme, ship);
            warChest += ship.getCommissionFee() / 2; // 50% refund on decommission
            return true; // Original return preserved
        }
        return false;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String ref)
    {
        if (reserveFleet.containsKey(ref))
        {
            Ship ship = reserveFleet.get(ref);
            ship.setState("active");
            squadron.put(ref, ship);
            reserveFleet.remove(ref);
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
     public boolean isEncounter(int num)
     {
         return false;
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
       
        if (isEncounter(encNo)) // Inverted condition
     {
        Encounter encounter = encounters.get(encNo);
        Ship bestShip = findBestShip(encounter);
        if (bestShip == null)
        {
            warChest -= encounter.getPrize();
            if (isDefeated())
            {
                return "1-Encounter lost as no ship available. You have been defeated.";
            }
            return "1-Encounter lost as no ship available";
        }
        if (bestShip.getSkillLevel() >= encounter.getDifficulty())
        {
            bestShip.setState("resting");
            warChest += encounter.getPrize();
            return "0-Encounter won by " + bestShip.getName() + ". War Chest: " + warChest;
        }
        else
        {
            bestShip.setState("sunk");
            squadron.remove(bestShip.getName());
            sunkShips.add(bestShip.getName());
            warChest -= encounter.getPrize();
            if (isDefeated())
            {
                return "2-Encounter lost and " + bestShip.getName() + " sunk. You have been defeated.";
            }
            return "2-Encounter lost and " + bestShip.getName() + " sunk";
        }
     }    
        return "Not done";
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
        if (encounters.containsKey(num))
        {
            return encounters.get(num).toString();
        }
        return "\nNo such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
      {
        StringBuilder sb = new StringBuilder();
        for (Encounter e : encounters.values())
        {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString(); // Return encounter details if encounters are not empty
      }
        return "No encounters";
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
       reserveFleet.put("HMS Victory", new Ship("HMS Victory", 5, 200));
       reserveFleet.put("HMS Queen", new Ship("HMS Queen", 3, 150));
     }
     
    private void setupEncounters()
    {
       encounters.put(1, new Encounter(1, "Pirate Battle", 3, 100));
       encounters.put(2, new Encounter(2, "Stormy Seas", 5, 150));
    }
        
    // Useful private methods to "get" objects from collections/maps

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
      
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int difficulty = Integer.parseInt(parts[2].trim());
                double prize = Double.parseDouble(parts[3].trim());
                encounters.put(id, new Encounter(id, name, difficulty, prize));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading encounter file: " + e.getMessage());
        }
        
    }   
 
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   // uses object serialisation 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname)))
        {
            out.writeObject(this);
        }
        catch (IOException e)
        {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {   // uses object serialisation 
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname)))
        {
            return (SeaBattles) in.readObject(); // Attempt to load the game
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Error loading game: " + e.getMessage());
            return null; // Explicitly return null if an error occurs
        }
        return null;
    } 
    
 
}



