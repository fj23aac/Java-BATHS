package wars;
import java.io.*;
import java.util.HashMap;

/**
 * Class representing the reserve fleet
 * @author TEAM85
 * @version 1.0
 */
public class ReserveFleet implements Serializable {
    private final HashMap<String, Ship> ships;

    public ReserveFleet() {
        ships = new HashMap<>();
    }

    public void addShip(Ship ship) {
        ships.put(ship.getName().toLowerCase(), ship);
    }

    public Ship getShip(String name) {
        return ships.get(name.toLowerCase());
    }

    public String toString() {
        StringBuilder s = new StringBuilder("Reserve Fleet:");
        ships.values().forEach(ship -> s.append("\n").append(ship));
        return s.toString();
    }
}
