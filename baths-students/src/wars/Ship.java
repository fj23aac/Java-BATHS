package wars;
import java.io.*;

/**
 * Abstract class representing a ship in the game
 * 
 * @author TEAM85
 * @version 1.3
 */
public abstract class Ship implements Serializable {
    private String name;
    private String captain;
    private int commissionFee;
    private int skillLevel;
    private ShipState state;

    public Ship(String name, String captain, int commissionFee, int skillLevel) {
        this.name = name;
        this.captain = captain;
        this.commissionFee = commissionFee;
        this.skillLevel = skillLevel;
        this.state = ShipState.RESERVE;
    }

    public String getName() {
        return name;
    }

    public String getCaptain() {
        return captain;
    }

    public int getCommissionFee() {
        return commissionFee;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public abstract boolean canFight(EncounterType type);

    public String toString() {
        return name + " (" + captain + ") - " + state + " [Skill: " + skillLevel + ", Fee: " + commissionFee + "]";
    }
}
