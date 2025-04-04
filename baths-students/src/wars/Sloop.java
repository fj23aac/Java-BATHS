package wars;

/**
 * Class representing a Sloop ship
 * @author TEAM85
 * @version 1.2
 */
public class Sloop extends Ship {
    private boolean hasDoctor;

    public Sloop(String name, String captain, int commissionFee, int skillLevel, boolean hasDoctor) {
        super(name, captain, commissionFee, skillLevel);
        this.hasDoctor = hasDoctor;
    }

    public boolean canFight(EncounterType type) {
        return type == EncounterType.BATTLE || type == EncounterType.SKIRMISH;
    }

    public String toString() {
        return super.toString() + " [Doctor: " + (hasDoctor ? "Yes" : "No") + "]";
    }
}
