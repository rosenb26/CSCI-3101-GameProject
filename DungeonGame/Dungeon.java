
public class Dungeon<E> {

    private Cell head;
    private Cell tail;
    private int size;

    /**
     * Adds an item to the end of the list.
     *
     * @param item A data item of type E.
     */
    public void add(E item) {
        if (this.head == null) {
            this.head = new Cell(item);
        }
        else {

            Cell temp = this.head;
            while (temp.getNextCell() != null && !temp.getNextCell().equals(this.head)) {
                temp = temp.getNextCell();
            }
            Cell newCell = new Cell(item);
            temp.setNextCell(newCell);
            newCell.setNextCell(this.head);
            newCell.setPreviousCell(temp);
            this.tail = newCell;
            this.head.setPreviousCell(this.tail);

        }
        this.size++;
    }

    /**
     * Adds an item to the beginning of the list.
     *
     * @param item The item to be added.
     */
    public void addFirst(E item) {
        Cell temp = new Cell(item);
        if (this.head == null) {//list is empty
            this.head = temp;
        }
        else if (this.tail == null) {//list has exactly 1 element
            this.tail = this.head;
            this.head = temp;
            this.tail.setPreviousCell(this.head);
            this.tail.setNextCell(this.head);
            this.head.setPreviousCell(this.tail);
            this.head.setNextCell(this.tail);

        }
        else {
            temp.setPreviousCell(this.tail);
            this.tail.setNextCell(temp);
            temp.setNextCell(this.head);
            this.head.setPreviousCell(temp);
            this.head = temp;
        }
        this.size++;
    }

