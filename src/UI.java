
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class UI {

    private Dungeon dungeon;
    private ArrayList<Encounter> encounters;
    private int playerPosition;
    private int currentLevel;
    private boolean monsterDefeated;
    StatsPrinter statistician;
    Scanner reader;

    public UI() {
        this.reader = new Scanner(System.in);
        this.statistician = new StatsPrinter();
    }

    public void printWelcomeMessage() {
        System.out.print("Welcome to the dungeon!\n\nWhat is your name? ");
        String name = reader.nextLine();
        while (!this.validateName(name)) {
            System.out.println("\nSorry, but that name is unavailable. Please choose a different name.");
            name = this.reader.nextLine();
        }
        int number = 0;
        while (true) {
            System.out.print("\nHow many cells would you like in your dungeon? (minimum 2) ");
            try {
                number = Integer.parseInt(reader.nextLine());
                if (number >= 2) {
                    break;
                }
                System.out.println("Number of cells must be at least 2.");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        this.makeDungeon(number, name);
    }

    public boolean validateName(String name) {
        ArrayList<String> names = new ArrayList(Arrays.asList("Aberration", "Beast", "Celestial", "Construct", "Dragon", "Elemental", "Fey", "Fiend", "Giant",
                "Humanoid", "Monstrosity", "Ooze", "Plant", "Undead", "Armor", "Potion", "Ring", "Single use", "Weapon"));
        if (names.contains(name)) {
            return false;
        }
        return true;
    }

    public void printMainMenu() {
        while (true) {
            System.out.println("\nMain Menu:\n");
            System.out.println("1 - Print dungeon");
            System.out.println("2 - Find exit");
            System.out.println("3 - Get stats");
            System.out.println("4 - Move");
            System.out.println("5 - Show neighboring cells");
            System.out.println("q - Quit game\n");

            String choice = this.reader.nextLine();
            if (choice.equalsIgnoreCase("q")) {
                break;
            }

            this.analyzeMainMenuChoice(choice);
        }
        System.out.println("\nThanks for playing!");

    }

    public void analyzeMainMenuChoice(String choice) {
        if (choice.equals("1")) {
            this.printDungeon();
        }
        else if (choice.equals("2")) {
            int exitPosition = this.exitPosition();

            int cellDistance = Math.abs(exitPosition - playerPosition);
            if (cellDistance > this.encounters.size() / 2) {
                cellDistance -= this.encounters.size();
            }

            System.out.print("\nThe exit is located " + cellDistance);
            if (Math.abs(cellDistance) == 1) {
                System.out.print(" cell ");
            }
            else {
                System.out.print(" cells ");
            }
            System.out.print("to your ");
            if (exitPosition > this.playerPosition) {

                System.out.println("right.");

            }
            else {

                System.out.println("left.");

            }
        }
        else if (choice.equals("3")) {
            String whichStats;
            while (true) {
                System.out.println("\n1 - Dungeon stats");
                System.out.println("2 - Player stats");
                System.out.println("x - back to main menu");
                whichStats = this.reader.nextLine();
                if (!(whichStats.equals("1") || whichStats.equals("2") || whichStats.equals("x"))) {
                    System.out.println("\nInvalid selection. Please try again.\n");
                }
                else {
                    break;
                }

            }

            if (whichStats.equals("1")) {
                this.statistician.printDungeonStats(this.dungeon);
            }
            else if (whichStats.equals("2")) {
                this.statistician.printPlayerStats(this.encounters.get(this.playerPosition));
            }
            else {
                this.printMainMenu();
                return;
            }
        }
        else if (choice.equals("4")) {
            String direction;
            while (true) {
                System.out.println("L - move left");
                System.out.println("R - move right");
                System.out.println("x - back to main menu");
                direction = this.reader.nextLine();
                if (direction.equalsIgnoreCase("x")) {
                    this.printMainMenu();
                    return;
                }
                if (!(direction.equalsIgnoreCase("L") || direction.equalsIgnoreCase("R"))) {
                    System.out.println("\nInvalid selection. Please try again.\n");
                }
                else {
                    break;
                }
            }
            this.movePlayer(direction);
        }
        else if (choice.equals("5")) {
            while (true) {
                System.out.print("How many nearby cells (on each side of you) would you like to see? ");

                try {
                    int neighbors = Integer.valueOf(this.reader.nextLine());
                    if (neighbors <= 0) {
                        System.out.print("Enter a positive integer ");
                    }
                    else {
                        this.dungeon.showNeighbors(neighbors);
                        break;
                    }
                }
                catch (NumberFormatException e) {
                    System.out.print("Invalid input. You can try again ");
                }
                System.out.print("or press 'x' to return to the Main Menu: ");
                if (this.reader.nextLine().equals("x")) {
                    break;
                }
            }
        }
    }

    public int exitPosition() {
        int position = -1;
        for (int i = 0; i < this.encounters.size(); i++) {
            Encounter e = this.encounters.get(i);
            if (e.getName().equals("Exit")) {
                position = i;
                break;
            }
        }
        return position;
    }

    public void makeDungeon(int numberOfCells, String playerName) {
        this.currentLevel++;

        ArrayList<Encounter> temp = new ArrayList<>();
        if (currentLevel > 1) {
            temp.add(this.encounters.get(this.playerPosition));
        }
        else {
            temp.add(new Player(playerName.toUpperCase(), numberOfCells / 3));
        }
        Encounter exit = new Encounter("Exit");
        temp.add(exit);

        for (int i = 0; i < numberOfCells / 4; i++) {
            temp.add(new Enemy());
            temp.add(new Item());
        }
        while (temp.size() < numberOfCells) {
            Encounter empty = new Encounter("Empty");
            temp.add(empty);

        }

        Dungeon<Encounter> dungeon = new Dungeon<>();
        this.encounters = new ArrayList<>();

        Random rng = new Random();
        for (int i = 0; i < numberOfCells; i++) {
            int number = rng.nextInt(temp.size());
            Encounter nextEncounter = temp.get(number);
            dungeon.add(nextEncounter);
            this.encounters.add(nextEncounter);
            if (nextEncounter.getClass().getSimpleName().equals("Player")) {
                this.playerPosition = i;
            }
            temp.remove(number);
        }
        this.dungeon = dungeon;
    }

    public void movePlayer(String direction) {
        String name;
        int toCell;

        if (direction.equalsIgnoreCase("l")) {
            if (this.playerPosition == 0) {
                name = this.encounters.get(this.encounters.size() - 1).getClass().getSimpleName();
                toCell = this.encounters.size() - 1;
            }
            else {
                name = this.encounters.get(this.playerPosition - 1).getClass().getSimpleName();
                toCell = this.playerPosition - 1;
            }

        }
        else {
            if (this.playerPosition == this.encounters.size() - 1) {
                name = this.encounters.get(0).getClass().getSimpleName();
                toCell = 0;
            }
            else {
                name = this.encounters.get(this.playerPosition + 1).getClass().getSimpleName();
                toCell = this.playerPosition + 1;
            }
        }
        if (name.equals("Encounter")) {
            if (this.encounters.get(toCell).getName().equals("Exit")) {
                this.exit();
            }
            else if (this.encounters.get(toCell).getName().equals("Empty")) {
                this.dungeon.swapPlayerWithNeighbor(direction);
                this.swapNeighborsInArray(direction);
            }
        }
        else {
            if (name.equals("Enemy")) {
                this.enemyMenu(toCell);
                if (this.monsterDefeated) {
                    this.encounters.remove(toCell);
                    this.dungeon.removeByIndex(toCell);
                    this.monsterDefeated = false;
                    return;
                }
            }
            else if (name.equals("Item")) {
                this.pickUpItem(toCell);
                this.encounters.set(toCell, new Encounter("Empty"));
                this.dungeon.set(new Encounter("Empty"), toCell);

                this.dungeon.swapPlayerWithNeighbor(direction);

                this.swapNeighborsInArray(direction);

            }
            if (direction.equalsIgnoreCase("L")) {
                this.dungeon.swapPlayerWithNeighbor("L");
                this.swapNeighborsInArray("L");
            }
            else {
                this.dungeon.swapPlayerWithNeighbor("R");
                this.swapNeighborsInArray("R");
            }

        }

    }

    public void swapNeighborsInArray(String direction) {
        Encounter temp = this.encounters.get(this.playerPosition);
        if (direction.equalsIgnoreCase("L")) {
            if (this.playerPosition != 0) {
                this.encounters.set(this.playerPosition, this.encounters.get(this.playerPosition - 1));
                this.encounters.set(this.playerPosition - 1, temp);
                this.playerPosition--;
            }
            else {
                this.encounters.set(0, this.encounters.get(this.encounters.size() - 1));
                this.encounters.set(this.encounters.size() - 1, temp);
                this.playerPosition = this.encounters.size() - 1;
            }
        }
        else {
            if (this.playerPosition != this.encounters.size() - 1) {
                this.encounters.set(this.playerPosition, this.encounters.get(this.playerPosition + 1));
                this.encounters.set(this.playerPosition + 1, temp);
                this.playerPosition++;
            }
            else {
                this.encounters.set(this.encounters.size() - 1, this.encounters.get(0));
                this.encounters.set(0, temp);
                this.playerPosition = 0;

            }
        }
        Player player = (Player) this.encounters.get(this.playerPosition);
        player.incrementSteps();

    }

    public void pickUpItem(int cell) {
        Item thing = (Item) this.encounters.get(cell);
        Player player = (Player) this.encounters.get(this.playerPosition);
        player.addItem(thing);

    }

    public void exit() {
        System.out.println("Congratulations! You have found the exit!");
        System.out.println("You will now move to level " + (this.currentLevel + 1)
                + " where you will have " + (2 * this.encounters.size()) + " cells!");
        Player player = (Player) this.encounters.get(this.playerPosition);
        player.incrementSteps();
        player.incrementLevel();
        this.makeDungeon(2 * this.encounters.size(), this.encounters.get(this.playerPosition).getName());
    }

    public void enemyMenu(int cell) {
        Encounter monster = this.encounters.get(cell);
        int choice;
        System.out.println("\nOh noes! You have encountered an enemy by the name of \"" + monster.getName() + "\". "
                + "How would you like to proceed?\n");
        while (true) {

            System.out.println("1 - Compare your stats to " + monster.getName() + "'s");
            System.out.println("2 - Fight! ");

            Player player = (Player) this.encounters.get(this.playerPosition);
            boolean freePasses = false;
            if (player.getFreePasses() > 0) {
                System.out.println("3 - Avoid " + monster.getName() + " at a cost of 1 free pass (you have "
                        + player.getFreePasses() + " free pass(es) available).");
                freePasses = true;
            }
            System.out.println("4 - Back to main menu\n");

            try {
                choice = Integer.valueOf(this.reader.nextLine());
                if (freePasses) {
                    if (choice < 1 || choice > 4) {
                        System.out.println("\nInvalid choice. You must choose one of the three options!");
                    }
                    else if (choice == 1) {
                        this.statistician.printStatsComparison(this.encounters.get(this.playerPosition), monster);
                        continue;

                    }
                    else if (choice == 4) {
                        this.printMainMenu();
                        return;
                    }
                    else {
                        break;
                    }
                }
                else {
                    if (choice < 1 || choice > 2) {
                        System.out.println("\nInvalid choice. You must choose one of the two options!");
                    }
                    else {
                        break;
                    }
                }
            }
            catch (NumberFormatException e) {
                System.out.println("\nEnter 1, 2, or 3 in accordance with your choice.");
            }
        }

        this.analyzeEnemyChoice(choice, monster, cell);

    }

    public void analyzeEnemyChoice(int choice, Encounter e, int cell) {
        if (choice == 2) {
            this.performCombatMath(e, this.encounters.get(this.playerPosition), cell);
        }
        else if (choice == 3) {
            Player player = (Player) this.encounters.get(this.playerPosition);
            player.setFreePasses(player.getFreePasses() - 1);
        }
    }

    public void performCombatMath(Encounter first, Encounter second, int cell) {
        /*Determine who the attacker is according to the greater speed.
        If the speeds are equal, flip a coin.*/
        Encounter attacker;
        Encounter defender;
        if (first.getSpeed() > second.getSpeed()) {
            attacker = first;
            defender = second;
        }
        else if (second.getSpeed() > first.getSpeed()) {
            attacker = second;
            defender = first;
        }
        else {
            Random rng = new Random();
            int coinFlip = rng.nextInt(2);
            if (coinFlip == 0) {
                attacker = first;
                defender = second;

            }
            else {
                attacker = second;
                defender = first;
            }
        }
        int damage = attacker.getAttack() - defender.getDefense();
        if (damage < 1) {
            damage = 1;
        }
        defender.setLife(defender.getLife() - damage);
        if (defender.getLife() <= 0) {
            if (defender.getName().equals(this.encounters.get(this.playerPosition).getName())) {
                this.gameOverMenu();
            }
            else {
                String monsterName = defender.getName();
                int monsterExperience = defender.getExperience();
                System.out.println("Congratulations! You have defeated " + monsterName + " and you have gained " + monsterExperience + " experience.");
                attacker.setExperience(attacker.getExperience() + monsterExperience);
                Player player = (Player) this.encounters.get(this.playerPosition);
                player.addVictim(defender);
                this.monsterDefeated = true;
            }
        }

    }

    public void gameOverMenu() {
        System.out.println("Oh no! Game over!\nHere are your stats:\n");
        this.statistician.printPlayerStats(this.encounters.get(this.playerPosition));
        System.exit(0);
    }

    public void printDungeon() {
        this.dungeon.printContents();
    }

}
