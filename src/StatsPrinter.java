
public class StatsPrinter {

    public void printDungeonStats(Dungeon dungeon) {
        System.out.printf("\n%-34s%d", "Number of rooms in dungeon:", dungeon.getSize());
        System.out.printf("\n%-34s%d", "Number of monsters in dungeon:", this.getNumberOf(dungeon, "Enemy"));
        System.out.printf("\n%-34s%d", "Number of items in dungeon:", this.getNumberOf(dungeon, "Item"));
        System.out.printf("\n%-34s%d\n", "Number of empty rooms in dungeon:", this.getNumberOf(dungeon, "Empty"));
    }

    public int getNumberOf(Dungeon d, String s) {
        int counter = 0;
        for (int i = 0; i < d.getSize(); i++) {
            Encounter e = (Encounter) d.get(i);

            if (s.equals("Empty")) {
                if (e.getName().equals(s)) {
                    counter++;
                }
            }
            else if (e.getClass().getSimpleName().equals(s)) {
                counter++;
            }
        }
        return counter;
    }

    public void printStatsComparison(Encounter first, Encounter second) {
        int firstWidth = first.getName().length();
        int secondWidth = second.getName().length();
        System.out.printf("%10s|%s|%s\n", "", first.getName(), second.getName());
        this.printLineSeparator(firstWidth, secondWidth);
        System.out.printf("\n%-10s|%" + firstWidth + "d|%" + secondWidth + "d\n", "Attack", first.getAttack(), second.getAttack());
        this.printLineSeparator(firstWidth, secondWidth);
        System.out.printf("\n%-10s|%" + firstWidth + "d|%" + secondWidth + "d\n", "Defense", first.getDefense(), second.getDefense());
        this.printLineSeparator(firstWidth, secondWidth);
        System.out.printf("\n%-10s|%" + firstWidth + "d|%" + secondWidth + "d\n", "Speed", first.getSpeed(), second.getSpeed());
        this.printLineSeparator(firstWidth, secondWidth);
        System.out.printf("\n%-10s|%" + firstWidth + "d|%" + secondWidth + "d\n", "Life", first.getLife(), second.getLife());
        this.printLineSeparator(firstWidth, secondWidth);
        System.out.printf("\n%-10s|%" + firstWidth + "d|%" + secondWidth + "d\n", "Experience", first.getExperience(), second.getExperience());

        System.out.println();
    }

    public void printLineSeparator(int m, int n) {
        for (int i = 0; i < m + n + 13; i++) {
            System.out.print("-");
        }
    }

    public void printPlayerProperties(Encounter e) {
        Player player = (Player) e;
        System.out.printf("\n%-25s%d", "Attack:", e.getAttack());
        System.out.printf("\n%-25s%d", "Defense:", e.getDefense());
        System.out.printf("\n%-25s%d", "Life:", e.getLife());
        System.out.printf("\n%-25s%d", "Speed:", e.getSpeed());
        System.out.printf("\n%-25s%d", "Experience:", e.getExperience());
        System.out.printf("\n%-25s%d", "Free passes:", player.getFreePasses());
    }

    public void printPlayerStats(Encounter e) {
        Player player = (Player) e;
        System.out.println("==============================");
        System.out.printf("%-25s%s", "Name:", e.getName());
        System.out.printf("\n%-25s%d\n", "Enemies slain: ", player.getVictims().size());
        System.out.printf("%-24s%d\n", "Highest level completed: ", player.getHighestLevel());
        System.out.printf("%-25s%d\n", "Steps taken:", player.getStepsTaken());
        if (player.getInventory().equals("None")) {
            System.out.printf("%-25s%s", "Items collected:", "none");

        }
        else {
            System.out.printf("%-25s%s", "Items collected:", player.getInventory());
        }
        this.printPlayerProperties(e);
        System.out.println("\n==============================");

    }
}
