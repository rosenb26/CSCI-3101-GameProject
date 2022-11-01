
import java.util.Random;


public class Item extends Encounter {

    private String[] items;
    
    public Item(){
        this.items = new String[]{"Armor", "Potion", "Bullet", "Book", "Weapon"};
        this.setName(this.getRandomItem());
    }
    
    public String getRandomItem(){
        Random rng = new Random();
        return this.items[rng.nextInt(this.items.length)];
    }
}
