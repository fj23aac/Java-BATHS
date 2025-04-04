package wars;

/**
 * Class representing a Frigate ship
 * @author TEAM85
 * @version 1.2
 */
public class Frigate extends Ship {
    private int cannons;
    private boolean hasPinnace;

    public Frigate(String name, String captain, int commissionFee, int skillLevel, int cannons, boolean hasPinnace) {
        super(name, captain, commissionFee, skillLevel);
        this.cannons = cannons;
        this.hasPinnace = hasPinnace;
    }

    public boolean canFight(EncounterType type) {
        return type == EncounterType.BATTLE || type == EncounterType.SKIRMISH || (type == EncounterType.BLOCKADE && hasPinnace);
    }

    public String toString() {
        return super.toString() + " [Cannons: " + cannons + ", Pinnace: " + (hasPinnace ? "Yes" : "No") + "]";
    }
}
