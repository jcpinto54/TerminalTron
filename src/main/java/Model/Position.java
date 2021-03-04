package Model;


public class Position{
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position up() {
        return new Position(this.x, this.y - 1);
    }

    public Position right() {
        return new Position(this.x + 1, this.y);
    }

    public Position down() {
        return new Position(this.x, this.y + 1);
    }

    public Position left() {
        return new Position(this.x - 1, this.y);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }


        if (!(o instanceof Position)) {
            return false;
        }

        Position p = (Position) o;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public String toString() {
        String xStr = String.valueOf(x), yStr = String.valueOf(y);
        return xStr + ", " + yStr;
    }
}
