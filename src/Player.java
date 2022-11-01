
import java.util.ArrayList;

public class Player extends Encounter {

    private int freePasses;
    private int stepsTaken;
    private int highestLevel;
    private ArrayList<Item> itemInventory;
    private ArrayList<Encounter> victims;

    public Player(String name, int freePasses) {

        this.setName(name);
        this.setAttributes();

        this.freePasses = freePasses;
        this.itemInventory = new ArrayList<>();
        this.victims = new ArrayList<>();
    }

    public void addVictim(Encounter monster) {
        this.victims.add(monster);
    }

    public void addItem(Item thing) {
        System.out.println("Oh wow! You have stumbled upon an item by the name of " + thing.getName()
                + " and it has been added to your inventory.");
        this.analyzeItem(thing);
        this.itemInventory.add(thing);
    }

    public void analyzeItem(Item thing) {
        String name = thing.getName();
        if (name.equals("Armor")) {
            this.setDefense((int) (this.getDefense() * 1.1));
        }
        else if (name.equals("Potion")) {
            this.setLife((int) (this.getLife() * 1.25));
        }
        else if (name.equals("Weapon")) {
            this.setAttack((int) (this.getAttack() * 1.2));
        }
        else if (name.equals("Bullet")) {
            this.setSpeed((int) (this.getSpeed() * 2));
        }
        else if (name.equals("book")) {
            this.setExperience((int) (this.getExperience() + 10));
        }

    }

    public int getHighestLevel() {
        return this.highestLevel;
    }

    public void incrementLevel() {
        this.highestLevel++;
    }

    public int getStepsTaken() {
        return this.stepsTaken;
    }

    public ArrayList<Encounter> getVictims() {
        return this.victims;
    }

    public String getInventory() {
        String result = "";
        if (this.itemInventory.isEmpty()) {
            return "None";
        }
        else {
            for (Item x : this.itemInventory) {
                result += x.getName() + ", ";
            }
        }
        return result.substring(0, result.length() - 2);
    }

    public int getFreePasses() {
        return this.freePasses;
    }

    public void setFreePasses(int n) {
        this.freePasses--;
    }

    public void incrementSteps() {
        this.stepsTaken++;
    }
}
