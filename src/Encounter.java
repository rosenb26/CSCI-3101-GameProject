
import java.util.Random;

public class Encounter {

    private String name;
    private int attack, speed, defense, life, experience;

    public Encounter() {

    }

    public Encounter(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes() {
        Random rng = new Random();

        this.attack = rng.nextInt(100) + 1;
        this.speed = rng.nextInt(100) + 1;
        this.defense = rng.nextInt(100) + 1;
        this.life = rng.nextInt(100) + 1;

    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return this.life;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int n) {
        this.defense = n;
    }

    public void setAttack(int n) {
        this.attack = n;
    }

    public void setSpeed(int n) {
        this.speed = n;
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getName() {
        return this.name;
    }

    public void levelUp() {
        this.attack++;
        this.defense++;
        this.speed++;
        this.life++;
    }
}
