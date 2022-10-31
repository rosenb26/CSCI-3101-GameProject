
import java.util.ArrayList;
import java.util.Random;

public class Player extends Encounter {

    private int freePasses;
    private int maxLife;
    private int stepsTaken;
    private int highestLevel;
    private ArrayList<Item> itemInventory;
    private ArrayList<Encounter> victims;

    public Player(String name, int freePasses) {
        Random rng = new Random();

        this.setName(name);
        this.setAttributes();
        this.maxLife = (int) (this.getLife() * 1.25);

        this.freePasses = freePasses;
        this.itemInventory = new ArrayList<>();
        this.victims = new ArrayList<>();
    }
    
    public void addVictim(Encounter monster){
        this.victims.add(monster);
    }
    
    public void addItem(Item thing){
        this.itemInventory.add(thing);
    }
    
    public int getHighestLevel(){
        return this.highestLevel;
    }
    
    public void incrementLevel(){
        this.highestLevel++;
    }
    
    public int getStepsTaken(){
        return this.stepsTaken;
    }
    
    public ArrayList<Encounter> getVictims(){
        return this.victims;
    }
    
    public String getInventory(){
        String result = "";
        if(this.itemInventory.isEmpty()){
            return "None";
        }
        else{
            for(Item x: this.itemInventory){
                result += x.getName() + ", ";
            }
        }
        return result.substring(0, result.length() - 2);
    }
    
    public int getFreePasses(){
        return this.freePasses;
    }
    
    public void setFreePasses(int n){
        this.freePasses--;
    }
    
    public void incrementSteps(){
        this.stepsTaken++;
    }
}
