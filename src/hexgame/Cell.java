package hexgame;

public record Cell(int r, int c, int player) {

    public Cell(int r, int c) {
        this(r, c, 0);
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (r != cell.r) return false;
        return c == cell.c;

    }
}
