
public class Cell<E> {

    private E contents;
    private Cell nextCell;
    private Cell previousCell;

    public Cell(E contents) {
        this.contents = contents;
    }

    public Cell getNextCell() {
        return this.nextCell;
    }

    public E getContents() {
        return this.contents;
    }

    public void setContents(E contents) {
        this.contents = contents;
    }

    public void setNextCell(Cell next) {
        this.nextCell = next;
    }

    public void setPreviousCell(Cell previous) {
        this.previousCell = previous;
    }

    public Cell getPreviousCell() {
        return this.previousCell;
    }

}
