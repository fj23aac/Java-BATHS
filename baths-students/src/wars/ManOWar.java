package wars;

/**
 * Class representing a Man-O-War ship
 * @author TEAM85
 * @version 1.2
 */
public class ManOWar extends Ship {
    private int decks;
    private int marines;

    public ManOWar(String name, String captain, int commissionFee, int skillLevel, int decks, int marines) {
        super(name, captain, commissionFee, skillLevel);
        this.decks = decks;
        this.marines = marines;
    }

    public boolean canFight(EncounterType type) {
        return type == EncounterType.BATTLE || type == EncounterType.BLOCKADE;
    }

    public String toString() {
        return super.toString() + " [Decks: " + decks + ", Marines: " + marines + "]";
    }
}