    /**
     * Inserts an item into the list at the given index.
     *
     * @param item The item to be inserted.
     * @param index The index of element after insertion.
     */
    public void add(E item, int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            this.addFirst(item);
        }
        else if (index == this.size) {
            this.add(item);
        }
        else {

            int counter = 0;
            Cell temp = this.head;

            while (counter < index - 1) {
                temp = temp.getNextCell();
                counter++;
            }
            Cell newCell = new Cell(item);
            newCell.setNextCell(temp.getNextCell());
            temp.getNextCell().setPreviousCell(newCell);
            temp.setNextCell(newCell);
            newCell.setPreviousCell(temp);

            this.size++;
        }
    }

    /**
     * Removes the first item in the list.
     *
     * @return The removed item.
     */
    public E removeFirst() {
        if (this.head == null) {
            return null;
        }
        E temp = (E) this.head.getContents();

        if (this.size == 1) {
            this.head = null;
        }
        else if (this.size == 2) {

            this.tail.setNextCell(null);
            this.tail.setPreviousCell(null);
            this.head = this.tail;
            this.tail = null;
        }
        else {
            Cell newHead = this.head.getNextCell();
            newHead.setPreviousCell(this.tail);
            this.tail.setNextCell(newHead);
            this.head = newHead;
        }
        this.size--;

        return temp;

    }

    /**
     * Removes the last item in the list.
     *
     * @return The removed item.
     */
    public E removeLast() {
        if (this.size == 0) {
            return null;
        }
        if (this.size == 1) {
            return this.removeFirst();
        }
        if (this.size == 2) {
            E temp = (E) this.tail.getContents();
            this.head.setNextCell(null);
            this.head.setPreviousCell(null);
            this.size--;
            return temp;
        }
        E oldTail = (E) this.tail.getContents();
        Cell temp = this.tail.getPreviousCell();
        temp.setNextCell(this.head);
        this.head.setPreviousCell(temp);
        this.tail = temp;
        this.size--;
        return oldTail;

    }

    /**
     * Removes the item at the given index.
     *
     * @param index The index at which the item is to be removed.
     * @return The removed item.
     */
    public E removeByIndex(int index) {
        if (index < 0 || index >= size || this.head == null) {
            return null;
        }
        if (index == 0) {
            return this.removeFirst();
        }
        if (index == this.size - 1) {
            return this.removeLast();
        }
        Cell temp = this.head;
        int counter = 0;
        while (counter < index - 1) {
            temp = temp.getNextCell();
            counter++;
        }
        E contents = (E) temp.getNextCell().getContents();
        Cell newNext = temp.getNextCell().getNextCell();
        temp.setNextCell(newNext);
        newNext.setPreviousCell(temp);
        this.size--;
        return contents;
    }

    /**
     * Removes the given item.
     *
     * @param item The item to be removed.
     * @return
     */
    public E removeByElement(E item) {
        if (this.head == null) {
            return null;
        }
        int counter = 0;
        Cell temp = this.head;
        while (temp != null) {
            if (temp.getContents().equals(item)) {
                return this.removeByIndex(counter);
            }
            temp = temp.getNextCell();
            counter++;
        }
        return null;
    }

    /**
     * Checks if the list contains a given item.
     *
     * @param item
     * @return True if the list contains the item, and false otherwise.
     */
    public boolean contains(E item) {
        Cell temp = this.head;
        while (temp != null) {
            if (temp.getContents().equals(item)) {
                return true;
            }
            temp = temp.getNextCell();
        }
        return false;
    }

    /**
     * Checks if the list is empty, i.e. holds no items.
     *
     * @return True if the list is empty, and false if the list contains at
     * least one item.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    public E get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return (E) this.head.getContents();
        }
        if (index == this.size - 1) {
            return (E) this.tail.getContents();
        }
        Cell temp = this.head;
        int counter = 0;
        while (counter < index) {
            temp = temp.getNextCell();
            counter++;
        }
        return (E) temp.getContents();
    }

    public void set(E item, int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        int position = 0;
        Cell temp = this.head;
        while (position < index) {
            temp = temp.getNextCell();
            position++;
        }
        temp.setContents(item);
    }

    public void swapPlayerWithNeighbor(String direction) {
        int playerPosition = this.getPlayerPosition();
        int sizeBefore = this.size;
        E temp = this.removeByIndex(playerPosition);

        if (direction.equalsIgnoreCase("L")) {
            if(playerPosition != 0){
                this.add(temp, playerPosition - 1);
            }
            else{
                this.add(temp, this.size);
            }
        }
        else{
            if(playerPosition != sizeBefore - 1){
                this.add(temp, playerPosition +1);
            }
            else{
                this.add(temp, 0);
            }
        }

    }

    /**
     * Gets the size of the list.
     *
     * @return The size of the list.
     */
    public int getSize() {
        return this.size;
    }

    public void showNeighbors(int n) {
        if (2 * n + 1 > this.size) {
            this.printContents();
        }
        else {
            Cell temp = this.head;

            int position = this.getPlayerPosition();
            for (int i = 0; i < position; i++) {
                temp = temp.getNextCell();
            }
            int counter = 0;
            while (counter < n) {
                temp = temp.getPreviousCell();
                counter++;
            }
            for (int i = 0; i < 2 * n; i++) {
                System.out.print(((Encounter) temp.getContents()).getName() + " <==> ");
                temp = temp.getNextCell();
            }
            System.out.println(((Encounter) temp.getContents()).getName());
        }
    }

    /**
     * Display the contents of the list with the player in the middle.
     */
    public void printContents() {
        System.out.println();
        if (this.size == 0) {

            System.out.println("List is empty.");

        }
        

        int position = this.getPlayerPosition();
        Cell temp = this.head;
        for (int i = 0; i < position; i++) {
            temp = temp.getNextCell();
        }
        for (int i = 0; i <= this.size / 2; i++) {
            temp = temp.getNextCell();
        }

        for (int i = 0; i < this.size; i++) {

            Encounter encounter = (Encounter) temp.getContents();
            System.out.print(encounter.getName());

            temp = temp.getNextCell();

            if (i < this.size - 1) {
                System.out.print(" <==> ");
            }

        }
        System.out.println();
    }

    public int getPlayerPosition() {
        int position = 0;
        Cell temp = this.head;
        String name = temp.getContents().getClass().getSimpleName();
        while (!name.equals("Player")) {
            temp = temp.getNextCell();
            name = temp.getContents().getClass().getSimpleName();
            position++;
        }
        return position;
    }

    public int getExitPosition() {
        int position = 0;
        Cell temp = this.head;

        while (true) {
            Encounter e = (Encounter) temp.getContents();
            String name = e.getName();
            if (name.equals("Exit")) {
                break;
            }
            temp = temp.getNextCell();
            position++;
        }
        return position;
    }

    /**
     * Display the contents of the list in reverse order.
     */
    public void printReverse() {
        if (this.size == 0) {

            System.out.println("List is empty.");

        }
        else if (this.size == 1) {
            this.printContents();
        }
        else {
            Cell temp = this.tail;

            while (temp != null) {
                System.out.print(temp.getContents());
                temp = temp.getPreviousCell();
                if (temp.equals(this.tail)) {
                    break;
                }
                if (temp != null) {
                    System.out.print(" <==> ");
                }

            }
            System.out.println();
        }
    }

    

}
