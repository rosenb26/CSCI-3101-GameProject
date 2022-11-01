
import java.util.Random;

public class Enemy extends Encounter {

    public Enemy() {
        String[] names = {"Aberration", "Beast", "Celestial", "Construct", "Dragon", "Elemental", "Fey", "Fiend", "Giant",
            "Humanoid", "Monstrosity", "Ooze", "Plant", "Undead"};

        Random rng = new Random();
        this.setName(names[rng.nextInt(names.length)]);
        this.setAttributes();
        this.setExperience(rng.nextInt(100) + 1);

    }
}
