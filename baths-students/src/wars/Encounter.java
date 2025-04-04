package wars;
import java.io.*;

/**
 * Class representing an encounter in the game
 * 
 * @author TEAM85
 * @version 1.4
 */
public class Encounter implements Serializable {
    private int number;
    private EncounterType type;
    private String location;
    private int skillLevel;
    private int prizeMoney;

    public Encounter(int number, EncounterType type, String location, int skillLevel, int prizeMoney) {
        this.number = number;
        this.type = type;
        this.location = location;
        this.skillLevel = skillLevel;
        this.prizeMoney = prizeMoney;
    }

    public int getNumber() {
        return number;
    }

    public EncounterType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public String toString() {
        return "Encounter " + number + ": " + type + " at " + location + " (Skill: " + skillLevel + ", Prize: " + prizeMoney + ")";
    }
}
